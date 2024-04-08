package edu.java.service;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface UpdateChecker {
    Optional<String> description(String url);

    OffsetDateTime getLastActivity(String url, String domain);
}
