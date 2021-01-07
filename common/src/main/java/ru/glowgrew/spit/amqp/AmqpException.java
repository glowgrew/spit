package ru.glowgrew.spit.amqp;

/**
 * @author glowgrew
 */
public class AmqpException extends RuntimeException {

    public AmqpException(Throwable throwable) {
        super(throwable);
    }
}
