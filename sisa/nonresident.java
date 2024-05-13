package com.example.sisa;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class nonresident extends AppCompatActivity {
    EditText estatename,Rnumber,Phone,email,issue;
   ImageButton chat;
   Button booking;

    //Initialize the Database Instance
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nonresident);

        // Hooks
        estatename = findViewById(R.id.estatename);
        issue = findViewById(R.id.issue);
        Rnumber = findViewById(R.id.Rnumber);
        Phone = findViewById(R.id.Phone);
        email = findViewById(R.id.Email);
        chat = findViewById(R.id.chat);
        booking = findViewById(R.id.booking);  // Add this line

        // Set onClick listener to the Register Button
        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get a unique id from Firebase
                String id = databaseReference.push().getKey();
                String estatename1 = estatename.getText().toString().trim();
                String issue1 = issue.getText().toString().trim();
                String Rnumber1 = Rnumber.getText().toString().trim();
                String Phone1 = Phone.getText().toString().trim();
                String email1 = email.getText().toString().trim();

                // Check if the fields are empty
                if (estatename1.isEmpty() || issue1.isEmpty() || Rnumber1.isEmpty() || Phone1.isEmpty() || email1.isEmpty()) {
                    Toast.makeText(nonresident.this, "All fields should be filled", Toast.LENGTH_SHORT).show();
                } else {
                    nonmodel nonResident = new nonmodel(id, estatename1, Rnumber1, Phone1, email1, issue1, "student", Constants.getDate());

                    // Ensures that a user with the same email cannot register more than once
                    databaseReference.child("nonResidents").child(id).setValue(nonResident)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(nonresident.this, "Added successfully", Toast.LENGTH_SHORT).show();
                                    clearInputFields();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(nonresident.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        // Move these outside the onClick listener
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(nonresident.this, chats.class);
                startActivity(intent);
            }
        });

        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(nonresident.this, hostels.class);
                startActivity(intent);
            }
        });
    }

    // Add the clearInputFields method outside of the onCreate method
    private void clearInputFields() {
        estatename.getText().clear();
        issue.getText().clear();
        Rnumber.getText().clear();
        Phone.getText().clear();
        email.getText().clear();
        issue.getText().clear();
    }

}//