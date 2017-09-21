package com.testTask.creditCardManager.data;

import com.testTask.creditCardManager.models.Role;
import com.testTask.creditCardManager.models.User;
import com.testTask.creditCardManager.repositories.RoleRepo;
import com.testTask.creditCardManager.repositories.UserRepo;
import com.testTask.creditCardManager.services.CardServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by giulio.farrugia.
 * <p>
 * This class is used just to load initial date for this example.
 */
@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger LOG = LoggerFactory.getLogger(CardServiceImpl.class);

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        LOG.info("Creating dummy data!");

        // creating a simple user role
        Role simpleRole = new Role();
        simpleRole.setRoleName("simpleUser");
        roleRepo.save(simpleRole);

        // creating an admin role
        Role adminRole = new Role();
        adminRole.setRoleName("admin");
        roleRepo.save(adminRole);

        // creating the admin user
        User user = new User();
        user.setUsername("admin");
        user.setPassword(bCryptPasswordEncoder.encode("admin"));

        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        user.setRoles(adminRoles);

        userRepo.save(user);

        LOG.info("Data Created Successfully!");
    }
}
