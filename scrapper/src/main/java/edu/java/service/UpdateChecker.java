package edu.java.service;

import edu.java.dto.LinkDTO;
import edu.java.dto.UpdateCheckerResponse;

public interface UpdateChecker {
    UpdateCheckerResponse updateLink(LinkDTO link);
}
