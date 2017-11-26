package com.example.investigation_deduction;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class investigator_final extends AppCompatActivity {

    Clue guess;
    ClueHandler clueHandler;

    TextView Headline;
    TextView mainText;
    TextView youGuessed;
    TextView finalLose;
    TextView isMurderer;
    ImageView guessChar;
    ImageView guessLoca;
    ImageView guessWeap;
    TextView counterText;

    ImageView murderChar;
    ImageView murderLoca;
    ImageView murderWeap;
    GridLayout murderView;

    Button button_BackToMain;
    Button button_playAgain;

    DBHandler db = databaseProvider.getDB();
    Player player = db.findPlayer("Player");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_investigator_final);

        guess = (Clue) getIntent().getSerializableExtra("guess");
        clueHandler = (ClueHandler) getIntent().getSerializableExtra("clueHandler");

        Headline = (TextView) findViewById(R.id.final_text_heading);
        mainText = (TextView) findViewById(R.id.final_text_main);
        youGuessed = (TextView)findViewById(R.id.final_you_guessed);

        RelativeLayout lost = (RelativeLayout)findViewById(R.id.final_murderView);
        finalLose = (TextView)findViewById(R.id.final_text_thisIsmurderer);
        isMurderer = (TextView)findViewById(R.id.final_this_murderer);

        guessChar = (ImageView)findViewById(R.id.guess_char);
        guessLoca = (ImageView)findViewById(R.id.guess_loca);
        guessWeap = (ImageView)findViewById(R.id.guess_weap);

        counterText = (TextView) findViewById(R.id.final_text_counter);

        Boolean didWin = clueHandler.isMurderer(guess);

        int[] drawables = guess.getDrawable();

        guessChar.setImageResource(drawables[0]);
        guessLoca.setImageResource(drawables[1]);
        guessWeap.setImageResource(drawables[2]);

        if (didWin) {
            Headline.setText(R.string.investigator_final_Win_Head);
            mainText.setText(R.string.investigator_final_Win_Main1);
            youGuessed.setText(R.string.investigator_final_Win_Main2);
            counterText.setText(R.string.investigator_final_Win_Counter1);
            counterText.append(Integer.toString(clueHandler.counter()));

            db.addScore(clueHandler.counter(), player.get_id());

        } else {
            Headline.setText(R.string.investigator_final_Lose_Head);
            mainText.setText(R.string.investigator_final_Lose_Main1);
            youGuessed.setText(R.string.investigator_final_Win_Main2);
            isMurderer.setText(R.string.investigator_final_Lose_Main2);
            lost.setVisibility(View.VISIBLE);

            murderView = (GridLayout)findViewById(R.id.final_showMurderer_grid);
            murderChar = (ImageView)findViewById(R.id.murderer_char);
            murderLoca = (ImageView)findViewById(R.id.murderer_loca);
            murderWeap = (ImageView)findViewById(R.id.murderer_weap);

            Clue murderer = clueHandler.murderer;
            int[] murderD = murderer.getDrawable();
            murderChar.setImageResource(murderD[0]);
            murderLoca.setImageResource(murderD[1]);
            murderWeap.setImageResource(murderD[2]);
            murderView.setVisibility(View.VISIBLE);

            db.addScore(-1, player.get_id());

        }
        clueHandler.clearGame();

        button_BackToMain = (Button) findViewById(R.id.final_button_backToMenu);
        button_BackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(investigator_final.this, investigator_MainActivity.class);
                startActivity(i);
            }
        });

        button_playAgain = (Button) findViewById(R.id.final_button_playAgain);
        button_playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(investigator_final.this, investigator_play.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}
