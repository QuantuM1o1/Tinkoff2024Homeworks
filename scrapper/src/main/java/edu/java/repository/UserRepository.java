package edu.java.repository;

import edu.java.dto.UserDTO;
import java.util.List;

public interface UserRepository {
    void addUser(long chatId);

    void removeUser(long chatId);

    List<UserDTO> findAllUsers();

    List<UserDTO> findUserById(long chatId);
}
