package ru.glowgrew.spit.serialize;

/**
 * @author glowgrew
 */
public class MessageDescriptor {

    private String type, content;

    public MessageDescriptor(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
