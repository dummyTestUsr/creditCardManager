package com.testTask.creditCardManager.controllers;

import com.testTask.creditCardManager.models.Card;
import com.testTask.creditCardManager.models.User;
import com.testTask.creditCardManager.services.CardService;
import com.testTask.creditCardManager.services.SecurityService;
import com.testTask.creditCardManager.services.UserService;
import com.testTask.creditCardManager.validations.CardValidator;
import com.testTask.creditCardManager.validations.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by giulio.farrugia.
 */
@Controller
public class CreditCardManagerController {

    @Autowired
    UserService userService;

    @Autowired
    CardService cardService;

    @Autowired
    SecurityService securityService;

    @Autowired
    CardValidator cardValidator;

    @Autowired
    UserValidator userValidator;

    private static final Logger LOG = LoggerFactory.getLogger(CreditCardManagerController.class);

    /**
     * Service used to retrieve the registration page.
     *
     * @param model the UI Model.
     * @return the registration template.
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * The service used to create a new user.
     *
     * @param user          The user to be created.
     * @param bindingResult the binding result for validations.
     * @param model         the UI Model.
     * @return template according to whether attempt was successful.
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {

        // validating the user
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Data entered is invalid!");
            LOG.error("Data entered is invalid!");
            return "register";
        }

        userService.storeUser(user);
        return "login";
    }

    /**
     * The service retrieving the login template.
     *
     * @param model  the UI Model.
     * @param error  any errors occurred.
     * @param logout logout indicator.
     * @return the login template.
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {

        if (error != null) {
            model.addAttribute("error", "Your username/password combination is invalid.");
        }

        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }

        return "login";
    }

    /**
     * The Card Information Template.
     *
     * @param model the UI Model.
     * @return the Card Information template.
     */
    @RequestMapping(value = {"/", "/cardInformation"}, method = RequestMethod.GET)
    public String viewCardInformation(Model model) {
        model.addAttribute("card", new Card());
        return "cardInformation";
    }

    /**
     * The Card Search Template.
     *
     * @param model the UI Model.
     * @return the Card Search Template.
     */
    @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
    public String searchCard(Model model) {
        model.addAttribute("card", new Card());
        return "search";
    }

    /**
     * The service used to persist a Card.
     *
     * @param card          The card to be persisted.
     * @param bindingResult the binding result for validations.
     * @param model         the UI Model.
     * @return a particular template according to whether attempt was successful.
     */
    @RequestMapping(value = {"/storeCard"}, method = RequestMethod.POST)
    public String storeCard(@ModelAttribute("card") Card card, BindingResult bindingResult, Model model) {

        // validating the card.
        cardValidator.validate(card, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Data entered is invalid!");
            LOG.error("Data entered is invalid!");
            return "cardInformation";
        }

        // setting the creator to the current user.
        User user = userService.findByUsername(securityService.getCurrentUser());

        card.setCreator(user);
        cardService.storeCard(card);
        return "search";
    }

    /**
     * The Service used to perform a search for Cards.
     *
     * @param card          The card by which number the search is to be performed.
     * @param bindingResult the binding result for validations.
     * @param model         the UI Model.
     * @return the search template.
     */
    @RequestMapping(value = {"/search"}, method = RequestMethod.POST)
    public String searchCard(@ModelAttribute("card") Card card, BindingResult bindingResult, Model model) {

        String cardNumber = bindingResult.getFieldValue("cardNumber").toString();
        List<Card> result = cardService.findByCardNumberContaining(cardNumber);
        model.addAttribute("searchResults", result);
        return "search";
    }
}
