package com.example.sisa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class student extends AppCompatActivity {
    TextView back;
    LinearLayout signup;
    EditText ed_username, ed_Password;
    // Initialize the Database Instance
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        // Hooks
        ed_username = findViewById(R.id.username);
        signup = findViewById(R.id.signup);
        ed_Password = findViewById(R.id.Password);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(student.this, register.class);
                Toast.makeText(student.this, "Please visit the registrar's office for registration. Thank you.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text from the input fields
                String username1 = ed_username.getText().toString().trim();
                String password = ed_Password.getText().toString().trim();

                if (username1.isEmpty() || password.isEmpty()) {
                    Toast.makeText(student.this, "All fields should contain a character", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the user with that username exists
                    Query query = databaseReference.child("users").orderByChild("username").equalTo(username1);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                // User with that username exists
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    String getPassword = dataSnapshot.child("password").getValue(String.class);
                                    if (password.equals(getPassword)) {
                                        // Login successful
                                        String getRole = dataSnapshot.child("role").getValue(String.class);
                                        // Redirect based on user role
                                        if (getRole.equals("student")) {
                                            Intent intent = new Intent(student.this, studentcheck.class);
                                            startActivity(intent);
                                            finish();
                                        } else if (getRole.equals("cod")) {
                                            Intent intent = new Intent(student.this, COD.class);
                                            startActivity(intent);
                                            finish();
                                        } else if (getRole.equals("admin")) {
                                            Intent intent = new Intent(student.this, ADMIN.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } else {
                                        // Incorrect password
                                        Toast.makeText(student.this, "Login Failed. Incorrect Credentials", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                // User with that username doesn't exist
                                Toast.makeText(student.this, "User doesn't exist. Kindly register first to proceed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(student.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
