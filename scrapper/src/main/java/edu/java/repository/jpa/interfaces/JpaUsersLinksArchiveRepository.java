package edu.java.repository.jpa.interfaces;

import edu.java.entity.UserLinkArchiveEntity;
import java.time.OffsetDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUsersLinksArchiveRepository extends JpaRepository<UserLinkArchiveEntity, Long> {
    @Modifying
    @Query("UPDATE UsersLinksArchive ul SET ul.deletedAt = :deletedAt "
        + "WHERE ul.userId = :tgChatId AND ul.linkId = :linkId AND ul.deletedAt IS NULL")
    void markAsDeleted(OffsetDateTime deletedAt, long tgChatId, long linkId);
}
