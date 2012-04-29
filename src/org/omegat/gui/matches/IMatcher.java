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
package org.omegat.gui.matches;

import org.omegat.core.matching.NearString;

/**
 * Interface for access to editor functionality.
 * 
 * @author Alex Buloichik (alex73mail@gmail.com)
 */
public interface IMatcher {
    /**
     * Get selected match.
     * 
     * Must be called from UI thread only.
     */
    NearString getActiveMatch();

    /**
     * Set specified mathc as active.
     * 
     * @param index
     *            new active match
     * 
     *            Must be called from UI thread only.
     */
    void setActiveMatch(int index);
    
    String substituteNumbers(String source, String sourceMatch, String targetMatch);
}
