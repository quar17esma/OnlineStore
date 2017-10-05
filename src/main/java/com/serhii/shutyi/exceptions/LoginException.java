package com.serhii.shutyi.exceptions;

/**
 *
 * @author S.Shutyi
 * Created on 25.09.2017
 */
public class LoginException extends RuntimeException {
    /**
     * Contains inputted login
     */
    private String login;

    /**
     * Constructs an {@code LoginException} with the specified
     * detail message and inputted login.
     * @param message
     * @param login
     */
    public LoginException(String message, String login) {
        super(message);
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
