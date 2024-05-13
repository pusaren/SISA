package com.example.sisa;// ChatsActivity.java
import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class chatmis extends AppCompatActivity {
    private RecyclerView chatmis;
    private com.example.sisa.chatmisadapter chatmisadapter;
    private List<chatmismodel> chatmisList;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatmis);

        // Initialize RecyclerView
        chatmis = findViewById(R.id.chatmis);
        chatmis.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("missingmarks");

        // Initialize data list
        chatmisList = new ArrayList<>();

        // Initialize adapter
       chatmisadapter = new chatmisadapter(chatmisList);

        // Set adapter to RecyclerView
        chatmis.setAdapter(chatmisadapter);

        // Retrieve data from Firebase
        retrieveDataFromFirebase();
    }

    private void retrieveDataFromFirebase() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    chatmismodel chatmisModel = dataSnapshot.getValue(chatmismodel.class);
                    chatmisList.add(chatmisModel);
                }

                // Notify the adapter that the data has changed
                //chatmisadapter.notifyDataSetChanged();
                chatmisadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });
    }
}
