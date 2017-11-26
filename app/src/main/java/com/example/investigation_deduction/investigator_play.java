package com.example.investigation_deduction;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class investigator_play extends AppCompatActivity {

    // Basic buttons
    ImageView button_quit;
    Button button_guess;
    Button button_newClue;

    // Recycler views for generated clues
    RecyclerView mRecyclerViewInnocent;
    MyAdapter mAdapterI;
    RecyclerView.LayoutManager mLayoutManagerI;
    RecyclerView mRecyclerViewSuspect;
    MyAdapter mAdapterS;
    RecyclerView.LayoutManager mLayoutManagerS;

    // Objects for showing all facts

    RelativeLayout tab_AllFacts;
    ImageView tab_arrow;
    LinearLayout layout_AllFacts;

    // Objects for displaying chosen facts

    CardView cardview;
    ImageView imgCharacter;
    ImageView imgLocation;
    ImageView imgWeapon;

    // objects for saving chosen facts and guess
    String guessedCharacter;
    String guessedLocation;
    String guessedWeapon;
    Clue guess;

    // investigator_gameFunctions game;
    ClueHandler clueHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_investigator_play);

        clueHandler = new ClueHandler();

        // ------- QUIT GAME --------- //

        button_quit = (ImageView) findViewById(R.id.play_backArrow);
        button_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(investigator_play.this, investigator_MainActivity.class);
                startActivity(i);
            }
        });

        // -------NEW CLUE --------- //

        cardview = (CardView) findViewById(R.id.play_newClueView);
        imgCharacter = (ImageView) findViewById(R.id.imageCharacter);
        imgLocation = (ImageView) findViewById(R.id.imageLocation);
        imgWeapon = (ImageView) findViewById(R.id.imageWeapon);

        // innocent recyclerView
        mRecyclerViewInnocent = (RecyclerView) findViewById(R.id.investigator_recyclerView_innocent);
        mRecyclerViewInnocent.setHasFixedSize(true);
        mLayoutManagerI = new LinearLayoutManager(this);
        mRecyclerViewInnocent.setLayoutManager(mLayoutManagerI);

        // specify an adapter for innocents
        mAdapterI = new MyAdapter(clueHandler.findCluesByStatus("innocent"));
        mRecyclerViewInnocent.setAdapter(mAdapterI);

        // suspect recyclerView
        mRecyclerViewSuspect = (RecyclerView) findViewById(R.id.investigator_recyclerView_suspect);
        mRecyclerViewSuspect.setHasFixedSize(true);
        mLayoutManagerS = new LinearLayoutManager(this);
        mRecyclerViewSuspect.setLayoutManager(mLayoutManagerS);

        // specify an adapter for suspects
        mAdapterS = new MyAdapter(clueHandler.findCluesByStatus("suspect"));
        mRecyclerViewSuspect.setAdapter(mAdapterS);

// -------- ON CLICK NEW CLUE ---------- //

        button_newClue = (Button) findViewById(R.id.play_clue_button);
        button_newClue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (clueHandler.newClue()) {
                    final Clue newClue = clueHandler.latestClue();
                    String count = Integer.toString(clueHandler.counter());
                    TextView counter = (TextView) findViewById(R.id.clue_counterNum);
                    counter.setText(count);

                    if (newClue.status.equals("innocent")) {
                        ArrayList<Clue> innocents = clueHandler.findCluesByStatus("innocent");
                        mAdapterI.Update(innocents);
                    } else if (newClue.status.equals("suspect")) {
                        ArrayList<Clue> suspects = clueHandler.findCluesByStatus("suspect");
                        mAdapterS.Update(suspects);
                    }
                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(investigator_play.this);
                    builder1.setMessage(R.string.investigator_play_noMoreClues);
                    builder1.setTitle(R.string.investigator_play_noMoreCluesHeading);
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog noMoreClues = builder1.create();
                    noMoreClues.show();
                }

            }
        });

// ------ SHOW ALL FACTS ------ //

        tab_AllFacts = (RelativeLayout) findViewById(R.id.play_tab_AllFacts);
        tab_arrow = (ImageView) findViewById(R.id.play_arrow_AllFacts);
        layout_AllFacts = (LinearLayout) findViewById(R.id.play_layout_AllFacts);
        tab_AllFacts.animate().translationY(400).setDuration(0);
        layout_AllFacts.animate().translationY(400).setDuration(0);
        layout_AllFacts.setVisibility(View.INVISIBLE);

        tab_AllFacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tab_arrow.getContentDescription().equals("up")) {
                    tab_arrow.setImageResource(R.drawable.ic_expand_more_black_24dp);
                    tab_arrow.setContentDescription("down");
                    layout_AllFacts.setVisibility(View.VISIBLE);

                    tab_AllFacts.animate().translationY(0).setDuration(900);
                    layout_AllFacts.animate()
                            .translationY(0)
                            .setDuration(900)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    layout_AllFacts.setVisibility(View.VISIBLE);
                                }
                            });

                } else {
                    tab_arrow.setImageResource(R.drawable.ic_expand_less_black_24dp);
                    tab_arrow.setContentDescription("up");

                    tab_AllFacts.animate().translationY(400).setDuration(900);
                    layout_AllFacts.animate()
                            .translationY(400)
                            .setDuration(900)
                            .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            layout_AllFacts.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        });

// ------ GUESS FUNCTIONS ------- //

        button_guess = (Button) findViewById(R.id.play_guess_button);
        button_guess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allFactsChosen()) {
                    guess = new Clue(guessedCharacter, guessedLocation, guessedWeapon);

                    Intent i = new Intent(investigator_play.this, investigator_final.class);
                    i.putExtra("clueHandler", clueHandler);
                    i.putExtra("guess", guess);
                    startActivity(i);

                } else {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(investigator_play.this);
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
    }

// ---------- onClickFunctions for facts ------------ //

    public void clickedCharacter(View v) {
        guessedCharacter = (String) v.getContentDescription();
        Drawable d = ((ImageView) v).getDrawable();
        imgCharacter.setImageDrawable(d);
        imgCharacter.setBackgroundResource(R.drawable.imagebutton_bordersolid);

        if(allFactsChosen()){
            cardview.setCardBackgroundColor(Color.rgb(9,106,163));
            button_guess.setBackgroundResource(R.drawable.button_colors);
            button_guess.setClickable(true);
        }
    }

    public void clickedLocation(View v){
        guessedLocation = (String) v.getContentDescription();
        Drawable d = ((ImageView) v).getDrawable();
        imgLocation.setImageDrawable(d);
        imgLocation.setBackgroundResource(R.drawable.imagebutton_bordersolid);

        if(allFactsChosen()){
            cardview.setCardBackgroundColor(Color.rgb(9,106,163));
            button_guess.setBackgroundResource(R.drawable.button_colors);
            button_guess.setClickable(true);
        }

    }

    public void clickedWeapon(View v){
        guessedWeapon = (String) v.getContentDescription();
        Drawable d = ((ImageView) v).getDrawable();
        imgWeapon.setImageDrawable(d);
        imgWeapon.setBackgroundResource(R.drawable.imagebutton_bordersolid);

        if(allFactsChosen()){
            cardview.setCardBackgroundColor(Color.rgb(9,106,163));
            button_guess.setBackgroundResource(R.drawable.button_colors);
            button_guess.setClickable(true);
        }
    }

    private Boolean allFactsChosen(){
        return((guessedCharacter != null && guessedCharacter.length() > 0)
                && (guessedLocation != null && guessedLocation.length() > 0)
                && (guessedWeapon != null && guessedWeapon.length() > 0));
    }
}