package com.example.memo;

public class ChooseItem {
    private String content;
    private String date;
    private int state;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setState(int state){
        this.state = state;
    }
    public int getState(){
        return state;
    }
}
