package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class OnBoarding extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] dots;
    private Button empecemos;
    private int posicionActual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        viewPager = findViewById(R.id.sliderOnboard);
        dotsLayout = findViewById(R.id.dots);
        empecemos = findViewById(R.id.get_started_button);


        sliderAdapter = new SliderAdapter(this);

        viewPager.setAdapter(sliderAdapter);

        addDots(0);

        viewPager.addOnPageChangeListener(changeListener);

    }

    public void skip(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    public void next(View view){
        viewPager.setCurrentItem(posicionActual+1);
    }

    private void addDots(int position){

        dots = new TextView[5];
        dotsLayout.removeAllViews();

        int i;
        for ( i=0; i < dots.length;i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(30);

            dotsLayout.addView(dots[i]);

        }

        if (dots.length>0){
            dots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }

    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDots(position);
            posicionActual = position;

            if (position == 4) {
                empecemos.setVisibility(View.VISIBLE);
            }else {
                empecemos.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}