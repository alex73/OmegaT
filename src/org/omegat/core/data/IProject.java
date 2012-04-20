/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2008 Alex Buloichik
               2010 Didier Briel
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

import gen.core.filters.Filters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.omegat.core.matching.ITokenizer;
import org.omegat.core.segmentation.SRX;
import org.omegat.core.statistics.StatisticsInfo;
import org.omegat.core.team.IRemoteRepository;
import org.omegat.filters2.master.FilterMaster;

/**
 * Interface for access to loaded project. Each loaded project will be new instance of IProject.
 * 
 * @author Alex Buloichik (alex73mail@gmail.com)
 * @author Didier Briel
 */
public interface IProject {

    /**
     * Save project properties only.
     */
    void saveProjectProperties() throws Exception;

    /**
     * Save project.
     */
    void saveProject();

    /**
     * Close project.
     */
    void closeProject();

    /**
     * Create translated documents.
     */
    void compileProject(String sourcePattern) throws Exception;

    /**
     * Get project properties.
     * 
     * @return project properties
     */
    ProjectProperties getProjectProperties();
    
    /**
     * Returns repository for team project, or null.
     */
    IRemoteRepository getRepository();

    /**
     * Get project loaded status.
     * 
     * @return true if project loaded
     */
    boolean isProjectLoaded();

    /**
     * Is project modified ?
     */
    boolean isProjectModified();

    /**
     * Returns tokenizer for source language.
     */
    ITokenizer getSourceTokenizer();

    /**
     * Returns tokenizer for target language.
     */
    ITokenizer getTargetTokenizer();

    /**
     * Get all source segments. It's unmodifiable list, so, there is no need synchronization to read it.
     */
    List<SourceTextEntry> getAllEntries();

    /**
     * Set translation for entry. Use when user has typed a new translation.
     * 
     * @param entry
     *            entry
     * @param trans
     *            translation. Null for remove translation, empty string for empty transation.
     * @param isDefault
     *            true if default translation should be changed
     */
    void setTranslation(SourceTextEntry entry, String trans, String note, boolean isDefault);

    /**
     * Get statistics for project.
     * 
     * @return
     */
    StatisticsInfo getStatistics();

    /**
     * Get translation info for specified entry. It looks first for multiple, then for default. This method
     * ALWAYS returns TMXEntry, because note can exist even for non-translated segment. Use
     * TMXEntry.isTranslated() for check if translation text really exist. Translation can be checked for
     * default/alternative by the TMXEntry.defaultTranslation.
     * 
     * @param ste
     *            source entry
     * @return translation
     */
    TMXEntry getTranslationInfo(SourceTextEntry ste);

    /**
     * Iterate by all default translations in project.
     */
    void iterateByDefaultTranslations(DefaultTranslationsIterator it);

    /**
     * Iterate by all multiple translations in project.
     */
    void iterateByMultipleTranslations(MultipleTranslationsIterator it);

    /**
     * Check if orphaned.
     */
    boolean isOrphaned(String source);

    /**
     * Check if orphaned.
     */
    boolean isOrphaned(EntryKey entry);

    /**
     * Get all translation memories from /tm/ folder.
     * 
     * @return translation memories
     */
    Map<String, ExternalTMX> getTransMemories();

    /**
     * Get info about each source file in project. It's unmodifiable list, so, there is no need
     * synchronization to read it.
     */
    List<FileInfo> getProjectFiles();

    public static class FileInfo {
        public String filePath;

        public List<SourceTextEntry> entries = new ArrayList<SourceTextEntry>();
    }

    public interface DefaultTranslationsIterator {
        void iterate(String source, TMXEntry trans);
    }

    public interface MultipleTranslationsIterator {
        void iterate(EntryKey source, TMXEntry trans);
    }

    /**
     * Returns the project specific FilterMaster, if it exists.
     */
    public FilterMaster getFilterMaster();

    /**
     * Sets new file filter configuration for the project
     */
    public void setConfig(Filters filters);

    /**
     * Returns project specific segmentation rules if they exist.
     */
    public SRX getSRX();
    
    /**
     * Sets new project specific segmentation rules.
     */
    public void setSRX(SRX srx);

    /**
     * Returns the directory where segmentation rules should be stored.
     */
    public String getSegmentationConfigDir();

}
