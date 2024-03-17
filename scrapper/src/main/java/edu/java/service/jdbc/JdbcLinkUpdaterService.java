package edu.java.service.jdbc;

import edu.java.dao.JdbcLinkDAO;
import edu.java.dto.LinkDTO;
import edu.java.service.LinkUpdaterService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JdbcLinkUpdaterService implements LinkUpdaterService {
    @Autowired
    private JdbcLinkDAO linkRepository;

    @Override
    public List<LinkDTO> findNLinksToUpdate(int n) {
        return linkRepository.findNLinksLastUpdated(n);
    }
}
