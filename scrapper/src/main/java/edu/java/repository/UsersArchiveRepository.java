package edu.java.repository;

import edu.java.dto.UserDTO;
import java.util.List;

public interface UsersArchiveRepository {
    void addUser(long chatId);

    void removeUser(long chatId);
}
