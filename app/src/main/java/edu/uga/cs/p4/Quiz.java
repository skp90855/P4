package edu.uga.cs.p4;

public class Quiz {
    private int quizId;
    private String quizDate;
    private int quizResult;
    private int questionsAnswered;

    public Quiz() {
        // Default constructor
    }

    public Quiz(String quizDate, int quizResult, int questionsAnswered) {
        this.quizDate = quizDate;
        this.quizResult = quizResult;
        this.questionsAnswered = questionsAnswered;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getQuizDate() {
        return quizDate;
    }

    public void setQuizDate(String quizDate) {
        this.quizDate = quizDate;
    }

    public int getQuizResult() {
        return quizResult;
    }

    public void setQuizResult(int quizResult) {
        this.quizResult = quizResult;
    }

    public int getQuestionsAnswered() {
        return questionsAnswered;
    }

    public void setQuestionsAnswered(int questionsAnswered) {
        this.questionsAnswered = questionsAnswered;
    }
}
