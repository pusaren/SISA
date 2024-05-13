package com.example.sisa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class query extends AppCompatActivity {
    private static final int REQUEST_PICK_PDF_FILE = 101;
    private Uri pdfUri;

    private EditText fnameEditText, regnoEditText, emailEditText, phoneEditText, amountEditText, upnameEditText;
    private Button submitButton;
    private ImageButton uploadButton;

    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        // Initialize Firebase Database and Storage references
        databaseReference = FirebaseDatabase.getInstance().getReference().child("queries");
        storageReference = FirebaseStorage.getInstance().getReference();

        // Initialize UI components
        fnameEditText = findViewById(R.id.fname);
        regnoEditText = findViewById(R.id.regno);
        emailEditText = findViewById(R.id.Email);
        phoneEditText = findViewById(R.id.phone);
        amountEditText = findViewById(R.id.amount);
        upnameEditText = findViewById(R.id.upname);
        submitButton = findViewById(R.id.submit2);
        uploadButton = findViewById(R.id.upload);

        // Handle submit button click
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitQuery();
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker();
            }
        });
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf"); // Limit to PDF files only
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select PDF file"), REQUEST_PICK_PDF_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_PDF_FILE && resultCode == RESULT_OK && data != null) {
            pdfUri = data.getData();
            if (pdfUri != null) {
                Toast.makeText(query.this, "PDF selected: " + pdfUri.getPath(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadFile() {
        if (pdfUri != null) {
            StorageReference fileRef = storageReference.child("uploads").child("query.pdf");
            fileRef.putFile(pdfUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Get the download URL for the uploaded file
                                    String downloadUrl = uri.toString();
                                    // Store the download URL in the database along with other query details
                                    DatabaseReference newQueryRef = databaseReference.push();
                                    newQueryRef.child("fname").setValue(fnameEditText.getText().toString().trim());
                                    newQueryRef.child("regno").setValue(regnoEditText.getText().toString().trim());
                                    newQueryRef.child("email").setValue(emailEditText.getText().toString().trim());
                                    newQueryRef.child("phone").setValue(phoneEditText.getText().toString().trim());
                                    newQueryRef.child("amount").setValue(amountEditText.getText().toString().trim());
                                    newQueryRef.child("upname").setValue(upnameEditText.getText().toString().trim());
                                    newQueryRef.child("pdfUrl").setValue(downloadUrl)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(query.this, "Query submitted successfully!", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(query.this, "Failed to write to database: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                    // Clear input fields after submission
                                    clearFields();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(query.this, "Failed to upload PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(query.this, "No PDF selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitQuery() {
        String fname = fnameEditText.getText().toString().trim();
        String regno = regnoEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String amount = amountEditText.getText().toString().trim();
        String upname = upnameEditText.getText().toString().trim();

        // Validate input fields
        if (fname.isEmpty()) {
            fnameEditText.setError("Please enter your name");
            fnameEditText.requestFocus();
            return;
        }

        if (!fname.matches("[a-zA-Z]+")) {
            fnameEditText.setError("Name should only contain letters");
            fnameEditText.requestFocus();
            return;
        }

        if (regno.isEmpty()) {
            regnoEditText.setError("Please enter your registration number");
            regnoEditText.requestFocus();
            return;
        }

        if (regno.length() > 15) {
            regnoEditText.setError("Registration number should not exceed 15 characters");
            regnoEditText.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            emailEditText.setError("Please enter your email");
            emailEditText.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please enter a valid email address");
            emailEditText.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            phoneEditText.setError("Please enter your phone number");
            phoneEditText.requestFocus();
            return;
        }

        if (!phone.matches("254\\d{9,12}")) {
            phoneEditText.setError("Please enter a valid Kenya phone number starting with 254");
            phoneEditText.requestFocus();
            return;
        }

        if (amount.isEmpty()) {
            amountEditText.setError("Please enter the amount");
            amountEditText.requestFocus();
            return;
        }

        int amountValue = Integer.parseInt(amount);
        if (amountValue < 1 || amountValue > 1000000) {
            amountEditText.setError("Amount should be between 1 and 1,000,000");
            amountEditText.requestFocus();
            return;
        }

        if (upname.isEmpty()) {
            upnameEditText.setError("Please enter the name of the upload");
            upnameEditText.requestFocus();
            return;
        }
        uploadFile();
    }

    private void clearFields() {
        EditText fnameEditText = findViewById(R.id.fname);
        EditText regnoEditText = findViewById(R.id.regno);
        EditText emailEditText = findViewById(R.id.Email);
        EditText phoneEditText = findViewById(R.id.phone);
        EditText amountEditText = findViewById(R.id.amount);
        EditText uploadNameEditText = findViewById(R.id.upname);

        fnameEditText.setText("");
        regnoEditText.setText("");
        emailEditText.setText("");
        phoneEditText.setText("");
        amountEditText.setText("");
        uploadNameEditText.setText("");
    }
}
