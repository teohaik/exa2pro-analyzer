package gr.uom.exa2pro.git.analyzer.exporters;

import gr.uom.exa2pro.git.analyzer.RepoFile;
import java.util.Collection;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Thodoris
 */
public class CsvExporter {
    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(CsvExporter.class.getName());

    public static void exportToCsv(Collection<RepoFile> repoFiles) {

        System.out.println("<Result_CSV_Start>");
        System.out.println("File,Edits");
        logger.trace("File,Edits");
        for(RepoFile repoFile : repoFiles){
            System.out.println(repoFile.getPath() + "," + repoFile.getCommitCount());
            logger.trace(repoFile.getPath() + "," + repoFile.getCommitCount());
        }
        System.out.println("<Result_CSV_End>");
    }

}
