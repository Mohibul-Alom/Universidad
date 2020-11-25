package com.example.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.example.quiz.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    int images[] = {
            R.drawable.hello_web_series__wordmark__logo,
            R.drawable.pantalla_registro,
            R.drawable.pantalla_inicio_sesion,
            R.drawable.pantalla_pregunta,
            R.drawable.pantalla_puntuacion,
            R.drawable.pantalla_ranking,
            R.drawable.signo_de_interrogacion
    };

    int description[] = {
            R.string.textMensaje,
            R.string.tutorial2,
            R.string.tutorial3,
            R.string.tutorial4,
            R.string.tutorial5,
            R.string.tutorial6,
            R.string.tutorial7
    };


    @Override
    public int getCount() {
        return description.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fragment_tutorial,container,false);

        ImageView imageView = view.findViewById(R.id.tutorial_image);
        TextView desc = view.findViewById(R.id.tutorial_text);

        imageView.setImageResource(images[position]);
        desc.setText(description[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
