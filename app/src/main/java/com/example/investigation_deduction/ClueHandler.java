package com.example.investigation_deduction;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Sofie on 2016-10-02.
 */

public class ClueHandler implements Serializable {
    private ArrayList<Clue> generatedClues;
    public Clue murderer;
    int maxClues;

    public ClueHandler(){
        this.generatedClues = new ArrayList<>();
        Clue m = new Clue();
        m.status = "murderer";
        this.generatedClues.add(m);
        this.murderer = m;
        this.maxClues = m.maxClues();
    }

    public boolean newClue(){
        if(counter() < maxClues) {
            Clue newClue = new Clue();
            if (noDuplicates(newClue)) {
                setStatus(newClue);
                generatedClues.add(0, newClue);
            } else {
                newClue();
            }
            return true;
        }
        else{
            return false;
        }
    }

    private Boolean noDuplicates(Clue clue){
        for (Clue c: generatedClues ) {
            if(   c.name.equals(clue.name)
               && c.location.equals(clue.location)
               && c.weapon.equals(clue.weapon)  ){
                return false;
            }
        }
        return true;
    }

    private void setStatus(Clue clue){
        if(     !murderer.name.equals(clue.name)
             && !murderer.location.equals(clue.location)
             && !murderer.weapon.equals(clue.weapon)   ){
            clue.status = "innocent";
        }
        else if(   murderer.name.equals(clue.name)
                || murderer.location.equals(clue.location)
                || murderer.weapon.equals(clue.weapon)){
            clue.status = "suspect";
        }
    }

    public Boolean isMurderer(Clue clue){
        if(   this.murderer.name.equals(clue.name)
           && this.murderer.location.equals(clue.location)
           && this.murderer.weapon.equals(clue.weapon)  ){
            return true;
        }
        else{
            return false;
        }
    }

    public ArrayList<Clue> findCluesByStatus(String status){
        ArrayList<Clue> temp = new ArrayList<>();
        for (Clue c: generatedClues) {
            if(c.status.equals(status)){
                temp.add(c);
            }
        }
        return temp;
    }

    public String[] stringedClueListByStatus(String status){
        ArrayList<Clue> temp = findCluesByStatus(status);
        String[] stringedClues = new String[temp.size()];

        for (int i = 0; i < temp.size(); i++){
            stringedClues[i] = temp.get(i).stringify();
        }

        return stringedClues;
    }

    public String[] getNameList(){
        return murderer.NameList;
    }

    public ArrayList<String[]> getFactLists(){
        ArrayList<String[]> lists = new ArrayList<>();
        lists.add(0, murderer.NameList);
        lists.add(1, murderer.LocationList);
        lists.add(2, murderer.WeaponList);

        return lists;
    }

    public Clue latestClue(){
        return generatedClues.get(0);
    }

    public void clearGame(){
        generatedClues.clear();
        murderer = null;
    }

    public int counter(){
        return generatedClues.size()-1;
    }
}