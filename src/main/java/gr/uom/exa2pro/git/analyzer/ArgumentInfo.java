package gr.uom.exa2pro.git.analyzer;

import org.apache.commons.cli.*;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class ArgumentInfo {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ArgumentInfo.class.getName());

    private static final String GIT_REPO_LITERAL = "repo";
    private static final String FILE_TYPE_LITERAL = "filetype";

    private static Options options = new Options();
    String localRepoPath;
    String fileTypeExtension;

    static {
        Option dbHostOption = Option.builder(GIT_REPO_LITERAL)
                .required(true)
                .desc("The local repo path. The folder in which '.git' folder resides")
                .longOpt(GIT_REPO_LITERAL).hasArg()
                .build();
        Option dbUserOption = Option.builder(FILE_TYPE_LITERAL)
                .required(true)
                .desc("The Filetype extension to include in analysis. For example: '.java', '.f', '.f90', '.F', '.c'")
                .longOpt(FILE_TYPE_LITERAL).hasArg()
                .build();

        options.addOption(dbHostOption);
        options.addOption(dbUserOption);
    }

    public static ArgumentInfo loadConfiguration(String... args) {
        ArgumentInfo connInfo = new ArgumentInfo();

        if (args != null && args.length > 1) {
            logger.info("Loading configuration from arguments");
            connInfo = loadConfigFromCommandLineArgs(args);
        }
        printLoadedConfig(connInfo);

        return connInfo;
    }

    private static ArgumentInfo loadConfigFromCommandLineArgs(String[] commandLineArguments) {
        CommandLine commandLine;

        CommandLineParser parser = new DefaultParser();

        try {
            commandLine = parser.parse(options, commandLineArguments);
            return createConfigInfo(commandLine.getOptionValue(GIT_REPO_LITERAL),
                    commandLine.getOptionValue(FILE_TYPE_LITERAL));
        } catch (ParseException exception) {
            logger.error("Command line argument error: ", exception);
        }
        return new ArgumentInfo();
    }

    private static ArgumentInfo createConfigInfo(String gitRepo, String fileType) {
        ArgumentInfo connInfo = new ArgumentInfo();
        connInfo.fileTypeExtension = fileType;
        connInfo.localRepoPath = gitRepo;
        return connInfo;
    }

    private static void printLoadedConfig(ArgumentInfo connInfo) {
        logger.info("------------------------------------------------------------------------------------");
        logger.info("                              LOADED CONFIGURATION:  ");
        logger.info("------------------------------------------------------------------------------------");
        logger.info("LOCAL GIT REPO = " + connInfo.localRepoPath);
        logger.info("Filetype extension to analyze = " + connInfo.fileTypeExtension);
        logger.info("------------------------------------------------------------------------------------");
    }

    public static void checkForHelpAndPrintIfNeeded(String... args) {
        if (needsHelp(args)) {
            System.out.println("----------------------------------------------------------------");
            System.out.println();
            System.out.println("Git Analyzer CLI tool");
            System.out.println();
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar gitAnalyzer-executable.jar", options);

            System.out.println();
            System.out.println();
            System.exit(0);
        } else if (args.length == 0) {
            System.out.println("----------------------------------------------------------------");
            System.out.println();
            System.out.println("GitAnalyzer - For help, run with option --help");
            System.out.println("");
            System.out.println("");
            System.exit(0);
        }
    }

    private static boolean needsHelp(String... args) {
        if (args != null && args.length == 1) {
            List<String> strings = Arrays.asList(args);
            return strings.contains("--help");
        }
        return false;
    }

}
