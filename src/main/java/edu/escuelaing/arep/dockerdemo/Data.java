package edu.escuelaing.arep.dockerdemo;

import java.util.Date;

public class Data {
    private String text;
    private Date date;

    public Data(){}

    public Data(String text, Date date){
        this.text=text;

        this.date=date;
    }

    public Data(String text){
        this.text=text;
        date= new Date();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}