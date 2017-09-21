package com.testTask.creditCardManager.services;

import com.testTask.creditCardManager.models.User;
import com.testTask.creditCardManager.repositories.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by giulio.farrugia.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    private static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    /**
     * This method is responsible to retrieve the user by the username and create the User Security Object.
     *
     * @param username the given username.
     * @return The userDetails security object.
     * @throws UsernameNotFoundException if the username does not match any user.
     */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsername(username);

        Set<GrantedAuthority> authoritySet = new HashSet<>();
        if (user != null) {

            LOG.info("User Retrieved!");
            user.getRoles().forEach(role -> {
                authoritySet.add(new SimpleGrantedAuthority(role.getRoleName()));
            });
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
            authoritySet);
    }
}
