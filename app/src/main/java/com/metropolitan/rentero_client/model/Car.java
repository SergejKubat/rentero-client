package com.metropolitan.rentero_client.model;

public class Car {

    private long id;
    private String brand;
    private String model;
    private String mainImageUrl;
    private int capacity;
    private int doors;
    private int hp;
    private int engineSize;
    private int year;
    private String fuel;
    private String gearStick;
    private String description;
    private double pricePerDay;
    private boolean reserved;

    public Car() {
    }

    public Car(long id, String brand, String model, String mainImageUrl, int capacity,
               int doors, int hp, int engineSize, int year, String fuel, String gearStick,
               String description, double pricePerDay, boolean reserved) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.mainImageUrl = mainImageUrl;
        this.capacity = capacity;
        this.doors = doors;
        this.hp = hp;
        this.engineSize = engineSize;
        this.year = year;
        this.fuel = fuel;
        this.gearStick = gearStick;
        this.description = description;
        this.pricePerDay = pricePerDay;
        this.reserved = reserved;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(int engineSize) {
        this.engineSize = engineSize;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getGearStick() {
        return gearStick;
    }

    public void setGearStick(String gearStick) {
        this.gearStick = gearStick;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
}