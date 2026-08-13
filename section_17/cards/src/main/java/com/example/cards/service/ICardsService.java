package com.example.cards.service;

import com.example.cards.dto.CardsDto;
import jakarta.validation.constraints.Pattern;

public interface ICardsService {

    /**
     * @param mobileNumber - Input Mobile Number
     */
    void createCard(String mobileNumber);

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Cards Details based on a given mobileNumber
     */
    CardsDto fetchCard(String mobileNumber);

    /**
     * @param cardsDto - CustomerDto Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    boolean updateCard(CardsDto cardsDto);

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    boolean deleteCard(String mobileNumber);

}
