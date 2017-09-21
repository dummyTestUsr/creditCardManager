package com.testTask.creditCardManager.services;

import com.testTask.creditCardManager.models.Card;

import java.util.List;

/**
 * Created by giulio.farrugia.
 */
public interface CardService {

    /**
     * Used to persist a card into the database.
     *
     * @param card The card to be persisted.
     */
    void storeCard(Card card);

    /**
     * Retrieves a single card by its Card Number
     *
     * @param cardNumber The given Card Number.
     * @return the card retrieved from the database.
     */
    Card findByCardNumber(String cardNumber);

    /**
     * Retrieves a list of card which are retrieved using the LIKE operator.
     *
     * @param cardNumber the given Card Number.
     * @return a list of Cards which have their card number partially or fully matchin the given Card Number.
     */
    List<Card> findByCardNumberContaining(String cardNumber);

}
