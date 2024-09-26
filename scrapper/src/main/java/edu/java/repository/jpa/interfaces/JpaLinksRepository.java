package edu.java.repository.jpa.interfaces;

import edu.java.entity.LinkEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLinksRepository extends JpaRepository<LinkEntity, Long> {
    LinkEntity getByUrl(String url);

    List<LinkEntity> findAllByOrderByUpdatedAt();
}
