package com.serhii.shutyi.controller.checker;

public class InputClientChecker extends InputDataChecker {
    private static final int NAME_LENGTH_MAX = 100;
    private static final int EMAIL_LENGTH_MAX = 100;

    public boolean isInputDataCorrect(String name, String email) {

        if (name == null || email == null) {
            return false;
        } else if (name.isEmpty() || email.isEmpty()) {
            return false;
        }

        return isMatches(CheckPatterns.NAME, name) &&
                isMatches(CheckPatterns.EMAIL, email) &&
                name.length() <= NAME_LENGTH_MAX &&
                email.length() <= EMAIL_LENGTH_MAX;
    }
}
