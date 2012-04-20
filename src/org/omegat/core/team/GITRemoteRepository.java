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

import java.io.File;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.omegat.util.FileUtil;
import org.omegat.util.Log;

/**
 * SVN repository connection implementation.
 * 
 * Please, do not use it with autocrlf option, since jgit not supported it yet.
 * 
 * @author Alex Buloichik (alex73mail@gmail.com)
 */
public class GITRemoteRepository implements IRemoteRepository {
    static String LOCAL_BRANCH = "master";
    static String REMOTE_BRANCH = "origin/master";
    static String REMOTE = "origin";

    File localDirectory;
    Repository repository;

    public static boolean isGITDirectory(File localDirectory) {
        return getLocalRepositoryRoot(localDirectory) != null;
    }

    public GITRemoteRepository(File localDirectory) throws Exception {
        this.localDirectory = localDirectory;
        File localRepositoryDirectory = getLocalRepositoryRoot(localDirectory);
        if (localRepositoryDirectory != null) {
            repository = Git.open(localRepositoryDirectory).getRepository();
        }
    }

    public void checkoutFullProject(String repositoryURL) throws Exception {
        CloneCommand c = Git.cloneRepository();
        c.setURI(repositoryURL);
        c.setDirectory(localDirectory);
        c.call();
        repository = Git.open(localDirectory).getRepository();
    }

    public boolean isChanged(File file) throws Exception {
        String relativeFile = FileUtil.computeRelativePath(repository.getWorkTree(), file);
        Status status = new Git(repository).status().call();
        return status.getModified().contains(relativeFile);
    }

    public void setCredentials(String username, String password) {
    }

    public String getBaseRevisionId(File file) throws Exception {
        RevWalk walk = new RevWalk(repository);

        Ref localBranch = repository.getRef("HEAD");
        Ref remoteBranch = repository.getRef(REMOTE_BRANCH);
        RevCommit headCommit = walk.lookupCommit(localBranch.getObjectId());
        RevCommit upstreamCommit = walk.lookupCommit(remoteBranch.getObjectId());

        LogCommand cmd = new Git(repository).log().addRange(upstreamCommit, headCommit);
        Iterable<RevCommit> commitsToUse = cmd.call();
        RevCommit last = null;
        for (RevCommit commit : commitsToUse) {
            last = commit;
        }
        RevCommit commonBase = last != null ? last.getParent(0) : upstreamCommit;
        return commonBase.getName();
    }

    public void restoreBase(File file) throws Exception {
        new Git(repository).reset().setMode(ResetCommand.ResetType.HARD).call();
        new Git(repository).checkout().setName(getBaseRevisionId(file)).call();
    }

    public void updateFullProject() throws Exception {
        Log.logInfoRB("GIT_START", "pull");
        try {
            new Git(repository).fetch().call();
            new Git(repository).checkout().setName(REMOTE_BRANCH).call();
            new Git(repository).branchDelete().setBranchNames(LOCAL_BRANCH).setForce(true).call();
            new Git(repository).checkout().setStartPoint(REMOTE_BRANCH).setCreateBranch(true)
                    .setName(LOCAL_BRANCH).setForce(true).call();
            Log.logInfoRB("GIT_FINISH", "pull");
        } catch (Exception ex) {
            Log.logErrorRB("GIT_ERROR", "pull", ex.getMessage());
            throw ex;
        }
    }

    public void download(File file) throws Exception {
        Log.logInfoRB("GIT_START", "download");
        try {
            new Git(repository).fetch().call();
            new Git(repository).checkout().setName(REMOTE_BRANCH).call();
            new Git(repository).branchDelete().setBranchNames(LOCAL_BRANCH).setForce(true).call();
            new Git(repository).checkout().setStartPoint(REMOTE_BRANCH).setCreateBranch(true)
                    .setName(LOCAL_BRANCH).setForce(true).call();
            Log.logInfoRB("GIT_FINISH", "download");
        } catch (Exception ex) {
            Log.logErrorRB("GIT_ERROR", "download", ex.getMessage());
        }
    }

    public void upload(File file, String commitMessage) throws Exception {
        boolean ok = true;
        Log.logInfoRB("GIT_START", "upload");
        try {
            if (!isChanged(file)) {
                Log.logInfoRB("GIT_FINISH", "upload(not changed)");
                return;
            }
            String filePattern = FileUtil.computeRelativePath(repository.getWorkTree(), file);
            new Git(repository).add().addFilepattern(filePattern).call();
            new Git(repository).commit().setMessage(commitMessage).call();
            Iterable<PushResult> results = new Git(repository).push().setRemote(REMOTE).add(LOCAL_BRANCH)
                    .call();
            int count = 0;
            for (PushResult r : results) {
                for (RemoteRefUpdate update : r.getRemoteUpdates()) {
                    count++;
                    if (update.getStatus() != RemoteRefUpdate.Status.OK) {
                        ok = false;
                    }
                }
            }
            if (count < 1) {
                ok = false;
            }
            Log.logInfoRB("GIT_FINISH", "upload");
        } catch (Exception ex) {
            Log.logErrorRB("GIT_ERROR", "upload", ex.getMessage());
            throw ex;
        }
        if (!ok) {
            Log.logWarningRB("GIT_CONFLICT");
            throw new Exception("Conflict");
        }
    }

    private static File getLocalRepositoryRoot(File path) {
        if (path == null) {
            return null;
        }
        File possibleControlDir = new File(path, ".git");
        if (possibleControlDir.exists() && possibleControlDir.isDirectory()) {
            return path;
        } else {
            // We need to call getAbsoluteFile() because "path" can be relative. In this case, we will have
            // "null" instead real parent directory.
            return getLocalRepositoryRoot(path.getAbsoluteFile().getParentFile());
        }
    }

    static ProgressMonitor gitProgress = new ProgressMonitor() {
        public void update(int completed) {
            System.out.println("update: " + completed);
        }

        public void start(int totalTasks) {
            System.out.println("start: " + totalTasks);
        }

        public boolean isCancelled() {
            return false;
        }

        public void endTask() {
            System.out.println("endTask");
        }

        public void beginTask(String title, int totalWork) {
            System.out.println("beginTask: " + title + " total: " + totalWork);
        }
    };
}
