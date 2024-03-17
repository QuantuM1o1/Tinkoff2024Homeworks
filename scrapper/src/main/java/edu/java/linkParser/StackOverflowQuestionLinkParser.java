package edu.java.linkParser;

import edu.java.dto.StackOverflowQuestionRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StackOverflowQuestionLinkParser {
    private StackOverflowQuestionLinkParser() {
    }

    public static StackOverflowQuestionRequest createRequest(String url) {
        String patternString = "^https?://stackoverflow\\.com/.*/(.*)/.*$";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(url);
        long questionId = Long.parseLong(matcher.group(1));

        return new StackOverflowQuestionRequest("stackoverflow", questionId);
    }
}
