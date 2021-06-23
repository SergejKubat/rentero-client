package com.metropolitan.rentero_client.model;

public class Reservation {

    private long id;
    private String carBrandAndModel;
    private String companyAddress;
    private String startDate;
    private String endDate;
    private double price;
    private boolean enabled;

    public Reservation() {
    }

    public Reservation(long id, String carBrandAndModel, String companyAddress, String startDate, String endDate, double price, boolean enabled) {
        this.id = id;
        this.carBrandAndModel = carBrandAndModel;
        this.companyAddress = companyAddress;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.enabled = enabled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCarBrandAndModel() {
        return carBrandAndModel;
    }

    public void setCarBrandAndModel(String carBrandAndModel) {
        this.carBrandAndModel = carBrandAndModel;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}