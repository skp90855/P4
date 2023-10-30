package edu.uga.cs.p4;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class QuizPagerAdapter extends FragmentStateAdapter {

    private String[] questions;
    private String[][] answerChoices;

    public QuizPagerAdapter(
            FragmentManager fragmentManager,
            Lifecycle lifecycle,
            String[] questions,
            String[][] answerChoices) {
        super(fragmentManager, lifecycle);
        this.questions = questions;
        this.answerChoices = answerChoices;
    }

    @Override
    public Fragment createFragment(int position) {
        // Check if the position is within bounds
        if (position >= 0 && position < questions.length && position < answerChoices.length) {
            String question = questions[position];
            String choice1 = answerChoices[position][0];
            String choice2 = answerChoices[position][1];
            String choice3 = answerChoices[position][2];

            // Ensure that you return a valid Fragment instance
            return QuizFragment.newInstance(position, question, choice1, choice2, choice3);
        } else {
            // Return a default or empty Fragment in case of an invalid position
            return new Fragment();
        }
    }


    @Override
    public int getItemCount() {
        // Return the number of questions or the number of fragments you want to display
        return questions.length;
    }
}
