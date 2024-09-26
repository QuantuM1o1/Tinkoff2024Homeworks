package edu.java.repository.jpa;

import edu.java.entity.UserArchiveEntity;
import edu.java.repository.UsersArchiveRepository;
import edu.java.repository.jpa.interfaces.JpaUsersArchiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.time.OffsetDateTime;

@Repository
public class JpaUsersArchiveRepositoryImpl implements UsersArchiveRepository {
    @Autowired
    private JpaUsersArchiveRepository jpaUsersArchiveRepository;

    @Override
    public void addUser(long chatId) {
        UserArchiveEntity userArchiveEntity = new UserArchiveEntity();
        userArchiveEntity.setTgChatId(chatId);
        userArchiveEntity.setAddedAt(OffsetDateTime.now());
        this.jpaUsersArchiveRepository.saveAndFlush(userArchiveEntity);
    }

    @Override
    public void removeUser(long chatId) {
        OffsetDateTime currentTime = OffsetDateTime.now();

        this.jpaUsersArchiveRepository.markAsDeleted(currentTime, chatId);
    }
}
