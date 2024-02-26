package edu.java.bot.dto;

import java.net.URI;
import java.util.List;

public record ChatUser(
    long chatID,
    String userName,
    List<URI> trackedURLs
) {
}
