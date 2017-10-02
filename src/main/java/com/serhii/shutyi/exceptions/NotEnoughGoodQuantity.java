package com.serhii.shutyi.exceptions;

import com.serhii.shutyi.entity.Good;

public class NotEnoughGoodQuantity extends RuntimeException {

    private Good good;

    /**
     * Constructs an {@code BusyEmailException} with the specified
     * detail message and inputted data.
     * @param message
     */
    public NotEnoughGoodQuantity(String message, Good good) {
        super(message);
        this.good = good;
    }

    public NotEnoughGoodQuantity(NotEnoughGoodQuantity e) {
        super(e.getMessage());
        this.good = e.getGood();
    }

    public Good getGood() {
        return good;
    }
}
