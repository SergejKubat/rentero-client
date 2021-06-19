package com.metropolitan.rentero_client.model;

public class Question {

    private String heading;
    private String question;
    private String answer;

    public Question() {
    }

    public Question(String heading, String question, String answer) {
        this.heading = heading;
        this.question = question;
        this.answer = answer;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}