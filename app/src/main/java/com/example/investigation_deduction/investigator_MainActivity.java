package com.example.investigation_deduction;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class investigator_MainActivity extends AppCompatActivity {

    ImageView button_menu;
    RelativeLayout menu;
    RelativeLayout hideContent;

    TextView highScore;
    TextView playedGames;
    TextView wonGames;
    TextView lostGames;
    TextView resetStats;

    Button button_instruction;
    Button button_play;
    Player currentPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //changing something for git

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_investigator_main);

        // Check if there exists a player, otherwise create one ??
        databaseProvider.setDB(this);

        final DBHandler db = databaseProvider.getDB();
        if(!(db.findPlayer("Player") == null)){
            currentPlayer = db.findPlayer("Player");
        }
        else
        {
            currentPlayer = new Player("Player");
            db.addPlayer(currentPlayer);
        }
        currentPlayer.set_scores(db.findPlayerScores(currentPlayer.get_id()));

        button_instruction = (Button) findViewById(R.id.main_instructions_button);
        button_instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(investigator_MainActivity.this, investigator_instructions.class);
                startActivity(i);
            }
        });

        button_play = (Button) findViewById(R.id.main_play_button);
        button_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(investigator_MainActivity.this, investigator_play.class);
                startActivity(i);
            }
        });

        menu = (RelativeLayout) findViewById(R.id.main_menu);
        menu.animate().translationX(400).setDuration(0);
        menu.setVisibility(View.INVISIBLE);

        hideContent = (RelativeLayout) findViewById(R.id.main_hideContent);
        button_menu = (ImageView) findViewById(R.id.main_menu_button);
        button_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenu();
            }
        });

        hideContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMenu();
            }
        });

        resetStats = (TextView)findViewById(R.id.main_menu_resetStat);
        resetStats.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                db.removePlayerScores(currentPlayer);
                currentPlayer.set_scores(db.findPlayerScores(currentPlayer.get_id()));
                setStats();
            }
        });

    }

    private void setStats(){
        highScore = (TextView)findViewById(R.id.stats_highScore);
        playedGames = (TextView)findViewById(R.id.stats_numberOfPlayed);
        wonGames = (TextView)findViewById(R.id.stats_numberOfWins);
        lostGames = (TextView)findViewById(R.id.stats_losses);

        int hs = currentPlayer.get_highScore();
        if(hs != -1)
            highScore.setText(Integer.toString(hs));
        else
            highScore.setText("n/a");


        playedGames.setText(Integer.toString(currentPlayer.get_numberOfPlayed()));
        wonGames.setText(Integer.toString(currentPlayer.get_numberOfWins()));
        lostGames.setText(Integer.toString(currentPlayer.get_numberOfLosses()));
    }

    private void toggleMenu(){
        if (menu.getContentDescription().equals("hidden")) {
            menu.setContentDescription("shown");
            setStats();
            menu.setVisibility(View.VISIBLE);
            menu.animate()
                    .translationX(0)
                    .setDuration(900)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            menu.setVisibility(View.VISIBLE);
                        }
                    });
            hideContent.animate()
                    .alpha(1.0f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            hideContent.setVisibility(View.VISIBLE);
                        }
                    });

        } else {
            menu.setContentDescription("hidden");
            menu.animate()
                    .translationX(400)
                    .setDuration(900)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            menu.setVisibility(View.INVISIBLE);
                        }
                    });

            hideContent.animate()
                    .alpha(0.0f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            hideContent.setVisibility(View.GONE);
                        }
                    });
        }
    }
}

