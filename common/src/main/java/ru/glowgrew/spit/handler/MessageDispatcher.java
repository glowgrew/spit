package ru.glowgrew.spit.handler;

import ru.glowgrew.spit.serialize.MessageDescriptor;
import ru.glowgrew.spit.serialize.MessageSerializer;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Class responsible for passing incoming messages to handlers.
 *
 * @author glowgrew
 */
public class MessageDispatcher {

    private final Map<String, MessageHandler<?>> handlers = new HashMap<>();
    private final MessageSerializer messageSerializer;

    public MessageDispatcher(MessageSerializer messageSerializer) {
        this.messageSerializer = messageSerializer;
    }

    /**
     * Registers message handler
     *
     * @param handler message handlers.
     */
    public void addHandler(MessageHandler<?> handler) {
        handlers.put(typeName(handler.getMessageType()), handler);
    }

    /**
     * De-serializes incoming message and passes it to the appropriate handler.
     *
     * @param payload raw serialized message payload.
     * @throws IllegalArgumentException if there is no handler corresponding to the message type
     */
    public void dispatch(byte[] payload) {
        MessageDescriptor message = StandardParser.INSTANCE.parse(payload);
        MessageHandler<?> messageHandler = handlers.get(message.getType());
        if (messageHandler == null) {
            throw new IllegalArgumentException("No handler registered for type " + message.getType());
        }
        messageHandler.accept(messageSerializer.deserialize(message.getContent(), messageHandler.getMessageType()));
    }

    private String typeName(Type type) {
        String typeName = type.getTypeName();
        return typeName.substring(typeName.lastIndexOf('.') + 1);
    }

    private enum StandardParser implements Parser {

        INSTANCE;

        @Override
        public MessageDescriptor parse(byte[] payload) {
            String dataString = new String(payload, StandardCharsets.UTF_8);
            int separator = dataString.indexOf(':');
            return new MessageDescriptor(dataString.substring(0, separator), dataString.substring(separator + 1));
        }
    }

    private interface Parser {

        MessageDescriptor parse(byte[] payload);
    }
}
