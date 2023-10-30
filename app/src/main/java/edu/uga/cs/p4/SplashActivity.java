package edu.uga.cs.p4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_splash);

        //Initializing the button
        Button buttonOpenMain = findViewById(R.id.buttonOpenMain);

        //Setting a click listener for the button
        buttonOpenMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the main activity when the button is clicked
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close the splash activity
            }
        });
    }
}
