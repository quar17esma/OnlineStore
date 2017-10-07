package com.serhii.shutyi.controller.checker;

/**
 * Class for checking inputted data of good.
 *
 * @author S.Shutyi
 * Created on 25.09.2017.
 */
public class InputGoodChecker extends InputDataChecker {
    private static final int DESCRIPTION_LENGTH_MAX = 1000;
    private static final int NAME_LENGTH_MAX = 100;
    private static final int PRICE_MIN = 0;
    private static final int QUANTITY_MIN = 0;

    public boolean isInputDataCorrect(String name, String description, int price, int quantity) {

        if (name == null || description == null) {
            return false;
        } else if (name.isEmpty() || description.isEmpty()) {
            return false;
        }

        return isMatches(CheckPatterns.CHAR_DIGIT_PUNCT, name) &&
                isMatches(CheckPatterns.CHAR_DIGIT_PUNCT, description) &&
                name.length() <= NAME_LENGTH_MAX &&
                description.length() <= DESCRIPTION_LENGTH_MAX &&
                price >= PRICE_MIN &&
                quantity >= QUANTITY_MIN;
    }
}
