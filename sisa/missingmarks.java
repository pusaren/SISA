package com.example.sisa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class missingmarks extends AppCompatActivity {
    EditText school, department, studentname, regno, phonenumber, unitcode;
    ImageButton chat;
    Button submitmis;
    RadioButton cat, main, special, sup, retake;

    // Initialize the Database Instance
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missingmarks);

        // Hooks
        school = findViewById(R.id.school);
        department = findViewById(R.id.department);
        studentname = findViewById(R.id.studentname);
        regno = findViewById(R.id.regno);
        phonenumber = findViewById(R.id.phonenumber);
        unitcode = findViewById(R.id.unitcode);
        cat = findViewById(R.id.cat);
        main = findViewById(R.id.main);
        special = findViewById(R.id.special);
        sup = findViewById(R.id.sup);
        chat = findViewById(R.id.chatm);
        retake = findViewById(R.id.retake);
        submitmis = findViewById(R.id.submitmis);

        submitmis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input values
                String schooled = school.getText().toString().trim();
                String departmented = department.getText().toString().trim();
                String studentnameed = studentname.getText().toString().trim();
                String regnoed = regno.getText().toString().trim();
                String phonenumbered = phonenumber.getText().toString().trim();
                String unitcodeed = unitcode.getText().toString().trim();
                String selectedRadioButton = getSelectedRadioButtonText();

                // Validate input fields
                if (schooled.isEmpty() || departmented.isEmpty() || studentnameed.isEmpty() || regnoed.isEmpty() ||
                        phonenumbered.isEmpty() || unitcodeed.isEmpty() || selectedRadioButton.isEmpty()) {
                    Toast.makeText(missingmarks.this, "All fields should be filled", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!regnoed.matches("[a-zA-Z0-9]+(/[0-9]+){2}")) {
                    Toast.makeText(missingmarks.this, "Invalid registration number format", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!phonenumbered.matches("\\+254[0-9]{9}")) {
                    Toast.makeText(missingmarks.this, "Invalid phone number format", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save data to Firebase
                String id = databaseReference.child("missingmarks").push().getKey(); // Generate a unique ID
                if (id != null) {
                    marksmodel missingmarks = new marksmodel(id, schooled, departmented, studentnameed, regnoed, phonenumbered, unitcodeed, selectedRadioButton, Constants.getDate());
                    databaseReference.child("missingmarks").child(id).setValue(missingmarks)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(missingmarks.this, "Added successfully", Toast.LENGTH_SHORT).show();
                                    clearInputFields();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(missingmarks.this, "Failed to add data", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(missingmarks.this, chatmis.class);
                startActivity(intent);
            }
        });
    }

    private String getSelectedRadioButtonText() {
        if (cat.isChecked()) {
            return cat.getText().toString().trim();
        } else if (main.isChecked()) {
            return main.getText().toString().trim();
        } else if (special.isChecked()) {
            return special.getText().toString().trim();
        } else if (sup.isChecked()) {
            return sup.getText().toString().trim();
        } else if (retake.isChecked()) {
            return retake.getText().toString().trim();
        }
        return "";
    }

    private void clearInputFields() {
        school.getText().clear();
        department.getText().clear();
        studentname.getText().clear();
        regno.getText().clear();
        phonenumber.getText().clear();
        unitcode.getText().clear();
        cat.setChecked(false);
        main.setChecked(false);
        special.setChecked(false);
        sup.setChecked(false);
        retake.setChecked(false);
    }
}
