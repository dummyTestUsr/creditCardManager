package com.testTask.creditCardManager.services;

import com.testTask.creditCardManager.models.Card;
import com.testTask.creditCardManager.models.Role;
import com.testTask.creditCardManager.models.User;
import com.testTask.creditCardManager.repositories.CardRepo;
import com.testTask.creditCardManager.repositories.UserRepo;
import com.testTask.creditCardManager.validations.CardValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by giulio.farrugia.
 */
@Service
public class CardServiceImpl implements CardService {

    @Autowired
    CardRepo cardRepo;

    @Autowired
    SecurityService securityService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    CardValidator cardValidator;

    private static final Logger LOG = LoggerFactory.getLogger(CardServiceImpl.class);

    /**
     * Checks whether the card is existing or new. If new,
     * perform an Insert, otherwise update the expiryDate
     * and perform an update.
     *
     * @param card The card to be persisted.
     */
    @Override
    public void storeCard(Card card) {

        // check if card already exists.
        Card cardRetrieved = findByCardNumber(card.getCardNumber());

        if (cardRetrieved != null && cardValidator.validateExpiryDate(card.getExpiryDate())) {

            // update the card by the expiry date and perform update.
            cardRetrieved.setExpiryDate(card.getExpiryDate());

            LOG.info("Card to be updated!");
            cardRepo.save(cardRetrieved);
        } else {

            // insert the new card.

            LOG.info("New Card to be inserted!");
            cardRepo.save(card);
        }
    }

    @Override
    public Card findByCardNumber(String cardNumber) {
        return cardRepo.findByCardNumber(cardNumber);
    }

    /**
     * @param cardNumber the given Card Number.
     * @return a List of Cards which match the given card number and are persmissible
     * to viewed by the current user, according to the user roles.
     */
    @Override
    public List<Card> findByCardNumberContaining(String cardNumber) {

        // retrieve the list of cards
        List<Card> cards = cardRepo.findByCardNumberContaining(cardNumber);
        List<Card> result = new ArrayList<>();

        // retrieve the current user
        User user = userRepo.findByUsername(securityService.getCurrentUser());

        // check whether the current user is the creator or an admin.
        cards.forEach(card -> {
            Set<String> rolesByUser = user.getRoles().stream().map(Role::getRoleName).collect(Collectors
                .toSet());
            if (card.getCreator().getUserId().equals(user.getUserId()) || rolesByUser.contains("admin")) {
                result.add(card);
            }
        });

        return result;
    }

}
