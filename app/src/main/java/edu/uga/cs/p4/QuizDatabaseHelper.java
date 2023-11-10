package edu.uga.cs.p4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Arrays;

public class QuizDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QuizApp.db";
    private static final int DATABASE_VERSION = 1;

    // Define the table creation SQL statement for Questions
    private static final String CREATE_QUESTIONS_TABLE = "CREATE TABLE Questions" +
            "(question_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "question TEXT, capital_city TEXT, choice_one TEXT, choice_two TEXT, " +
            "choice_three TEXT, chosen_answer INTEGER)";

    // Define the table creation SQL statement for Quizzes
    private static final String CREATE_QUIZZES_TABLE = "CREATE TABLE Quizzes " +
            "(quiz_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "quiz_date TEXT, quiz_result INTEGER)";

    public QuizDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.out.println("Hiiiiii");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the Questions and Quizzes tables
        //db.execSQL("drop Table if exists Questions ");
        db.execSQL(CREATE_QUESTIONS_TABLE);
        db.execSQL(CREATE_QUIZZES_TABLE);
        Cursor dbCursor = db.query("Questions", null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();
        System.out.println(Arrays.toString(columnNames));
        System.out.println("Hiiiiii");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
        db.execSQL("drop table if EXISTS Questions ");
        if (oldVersion < 2) {
            // Upgrade from version 1 to version 2
            // Add upgrade statements if necessary
            db.execSQL("ALTER TABLE Questions ADD COLUMN new_column INTEGER;");
            oldVersion = 2;
        }
    }

    public boolean insertQuizData(String time, String correct) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("quiz_date", time);
        contentValues.put("quiz_result", correct);
        long result = DB.insert("Quizzes", null, contentValues);
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getQuizData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM Quizzes", null);
        return cursor;
    }

    public boolean insertQuesitons(String question, String capitalCity, String choiceOne, String choiceTwo, String choiceThree, int chosenAnswer) {

        SQLiteDatabase DB = this.getWritableDatabase();
        //DB.execSQL("delete from Questions");
        ContentValues contentValues = new ContentValues();
        contentValues.put("question", question);
        contentValues.put("capital_city", capitalCity);
        contentValues.put("choice_one", choiceOne);
        contentValues.put("choice_two", choiceTwo);
        contentValues.put("choice_three", choiceThree);
        contentValues.put("chosen_answer", chosenAnswer);

        long result = DB.insert("Questions", null, contentValues);
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getQuestionsData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM Questions", null);
        return cursor;
    }

    public void clearPreviousQuiz() {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("delete from Questions");
    }


}
