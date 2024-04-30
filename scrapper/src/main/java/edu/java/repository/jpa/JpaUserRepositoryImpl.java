package edu.java.repository.jpa;

import edu.java.dto.UserDTO;
import edu.java.entity.UserEntity;
import edu.java.repository.JpaUserRepository;
import edu.java.repository.UserRepository;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class JpaUserRepositoryImpl implements UserRepository {
    private  JpaUserRepository jpaUserRepository;

    @Override
    public void addUser(long chatId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setChatId(chatId);
        userEntity.setAddedAt(OffsetDateTime.now());
        this.jpaUserRepository.saveAndFlush(userEntity);
    }

    @Override
    public void removeUser(long chatId) {
        UserEntity userEntity = this.jpaUserRepository.getReferenceById(chatId);
        this.jpaUserRepository.delete(userEntity);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<UserEntity> userEntities = this.jpaUserRepository.findAll();
        List<UserDTO> list = new ArrayList<>();
        userEntities.forEach(userEntity -> list.add(new UserDTO(
            userEntity.getChatId(),
            userEntity.getAddedAt()
        )));

        return list;
    }

    @Override
    public List<UserDTO> findUserById(long chatId) {
        UserEntity userEntity = this.jpaUserRepository.getReferenceById(chatId);
        List<UserDTO> list = new ArrayList<>();
        list.add(new UserDTO(
            userEntity.getChatId(),
            userEntity.getAddedAt()
        ));

        return list;
    }
}
