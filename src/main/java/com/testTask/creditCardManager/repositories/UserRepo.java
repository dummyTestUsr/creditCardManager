package com.testTask.creditCardManager.repositories;

import com.testTask.creditCardManager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by giulio.farrugia.
 */
public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
