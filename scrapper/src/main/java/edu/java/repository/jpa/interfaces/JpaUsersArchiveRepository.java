package edu.java.repository.jpa.interfaces;

import edu.java.entity.UserArchiveEntity;
import java.time.OffsetDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUsersArchiveRepository extends JpaRepository<UserArchiveEntity, Long> {
    @Modifying
    @Query("UPDATE UsersArchive u SET u.deletedAt = :deletedAt WHERE u.tgChatId = :tgChatId AND u.deletedAt IS NULL")
    void markAsDeleted(OffsetDateTime deletedAt, long tgChatId);
}
