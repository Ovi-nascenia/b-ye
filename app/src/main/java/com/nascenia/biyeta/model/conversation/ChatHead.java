
package com.nascenia.biyeta.model.conversation;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatHead {

    @SerializedName("messages")
    @Expose
    private List<Message> messages = null;
    @SerializedName("total_message")
    @Expose
    private Integer totalMessage;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Integer getTotalMessage() {
        return totalMessage;
    }

    public void setTotalMessage(Integer totalMessage) {
        this.totalMessage = totalMessage;
    }

}
