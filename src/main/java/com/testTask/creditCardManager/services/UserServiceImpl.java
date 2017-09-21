package com.testTask.creditCardManager.services;

import com.testTask.creditCardManager.models.Role;
import com.testTask.creditCardManager.models.User;
import com.testTask.creditCardManager.repositories.RoleRepo;
import com.testTask.creditCardManager.repositories.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by giulio.farrugia.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * This method is used to store a user, which for this example,
     * is given a default simple role. The password is encoded using bCryptPasswordEncoder.
     *
     * @param user the user to be stored.
     */
    @Override
    public void storeUser(User user) {

        Set<Role> userRoles = new HashSet<>();

        Role simpleRole = roleRepo.findByRoleName("simpleUser");

        if (simpleRole != null) {
            userRoles.add(simpleRole);
        }

        user.setRoles(userRoles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        user.setRoles(userRoles);
        userRepo.save(user);

        LOG.info("User Persisted!");
    }

    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
