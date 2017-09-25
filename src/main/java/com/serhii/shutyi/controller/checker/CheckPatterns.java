package com.serhii.shutyi.controller.checker;

import java.util.regex.Pattern;

/**
 * The {@code CheckPatterns} interface contains patterns to check user's input.
 *
 * @author S.Shutyi
 * Created on 25.09.2017.
 */
public interface CheckPatterns {
    Pattern NAME = Pattern.compile("[A-Z][a-z]+");
    Pattern EMAIL = Pattern.compile("[a-zA-Z1-9\\-\\._]+@[a-z1-9]+(.[a-z1-9]+){1,}");
    Pattern NICKNAME = Pattern.compile("[a-zA-Z0-9_.-]{3,}");
    Pattern ANY = Pattern.compile(".*");
    Pattern PHONE_NUMBER = Pattern.compile("\\+?\\d+([\\(\\s\\-]?\\d+[\\)\\s\\-]?[\\d\\s\\-]+)?");
    Pattern POST_INDEX = Pattern.compile("[0-9]{5}");
    Pattern NUMBER = Pattern.compile("[0-9]{1,5}[a-z]?");
}
