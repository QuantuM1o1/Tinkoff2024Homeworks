package edu.java.service.jooq;

import edu.java.dao.jooq.JooqLinkDAO;
import edu.java.dto.LinkDTO;
import edu.java.service.LinkUpdaterService;
import java.util.List;

public class JooqLinkUpdaterService implements LinkUpdaterService {
    private final JooqLinkDAO jooqLinkRepository;

    public JooqLinkUpdaterService(JooqLinkDAO jooqLinkRepository) {
        this.jooqLinkRepository = jooqLinkRepository;
    }

    @Override
    public List<LinkDTO> findNLinksToUpdate(int n) {
        return this.jooqLinkRepository.findNLinksLastUpdated(n);
    }
}
