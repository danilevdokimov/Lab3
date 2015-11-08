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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Participant that = (Participant) o;

        if (fio != null ? !fio.equals(that.fio) : that.fio != null) return false;
        return !(post != null ? !post.equals(that.post) : that.post != null);

    }

    @Override
    public int hashCode() {
        int result = fio != null ? fio.hashCode() : 0;
        result = 31 * result + (post != null ? post.hashCode() : 0);
        return result;
    }
}
