package com.testTask.creditCardManager.repositories;

import com.testTask.creditCardManager.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by giulio.farrugia.
 */
public interface RoleRepo extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleName);
}
