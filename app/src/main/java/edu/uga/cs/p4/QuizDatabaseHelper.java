package edu.uga.cs.p4;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuizDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QuizApp.db";
    private static final int DATABASE_VERSION = 1;

    // Define the table creation SQL statement for Questions
    private static final String CREATE_QUESTIONS_TABLE = "CREATE TABLE Questions " +
            "(question_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "state TEXT, capital_city TEXT)";

    // Define the table creation SQL statement for Quizzes
    private static final String CREATE_QUIZZES_TABLE = "CREATE TABLE Quizzes " +
            "(quiz_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "quiz_date TEXT, quiz_result INTEGER)";

    public QuizDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the Questions and Quizzes tables
        db.execSQL(CREATE_QUESTIONS_TABLE);
        db.execSQL(CREATE_QUIZZES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
        if (oldVersion < 2) {
            // Upgrade from version 1 to version 2
            // Add upgrade statements if necessary
            db.execSQL("ALTER TABLE Questions ADD COLUMN new_column INTEGER;");
            oldVersion = 2;
        }
    }
}
