package edu.uga.cs.p4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ResultFragment extends Fragment {

    private int correctAnswers;

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
        resultTextView.setText("You got " + correctAnswers + " out of 6 questions correct.");

        return view;
    }
}
