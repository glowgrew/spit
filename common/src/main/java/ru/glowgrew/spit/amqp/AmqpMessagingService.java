package ru.glowgrew.spit.amqp;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import ru.glowgrew.spit.Message;
import ru.glowgrew.spit.MessagingException;
import ru.glowgrew.spit.MessagingService;
import ru.glowgrew.spit.serialize.MessageSerializer;

import java.io.IOException;

/**
 * @author glowgrew
 */
public class AmqpMessagingService implements MessagingService {

    private final Channel channel;
    private final MessageSerializer messageSerializer;

    public AmqpMessagingService(Channel channel, MessageSerializer messageSerializer) {
        this.channel = channel;
        this.messageSerializer = messageSerializer;
    }

    @Override
    public void send(Message message, String receiver) {
        try {
            channel.basicPublish("",
                                 receiver,
                                 new AMQP.BasicProperties.Builder().build(),
                                 messageSerializer.serialize(message));
        } catch (IOException e) {
            throw new MessagingException(e);
        }
    }
}
