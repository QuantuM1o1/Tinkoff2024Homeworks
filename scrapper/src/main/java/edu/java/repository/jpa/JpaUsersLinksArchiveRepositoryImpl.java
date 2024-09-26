package edu.java.repository.jpa;

import edu.java.entity.UserLinkArchiveEntity;
import edu.java.repository.UsersLinksArchiveRepository;
import edu.java.repository.jpa.interfaces.JpaUsersLinksArchiveRepository;
import java.time.OffsetDateTime;

public class JpaUsersLinksArchiveRepositoryImpl implements UsersLinksArchiveRepository {
    private JpaUsersLinksArchiveRepository jpaUsersLinksArchiveRepository;

    @Override
    public void addUserLink(long chatId, long linkId) {
        UserLinkArchiveEntity userLinkArchiveEntity = new UserLinkArchiveEntity();
        userLinkArchiveEntity.setUserId(chatId);
        userLinkArchiveEntity.setAddedAt(OffsetDateTime.now());
        this.jpaUsersLinksArchiveRepository.saveAndFlush(userLinkArchiveEntity);
    }

    @Override
    public void removeUserLink(long chatId, long linkId) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.jpaUsersLinksArchiveRepository.markAsDeleted(currentTime, chatId, linkId);
    }
}
