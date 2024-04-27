package edu.java.repository;

import edu.java.entity.LinkEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLinkRepository extends JpaRepository<LinkEntity, Long> {
    LinkEntity getByUrl(String url);

    List<LinkEntity> findAllByOrderByUpdatedAt();
}
