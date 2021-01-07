package ru.glowgrew.spit;

/**
 * @author glowgrew
 */
public interface MessagingService {

    /**
     * Transfers a message to another server through its channel.
     *
     * @param message  the object that being sent
     * @param receiver the server (or a collection of servers) that will receive a message
     */
    void send(Message message, String receiver);
}
