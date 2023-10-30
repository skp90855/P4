package edu.uga.cs.p4;

public class Question {

    private long questionId;
    private String state;
    private String capitalCity;

    public Question(String state, String capitalCity) {
        this.state = state;
        this.capitalCity = capitalCity;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

}
