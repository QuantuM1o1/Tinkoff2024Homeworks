package edu.java.service;

import edu.java.dto.LinkDTO;
import java.util.List;

public interface LinkUpdaterService {
    List<LinkDTO> findNLinksToUpdate(int n);
}
