package gr.uom.exa2pro.git.analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeSet;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathSuffixFilter;
import org.slf4j.LoggerFactory;

public class GitAnalyzer {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(GitAnalyzer.class.getName());

    public static void main(String[] args) throws NoHeadException, GitAPIException {
        logger.info("Hello from Logger system of {}", GitAnalyzer.class.getCanonicalName());

        ArgumentInfo.checkForHelpAndPrintIfNeeded(args);
        ArgumentInfo configuration = ArgumentInfo.loadConfiguration(args);
 
        analyzeRepo(Paths.get(configuration.localRepoPath), configuration.fileTypeExtension);
    }

    private static void analyzeRepo(Path path, String fileTypeExtension) throws GitAPIException, RevisionSyntaxException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        try {
            Repository repository = builder
                    .setGitDir(Paths.get(path.toString(), ".git").toFile())
                    .readEnvironment() // scan environment GIT_* variables
                    .findGitDir() // scan up the file system tree
                    .build();

            System.out.println("Building repo " + repository);

            //	walkCommits(repository, walk);
            System.out.println("-------------------------------------------------------");

            Ref head = repository.findRef("HEAD");
            Git git = new Git(repository);

            TreeSet<RepoFile> fileSet = new TreeSet<>();

            // a RevWalk allows to walk over commits based on some filtering that is defined
            try (RevWalk walk = new RevWalk(repository)) {
                RevCommit commit = walk.parseCommit(head.getObjectId());
                RevTree tree = commit.getTree();
                System.out.println("Having tree: " + tree);

                // now use a TreeWalk to iterate over all files in the Tree recursively
                // you can set Filters to narrow down the results if needed
                try (TreeWalk treeWalk = new TreeWalk(repository)) {
                    treeWalk.addTree(tree);
                    treeWalk.setRecursive(true);
                    treeWalk.setFilter(PathSuffixFilter.create(fileTypeExtension));
                    while (treeWalk.next()) {
                        String fileRelativePath = treeWalk.getPathString();

                        LogCommand logCommand = git.log()
                                .add(git.getRepository().resolve(Constants.HEAD))
                                .addPath(fileRelativePath);
                        RepoFile repoFile = new RepoFile(fileRelativePath);
                        for (RevCommit revCommit : logCommand.call()) {
                            repoFile.addCommit(revCommit.getId().getName());
                        }
                        fileSet.add(repoFile);
                    }
                }
            }

            logger.debug("closing tree walker.... ");

            for (RepoFile f : fileSet) {
                System.out.println(f);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void walkCommits(Repository repository, RevWalk walk)
            throws MissingObjectException, IncorrectObjectTypeException, IOException, AmbiguousObjectException {
        RevCommit commit;
        String commitMessage;
        PersonIdent authorIdent;
        Date authorDate;
        walk.markStart(walk.parseCommit(repository.resolve("HEAD")));

        Iterator<RevCommit> iterator = walk.iterator();

        while (iterator.hasNext()) {
            commit = iterator.next();
            commitMessage = commit.getFullMessage();
            authorIdent = commit.getAuthorIdent();
            authorDate = authorIdent.getWhen();

            System.out.println("Commit " + commit + " Date: " + authorDate);
        }
    }

}
