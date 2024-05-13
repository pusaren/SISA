package com.example.sisa;

public class dropAdpter {
    String[] hostelNames = {"Select Hostel", "Ngaira", "Nolega", "Mwaitsi", "Tana", "Amugune", "House D"};
    String[] roomNumbers = {"Room 1", "Room 2", "Room 3", "Room 4", "Room 5", "Room 6", "Room 7", "Room 8", "Room 9", "Room 10"};

    public dropAdpter(String[] hostelNames, String[] roomNumbers) {
        this.hostelNames = hostelNames;
        this.roomNumbers = roomNumbers;
    }

    public String[] getHostelNames() {
        return hostelNames;
    }

    public String[] getRoomNumbers() {
        return roomNumbers;
    }
}
