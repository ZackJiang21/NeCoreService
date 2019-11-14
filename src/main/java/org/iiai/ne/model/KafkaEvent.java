package org.iiai.ne.model;

public class KafkaEvent {
    private String type;

    private String body;

    public KafkaEvent() {
    }

    public KafkaEvent(String type, String body) {
        this.type = type;
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
