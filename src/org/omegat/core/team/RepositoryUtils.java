/**************************************************************************
 OmegaT - Computer Assisted Translation (CAT) tool 
          with fuzzy matching, translation memory, keyword search, 
          glossaries, and translation leveraging into updated projects.

 Copyright (C) 2012 Alex Buloichik
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
package org.omegat.core.team;

import org.omegat.core.Core;
import org.omegat.gui.dialogs.UserPassDialog;
import org.omegat.util.OStrings;
import org.omegat.util.gui.DockingUI;

/*
 * Some utility methods for working with remote repository.
 *  
 * @author Alex Buloichik <alex73mail@gmail.com>
 */
public class RepositoryUtils {
    /**
     * Display dialog for credentials.
     * 
     * @return true if user entered credentials, otherwise - false
     */
    public static boolean askForCredentials(IRemoteRepository repository, String message) {
        UserPassDialog userPassDialog = new UserPassDialog(Core.getMainWindow().getApplicationFrame());
        DockingUI.displayCentered(userPassDialog);
        userPassDialog.setTitle(OStrings.getString("TEAM_USERPASS_TITLE"));
        userPassDialog.descriptionTextArea.setText(message);
        userPassDialog.setVisible(true);
        if (userPassDialog.getReturnStatus() == UserPassDialog.RET_OK) {
            repository.setCredentials(userPassDialog.userText.getText(), new String(
                    userPassDialog.passwordField.getPassword()));
            return true;
        } else {
            return false;
        }
    }

    public static abstract class AskCredentials {
        public void execute(IRemoteRepository repository) throws Exception {
            boolean firstPass = true;
            while (true) {
                try {
                    callRepository();
                    break;
                } catch (IRemoteRepository.AuthenticationException ex) {
                    boolean entered = RepositoryUtils.askForCredentials(repository,
                            OStrings.getString(firstPass ? "TEAM_USERPASS_FIRST" : "TEAM_USERPASS_WRONG"));
                    if (!entered) {
                        throw ex;
                    }
                    firstPass = false;
                }
            }
        }

        abstract protected void callRepository() throws Exception;
    }

}
