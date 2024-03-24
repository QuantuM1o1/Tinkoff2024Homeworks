package edu.java.service.jdbc;

import edu.java.dao.JdbcLinkDAO;
import edu.java.dto.LinkDTO;
import edu.java.service.LinkUpdaterService;
import java.util.List;

public class JdbcLinkUpdaterService implements LinkUpdaterService {
    private final JdbcLinkDAO jdbcLinkRepository;

    public JdbcLinkUpdaterService(JdbcLinkDAO jdbcLinkRepository) {
        this.jdbcLinkRepository = jdbcLinkRepository;
    }

    @Override
    public List<LinkDTO> findNLinksToUpdate(int n) {
        return jdbcLinkRepository.findNLinksLastUpdated(n);
    }
}
