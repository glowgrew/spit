package ru.glowgrew.spit.handler;

import java.lang.reflect.Type;

/**
 * This class is responsible for handling incoming messages of a certain type.
 *
 * @param <T> type of the messages the handler handles
 * @author glowgrew
 */
public interface MessageHandler<T> {

    /**
     * A message handling logic.
     *
     * @param message body of message
     */
    void accept(T message);

    /**
     * A Java type of the message.
     *
     * @return message type.
     */
    Type getMessageType();
}
