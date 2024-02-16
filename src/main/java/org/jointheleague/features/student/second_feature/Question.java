package org.jointheleague.features.student.second_feature;

public class Question {
    public String content;
    public String answer;
    public Question(String content,String answer) {
        this.content = content;
        this.answer = answer;
    }

    public String getContent() {
        return content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setContent(String cont) {
        content = cont;
    }

    public void setAnswer(String ans) {
        answer = ans;
    }

    public Boolean checkAnswer(String ans) {
        if(answer.equalsIgnoreCase(ans)) {
            return true;
        }
        return false;
    }
}
