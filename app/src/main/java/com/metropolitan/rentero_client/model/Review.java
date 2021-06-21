package com.metropolitan.rentero_client.model;

public class Review {

    private long id;
    private long customerId;
    private int mark;
    private String comment;
    private String dateCreated;

    public Review() {
    }

    public Review(long id, long customerId, int mark, String comment, String dateCreated) {
        this.id = id;
        this.customerId = customerId;
        this.mark = mark;
        this.comment = comment;
        this.dateCreated = dateCreated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
