package ru.glowgrew.spit.message;

import ru.glowgrew.spit.Message;

/**
 * @author glowgrew
 */
public class KickMessage implements Message {

    private String username, reason;

    public KickMessage() {
    }

    public KickMessage(String username, String reason) {
        this.username = username;
        this.reason = reason;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
