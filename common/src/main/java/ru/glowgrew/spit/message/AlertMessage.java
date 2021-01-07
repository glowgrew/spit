package ru.glowgrew.spit.message;

import ru.glowgrew.spit.Message;

/**
 * @author glowgrew
 */
public class AlertMessage implements Message {

    private String message;

    public AlertMessage() {
    }

    public AlertMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
