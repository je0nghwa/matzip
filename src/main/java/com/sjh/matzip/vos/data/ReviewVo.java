package com.sjh.matzip.vos.data;

import com.sjh.matzip.entities.data.ReviewEntity;

public class ReviewVo extends ReviewEntity {
    private String userNickname;
    private int[] imageIndexes;

    public String getUserNickname() {
        return userNickname;
    }

    public ReviewVo setUserNickname(String userNickname) {
        this.userNickname = userNickname;
        return this;
    }

    public int[] getImageIndexes() {
        return imageIndexes;
    }

    public ReviewVo setImageIndexes(int[] imageIndexes) {
        this.imageIndexes = imageIndexes;
        return this;
    }
}
