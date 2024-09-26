package edu.java.repository.jpa.interfaces;

import edu.java.entity.LinkArchiveEntity;
import java.time.OffsetDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLinksArchiveRepository extends JpaRepository<LinkArchiveEntity, Long> {
    @Modifying
    @Query("UPDATE LinkArchiveEntity l SET l.deletedAt = :deletedAt WHERE l.linkId = :linkId AND l.deletedAt IS NULL")
    void markAsDeleted(OffsetDateTime deletedAt, long linkId);
}
