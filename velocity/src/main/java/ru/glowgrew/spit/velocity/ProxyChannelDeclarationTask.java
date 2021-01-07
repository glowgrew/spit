package ru.glowgrew.spit.velocity;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import ru.glowgrew.spit.SharedChannelConstants;
import ru.glowgrew.spit.amqp.AmqpException;

import java.io.IOException;

/**
 * Task responsible for declaring AMQP channel for Velocity.
 *
 * @author glowgrew
 */
public class ProxyChannelDeclarationTask implements Runnable {

    private final DefaultConsumer messageListener;
    private final String serverName;
    private final Channel channel;

    public ProxyChannelDeclarationTask(
            DefaultConsumer messageListener, String serverName, Channel channel
    ) {
        this.messageListener = messageListener;
        this.serverName = serverName;
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            channel.queueDeclare(SharedChannelConstants.PROXY, false, false, false, null);
            channel.queueDeclare(serverName, false, false, false, null);
            channel.basicConsume(SharedChannelConstants.PROXY, true, messageListener);
            channel.basicConsume(serverName, true, messageListener);
        } catch (IOException e) {
            throw new AmqpException(e);
        }
    }
}
