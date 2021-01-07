package ru.glowgrew.spit.amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import ru.glowgrew.spit.ShutdownHooks;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * A factory that responsible for creating AMQP messaging channels.
 *
 * @author glowgrew
 */
public class ChannelFactory {

    private final ConnectionFactory connectionFactory;
    private final ShutdownHooks shutdownHooks;

    public ChannelFactory(ConnectionFactory connectionFactory, ShutdownHooks shutdownHooks) {
        this.connectionFactory = connectionFactory;
        this.shutdownHooks = shutdownHooks;
    }

    /**
     * Creates a new RabbitMQ channel with shutdown hooks
     * to terminate a channel when server shuts down.
     *
     * @return created channel instance
     */
    public Channel newChannel() {
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            shutdownHooks.addHook(() -> close(connection));
            shutdownHooks.addHook(() -> close(channel));
            return channel;
        } catch (IOException | TimeoutException e) {
            throw new AmqpException(e);
        }
    }

    private void close(AutoCloseable autoCloseable) {
        try {
            autoCloseable.close();
        } catch (Exception e) {
            throw new AmqpException(e);
        }
    }
}
