package com.testTask.creditCardManager.validations;

import static junit.framework.TestCase.assertEquals;

import com.testTask.creditCardManager.models.Card;
import com.testTask.creditCardManager.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
public class CardValidatorTest {

    @Autowired
    CardValidator cardValidator;
    public Errors errors;

    /**
     * Scenario: Test validation with different date formats:
     */
    @Test
    public void validateExpiryDate(){

        assertEquals(false, cardValidator.validateExpiryDate("01"));
        assertEquals(false, cardValidator.validateExpiryDate("01/10/1988"));
        assertEquals(false, cardValidator.validateExpiryDate("30/10/1990"));
        assertEquals(false, cardValidator.validateExpiryDate("12/22/1990"));
        assertEquals(false, cardValidator.validateExpiryDate("1998/12/01"));
        assertEquals(false, cardValidator.validateExpiryDate("01/21"));
        assertEquals(false, cardValidator.validateExpiryDate("01/30"));

        assertEquals(true, cardValidator.validateExpiryDate("01/01"));
        assertEquals(true, cardValidator.validateExpiryDate("12/11"));
        assertEquals(true, cardValidator.validateExpiryDate("17/03"));
        assertEquals(true, cardValidator.validateExpiryDate("10/10"));
    }

    /**
     * Scenario Valid Card.
     */
    @Test
    public void validateCard(){

        Card card = new Card();
        User user = Mockito.mock(User.class);
        errors = new BeanPropertyBindingResult(card, "card");

        card.setCreator(user);
        card.setCardNumber("123456789123456");
        card.setExpiryDate("10/10");
        card.setClientName("Mr. X Brown");

        cardValidator.validate(card, errors);

        assertEquals(0, errors.getErrorCount());
    }

    /**
     * Scenario Invalid Card by Card number
     */
    @Test
    public void invalidCard1(){

        Card card = new Card();
        User user = Mockito.mock(User.class);
        errors = new BeanPropertyBindingResult(card, "card");

        card.setCreator(user);
        card.setCardNumber("123");
        card.setExpiryDate("10/10");
        card.setClientName("Mr. X Brown");

        cardValidator.validate(card, errors);

        assertEquals(1, errors.getErrorCount());
    }

    /**
     * Scenario Invalid Card by Card number and date.
     */
    @Test
    public void invalidCard2(){

        Card card = new Card();
        User user = Mockito.mock(User.class);
        errors = new BeanPropertyBindingResult(card, "card");

        card.setCreator(user);
        card.setCardNumber("123");
        card.setExpiryDate("2017/10");
        card.setClientName("Mr. X Brown");

        cardValidator.validate(card, errors);

        assertEquals(2, errors.getErrorCount());
    }

    /**
     * Scenario Invalid Card by multiple variables.
     */
    @Test
    public void invalidCard3(){

        Card card = new Card();
        User user = Mockito.mock(User.class);
        errors = new BeanPropertyBindingResult(card, "card");

        card.setCreator(user);
        card.setCardNumber("123");
        card.setExpiryDate("2017/10");
        card.setClientName("p");

        cardValidator.validate(card, errors);

        assertEquals(3, errors.getErrorCount());
    }


}
