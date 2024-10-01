package edu.java.repository.jpa;

import edu.java.entity.UserLinkArchiveEntity;
import edu.java.repository.UsersLinksArchiveRepository;
import edu.java.repository.jpa.interfaces.JpaUsersLinksArchiveRepository;
import org.springframework.stereotype.Repository;
import java.time.OffsetDateTime;

@Repository
public class JpaUsersLinksArchiveRepositoryImpl implements UsersLinksArchiveRepository {
    private JpaUsersLinksArchiveRepository jpaUsersLinksArchiveRepository;

    @Override
    public void addUserLink(long chatId, String url) {
        UserLinkArchiveEntity userLinkArchiveEntity = new UserLinkArchiveEntity();
        userLinkArchiveEntity.setUserId(chatId);
        userLinkArchiveEntity.setUrl(url);
        userLinkArchiveEntity.setAddedAt(OffsetDateTime.now());
        this.jpaUsersLinksArchiveRepository.saveAndFlush(userLinkArchiveEntity);
    }

    @Override
    public void removeUserLink(long chatId, String url) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.jpaUsersLinksArchiveRepository.markAsDeleted(currentTime, chatId, url);
    }
}
