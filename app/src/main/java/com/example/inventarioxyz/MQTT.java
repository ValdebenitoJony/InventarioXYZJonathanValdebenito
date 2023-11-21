package com.example.inventarioxyz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MQTT extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mqtt);
    }

    public void volver(View view) {
        Intent intent = new Intent(MQTT.this, Lista.class);
        startActivity(intent);

    }
}