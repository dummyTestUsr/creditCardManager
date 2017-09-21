package com.testTask.creditCardManager.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * Created by giulio.farrugia.
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Override
    public String getCurrentUser() {
        Object user =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(user instanceof User){

            LOG.info("Current User retrieved!");
            return ((User) user).getUsername();
        }

        LOG.error("Cannot retrieve current user!");
        return null;
    }
}
