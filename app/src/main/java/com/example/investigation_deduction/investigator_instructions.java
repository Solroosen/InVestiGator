package com.example.investigation_deduction;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class investigator_instructions extends AppCompatActivity {
    Button button_toStart;
    FragmentPagerAdapter adapterViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_investigator_instructions);

        ViewPager vpPager = (ViewPager) findViewById(R.id.ViewPagerInstr);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);


        button_toStart = (Button)findViewById(R.id.instruction3_button_toMain);
        button_toStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(investigator_instructions.this, investigator_MainActivity.class);
                startActivity(i);
            }
        });


    }

    public class MyPagerAdapter extends FragmentPagerAdapter{
        // Constructor
        MyPagerAdapter(FragmentManager fMan){
            super(fMan);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    return new instruction1();
                case 1:
                    return new instruction2();
                case 2:
                    return new instruction3();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }


    }
}
