package uni.laboratorio.suresave;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import uni.laboratorio.suresave.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private TextView[] dots;
    private int posicionActual;

    private Button email;
    private ImageButton google;
    private ImageButton facebook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        viewPager = findViewById(R.id.slider);
        dotsLayout = findViewById(R.id.indicador);

        email = findViewById(R.id.btn_email);
        google = findViewById(R.id.btn_google);
        facebook = findViewById(R.id.btn_facebook);

        viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);

        addDots(0);

        viewPager.addOnPageChangeListener(changeListener);

    }


    private void addDots(int posicion) {

        dots = new TextView[4];
        dotsLayout.removeAllViews();

        int i;
        for (i = 0; i < dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(40);
            if (i > 0){
                dots[i].setPaddingRelative(50,0,0,0);
            }
            dots[i].setTextColor(ContextCompat.getColor(this,R.color.white));

            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0){
            dots[posicion].setTextColor(ContextCompat.getColor(this,R.color.azul));
        }

    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener(){

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDots(position);
            posicionActual = position;

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    public void iniciarGoogle(View view) {
        Toast.makeText(this, "Esta funcion pronto estará disponible", Toast.LENGTH_SHORT).show();
    }

    public void iniciarFacebook(View view) {
        Toast.makeText(this, "Esta funcion pronto estará disponible", Toast.LENGTH_SHORT).show();
    }

    public void iniciarSesion(View view) {
        Intent inicio = new Intent(this,Login.class);
        startActivity(inicio);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(this, PaginaInicial.class));
            finish();
        }

    }
}