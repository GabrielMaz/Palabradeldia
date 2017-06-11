package com.palabradeldia.palabradeldia.services;

public class Word {

    private int id;
    private String word;
    private String description;
    private int like;
    private int unlike;
    private int enabled;

    private String resp1;
    private String resp2;
    private String resp3;

    public Word(int id, String word, String description, int like, int unlike, int enabled) {
        this.id = id;
        this.word = word;
        this.description = description;
        this.like = like;
        this.unlike = unlike;
        this.enabled = enabled;
    }

    public Word(int id, String word, String description, int like, int unlike, int enabled, String resp1, String resp2, String resp3) {
        this.id = id;
        this.word = word;
        this.description = description;
        this.like = like;
        this.unlike = unlike;
        this.enabled = enabled;
        this.resp1 = resp1;
        this.resp2 = resp2;
        this.resp3 = resp3;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getUnlike() {
        return unlike;
    }

    public void setUnlike(int unlike) {
        this.unlike = unlike;
    }

    public int isEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getResp1() {
        return resp1;
    }

    public void setResp1(String resp1) {
        this.resp1 = resp1;
    }

    public String getResp2() {
        return resp2;
    }

    public void setResp2(String resp2) {
        this.resp2 = resp2;
    }

    public String getResp3() {
        return resp3;
    }

    public void setResp3(String resp3) {
        this.resp3 = resp3;
    }
}
