package edu.java.dto;

public record StackOverflowQuestionRequest(
    String site,
    long questionId
) {
}
