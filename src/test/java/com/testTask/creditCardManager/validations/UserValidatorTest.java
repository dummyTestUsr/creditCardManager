package com.testTask.creditCardManager.validations;

import static junit.framework.TestCase.assertEquals;

import com.testTask.creditCardManager.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

/**
 * Created by giulio.farrugia.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserValidatorTest {

    @Autowired
    UserValidator userValidator;
    public Errors errors;

    /**
     * Scenario Valid User.
     */
    @Test
    public void validateUser() {

        User user = new User();
        errors = new BeanPropertyBindingResult(user, "user");

        user.setUserId(1L);
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setConfirmedPassword("testPassword");

        userValidator.validate(user, errors);

        assertEquals(0, errors.getErrorCount());
    }

    /**
     * Scenario Username too short.
     */
    @Test
    public void validateUser2() {

        User user = new User();
        errors = new BeanPropertyBindingResult(user, "user");

        user.setUserId(1L);
        user.setUsername("t");
        user.setPassword("testPassword");
        user.setConfirmedPassword("testPassword");

        userValidator.validate(user, errors);

        assertEquals(1, errors.getErrorCount());
    }

    /**
     * Scenario Username too long.
     */
    @Test
    public void validateUser3() {

        User user = new User();
        errors = new BeanPropertyBindingResult(user, "user");

        user.setUserId(1L);
        user.setUsername("testUsername1234");
        user.setPassword("testPassword");
        user.setConfirmedPassword("testPassword");

        userValidator.validate(user, errors);

        assertEquals(1, errors.getErrorCount());
    }

    /**
     * Scenario Password too short.
     */
    @Test
    public void validateUser4() {

        User user = new User();
        errors = new BeanPropertyBindingResult(user, "user");

        user.setUserId(1L);
        user.setUsername("testUsername1234");
        user.setPassword("tst");
        user.setConfirmedPassword("tst");

        userValidator.validate(user, errors);

        assertEquals(1, errors.getErrorCount());
    }

    /**
     * Scenario Password do not match.
     */
    @Test
    public void validateUser5() {

        User user = new User();
        errors = new BeanPropertyBindingResult(user, "user");

        user.setUserId(1L);
        user.setUsername("testUsr");
        user.setPassword("testPass1");
        user.setConfirmedPassword("testPass2");

        userValidator.validate(user, errors);

        assertEquals(1, errors.getErrorCount());
    }
}
