package gr.uom.exa2pro.git.analyzer;

import static gr.uom.exa2pro.git.analyzer.RepoAnalyzer.analyzeRepo;
import gr.uom.exa2pro.git.analyzer.exporters.CsvExporter;
import gr.uom.exa2pro.git.analyzer.exporters.JsonExporter;
import java.nio.file.Paths;
import java.util.Collection;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.slf4j.LoggerFactory;

public class GitAnalyzer {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(GitAnalyzer.class.getName());

    public static void main(String[] args) throws NoHeadException, GitAPIException {
        logger.info("Hello from Logger system of {}", GitAnalyzer.class.getCanonicalName());

        ArgumentInfo.checkForHelpAndPrintIfNeeded(args);
        ArgumentInfo configuration = ArgumentInfo.loadConfiguration(args);
 
        Collection<RepoFile> repoFiles = analyzeRepo(Paths.get(configuration.localRepoPath), configuration.fileTypeExtension);
        
        switch(configuration.exportFormat){
            case "csv":{
                CsvExporter.exportToCsv(repoFiles);
                break;
            }
            case "json":{
                JsonExporter.exportToJson(repoFiles);
                break;
            }
        }
    }

}
