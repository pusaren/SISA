package com.example.sisa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class schools extends AppCompatActivity {
    LinearLayout scit,sohes,sosci,sobe,sess;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schools);
        scit=findViewById(R.id.scit);
        scit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(schools.this, student.class);
                startActivity(intent);
            }
        });
    }
}