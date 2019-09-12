## Git Analyzer

This program analyzes a git repository and returns the files and the number of commits for each file

### From the help:
----------------------------------------------------------------

Git Analyzer CLI tool

```
usage: java -jar gitAnalyzer-<version>.jar
 
 -filetype,--filetype <arg>           The Filetype extension to include in analysis. For example: '.java', '.f', '.f90', '.F', '.c'
 
 -repo,--repo <arg>                   The local repo path. The folder in which '.git' folder resides
 
 -exportformat,--exportformat <arg>   The Export format. Supportedformats: json, csv
 
 ```
 
Example usage:

```
$ java -jar gitAnalyzer-0.0.1.jar -filetype '.java' --repo 'C:\Users\Thodoris\git\seagle-server'
...
2019-09-13 00:26:52 INFO  GitAnalyzer:17 - Hello from Logger system of gr.uom.exa2pro.git.analyzer.GitAnalyzer
2019-09-13 00:26:52 INFO  ArgumentInfo:48 - Loading configuration from arguments
2019-09-13 00:26:52 INFO  ArgumentInfo:76 - ------------------------------------------------------------------------------------
2019-09-13 00:26:52 INFO  ArgumentInfo:77 -                               LOADED CONFIGURATION:
2019-09-13 00:26:52 INFO  ArgumentInfo:78 - ------------------------------------------------------------------------------------
2019-09-13 00:26:52 INFO  ArgumentInfo:79 - LOCAL GIT REPO = C:\Users\user\git\seagle-server
2019-09-13 00:26:52 INFO  ArgumentInfo:80 - Filetype extension to analyze = .java
2019-09-13 00:26:52 INFO  ArgumentInfo:81 - ------------------------------------------------------------------------------------
....

<Result_CSV_Start>
File,Edits
src/main/java/gr/uom/java/seagle/v2/metric/AnalysisCompletedListener.java,3
src/main/java/gr/uom/java/seagle/v2/analysis/AnalysisCompletedNotificationMailer.java,3
src/main/java/gr/uom/java/seagle/v2/ws/rest/project/model/RESTProject.java,2
src/main/java/gr/uom/java/seagle/v2/ws/rest/project/ProjectService.java,2
src/main/java/gr/uom/java/seagle/v2/metric/AnalysisCompletedActivator.java,2
<Result_CSV_End>


```

