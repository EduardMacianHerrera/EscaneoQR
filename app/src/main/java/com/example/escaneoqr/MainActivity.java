package com.example.escaneoqr;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private int requestCode = 1;
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    System.out.println("Permisos concedidos");
                } else {
                    System.out.println("Permisos no concedidos");
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        button.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(
                    MainActivity.this, Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(this, ScannerActivity.class);
                startActivityForResult(intent, requestCode);

            } else {
                requestPermissionLauncher.launch(
                        Manifest.permission.CAMERA);
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            textView.setText(data.getData().toString());
        }
    }

}