package com.serhii.shutyi.exceptions;

/**
 *
 *
 * @author S.Shutyi
 * Created on 29.07.2017
 */
public class BusyEmailException extends Exception {
    /**
     * Contains inputted client name
     */
    private String name;

    /**
     * Contains inputted client email
     */
    private String email;

    /**
     * Constructs an {@code BusyEmailException} with the specified
     * detail message and inputted data.
     * @param message
     * @param name
     * @param email
     */
    public BusyEmailException(String message, String name, String email) {
        super(message);
        this.name = name;
        this.email = email;
    }

    public BusyEmailException(BusyEmailException e) {
         super(e.getMessage());
         this.name = e.name;
         this.email = e.email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
