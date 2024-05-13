package com.example.sisa;// ChatsActivity.java
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sisa.ChatsAdapter;
import com.example.sisa.R;
import com.example.sisa.chatsmodel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class chats extends AppCompatActivity {
    private RecyclerView recyclerViewChats;
    private ChatsAdapter chatsAdapter;
    private List<nonmodel> chatsList;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);


        // Initialize RecyclerView
        recyclerViewChats = findViewById(R.id.recyclerViewChats);
        recyclerViewChats.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("nonResidents");

        // Initialize data list
        chatsList = new ArrayList<>();

        // Initialize adapter
        chatsAdapter = new ChatsAdapter(chatsList);

        // Set adapter to RecyclerView
        recyclerViewChats.setAdapter(chatsAdapter);

        // Retrieve data from Firebase
        retrieveDataFromFirebase();

    }

    private void retrieveDataFromFirebase() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(chats.this, "toastinggg", Toast.LENGTH_SHORT).show();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    nonmodel chatsModel = dataSnapshot.getValue(nonmodel.class);
                    chatsList.add(chatsModel);
                    Toast.makeText(chats.this, "List: "+chatsList.size(), Toast.LENGTH_SHORT).show();

                }

                // Notify the adapter that the data has changed
                chatsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors
            }
        });
    }
}
