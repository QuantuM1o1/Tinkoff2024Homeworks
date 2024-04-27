package edu.java.service;

import edu.java.dto.LinkDTO;
import edu.java.repository.LinkRepository;
import java.util.List;

public class LinkUpdaterService {
    private final LinkRepository linkRepository;

    public LinkUpdaterService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<LinkDTO> findNLinksToUpdate(int n) {
        return this.linkRepository.findNLinksLastUpdated(n);
    }
}
