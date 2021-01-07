package ru.glowgrew.spit.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import ru.glowgrew.spit.Message;

import java.lang.reflect.Type;

/**
 * @author glowgrew
 */
public class MessageSerializer {

    private final ObjectMapper mapper;

    public MessageSerializer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Serializes a message with the following format:
     * <p>
     * "Java message type:json message body"
     *
     * @param message the body of the message
     * @return serialized string
     */
    public byte[] serialize(Message message) {
        String json = "null";
        try {
            json = mapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return (message.getClass().getSimpleName() + ":" + json).getBytes();
    }

    /**
     * De-serializes a message with the following format:
     * <p>
     * "Java message type:json message body"
     *
     * @param content the json content
     * @param type    the java message type
     * @return serialized string
     */
    public <T> T deserialize(String content, Type type) {
        try {
            //noinspection unchecked
            return (T) mapper.readValue(content, TypeFactory.rawClass(type));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
