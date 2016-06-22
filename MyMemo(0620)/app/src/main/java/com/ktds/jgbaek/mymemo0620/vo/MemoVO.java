package com.ktds.jgbaek.mymemo0620.vo;

/**
 * Created by 206-008 on 2016-06-20.
 */
public class MemoVO {

    private int _id;

    private String title;
    private String content;
    private String date;


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
}
