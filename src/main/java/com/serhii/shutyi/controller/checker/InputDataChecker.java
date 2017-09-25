package com.serhii.shutyi.controller.checker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class InputDataChecker {

    /**
     * Check if pattern matches string.
     * @param pattern
     * @param string
     */
     protected boolean isMatches(Pattern pattern, String string) {
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
}
