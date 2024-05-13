package com.example.sisa;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sisa.query;

public class finance extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);

        // Button to check fees
        Button feesButton = findViewById(R.id.feesButton);
        feesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFees();
            }
        });

        // Button to query
        Button queryButton = findViewById(R.id.query);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query();
            }
        });
    }

    // Method to handle checking fees
    private void checkFees() {
        // Check if permission to write to external storage is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        } else {
            // Permission granted, start download
            startDownload();
        }
    }

    // Method to start the download
    private void startDownload() {
        // URL to download fees data from
        String fileUrl = "https://example.com/fees";

        // Create a DownloadManager.Request with the file URL
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl));

        // Set the title of the download notification
        request.setTitle("Fees");

        // Set the description of the download notification
        request.setDescription("Downloading fees data...");

        // Set the destination directory for the downloaded file
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "fees.pdf");

        // Get the download service and enqueue the download request
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
            Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to start download", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to handle querying
    private void query() {
        // Implement the logic for querying here
        // For example, you can open a new activity for querying
        Intent intent = new Intent(finance.this, query.class);
        startActivity(intent);
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start download
                startDownload();
            } else {
                // Permission denied, show a message
                Toast.makeText(this, "Permission denied. Cannot download fees data.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
