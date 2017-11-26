package com.example.investigation_deduction;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.jar.Attributes;

/**
 * Created by Sofie on 2016-10-02.
 */

public class Clue implements Serializable{
    public String name;
    public String location;
    public String weapon;
    public String status;

    public String[] NameList = {"Lemur", "Platypus", "Monkey", "Feline"};
    public String[] LocationList = {"Forest", "Cave", "Beach", "Savannah"};
    public String[] WeaponList = {"Gun", "Knife", "Rope", "Poison"};

    public Clue(){
        // if no arguments are passed with the constructor a random clue is generated
        name = NameList[random(NameList.length)];
        location = LocationList[random(LocationList.length)];
        weapon = WeaponList[random(WeaponList.length)];
    }

    public Clue(String name, String location, String weapon){
        this.name = name;
        this.location = location;
        this.weapon = weapon;

    }

    private int random(int max){
        Random rand = new Random();
        return rand.nextInt(max);
    }

    public String stringify(){
        return (this.status.toUpperCase() + "\n" + this.name + ", " + this.location + ", " + this.weapon);
    }

    public String stringifyWithoutStatus(){
        return (this.name + ", " + this.location + ", " + this.weapon);
    }

    public int maxClues(){
        return (NameList.length * LocationList.length * WeaponList.length)-1;
    }

    public int[] getDrawable(){
        int[] temp = new int[3];

        final String character = this.name;
        final String location = this.location;
        final String weapon = this.weapon;

        // Set character image
        switch (character){
            case "Lemur":
                temp[0] = R.drawable.lemur;
                break;
            case "Platypus":
                temp[0] = R.drawable.platypus;
                break;
            case "Monkey":
                temp[0] = R.drawable.monkey;
                break;
            case "Feline":
                temp[0] = R.drawable.feline;
                break;
            default:
                break;
        }

        switch (location){
            case "Beach":
                temp[1] = R.drawable.beach;
                break;
            case "Savannah":
                temp[1] = R.drawable.savannah;
                break;
            case "Forest":
                temp[1] = R.drawable.forest;
                break;
            case "Cave":
                temp[1] = R.drawable.cave;
                break;
            default:
                break;
        }

        switch (weapon){
            case "Gun":
                temp[2] = R.drawable.gun;
                break;
            case "Knife":
                temp[2] = R.drawable.knife;
                break;
            case "Rope":
                temp[2] = R.drawable.rope;
                break;
            case "Poison":
                temp[2] = R.drawable.poison;
                break;
            default:
                break;
        }

        return temp;
    }
}
