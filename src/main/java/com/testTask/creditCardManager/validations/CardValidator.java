package com.testTask.creditCardManager.validations;


import com.testTask.creditCardManager.models.Card;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by giulio.farrugia.
 */
@Component
public class CardValidator implements Validator {

    @Value("${card.cardNumber.minLength}")
    private int cardNumberMinLength;

    @Value("${card.cardNumber.maxLength}")
    private int cardNumberMaxLength;

    @Value("${card.clientName.minLength}")
    private int clientNameMinLength;

    @Value("${card.clientName.max}")
    private int clientNameMaxLength;

    @Override
    public boolean supports(Class<?> aClass) {
        return Card.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Card card = (Card) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cardNumber", "Card Number cannot be empty!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "clientName", "Client Name cannot be empty!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expiryDate", "Expiry Date cannot be empty!");

        if (!validateExpiryDate(card.getExpiryDate())) {
            errors.rejectValue("expiryDate", "Incorrect format!");
        }

        if (!card.getCardNumber().matches("[0-9]+") || card.getCardNumber().length() < cardNumberMinLength || card.getCardNumber()
            .length() > cardNumberMaxLength) {
            errors.rejectValue("cardNumber", "Incorrect length!");
        }

        if (card.getClientName().length() < clientNameMinLength || card.getClientName().length() > clientNameMaxLength) {
            errors.rejectValue("clientName", "Incorrect length!");
        }

    }

    /**
     * Validates an expiry date according to the requested format (yy/MM).
     *
     * @param inputDate The date to be validated.
     * @return true if date is in the correct format.
     */
    public boolean validateExpiryDate(String inputDate) {

        if (!(inputDate.contains("/") && inputDate.length()==5)){
            return false;
        }
        try {
            DateFormat formatter = new SimpleDateFormat("yy/MM");
            formatter.setLenient(false);
            formatter.parse(inputDate);
        } catch (ParseException e) {
            return false;
        }

        return true;
    }
}
