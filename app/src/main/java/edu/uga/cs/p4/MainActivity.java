package edu.uga.cs.p4;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    //A boolean variable to keep track of whether swiping is enabled in the ViewPager2
    private boolean isSwipingEnabled = true;
    private boolean inInitialStartup = false;


    QuizPagerAdapter quizPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Setting the content view to the layout defined in xml file
        setContentView(R.layout.activity_main);

        //Reading data from the CSV file and populate questions and answerChoices
        String[] questions = new String[6];
        String[] correctAnswers = new String[6];
        String[][] answerChoices = new String[6][3];
        //this.deleteDatabase("QuizApp.db"); Uncomment this to delete db for any updates

        try {
            InputStream inputStream = getAssets().open("StateCapitals.csv");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            List<String[]> csvData = new ArrayList<>();
            String line;

            //Reading and storing CSV data
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                csvData.add(data);
            }

            //Choosing random states and capital cities without repeats
            Random random = new Random();
            Set<Integer> selectedIndices = new HashSet<>();

            for (int i = 0; i < 6; i++) {
                int randomIndex;
                do {
                    randomIndex = random.nextInt(csvData.size());
                } while (selectedIndices.contains(randomIndex));

                selectedIndices.add(randomIndex);

                String[] data = csvData.get(randomIndex);

                //Getting the state and the capital city associated with the state
                String state = data[0];
                String correctCapital = data[1];

                questions[i] = "What is the capital of " + state + "?";
                correctAnswers[i] = data[1];

                //Choosing two incorrect capital cities
                List<String> incorrectCities = new ArrayList<>();
                for (int j = 2; j < data.length; j++) {
                    incorrectCities.add(data[j]);
                }
                incorrectCities.remove(correctCapital);

                //Randomly shuffling the choices
                List<String> choices = new ArrayList<>();
                choices.add(correctCapital);
                choices.add(incorrectCities.get(0));
                choices.add(incorrectCities.get(1));

                //Shuffling the choices to randomize their order
                for (int j = 0; j < 3; j++) {
                    int index = random.nextInt(choices.size());
                    answerChoices[i][j] = choices.get(index);
                    choices.remove(index);
                }
            }

            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Finding the ViewPager2 defined in the layout and disabling user swiping initially
        ViewPager2 pager = findViewById(R.id.viewpager);
        pager.setUserInputEnabled(false);

        //Creating an instance of QuizPagerAdapter with the questions and answers
        quizPagerAdapter = new QuizPagerAdapter(
                getSupportFragmentManager(), getLifecycle(), questions, answerChoices, correctAnswers);

        //Setting the orientation of the ViewPager2 to horizontal
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        //Setting the adapter of the ViewPager2 to the instance of QuizPagerAdapter
        pager.setAdapter(quizPagerAdapter);
        QuizDatabaseHelper DB = new QuizDatabaseHelper(this);
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                super.onPageSelected(position);
                if(position == 6) {
                    quizPagerAdapter.finishQuiz();
                    DB.clearPreviousQuiz();
                }
                //Enabling or disabling swiping based on the current position.
                isSwipingEnabled = position < 6;
                pager.setUserInputEnabled(isSwipingEnabled);
            }
        });

        new AsyncTask<ThreadInstructionHandler, String>().execute(new ThreadInstructionHandler<MainActivity>("write", this));


    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("Paused");
        new AsyncTask<ThreadInstructionHandler, String>().execute(new ThreadInstructionHandler<MainActivity>("write", this));
        /*
        String[] questions = quizPagerAdapter.getQuestions();
        String[][] answerChoices = quizPagerAdapter.getAnswerChoices();
        String[] actualAnswers = quizPagerAdapter.getActualAnswer();
        int[] chosenAnswer = quizPagerAdapter.getChosenAnswers();
        QuizDatabaseHelper DB = new QuizDatabaseHelper(this);
        DB.clearPreviousQuiz();
        for(int i = 0; i < 6; i++) {
            boolean inserted = DB.insertQuesitons(questions[i], actualAnswers[i], answerChoices[i][0], answerChoices[i][1], answerChoices[i][2], chosenAnswer[i]);
            System.out.println("inserting index: " + i);
            System.out.println(questions[i]);
            if(inserted) {
                System.out.println("inserted current quiz");
            } else {
                System.out.println("current quiz was not successfully inserted");
            }
        }

         */
    }

    @Override
    protected void onResume() {
       super.onResume();
       System.out.println("Resume");
       new AsyncTask<ThreadInstructionHandler, String>().execute(new ThreadInstructionHandler<MainActivity>("read", this));
       /*
        QuizDatabaseHelper DB = new QuizDatabaseHelper(this);
        Cursor res = DB.getQuestionsData();
        if(res.getCount() != 6) {
            System.out.println("There is no quiz data available");
            System.out.println(res.getCount());
        } else {
            int[] chosenAnswers = new int[6];
            String[][] answer_choices = new String[6][3];
            String[] newQuestions = new String[6];
            String[] actualAnswers = new String[6];
            int i = 0;
            while(res.moveToNext() && i < 6) {
                chosenAnswers[i] = res.getInt(6);
                answer_choices[i][0] = res.getString(3);
                answer_choices[i][1] = res.getString(4);
                answer_choices[i][2] = res.getString(5);
                newQuestions[i] = res.getString(1);
                actualAnswers[i] = res.getString(2);
                i++;
            }
            System.out.println(Arrays.toString(newQuestions));
            System.out.println(Arrays.toString(actualAnswers));
            quizPagerAdapter.updateCurrQuiz(chosenAnswers, answer_choices, newQuestions, actualAnswers);

        }

        */
    }

    public void writeToDB() {
        String[] questions = quizPagerAdapter.getQuestions();
        String[][] answerChoices = quizPagerAdapter.getAnswerChoices();
        String[] actualAnswers = quizPagerAdapter.getActualAnswer();
        int[] chosenAnswer = quizPagerAdapter.getChosenAnswers();
        QuizDatabaseHelper DB = new QuizDatabaseHelper(this);
        DB.clearPreviousQuiz();
        for(int i = 0; i < 6; i++) {
            boolean inserted = DB.insertQuesitons(questions[i], actualAnswers[i], answerChoices[i][0], answerChoices[i][1], answerChoices[i][2], chosenAnswer[i]);
            System.out.println("inserting index: " + i);
            System.out.println(questions[i]);
            if(inserted) {
                System.out.println("inserted current quiz");
            } else {
                System.out.println("current quiz was not successfully inserted");
            }
        }
    }

    public void readFromDB() {
        QuizDatabaseHelper DB = new QuizDatabaseHelper(this);
        Cursor res = DB.getQuestionsData();
        if(res.getCount() != 6) {
            System.out.println("There is no quiz data available");
            System.out.println(res.getCount());
        } else {
            System.out.println(res.getCount());
            int[] chosenAnswers = new int[6];
            String[][] answer_choices = new String[6][3];
            String[] newQuestions = new String[6];
            String[] actualAnswers = new String[6];
            int i = 0;
            while(res.moveToNext() && i < 6) {
                chosenAnswers[i] = res.getInt(6);
                answer_choices[i][0] = res.getString(3);
                answer_choices[i][1] = res.getString(4);
                answer_choices[i][2] = res.getString(5);
                newQuestions[i] = res.getString(1);
                actualAnswers[i] = res.getString(2);
                i++;
            }
            System.out.println(Arrays.toString(newQuestions));
            System.out.println(Arrays.toString(actualAnswers));
            quizPagerAdapter.updateCurrQuiz(chosenAnswers, answer_choices, newQuestions, actualAnswers);

        }
    }
}
