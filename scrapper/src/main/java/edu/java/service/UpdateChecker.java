package edu.java.service;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface UpdateChecker {
    Optional<String> description(String url);

    OffsetDateTime getLastActivity(String url, String domain);

    default int getAnswerCount(String url, String domain) {
        return 0;
    }

    default int getCommentCount(String url, String domain) {
        return 0;
    }
}
