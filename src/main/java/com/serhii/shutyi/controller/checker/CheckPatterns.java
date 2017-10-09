package com.serhii.shutyi.controller.checker;

import java.util.regex.Pattern;

public interface CheckPatterns {
    Pattern NAME = Pattern.compile("[A-Z][A-Za-z ]+");
    Pattern EMAIL = Pattern.compile("[a-zA-Z1-9\\-._]+@[a-z1-9]+(.[a-z1-9]+)+");
    Pattern CHAR_DIGIT_PUNCT = Pattern.compile("[A-Za-z0-9 _.,!\"'/$:;()]+");
}
