package com.example.sisa;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class hostels extends AppCompatActivity {

    private final String[] hostelsMale = {"Select hostel", "Ngaira", "Chilson", "Amugune", "House D"};
    private final String[] hostelsFemale = {"Select hostel", "Nolega", "Mwaitsi", "Amugisi", "Tana"};
    private final List<String> availableRooms = new ArrayList<>();
    private Spinner hostelSpinner, roomSpinner;
    private RadioGroup genderRadioGroup;
    private EditText usernameEditText, phonenumberEditText; // Added EditText field for username and phone number
    private Button submitButton;
    private DatabaseReference databaseReference;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostels);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("selected_data");
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        hostelSpinner = findViewById(R.id.hostel);
        roomSpinner = findViewById(R.id.room);
        genderRadioGroup = findViewById(R.id.gender);
        usernameEditText = findViewById(R.id.username);
        phonenumberEditText = findViewById(R.id.phoneh); // Initialize phone number EditText
        submitButton = findViewById(R.id.hostelsubmit);
        setupHostelSpinner();
        setupRoomSpinner();
        submitButton.setOnClickListener(v -> hostelsubmit());
        genderRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            if (radioButton != null) {
                String selectedGender = radioButton.getText().toString();
                if (selectedGender.equals("Male")) {
                    setupHostelSpinner();
                } else if (selectedGender.equals("Female")) {
                    setupHostelSpinner();
                }
            }
        });
    }

    private void setupHostelSpinner() {
        String[] hostels;
        RadioButton selectedRadioButton = findViewById(genderRadioGroup.getCheckedRadioButtonId());
        if (selectedRadioButton != null && selectedRadioButton.getText().toString().equals("Male")) {
            hostels = hostelsMale;
        } else {
            hostels = hostelsFemale;
        }
        ArrayAdapter<String> hostelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hostels);
        hostelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hostelSpinner.setAdapter(hostelAdapter);
    }

    private void setupRoomSpinner() {
        availableRooms.clear();
        availableRooms.add("Select room");
        for (int i = 1; i <= 15; i++) {
            availableRooms.add("Room " + i);
        }
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, availableRooms);
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(roomAdapter);
    }

    private void hostelsubmit() {
        final String selectedHostel = hostelSpinner.getSelectedItem().toString();
        final String selectedRoom = roomSpinner.getSelectedItem().toString();
        RadioButton selectedRadioButton = findViewById(genderRadioGroup.getCheckedRadioButtonId());
        final String selectedGender = selectedRadioButton != null ? selectedRadioButton.getText().toString() : "";
        final String username = usernameEditText.getText().toString().trim();
        final String phoneNumber = phonenumberEditText.getText().toString().trim(); // Get phone number from EditText

        if (selectedHostel.equals("Select hostel")) {
            Toast.makeText(this, "Please select a hostel", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedRoom.equals("Select room")) {
            Toast.makeText(this, "Please select a room", Toast.LENGTH_SHORT).show();
            return;
        }

        if (username.isEmpty()) {
            Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the phone number is a valid Kenyan number starting with "+254" and is numeric with 13 digits
        if (!phoneNumber.matches("\\+254\\d{9}")) {
            Toast.makeText(this, "Phone number must start with '+254' and have 10 digits after the country code", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if username already exists in the database
        usersRef.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(hostels.this, "Username already exists", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the selected room already has two users booked
                    Query roomQuery = databaseReference.orderByChild("room").equalTo(selectedRoom);
                    roomQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getChildrenCount() >= 2) {
                                Toast.makeText(hostels.this, "This room is already fully booked", Toast.LENGTH_SHORT).show();
                            } else {
                                // Save the selected data to the database
                                DatabaseReference newBookingRef = databaseReference.push();
                                newBookingRef.child("username").setValue(username);
                                newBookingRef.child("hostel").setValue(selectedHostel);
                                newBookingRef.child("room").setValue(selectedRoom);
                                newBookingRef.child("gender").setValue(selectedGender);
                                newBookingRef.child("phonenumber").setValue(phoneNumber); // Save phone number
                                newBookingRef.child("timestamp").setValue(ServerValue.TIMESTAMP); // Save timestamp

                                // Clear fields
                                clearFields();

                                Toast.makeText(hostels.this, "Booking submitted successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(hostels.this, "Failed to check room availability", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(hostels.this, "Failed to check username", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to clear the fields
    private void clearFields() {
        // Reset hostel spinner to default
        hostelSpinner.setSelection(0);
        // Reset room spinner to default
        roomSpinner.setSelection(0);
        // Clear radio button selection
        genderRadioGroup.clearCheck();
        // Clear username and phone number fields
        usernameEditText.setText("");
        phonenumberEditText.setText("");
    }
}
