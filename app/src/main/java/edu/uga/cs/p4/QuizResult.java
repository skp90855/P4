package edu.uga.cs.p4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class QuizResult extends AppCompatActivity {

    int correct;
    QuizDatabaseHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        String timeSubmitted = "";
        DB = new QuizDatabaseHelper(this);
        try {

            Intent intent = getIntent();
            correct = intent.getIntExtra("correct", 0);

        } catch(Exception e) {
            e.printStackTrace();
        }
        TextView resultTextView = findViewById(R.id.textView);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //Instant now = Instant.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
            //LocalDateTime now = LocalDateTime.now();
            ZonedDateTime now = ZonedDateTime.now();
            ZonedDateTime zonedTime = now.withZoneSameInstant(ZoneId.of("-05:00"));
            System.out.println("Time submitted: " + dtf.format(zonedTime));
            timeSubmitted = dtf.format(zonedTime);
        }
        resultTextView.setText("You got " + correct + " out of 6 questions correct.\nSubmission Date: " + timeSubmitted);
        boolean inserted = DB.insertQuizData(timeSubmitted, ""+correct);
        if(inserted) {
            System.out.println("New entry has been inserted");
        } else {
            System.out.println("Failed to insert new entry");
        }

        Cursor res = DB.getQuizData();
        if(res.getCount() == 0) {
            System.out.println("There is no quiz data available");
        } else {
            StringBuffer buffer = new StringBuffer();
            while(res.moveToNext()) {
                buffer.append("Date Comepleted: " + res.getString(1) + "\n");
                buffer.append("Correct Questions: " + res.getString(2) + "\n\n");
            }
            System.out.println(buffer.toString());
        }

    }
}