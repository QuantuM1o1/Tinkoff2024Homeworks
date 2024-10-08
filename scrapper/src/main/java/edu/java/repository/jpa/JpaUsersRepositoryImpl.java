package edu.java.repository.jpa;

import edu.java.dto.UserDTO;
import edu.java.entity.UserEntity;
import edu.java.repository.UsersRepository;
import edu.java.repository.jpa.interfaces.JpaUsersRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class JpaUsersRepositoryImpl implements UsersRepository {
    private JpaUsersRepository jpaUsersRepository;

    @Override
    public void addUser(long chatId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setTgChatId(chatId);
        this.jpaUsersRepository.saveAndFlush(userEntity);
    }

    @Override
    public void removeUser(long chatId) {
        UserEntity userEntity = this.jpaUsersRepository.getReferenceById(chatId);
        this.jpaUsersRepository.delete(userEntity);
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<UserEntity> userEntities = this.jpaUsersRepository.findAll();
        List<UserDTO> list = new ArrayList<>();
        userEntities.forEach(userEntity -> list.add(new UserDTO(userEntity.getTgChatId())));

        return list;
    }

    @Override
    public List<UserDTO> findUserById(long chatId) {
        UserEntity userEntity = this.jpaUsersRepository.getReferenceById(chatId);
        List<UserDTO> list = new ArrayList<>();
        list.add(new UserDTO(userEntity.getTgChatId()));

        return list;
    }
}
