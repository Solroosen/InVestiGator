package com.example.investigation_deduction;

/**
 * Created by Sofie on 2016-10-02.
 */

public class investigator_gameFunctions {
    private ClueHandler clues;

    public void initiate(){
        clues = new ClueHandler();
    }

    public void newClue(){
        clues.newClue();
    }

    public Clue latest(){
        return clues.latestClue();
    }

    public void makeGuess(String name, String location, String weapon){
        Clue guess = new Clue(name, location, weapon);
    }

    private Boolean guessIsCorrect(){
        return true;
    }





}
