package edu.kvcc.cis298.cis298inclass2;

import java.util.UUID;

/**
 * Created by cisco on 10/4/2017.
 */

public class Crime {

    private UUID mId;
    private String mTitle;

    public Crime() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
