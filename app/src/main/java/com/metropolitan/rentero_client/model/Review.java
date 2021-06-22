package com.metropolitan.rentero_client.model;

public class Review {

    private long id;
    private String customerName;
    private String customerAvatar;
    private int mark;
    private String comment;
    private String dateCreated;

    public Review() {
    }

    public Review(long id, String customerName, String customerAvatar, int mark, String comment, String dateCreated) {
        this.id = id;
        this.customerName = customerName;
        this.customerAvatar = customerAvatar;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAvatar() {
        return customerAvatar;
    }

    public void setCustomerAvatar(String customerAvatar) {
        this.customerAvatar = customerAvatar;
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