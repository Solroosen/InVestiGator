package com.example.investigation_deduction;

/**
 * Created by Sofie on 2016-10-13.
 */

public class Player {

    private int _id;
    private String _playerName;
    private int[] _scores;

    public Player() {
    }

    public Player(String _playerName) {
        this._playerName = _playerName;
    }

    public void set_scores(int[] _scores) {
        this._scores = _scores;
    }

    public Player(int _id, String _playerName) {
        this._id = _id;
        this._playerName = _playerName;

    }

    public int get_id() {
        return _id;
    }

    public String get_playerName() {
        return _playerName;
    }

    public int get_highScore() {
        int lowest = -1;
        int first = 0;
        for (int score : _scores) {
            if(first == 0) {
                lowest = score;
                first++;
            }
            else {
                if (score != -1) {
                    if (score < lowest)
                        lowest = score;
                }
            }
        }
        return lowest;
    }

    public int[] get_Scores() {
        return _scores;
    }

    public int get_numberOfPlayed() {
        return _scores.length;
    }

    public int get_numberOfWins() {
        int num = 0;
        for (int score:_scores) {
            if(score != -1)
                num++;
        }
        return num;
    }

    public int get_numberOfLosses() {
        int num = 0;
        for (int score:_scores) {
            if(score == -1)
                num++;
        }
        return num;
    }
}

