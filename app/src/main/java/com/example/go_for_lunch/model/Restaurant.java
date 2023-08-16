package com.example.go_for_lunch.model;

public class Restaurant {

    private long id;

    private String name;

    private String address;

    private String phoneNumber;

    private String rating;

    private String type;

    private Enum hours;

    private Enum distance;

    private String comment;

    public Restaurant(long id, String name, String address, String phoneNumber, String rating, String type, Enum hours, Enum distance, String comment) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.type = type;
        this.hours = hours;
        this.distance = distance;
        this.comment = comment;
    }

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public String getPhoneNumber() {return phoneNumber;}

    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    public String getRating() {return rating;}

    public void setRating(String rating) {this.rating = rating;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public Enum getHours() {return hours;}

    public void setHours(Enum hours) {this.hours = hours;}

    public Enum getDistance() {return distance;}

    public void setDistance(Enum distance) {this.distance = distance;}

    public String getComment() {return comment;}

    public void setComment(String comment) {this.comment = comment;}

}
