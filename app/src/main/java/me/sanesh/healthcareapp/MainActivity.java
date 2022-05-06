package me.sanesh.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    static int PERMISSION_CODE = 100;
    MaterialButton btnChatbot, btnBMI, btnCall, btnSignOut;

MaterialButton btnChatbot, btnShareLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnChatbot = findViewById(R.id.btnNavChatbot);
        btnBMI = findViewById(R.id.btnNavBMI);
        btnCall = findViewById(R.id.btnNavCallAmbulance);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnShareLocation = findViewById(R.id.btnNavCallAmbulance);

        btnChatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatbotActivity.class);
                startActivity(intent);
            }
        });

        btnBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BMIActivity.class);
                startActivity(intent);
            }
        });

        if (ContextCompat.checkSelfPermission(getApplicationContext().getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            ;
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, PERMISSION_CODE);

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + 1919));
                startActivity(intent);
            }
        });

        btnSignOut.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        });

        btnShareLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LocationShareActivity.class);
                startActivity(intent);
            }
        });
    }
}