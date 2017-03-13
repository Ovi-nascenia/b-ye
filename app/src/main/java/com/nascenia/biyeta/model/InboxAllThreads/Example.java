
package com.nascenia.biyeta.model.InboxAllThreads;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("inbox")
    @Expose
    private List<Inbox> inbox = null;

    public List<Inbox> getInbox() {
        return inbox;
    }

    public void setInbox(List<Inbox> inbox) {
        this.inbox = inbox;
    }

}
