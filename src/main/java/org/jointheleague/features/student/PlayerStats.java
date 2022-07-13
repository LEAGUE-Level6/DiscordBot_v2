
package org.jointheleague.features.student;

import java.util.HashMap;
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
    "chess_daily",
    "chess960_daily",
    "chess_rapid",
    "chess_bullet",
    "chess_blitz",
    "fide",
    "tactics",
    "lessons",
    "puzzle_rush"
})
@Generated("jsonschema2pojo")
public class PlayerStats {

    @JsonProperty("chess_daily")
    private ChessDaily chessDaily;
    @JsonProperty("chess960_daily")
    private Chess960Daily chess960Daily;
    @JsonProperty("chess_rapid")
    private ChessRapid chessRapid;
    @JsonProperty("chess_bullet")
    private ChessBullet chessBullet;
    @JsonProperty("chess_blitz")
    private ChessBlitz chessBlitz;
    @JsonProperty("fide")
    private Integer fide;
    @JsonProperty("tactics")
    private Tactics tactics;
    @JsonProperty("lessons")
    private Lessons lessons;
    @JsonProperty("puzzle_rush")
    private PuzzleRush puzzleRush;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("chess_daily")
    public ChessDaily getChessDaily() {
        return chessDaily;
    }

    @JsonProperty("chess_daily")
    public void setChessDaily(ChessDaily chessDaily) {
        this.chessDaily = chessDaily;
    }

    @JsonProperty("chess960_daily")
    public Chess960Daily getChess960Daily() {
        return chess960Daily;
    }

    @JsonProperty("chess960_daily")
    public void setChess960Daily(Chess960Daily chess960Daily) {
        this.chess960Daily = chess960Daily;
    }

    @JsonProperty("chess_rapid")
    public ChessRapid getChessRapid() {
        return chessRapid;
    }

    @JsonProperty("chess_rapid")
    public void setChessRapid(ChessRapid chessRapid) {
        this.chessRapid = chessRapid;
    }

    @JsonProperty("chess_bullet")
    public ChessBullet getChessBullet() {
        return chessBullet;
    }

    @JsonProperty("chess_bullet")
    public void setChessBullet(ChessBullet chessBullet) {
        this.chessBullet = chessBullet;
    }

    @JsonProperty("chess_blitz")
    public ChessBlitz getChessBlitz() {
        return chessBlitz;
    }

    @JsonProperty("chess_blitz")
    public void setChessBlitz(ChessBlitz chessBlitz) {
        this.chessBlitz = chessBlitz;
    }

    @JsonProperty("fide")
    public Integer getFide() {
        return fide;
    }

    @JsonProperty("fide")
    public void setFide(Integer fide) {
        this.fide = fide;
    }

    @JsonProperty("tactics")
    public Tactics getTactics() {
        return tactics;
    }

    @JsonProperty("tactics")
    public void setTactics(Tactics tactics) {
        this.tactics = tactics;
    }

    @JsonProperty("lessons")
    public Lessons getLessons() {
        return lessons;
    }

    @JsonProperty("lessons")
    public void setLessons(Lessons lessons) {
        this.lessons = lessons;
    }

    @JsonProperty("puzzle_rush")
    public PuzzleRush getPuzzleRush() {
        return puzzleRush;
    }

    @JsonProperty("puzzle_rush")
    public void setPuzzleRush(PuzzleRush puzzleRush) {
        this.puzzleRush = puzzleRush;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
