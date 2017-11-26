package com.example.investigation_deduction;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

public class investigator_guess extends AppCompatActivity {

    ImageView murderFactName;
    ImageView murderFactLocation;
    ImageView murderFactWeapon;
    String guessedCharacter;
    String guessedLocation;
    String guessedWeapon;

    ImageView imageChar1;
    ImageView imageChar2;
    ImageView imageChar3;
    ImageView imageChar4;

    ImageView imageLoca1;
    ImageView imageLoca2;
    ImageView imageLoca3;
    ImageView imageLoca4;

    ImageView imageWeap1;
    ImageView imageWeap2;
    ImageView imageWeap3;
    ImageView imageWeap4;

    Clue guess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_investigator_guess);

        // Get the game cluehandler from previous view
        final ClueHandler clueHandler = (ClueHandler) getIntent().getSerializableExtra("clueHandler");

        murderFactName = (ImageView)findViewById(R.id.guessed_murderFactName);
        murderFactLocation = (ImageView)findViewById(R.id.guessed_murderFactLocation);
        murderFactWeapon = (ImageView)findViewById(R.id.guessed_murderFactWeapon);

        Button sendGuess = (Button) findViewById(R.id.guess_button_makeGuess);
        sendGuess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((guessedCharacter != null && guessedCharacter.length() > 0)
                        && (guessedLocation != null && guessedLocation.length() > 0)
                        && (guessedWeapon != null && guessedWeapon.length() > 0)) {

                    guess = new Clue(guessedCharacter, guessedLocation, guessedWeapon);

                    Intent i = new Intent(investigator_guess.this, investigator_final.class);
                    i.putExtra("clueHandler", clueHandler);
                    i.putExtra("guess", guess);
                    startActivity(i);

                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(investigator_guess.this);
                    builder1.setMessage("You have to choose a fact from each category!");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }
        });

        ImageButton backToPlay = (ImageButton)findViewById(R.id.guess_backArrow);
        backToPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(investigator_guess.this, investigator_play.class);
                startActivity(i);
            }
        });

        // Clicked images in character card

        imageChar1 = (ImageView)findViewById(R.id.Lemur);
        imageChar1.setOnClickListener(clickedCharacter);

        imageChar2 = (ImageView)findViewById(R.id.Platypus);
        imageChar2.setOnClickListener(clickedCharacter);

        imageChar3 = (ImageView)findViewById(R.id.Feline);
        imageChar3.setOnClickListener(clickedCharacter);

        imageChar4 = (ImageView)findViewById(R.id.Monkey);
        imageChar4.setOnClickListener(clickedCharacter);

        // Clicked images in location card

        imageLoca1 = (ImageView)findViewById(R.id.Beach);
        imageLoca1.setOnClickListener(clickedLocation);

        imageLoca2 = (ImageView)findViewById(R.id.Cave);
        imageLoca2.setOnClickListener(clickedLocation);

        imageLoca3 = (ImageView)findViewById(R.id.Forest);
        imageLoca3.setOnClickListener(clickedLocation);

        imageLoca4 = (ImageView)findViewById(R.id.Savannah);
        imageLoca4.setOnClickListener(clickedLocation);

        // Clicked images in weapon card

        imageWeap1 = (ImageView)findViewById(R.id.Gun);
        imageWeap1.setOnClickListener(clickedWeapon);

        imageWeap2 = (ImageView)findViewById(R.id.Rope);
        imageWeap2.setOnClickListener(clickedWeapon);

        imageWeap3 = (ImageView)findViewById(R.id.Knife);
        imageWeap3.setOnClickListener(clickedWeapon);

        imageWeap4 = (ImageView)findViewById(R.id.Poison);
        imageWeap4.setOnClickListener(clickedWeapon);
    }

    View.OnClickListener clickedCharacter = new View.OnClickListener(){
        public void onClick(View v){
            guessedCharacter = (String) v.getContentDescription();
            Drawable d = ((ImageView) v).getDrawable();
            murderFactName.setImageDrawable(d);

            GridLayout parent = (GridLayout) v.getParent();
            for (int i = 0; i < parent.getChildCount(); i++) {
                if (parent.getChildAt(i).equals(v)) {
                    parent.getChildAt(i).setBackgroundResource(R.drawable.imagebutton_borderwhitedash);
                } else {
                    parent.getChildAt(i).setBackgroundResource(R.drawable.imagebutton_bordersolid);
                }
            }
        }
    };

    View.OnClickListener clickedLocation = new View.OnClickListener(){
        public void onClick(View v){
            guessedLocation = (String) v.getContentDescription();
            Drawable d = ((ImageView) v).getDrawable();
            murderFactLocation.setImageDrawable(d);

            GridLayout parent = (GridLayout) v.getParent();
            for (int i = 0; i < parent.getChildCount(); i++) {
                if (parent.getChildAt(i).equals(v)) {
                    parent.getChildAt(i).setBackgroundResource(R.drawable.imagebutton_borderwhitedash);
                } else {
                    parent.getChildAt(i).setBackgroundResource(R.drawable.imagebutton_bordersolid);
                }
            }
        }
    };

    View.OnClickListener clickedWeapon = new View.OnClickListener() {
        public void onClick(View v) {
            guessedWeapon = (String) v.getContentDescription();
            Drawable d = ((ImageView) v).getDrawable();
            murderFactWeapon.setImageDrawable(d);

            GridLayout parent = (GridLayout) v.getParent();
            for (int i = 0; i < parent.getChildCount(); i++) {
                if (parent.getChildAt(i).equals(v)) {
                    parent.getChildAt(i).setBackgroundResource(R.drawable.imagebutton_borderwhitedash);
                } else {
                    parent.getChildAt(i).setBackgroundResource(R.drawable.imagebutton_bordersolid);
                }
            }
        }

    };
}