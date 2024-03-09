package edu.java.dto;

public record GitHubRepositoryRequest(
    String owner,
    String repoName
) {
}
