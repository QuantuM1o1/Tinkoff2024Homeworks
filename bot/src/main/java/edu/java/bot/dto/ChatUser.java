package edu.java.bot.dto;

import java.util.List;

public record ChatUser(
    long chatID,
    String userName,
    List<String> trackedURLs
) {
}
