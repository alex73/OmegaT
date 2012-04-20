/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2010 Alex Buloichik
               Home page: http://www.omegat.org/
               Support center: http://groups.yahoo.com/group/OmegaT/

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 **************************************************************************/
package org.omegat.core.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.omegat.core.segmentation.Segmenter;
import org.omegat.util.Language;
import org.omegat.util.OConsts;
import org.omegat.util.StringUtil;
import org.omegat.util.TMXReader2;
import org.omegat.util.TMXWriter2;

/**
 * Class for store data from project_save.tmx.
 * 
 * Orphaned or non-orphaned translation calculated by RealProject.
 * 
 * @author Alex Buloichik (alex73mail@gmail.com)
 */
public class ProjectTMX {
    protected static final String PROP_FILE = "file";
    protected static final String PROP_ID = "id";
    protected static final String PROP_PREV = "prev";
    protected static final String PROP_NEXT = "next";
    protected static final String PROP_PATH = "path";

    /**
     * Storage for default translations for current project.
     * 
     * It must be used with synchronization around ProjectTMX.
     */
    final Map<String, TMXEntry> defaults;

    /**
     * Storage for alternative translations for current project.
     * 
     * It must be used with synchronization around ProjectTMX.
     */
    final Map<EntryKey, TMXEntry> alternatives;
    
    final CheckOrphanedCallback checkOrphanedCallback;

    public ProjectTMX(ProjectProperties props, File file, CheckOrphanedCallback callback,
            Map<EntryKey, TMXEntry> sourceTranslations) throws Exception {
        this.checkOrphanedCallback = callback;
        alternatives = new HashMap<EntryKey, TMXEntry>();
        alternatives.putAll(sourceTranslations);
        defaults = new HashMap<String, TMXEntry>();

        if (!file.exists()) {
            // file not exist - new project
            return;
        }

        new TMXReader2().readTMX(
                file,
                props.getSourceLanguage(),
                props.getTargetLanguage(),
                props.isSentenceSegmentingEnabled(),
                false,
                true,
                false,
                new Loader(props.getSourceLanguage(), props.getTargetLanguage(), props.isSentenceSegmentingEnabled()));
    }

    /**
     * It saves current translation into file.
     */
    public void save(ProjectProperties props, String translationFile) throws Exception {
        File newFile = new File(translationFile + OConsts.NEWFILE_EXTENSION);

        // Save data into '*.new' file
        exportTMX(props, newFile, false, false, true);

        File backup = new File(translationFile + ".bak");
        File orig = new File(translationFile);
        if (backup.exists()) {
            if (!backup.delete()) {
                throw new IOException("Error delete backup file");
            }
        }

        // Rename existing project file in case a fatal error
        // is encountered during the write procedure - that way
        // everything won't be lost
        if (orig.exists()) {
            if (!orig.renameTo(backup)) {
                throw new IOException("Error rename old file to backup");
            }
        }

        // Rename new file into TMX file
        if (!newFile.renameTo(orig)) {
            throw new IOException("Error rename new file to tmx");
        }
    }

    public void exportTMX(ProjectProperties props, File outFile, final boolean forceValidTMX,
            final boolean levelTwo, final boolean useOrphaned) throws Exception {
        TMXWriter2 wr = new TMXWriter2(outFile, props.getSourceLanguage(), props.getTargetLanguage(),
                props.isSentenceSegmentingEnabled(), levelTwo, forceValidTMX);
        try {
            Map<String, TMXEntry> tempDefaults = new TreeMap<String, TMXEntry>();
            Map<EntryKey, TMXEntry> tempAlternatives = new TreeMap<EntryKey, TMXEntry>();

            synchronized (this) {
                if (useOrphaned) {
                    // fast call - just copy
                    tempDefaults.putAll(defaults);
                    tempAlternatives.putAll(alternatives);
                } else {
                    // slow call - copy non-orphaned only
                    for(Map.Entry<String, TMXEntry> en:defaults.entrySet()) {
                        if (checkOrphanedCallback.existSourceInProject(en.getKey())) {
                            tempDefaults.put(en.getKey(), en.getValue());
                        }
                    }
                    for(Map.Entry<EntryKey, TMXEntry> en:alternatives.entrySet()) {
                        if (checkOrphanedCallback.existEntryInProject(en.getKey())) {
                            tempAlternatives.put(en.getKey(), en.getValue());
                        }
                    }
                }
            }

            wr.writeComment(" Default translations ");
            for (Map.Entry<String, TMXEntry> en : new TreeMap<String, TMXEntry>(tempDefaults).entrySet()) {
                wr.writeEntry(en.getKey(), en.getValue().translation, en.getValue(), null);
            }

            wr.writeComment(" Alternative translations ");
            for (Map.Entry<EntryKey, TMXEntry> en : new TreeMap<EntryKey, TMXEntry>(tempAlternatives).entrySet()) {
                EntryKey k = en.getKey();
                wr.writeEntry(en.getKey().sourceText, en.getValue().translation, en.getValue(), new String[] {
                        PROP_FILE, k.file, PROP_ID, k.id, PROP_PREV, k.prev, PROP_NEXT, k.next, PROP_PATH,
                        k.path });
            }
        } finally {
            wr.close();
        }
    }

    /**
     * Get default translation or null if not exist.
     */
    public TMXEntry getDefaultTranslation(String source) {
        synchronized (this) {
            return defaults.get(source);
        }
    }

    /**
     * Get multiple translation or null if not exist.
     */
    public TMXEntry getMultipleTranslation(EntryKey ek) {
        synchronized (this) {
            return alternatives.get(ek);
        }
    }

    /**
     * Set new translation.
     */
    public void setTranslation(SourceTextEntry ste, TMXEntry te, boolean isDefault) {
        synchronized (this) {
            if (te == null) {
                if (isDefault) {
                    defaults.remove(ste.getKey().sourceText);
                } else {
                    alternatives.remove(ste.getKey());
                }
            } else {
                if (isDefault) {
                    defaults.put(ste.getKey().sourceText, te);
                } else {
                    alternatives.put(ste.getKey(), te);
                }
            }
        }
    }

    private class Loader implements TMXReader2.LoadCallback {
        private final Language sourceLang;
        private final Language targetLang;
        private final boolean sentenceSegmentingEnabled;

        public Loader(Language sourceLang, Language targetLang,
                boolean sentenceSegmentingEnabled) {
            this.sourceLang = sourceLang;
            this.targetLang = targetLang;
            this.sentenceSegmentingEnabled = sentenceSegmentingEnabled;
        }

        public boolean onEntry(TMXReader2.ParsedTu tu, TMXReader2.ParsedTuv tuvSource,
                TMXReader2.ParsedTuv tuvTarget, boolean isParagraphSegtype) {
            if (tuvSource == null) {
                // source Tuv not found
                return false;
            }
            String changer = null;
            long dt = 0;
            String translation = null;

            if (tuvTarget != null) {
                changer = StringUtil.nvl(tuvTarget.changeid, tuvTarget.creationid, tu.changeid,
                    tu.creationid);
                dt = StringUtil.nvlLong(tuvTarget.changedate, tuvTarget.creationdate, tu.changedate,
                    tu.creationdate);
                translation = tuvTarget.text;
            }

            List<String> sources = new ArrayList<String>();
            List<String> targets = new ArrayList<String>();
            Segmenter.segmentEntries(sentenceSegmentingEnabled && isParagraphSegtype, sourceLang,
                    tuvSource.text, targetLang, translation, sources, targets);

            synchronized (this) {
                for (int i = 0; i < sources.size(); i++) {
                    String segmentSource = sources.get(i);
                    String segmentTranslation = targets.get(i);
                    EntryKey key = createKeyByProps(segmentSource, tu.props);
                    boolean defaultTranslation = key.file == null;
                    TMXEntry te = new TMXEntry(segmentSource, segmentTranslation, changer, dt, tu.note,
                            defaultTranslation);
                    if (defaultTranslation) {
                        // default translation
                        defaults.put(segmentSource, te);
                    } else {
                        // multiple translation
                        alternatives.put(key, te);
                    }
                }
            }
            return true;
        }
    };

    private EntryKey createKeyByProps(String src, Map<String, String> props) {
        return new EntryKey(props.get(PROP_FILE), src, props.get(PROP_ID), props.get(PROP_PREV),
                props.get(PROP_NEXT), props.get(PROP_PATH));
    }

    public interface CheckOrphanedCallback {
        boolean existEntryInProject(EntryKey key);

        boolean existSourceInProject(String src);
    }
}
