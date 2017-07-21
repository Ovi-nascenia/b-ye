
package com.nascenia.biyeta.model.InboxAllThreads;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("inbox")
    @Expose
    private List<Inbox> inbox = null;



    @SerializedName("current_user_signed_in")
    @Expose
    private  int current_user_signed_in;

    public List<Inbox> getInbox() {
        return inbox;
    }

    public void setInbox(List<Inbox> inbox) {
        this.inbox = inbox;
    }

    public void setCurrent_user_signed_in(int current_user_signed_in)
    {
        this.current_user_signed_in=current_user_signed_in;
    }
    public int getCurrent_user_signed_in()
    {
        return current_user_signed_in;
    }

}
