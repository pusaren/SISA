package com.example.sisa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class studentcheck extends AppCompatActivity {
    LinearLayout finance, non_resident;
    Button marks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentcheck);
        finance = findViewById(R.id.finance);
        non_resident = findViewById(R.id.non_residents);
        marks = findViewById(R.id.marks);
        finance.setOnClickListener(view -> {
            Intent intent = new Intent(studentcheck.this, finance.class);
            startActivity(intent);
        });
        non_resident.setOnClickListener(view -> {
            Intent intent = new Intent(studentcheck.this, nonresident.class);
            startActivity(intent);
        });
        marks.setOnClickListener(view -> {
            Intent intent = new Intent(studentcheck.this, missingmarks.class);
            startActivity(intent);
        });
    }
}
