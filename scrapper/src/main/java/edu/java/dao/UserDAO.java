package edu.java.dao;

import edu.java.dto.UserDTO;
import java.util.List;

public interface UserDAO {
    void addUser(Long chatId, String username);
    void removeUser(Long chatId);
    List<UserDTO> findAllUsers();
}
