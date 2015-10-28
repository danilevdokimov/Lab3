package com.danil.asus.shared;

/**
 * Created by Asus on 10/20/2015.
 */
public class Participant {
    private String fio;
    private String post;

    public Participant() {
    }

    public Participant(String fio, String post) {
        this.fio = fio;
        this.post = post;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
