package edu.java.service;

import edu.java.dto.LinkDTO;
import edu.java.repository.jdbc.JdbcLinkRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkUpdaterService {
    @Autowired
    private JdbcLinkRepository linkRepository;

    public List<LinkDTO> findNLinksToUpdate(int n) {
        return this.linkRepository.findNLinksLastUpdated(n);
    }
}
