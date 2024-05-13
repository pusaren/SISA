package com.example.sisa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class register extends AppCompatActivity {
    TextInputEditText edUsername, edPassword, edConfirmPassword;

    //Initialize the Database Instance
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Hooks
        edUsername = findViewById(R.id.ed_username);
        edPassword = findViewById(R.id.ed_password);
        edConfirmPassword =findViewById(R.id.ed_confirm_password);

        // Set onClick listener to the Register Button
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get a unique id from Firebase
                String id = databaseReference.push().getKey();

                //get the text from the input fields
                String username1 = edUsername.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                String confirmpassword1 = edConfirmPassword.getText().toString().trim();
                //Intent intent=new Intent()

                if (username1.isEmpty() || password.isEmpty() || confirmpassword1.isEmpty()) {
                    Toast.makeText(register.this, "One of the field is empty", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmpassword1)) {
                    Toast.makeText(register.this, "The passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    UserModel user = new UserModel(id,username1,password, "student",Constants.getDate());

                    // Ensures that a user with the same email cannot register more than once
                    Query query = databaseReference.child("users").orderByChild("username").equalTo(username1);

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Toast.makeText(register.this, "That email has already been taken", Toast.LENGTH_SHORT).show();
                            } else {
                                databaseReference.child("users").child(id).setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                clearInputFields();
                                                finish();
                                            }

                                            private void clearInputFields() {
                                                edUsername.getText().clear();
                                                edPassword.getText().clear();
                                                edConfirmPassword.getText().clear();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        // Set onClick listener to the Back Button
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(register.this, ADMIN.class);
                startActivity(intent);
            }
        });
    }
}
