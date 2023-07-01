package org.jointheleague.features.student.data_transfer_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clue {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("airdate")
    @Expose
    private String airdate;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("game_id")
    @Expose
    private Object gameId;
    @SerializedName("invalid_count")
    @Expose
    private Object invalidCount;
    @SerializedName("category")
    @Expose
    private Category category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getAirdate() {
        return airdate;
    }

    public void setAirdate(String airdate) {
        this.airdate = airdate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Object getGameId() {
        return gameId;
    }

    public void setGameId(Object gameId) {
        this.gameId = gameId;
    }

    public Object getInvalidCount() {
        return invalidCount;
    }

    public void setInvalidCount(Object invalidCount) {
        this.invalidCount = invalidCount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
