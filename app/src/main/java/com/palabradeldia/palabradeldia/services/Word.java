package com.palabradeldia.palabradeldia.services;

public class Word {

    private int id;
    private String word;
    private String description;
    private int like;
    private int unlike;
    private int enabled;

    public Word(int id, String word, String description, int like, int unlike, int enabled) {
        this.id = id;
        this.word = word;
        this.description = description;
        this.like = like;
        this.unlike = unlike;
        this.enabled = enabled;
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
}
