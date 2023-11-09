package edu.uga.cs.p4;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class QuizPagerAdapter extends FragmentStateAdapter {

    private String[] questions;
    private String[][] answerChoices;

    private int correctAnswers = 0;

    private String[] actualAnswer;
    private QuizFragment[] quizFragments;
    private ResultFragment result;

    private int currPage = 0;

    public QuizPagerAdapter(
            FragmentManager fragmentManager,
            Lifecycle lifecycle,
            String[] questions,
            String[][] answerChoices,
            String[] correctAnswers) {
        super(fragmentManager, lifecycle);
        this.questions = questions;
        this.answerChoices = answerChoices;
        this.actualAnswer = correctAnswers;
        quizFragments = new QuizFragment[6];
    }

    // Modify createFragment to handle the ResultFragment
    @Override
    public Fragment createFragment(int position) {
        if (position >= 0 && position < questions.length) {

            String question = questions[position];
            String choice1 = answerChoices[position][0];
            String choice2 = answerChoices[position][1];
            String choice3 = answerChoices[position][2];
            String correctAnswer = actualAnswer[position];
            currPage++;
            System.out.println(currPage);
            quizFragments[position] = QuizFragment.newInstance(position, question, choice1, choice2, choice3, correctAnswer);
            // Ensure that you return a valid Fragment instance
            return quizFragments[position];

        } else if (position == questions.length) {
            // ResultFragment creation
            return ResultFragment.newInstance(getCorrectAnswers());
        } else {
            // Return a default or empty Fragment in case of an invalid position
            return new Fragment();
        }
    }


    @Override
    public int getItemCount() {
        // Return the number of questions or the number of fragments you want to display
        return questions.length + 2;
    }

    public int getCorrectAnswers() {
        int i = 0;
        int correctAnswers = 0;
        while(i < quizFragments.length && quizFragments[i] != null) {
            correctAnswers += quizFragments[i].correct;
            i++;
        }
        if(result != null) {
            result.setCorrectAnswers(correctAnswers);
        }
        return correctAnswers;
    }

    //public int
}
