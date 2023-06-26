package org.awesome.models.user;

import java.util.ResourceBundle;

public class LoginValidationException extends RuntimeException {

    private String field;

    private static ResourceBundle bundle = ResourceBundle.getBundle("messages.validations");

    public LoginValidationException(String field, String message) {
        super(bundle.getString(message));
        this.field = field;
    }

    public String getField() {
        return field;
    }
}