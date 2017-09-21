package com.testTask.creditCardManager.services;

import com.testTask.creditCardManager.models.User;

/**
 * Created by giulio.farrugia.
 */
public interface UserService {

    /**
     * Stores the user into the repository.
     * @param user the user to be stored.
     */
    void storeUser(User user);

    /**
     * Retrieves the user from the repository.
     * @param username the given username.
     * @return the retrieved user.
     */
    User findByUsername(String username);
}
