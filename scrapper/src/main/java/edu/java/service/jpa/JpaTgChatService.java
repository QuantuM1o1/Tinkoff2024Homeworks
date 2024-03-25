package edu.java.service.jpa;

import edu.java.entity.UserEntity;
import edu.java.repository.JpaUserRepository;
import edu.java.service.TgChatService;
import java.time.OffsetDateTime;
import org.springframework.transaction.annotation.Transactional;

public class JpaTgChatService implements TgChatService {
    private final JpaUserRepository jpaUserRepository;

    public JpaTgChatService(JpaUserRepository userRepository) {
        this.jpaUserRepository = userRepository;
    }

    @Override
    @Transactional
    public void register(long tgChatId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setChatId(tgChatId);
        userEntity.setAddedAt(OffsetDateTime.now());
        this.jpaUserRepository.saveAndFlush(userEntity);
    }

    @Override
    @Transactional
    public void unregister(long tgChatId) {
        UserEntity userEntity = this.jpaUserRepository.getReferenceById(tgChatId);
        this.jpaUserRepository.delete(userEntity);
        this.jpaUserRepository.flush();
    }

    @Override
    @Transactional
    public boolean checkIfAlreadyRegistered(long tgChatId) {
        return this.jpaUserRepository.existsById(tgChatId);
    }
}
