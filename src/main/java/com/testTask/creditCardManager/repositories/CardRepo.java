package com.testTask.creditCardManager.repositories;

import com.testTask.creditCardManager.models.Card;
import com.testTask.creditCardManager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by giulio.farrugia.
 */
public interface CardRepo extends JpaRepository<Card, String> {

    Card findByCardNumber(String cardNumber);

    List<Card> findByCardNumberContaining(String cardNumber);

}
