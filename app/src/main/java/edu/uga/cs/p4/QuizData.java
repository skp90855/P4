package edu.uga.cs.p4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuizData {
    private SQLiteDatabase db;
    private QuizDatabaseHelper quizDbHelper;

    public QuizData(Context context) {
        quizDbHelper = new QuizDatabaseHelper(context);
    }

    public void open() throws SQLException {
        db = quizDbHelper.getWritableDatabase();
    }

    public void close() {
        if (quizDbHelper != null) {
            quizDbHelper.close();
        }
    }

    public Question storeQuestion(Question question) {
        ContentValues values = new ContentValues();
        values.put("state", question.getState());
        values.put("capital_city", question.getCapitalCity());

        long id = db.insert("Questions", null, values);
        question.setQuestionId(id);
        return question;
    }

    public List<Question> retrieveAllQuestions() {
        List<Question> questions = new ArrayList<>();
        Cursor cursor = null;

        String[] allColumns = {
                "question_id",
                "state",
                "capital_city",
        };

        cursor = db.query("Questions", allColumns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndex("question_id"));
            String state = cursor.getString(cursor.getColumnIndex("state"));
            String capitalCity = cursor.getString(cursor.getColumnIndex("capital_city"));

            Question question = new Question(state, capitalCity);
            question.setQuestionId(id);
            questions.add(question);
        }

        cursor.close();
        return questions;
    }

    public HashMap<String, String> readDataFromCSV(Context context) {
        HashMap<String, String> stateCapitalMap = new HashMap<>();

        try {
            InputStream inputStream = context.getAssets().open("StateCapitals.csv");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2) {
                    String state = data[0].trim();
                    String capitalCity = data[1].trim();
                    stateCapitalMap.put(state, capitalCity);
                }
            }

            bufferedReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stateCapitalMap;
    }

    public long storeQuizResult(String quizDate, int quizResult) {
        ContentValues values = new ContentValues();
        values.put("quiz_date", quizDate);
        values.put("quiz_result", quizResult);

        return db.insert("Quizzes", null, values);
    }
}
