package me.sanesh.healthcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
MaterialButton btnChatbot, btnShareLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnChatbot = findViewById(R.id.btnNavChatbot);
        btnShareLocation = findViewById(R.id.btnNavCallAmbulance);

        btnChatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChatbotActivity.class);
                startActivity(intent);
            }
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