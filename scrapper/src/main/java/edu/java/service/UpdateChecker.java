package edu.java.service;

import edu.java.dto.LinkDTO;

public interface UpdateChecker {
    void checkForUpdates(LinkDTO link);

    void setInfoFirstTime(LinkDTO link);
}
