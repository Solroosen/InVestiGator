package com.example.investigation_deduction;

/**
 * Created by Sofie on 2016-10-13.
 */

public class player_Score {
    int id;
    int score;
    int player_id;

    public player_Score(){
    }

    public player_Score(int score, int player_id) {
        this.score = score;
        this.player_id = player_id;
    }

    public player_Score(int id, int score, int player_id) {
        this.id = id;
        this.score = score;
        this.player_id = player_id;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPlayer_id() {
        return player_id;
    }
}
