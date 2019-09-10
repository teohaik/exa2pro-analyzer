package gr.uom.exa2pro.git.analyzer.exporters;

import gr.uom.exa2pro.git.analyzer.RepoFile;
import java.util.Collection;

/**
 *
 * @author Thodoris
 */
public class CsvExporter {

    public static void exportToCsv(Collection<RepoFile> repoFiles) {

        System.out.println("<Result_CSV_Start>");
        System.out.println("File,Edits");
        for(RepoFile repoFile : repoFiles){
            System.out.println(repoFile.getPath() + "," + repoFile.getCommitCount());
        }
        System.out.println("<Result_CSV_End>");
    }

}
