package com.serhii.shutyi.controller.checker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for checking inputted data of good.
 *
 * @author S.Shutyi
 * Created on 25.09.2017.
 */
public class InputGoodChecker {
    private static final int DESCRIPTION_LENGTH = 1000;
    private static final int PRICE_MIN = 0;
    private static final int QUANTITY_MIN = 0;

    public boolean isInputDataCorrect(String name, String description, int price, int quantity) {

        if (name.isEmpty() ||
                name == null ||
                description.isEmpty() ||
                description == null) {
            return false;
        }

        return (matcher(CheckPatterns.NAME, name) &&
                (description.length() <= DESCRIPTION_LENGTH) &&
                (price >= PRICE_MIN) &&
                (quantity >= QUANTITY_MIN));
    }

    /**
     * Check if pattern matches string.
     * @param pattern
     * @param string
     */
    private boolean matcher(Pattern pattern, String string) {
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
