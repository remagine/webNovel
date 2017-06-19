package com.arthur.webnovel.command;

public class StoryForm {
    private String title;
    private String description;
    private String foreword;
    private String tag;
    private String character;
    private String coAuthor;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getForeword() {
        return foreword;
    }
    public void setForeword(String foreword) {
        this.foreword = foreword;
    }
    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public String getCharacter() {
        return character;
    }
    public void setCharacter(String character) {
        this.character = character;
    }
    public String getCoAuthor() {
        return coAuthor;
    }
    public void setCoAuthor(String coAuthor) {
        this.coAuthor = coAuthor;
    }
}
