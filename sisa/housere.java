package com.example.sisa;

public class housere {
    private final String username;
    private final String phonenumber;
    private final String gender;
    private final String house;
    private final String room;

    public housere(String username, String phonenumber, String gender, String house, String room) {
        this.username = username;
        this.phonenumber = phonenumber;
        this.gender = gender;
        this.house = house;
        this.room = room;
    }

    public String getUsername() {
        return username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getGender() {
        return gender;
    }

    public String getHouse() {
        return house;
    }

    public String getRoom() {
        return room;
    }
}
