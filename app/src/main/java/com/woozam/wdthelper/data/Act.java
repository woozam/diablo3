package com.woozam.wdthelper.data;

import com.google.gson.annotations.SerializedName;

public class Act extends AbsData {

    private static final long serialVersionUID = -4683121686265355843L;

    @SerializedName("completed")
    private boolean completed;
    @SerializedName("completedQuests")
    private CompletedQuest[] completedQuests;

    public Act() {
        super();
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public CompletedQuest[] getCompletedQuests() {
        return completedQuests;
    }

    public void setCompletedQuests(CompletedQuest[] completedQuests) {
        this.completedQuests = completedQuests;
    }
}