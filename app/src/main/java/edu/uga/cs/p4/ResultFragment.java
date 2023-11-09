package edu.uga.cs.p4;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ResultFragment extends Fragment {

    private int correctAnswers;

    public ResultFragment() {

    }
    // Create a new instance of the ResultFragment and pass the number of correct answers
    public static ResultFragment newInstance(int correctAnswers) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putInt("correctAnswers", correctAnswers);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            correctAnswers = getArguments().getInt("correctAnswers");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result_fragment, container, false);

        // Display the results
        TextView resultTextView = view.findViewById(R.id.resultTextView);
        resultTextView.setText("Your score: " + correctAnswers + " out of " + 6);

        // Store the result in the database
        QuizData quizData = new QuizData(getContext());
        quizData.open();
        String currentTime = getCurrentTime();
        quizData.storeQuizResult(currentTime, correctAnswers);
        quizData.close();

        // Add a button to go back to the SplashActivity
        Button backButton = view.findViewById(R.id.backButton); // Make sure you have this button in your layout
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SplashActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        return view;
    }

    // Helper method to get current time in a formatted string for API level 24
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault());
        return sdf.format(Calendar.getInstance().getTime());
    }




    public void setCorrectAnswers(int num) {
        correctAnswers = num;
    }

}
