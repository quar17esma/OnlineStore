package com.serhii.shutyi.controller.checker;

import org.junit.Test;

import static org.junit.Assert.*;

public class InputGoodCheckerTest {

    @Test
    public void returnTrueCorrectData() throws Exception {
        String name = "Samsung S8";
        String description = "Some description, some description, some description 123";
        int price = 123456;
        int quantity = 99;

        InputGoodChecker checker = new InputGoodChecker();
        boolean result = checker.isInputDataCorrect(name, description, price, quantity);

        assertTrue(result);
    }

    @Test
    public void returnFalseNameNull() throws Exception {
        String name = null;
        String description = "Some description, some description, some description 123";
        int price = 123456;
        int quantity = 99;

        InputGoodChecker checker = new InputGoodChecker();
        boolean result = checker.isInputDataCorrect(name, description, price, quantity);

        assertFalse(result);
    }

    @Test
    public void returnFalseDescriptionNull() throws Exception {
        String name = "Samsung S8";
        String description = null;
        int price = 123456;
        int quantity = 99;

        InputGoodChecker checker = new InputGoodChecker();
        boolean result = checker.isInputDataCorrect(name, description, price, quantity);

        assertFalse(result);
    }

    @Test
    public void returnFalseNameEmpty() throws Exception {
        String name = "";
        String description = "Some description, some description, some description 123";
        int price = 123456;
        int quantity = 99;

        InputGoodChecker checker = new InputGoodChecker();
        boolean result = checker.isInputDataCorrect(name, description, price, quantity);

        assertFalse(result);
    }

    @Test
    public void returnFalseDescriptionEmpty() throws Exception {
        String name = "Samsung S8";
        String description = "";
        int price = 123456;
        int quantity = 99;

        InputGoodChecker checker = new InputGoodChecker();
        boolean result = checker.isInputDataCorrect(name, description, price, quantity);

        assertFalse(result);
    }

    @Test
    public void returnFalsePriceNegative() throws Exception {
        String name = "Samsung S8";
        String description = "Some description, some description, some description 123";
        int price = -2;
        int quantity = 99;

        InputGoodChecker checker = new InputGoodChecker();
        boolean result = checker.isInputDataCorrect(name, description, price, quantity);

        assertFalse(result);
    }

    @Test
    public void returnFalseQuantityNegative() throws Exception {
        String name = "Samsung S8";
        String description = "Some description, some description, some description 123";
        int price = 123456;
        int quantity = -1;

        InputGoodChecker checker = new InputGoodChecker();
        boolean result = checker.isInputDataCorrect(name, description, price, quantity);

        assertFalse(result);
    }
}