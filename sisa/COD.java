package com.example.sisa;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class COD extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_SMS = 1;

    Button approve;
    private RecyclerView cod;
    private codAdpter codAdapter;
    private List<codmodel> codModelList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cod);

        approve = findViewById(R.id.approve);
        cod = findViewById(R.id.cod);
        cod.setLayoutManager(new LinearLayoutManager(this));
        codModelList = new ArrayList<>();
        codAdapter = new codAdpter(codModelList);
        cod.setAdapter(codAdapter);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("missingmarks");

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show dialog to enter registration number
                showRegNoInputDialog();
            }
        });

        retrieveDataFromFirebase();
    }

    private void showRegNoInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Registration Number");

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(layoutParams);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String regNo = input.getText().toString();
                if (!regNo.isEmpty()) {
                    // Query the database for the phone number associated with the registration number
                    Query phoneNumberQuery = databaseReference.orderByChild("regno").equalTo(regNo).limitToFirst(1);
                    phoneNumberQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // Get the phone number from the first result
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    String phoneNumber = childSnapshot.child("phonenumber").getValue(String.class);
                                    if (phoneNumber != null && !phoneNumber.isEmpty()) {
                                        // Check SMS permission and send message
                                        if (checkSmsPermission()) {
                                            sendSMS(phoneNumber);
                                        } else {
                                            requestSmsPermission();
                                        }
                                    } else {
                                        Toast.makeText(COD.this, "Phone number is null or empty", Toast.LENGTH_SHORT).show();
                                    }
                                    // Break the loop after processing the first result
                                    break;
                                }
                            } else {
                                Toast.makeText(COD.this, "No phone number found for registration number: " + regNo, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(COD.this, "Failed to retrieve phone number for registration number: " + regNo, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(COD.this, "Please enter a registration number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void retrieveDataFromFirebase() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    codmodel codModel = dataSnapshot.getValue(codmodel.class);
                    codModelList.add(codModel);
                }
                codAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(COD.this, "Failed to retrieve data from database", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkSmsPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestSmsPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_SMS);
    }

    private void sendSMS(String phoneNumber) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, "Your missing marks has been approved.", null, null);
        Toast.makeText(this, "Message Sent Successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, send SMS
                approve.performClick(); // Trigger the approve button click again
            } else {
                // Permission denied
                Toast.makeText(this, "Permission denied to send SMS", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
