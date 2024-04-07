package edu.java.repository;

import edu.java.dto.UserDTO;
import java.util.List;

public interface UserRepository {
    void addUser(Long chatId);

    void removeUser(Long chatId);

    List<UserDTO> findAllUsers();

    List<UserDTO> findUserById(long chatId);
}
