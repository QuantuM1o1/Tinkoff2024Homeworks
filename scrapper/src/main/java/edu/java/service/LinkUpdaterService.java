package edu.java.service;

import edu.java.dto.LinkDTO;
import edu.java.repository.LinksRepository;
import java.util.List;

public class LinkUpdaterService {
    private final LinksRepository linksRepository;

    public LinkUpdaterService(LinksRepository linksRepository) {
        this.linksRepository = linksRepository;
    }

    public List<LinkDTO> findNLinksToUpdate(int n) {
        return this.linksRepository.findNLinksLastUpdated(n);
    }
}
