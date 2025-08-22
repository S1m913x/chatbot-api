package org.yjys.api.domain.ai.model.vo;

public class Choices {

    private int index;

    private Message message;

    private  String loggrobs;

    private String finish_reason;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getLoggrobs() {
        return loggrobs;
    }

    public void setLoggrobs(String loggrobs) {
        this.loggrobs = loggrobs;
    }

    public String getFinish_reason() {
        return finish_reason;
    }

    public void setFinish_reason(String finish_reason) {
        this.finish_reason = finish_reason;
    }
}
