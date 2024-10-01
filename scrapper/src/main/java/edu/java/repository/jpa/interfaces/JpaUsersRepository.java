package edu.java.repository.jpa.interfaces;

import edu.java.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUsersRepository extends JpaRepository<UserEntity, Long> {
}
