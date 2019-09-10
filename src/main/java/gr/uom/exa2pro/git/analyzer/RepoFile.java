package gr.uom.exa2pro.git.analyzer;

import java.util.HashSet;

public class RepoFile implements Comparable<RepoFile> {

    String path;
    HashSet<String> commits;

    public RepoFile(String path) {
        super();
        this.path = path;
        commits = new HashSet<>();
    }

    public String getPath() {
        return path;
    }
    
    
    public int getCommitCount(){
        return commits.size();
    }

    public void addCommit(String commit) {
        commits.add(commit);
    }

    @Override
    public int compareTo(RepoFile other) {
        if (this.commits.size() < other.commits.size()) {
            return 1;
        }
        return -1;
    }

    @Override
    public String toString() {
        return path + "," + commits.size();
    }

}
