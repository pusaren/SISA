package com.example.sisa;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ADMIN extends AppCompatActivity {
    private static final String TAG = "admin";
    DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Get a reference to the "users" node in the Firebase database
        usersRef = FirebaseDatabase.getInstance().getReference("users");

        LinearLayout add = findViewById(R.id.Add);
        LinearLayout privilege = findViewById(R.id.revock);
        LinearLayout remove = findViewById(R.id.remove);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ADMIN.this, register.class);
                startActivity(intent);
            }
        });

        privilege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangePrivilegeDialog();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRemoveUserDialog();
            }
        });
    }

    private void showChangePrivilegeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Username");

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(layoutParams);
        builder.setView(input);

        builder.setPositiveButton("Grant Admin Privileges", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String username = input.getText().toString();
                // Grant admin privileges to the user
                grantAdminPrivileges(username);
            }
        });

        builder.setNegativeButton("Revoke Admin Privileges", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String username = input.getText().toString();
                // Revoke admin privileges from the user
                revokeAdminPrivileges(username);
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void revokeAdminPrivileges(final String username) {
        Query query = usersRef.orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String currentRole = snapshot.child("role").getValue(String.class);
                        if (currentRole.equals("admin") || currentRole.equals("cod")) {
                            snapshot.getRef().child("role").setValue("regular")
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(ADMIN.this, "Admin privileges revoked successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(Exception e) {
                                            Toast.makeText(ADMIN.this, "Failed to revoke admin privileges: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            Log.e(TAG, "Failed to revoke admin privileges: " + e.getMessage());
                                        }
                                    });
                        } else {
                            Toast.makeText(ADMIN.this, "User does not have admin privileges", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(ADMIN.this, "User not found in database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ADMIN.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Database error: " + databaseError.getMessage());
            }
        });
    }

    private void grantAdminPrivileges(final String username) {
        Query query = usersRef.orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String currentRole = snapshot.child("role").getValue(String.class);
                        if (currentRole.equals("student") || currentRole.equals("cod")) {
                            snapshot.getRef().child("role").setValue("admin")
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(ADMIN.this, "Admin privileges granted successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(Exception e) {
                                            Toast.makeText(ADMIN.this, "Failed to grant admin privileges: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            Log.e(TAG, "Failed to grant admin privileges: " + e.getMessage());
                                        }
                                    });
                        } else {
                            Toast.makeText(ADMIN.this, "User already has admin privileges", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(ADMIN.this, "User not found in database", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ADMIN.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Database error: " + databaseError.getMessage());
            }
        });
    }

    private void showRemoveUserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Username");

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(layoutParams);
        builder.setView(input);

        builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String username = input.getText().toString();
                checkAndDeleteUser(username);
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

    private void checkAndDeleteUser(final String username) {
        Query query = usersRef.orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    dataSnapshot.getRef().removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ADMIN.this, "User removed successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(Exception e) {
                                    Toast.makeText(ADMIN.this, "Failed to remove user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.e(TAG, "Failed to remove user: " + e.getMessage());
                                }
                            });
                } else {
                    Toast.makeText(ADMIN.this, "User not found in database", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ADMIN.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Database error: " + databaseError.getMessage());
            }
        });
    }
}
