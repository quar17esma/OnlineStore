package com.serhii.shutyi.controller.checker;

public class InputClientChecker extends InputDataChecker {

    public boolean isInputDataCorrect(String name, String email) {

        if (name.isEmpty() ||
                name == null ||
                email.isEmpty() ||
                email == null) {
            return false;
        }

        return isMatches(CheckPatterns.NAME, name) &&
                isMatches(CheckPatterns.EMAIL, email);
    }
}
