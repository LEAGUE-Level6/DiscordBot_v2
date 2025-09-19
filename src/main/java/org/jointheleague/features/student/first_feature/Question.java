
package org.jointheleague.features.student.first_feature;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "difficulty",
    "category",
    "question",
    "correct_answer",
    "incorrect_answers"
})
@Generated("jsonschema2pojo")
public class Question {

    @JsonProperty("type")
    private String type;
    @JsonProperty("difficulty")
    private String difficulty;
    @JsonProperty("category")
    private String category;
    @JsonProperty("question")
    private String question;
    @JsonProperty("correct_answer")
    private String correctAnswer;
    @JsonProperty("incorrect_answers")
    private List<String> incorrectAnswers;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("difficulty")
    public String getDifficulty() {
        return difficulty;
    }

    @JsonProperty("difficulty")
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty("question")
    public String getQuestion() {
        return question;
    }

    @JsonProperty("question")
    public void setQuestion(String question) {
        this.question = question;
    }

    @JsonProperty("correct_answer")
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @JsonProperty("correct_answer")
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @JsonProperty("incorrect_answers")
    public List<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    @JsonProperty("incorrect_answers")
    public void setIncorrectAnswers(List<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Question.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("difficulty");
        sb.append('=');
        sb.append(((this.difficulty == null)?"<null>":this.difficulty));
        sb.append(',');
        sb.append("category");
        sb.append('=');
        sb.append(((this.category == null)?"<null>":this.category));
        sb.append(',');
        sb.append("question");
        sb.append('=');
        sb.append(((this.question == null)?"<null>":this.question));
        sb.append(',');
        sb.append("correctAnswer");
        sb.append('=');
        sb.append(((this.correctAnswer == null)?"<null>":this.correctAnswer));
        sb.append(',');
        sb.append("incorrectAnswers");
        sb.append('=');
        sb.append(((this.incorrectAnswers == null)?"<null>":this.incorrectAnswers));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
