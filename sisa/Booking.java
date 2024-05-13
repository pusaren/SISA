package com.example.sisa;

public class Booking {
    String hostel,room,gender,date;
    public Booking(String selectedHostel, String selectedRoom, String selectedGender) {
    }

    public Booking() {
    }

    public Booking(String hostel, String room, String gender, String date) {
        this.hostel = hostel;
        this.room = room;
        this.gender = gender;
        this.date = date;
    }

    public String getHostel() {
        return hostel;
    }

    public String getRoom() {
        return room;
    }

    public String getGender() {
        return gender;
    }

    public String getDate() {
        return date;
    }
}
