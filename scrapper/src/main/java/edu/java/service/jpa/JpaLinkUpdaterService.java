package edu.java.service.jpa;

import edu.java.dto.LinkDTO;
import edu.java.entity.LinkEntity;
import edu.java.repository.JpaLinkRepository;
import edu.java.service.LinkUpdaterService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public class JpaLinkUpdaterService implements LinkUpdaterService {
    private final JpaLinkRepository jpaLinkRepository;

    public JpaLinkUpdaterService(JpaLinkRepository linkRepository) {
        this.jpaLinkRepository = linkRepository;
    }

    @Override
    @Transactional
    public List<LinkDTO> findNLinksToUpdate(int n) {
        List<LinkEntity> linkEntities = jpaLinkRepository.findAllByOrderByUpdatedAt();
        List<LinkDTO> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            LinkEntity entity = linkEntities.get(i);
            list.add(new LinkDTO(
                entity.getId(),
                entity.getUrl(),
                entity.getAddedAt(),
                entity.getUpdatedAt(),
                entity.getLastActivity(),
                entity.getSiteId(),
                entity.getAnswerCount(),
                entity.getCommentCount()
            ));
        }

        return list;
    }
}
