/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2008 Alex Buloichik
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

package org.omegat.filters;

import java.util.List;

import org.junit.Test;
import org.omegat.core.data.IProject;
import org.omegat.filters2.html2.HTMLFilter2;

public class HTMLFilter2Test extends TestFilterBase {
    public void testParse() throws Exception {
        List<String> entries = parse(new HTMLFilter2(), "test/data/filters/html/file-HTMLFilter2.html");
        assertEquals(entries.size(), 2);
        assertEquals("This is first line.", entries.get(0));
        assertEquals("This is second line.", entries.get(1));
    }

    public void testTranslate() throws Exception {
        translateText(new HTMLFilter2(), "test/data/filters/html/file-HTMLFilter2.html");
    }

    @Test
    public void testLoad() throws Exception {
        String f = "test/data/filters/html/file-HTMLFilter2.html";
        IProject.FileInfo fi = loadSourceFiles(new HTMLFilter2(), f);

        checkMultiStart(fi, f);
        checkMulti("This is first line.", null, null, "", "This is second line.", null);
        checkMulti("This is second line.", null, null, "This is first line.", "", null);
        checkMultiEnd();
    }
}
