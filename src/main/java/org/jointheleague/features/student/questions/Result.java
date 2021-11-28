package org.jointheleague.features.student.questions;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;

public class Result {

    @JsonProperty("category")
    @Expose
    private String category;
    @JsonProperty("type")
    @Expose
    private String type;
    @JsonProperty("difficulty")
    @Expose
    private String difficulty;
    @JsonProperty("question")
    @Expose
    private String question;
    @JsonProperty("correct_answer")
    @Expose
    private String correctAnswer;
    @JsonProperty("incorrect_answers")
    @Expose
    private List<String> incorrectAnswers = null;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        while(question.contains("&quot;")) {
            question = question.replace("&quot;","");
        }
        while(question.contains("&#039;s")) {
            question = question.replace("&#039;s", "");
        }
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(List<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

}