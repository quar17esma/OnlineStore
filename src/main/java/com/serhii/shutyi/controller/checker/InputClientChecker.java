package com.serhii.shutyi.controller.checker;

public class InputClientChecker extends InputDataChecker {

    public boolean isInputDataCorrect(String name, String email) {

        if (name == null || email == null) {
            return false;
        } else if (name.isEmpty() || email.isEmpty()) {
            return false;
        }

        return isMatches(CheckPatterns.NAME, name) &&
                isMatches(CheckPatterns.EMAIL, email);
    }
}
