package edu.java.dao;

import edu.java.dto.UserDTO;
import java.util.List;

public interface UserDAO {
    void addUser(Long chatId);

    void removeUser(Long chatId);

    List<UserDTO> findAllUsers();
}
