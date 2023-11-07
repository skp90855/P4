package edu.uga.cs.p4;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Read data from the CSV file and populate questions and answerChoices
        String[] questions = new String[6];
        String[] correctAnswers = new String[6];
        String[][] answerChoices = new String[6][3];

        try {
            InputStream inputStream = getAssets().open("StateCapitals.csv");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            List<String[]> csvData = new ArrayList<>();
            String line;

            // Read and store CSV data
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                csvData.add(data);
            }

            // Choose random states and capital cities without repeats
            Random random = new Random();
            Set<Integer> selectedIndices = new HashSet<>();

            for (int i = 0; i < 6; i++) {
                int randomIndex;
                do {
                    randomIndex = random.nextInt(csvData.size());
                } while (selectedIndices.contains(randomIndex));

                selectedIndices.add(randomIndex);

                String[] data = csvData.get(randomIndex);

                String state = data[0];
                String correctCapital = data[1];
                questions[i] = "What is the capital of " + state + "?";
                correctAnswers[i] = data[1];

                // Choose two incorrect capital cities
                List<String> incorrectCities = new ArrayList<>();
                for (int j = 2; j < data.length; j++) {
                    incorrectCities.add(data[j]);
                }
                incorrectCities.remove(correctCapital);

                // Randomly shuffle the choices
                List<String> choices = new ArrayList<>();
                choices.add(correctCapital);
                choices.add(incorrectCities.get(0));
                choices.add(incorrectCities.get(1));

                // Shuffle the choices to randomize their order
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


        ViewPager2 pager = findViewById(R.id.viewpager);
        QuizPagerAdapter quizPagerAdapter = new QuizPagerAdapter(
                getSupportFragmentManager(), getLifecycle(), questions, answerChoices, correctAnswers);
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        pager.setAdapter(quizPagerAdapter);
        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                quizPagerAdapter.getCorrectAnswers();
            }
        });


    }
}
