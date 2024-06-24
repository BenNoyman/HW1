package com.example.hw1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.textview.MaterialTextView;

public class EndOfGameActivity extends AppCompatActivity {

    public static final String KEY_STATUS = "KEY_STATUS";
    private MaterialTextView main_status;
    private AppCompatButton main_Reset_button;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_game);
        
        findViews();
        initViews();
    }
    private void initViews() {
        String status = getIntent().getStringExtra(KEY_STATUS);
        main_status.setText(status);
        main_Reset_button.setOnClickListener(v -> resetGame());
    }

    private void resetGame() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void findViews() {
        main_status = findViewById(R.id.main_status);
        main_Reset_button =findViewById(R.id.main_Reset_button);
    }
}