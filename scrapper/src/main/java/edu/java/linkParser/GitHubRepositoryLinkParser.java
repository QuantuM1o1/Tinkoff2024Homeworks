package edu.java.linkParser;

import edu.java.dto.GitHubRepositoryRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitHubRepositoryLinkParser {
    private GitHubRepositoryLinkParser() {
    }

    public static GitHubRepositoryRequest createRequest(String url) {
        String patternString = "^https?://github\\.com/(.*)/(.*)$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(url);
        String owner = "";
        String repoName = "";
        if (matcher.find()) {
            owner = matcher.group(1);
            repoName = matcher.group(2);
        }

        return new GitHubRepositoryRequest(owner, repoName);
    }
}
