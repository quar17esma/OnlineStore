package com.serhii.shutyi.controller.checker;

import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class InputClientCheckerTest {
    @Test
    public void returnFalseWithNameNull() throws Exception {
        String name = null;
        String email = "some@gmail.com";

        InputClientChecker inputClientChecker = new InputClientChecker();
        boolean result = inputClientChecker.isInputDataCorrect(name, email);

        assertFalse(result);
    }

    @Test
    public void returnFalseWithEmailNull() throws Exception {
        String name = "John";
        String email = null;

        InputClientChecker inputClientChecker = new InputClientChecker();
        boolean result = inputClientChecker.isInputDataCorrect(name, email);

        assertFalse(result);
    }

    @Test
    public void returnFalseWithNameEmpty() throws Exception {
        String name = "";
        String email = "some@gmail.com";

        InputClientChecker inputClientChecker = new InputClientChecker();
        boolean result = inputClientChecker.isInputDataCorrect(name, email);

        assertFalse(result);
    }

    @Test
    public void returnFalseWithEmailEmpty() throws Exception {
        String name = "John";
        String email = "";

        InputClientChecker inputClientChecker = new InputClientChecker();
        boolean result = inputClientChecker.isInputDataCorrect(name, email);

        assertFalse(result);
    }

    @Test
    public void returnTrueWithCorrectEmailName() throws Exception {
        String name = "John";
        String email = "john@gmail.com";

        InputClientChecker inputClientChecker = new InputClientChecker();
        boolean result = inputClientChecker.isInputDataCorrect(name, email);

        assertTrue(result);
    }

    @Test
    public void returnFalseWithCorrectEmailNameWithNumbers() throws Exception {
        String name = "John123";
        String email = "john@gmail.com";

        InputClientChecker inputClientChecker = new InputClientChecker();
        boolean result = inputClientChecker.isInputDataCorrect(name, email);

        assertFalse(result);
    }

    @Test
    public void returnFalseWithCorrectNameWrongEmail() throws Exception {
        String name = "John";
        String email = "johngmail.com";

        InputClientChecker inputClientChecker = new InputClientChecker();
        boolean result = inputClientChecker.isInputDataCorrect(name, email);

        assertFalse(result);
    }
}