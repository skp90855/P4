package edu.uga.cs.p4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class QuizFragment extends Fragment {

    private int versionNum;
    private String question;
    private String choice1;
    private String choice2;
    private String choice3;

    public QuizFragment() {
        // Required empty public constructor
    }

    public static QuizFragment newInstance(int versionNum, String question, String choice1, String choice2, String choice3) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putInt("versionNum", versionNum);
        args.putString("question", question);
        args.putString("choice1", choice1);
        args.putString("choice2", choice2);
        args.putString("choice3", choice3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            versionNum = getArguments().getInt("versionNum");
            question = getArguments().getString("question");
            choice1 = getArguments().getString("choice1");
            choice2 = getArguments().getString("choice2");
            choice3 = getArguments().getString("choice3");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView questionView = view.findViewById(R.id.questionView);
        RadioButton choice1View = view.findViewById(R.id.choice1);
        RadioButton choice2View = view.findViewById(R.id.choice2);
        RadioButton choice3View = view.findViewById(R.id.choice3);

        // Set the question and answer choices in the fragment
        questionView.setText(question);
        choice1View.setText(choice1);
        choice2View.setText(choice2);
        choice3View.setText(choice3);
    }
}
