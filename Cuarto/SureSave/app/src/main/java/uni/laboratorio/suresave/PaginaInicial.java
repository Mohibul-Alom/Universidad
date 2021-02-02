package uni.laboratorio.suresave;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import uni.laboratorio.suresave.fragment.CuentaFragment;
import uni.laboratorio.suresave.fragment.FiltroFragment;
import uni.laboratorio.suresave.fragment.HomeFragment;
import uni.laboratorio.suresave.fragment.MetasFragment;

public class PaginaInicial extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private Fragment selectorFragment;

    private FloatingActionButton btn_mas,btn_ingresos,btn_gastos;

    private boolean selecionado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicial);



        navigationView = findViewById(R.id.navegacion);

        btn_mas = findViewById(R.id.btn_mas);
        btn_ingresos = findViewById(R.id.btn_ingresos);
        btn_gastos = findViewById(R.id.btn_gastos);


        btn_mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                masButtonClicked();
            }
        });

        btn_ingresos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PaginaInicial.this, "Ingresos", Toast.LENGTH_SHORT).show();
            }
        });

        btn_gastos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PaginaInicial.this, "Gastos", Toast.LENGTH_SHORT).show();
            }
        });



        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.nav_home:
                        selectorFragment = new HomeFragment();
                        break;

                    case R.id.nav_metas:
                        selectorFragment = new MetasFragment();
                        break;

                    case R.id.nav_filtro:
                        selectorFragment = new FiltroFragment();
                        break;

                    case R.id.nav_cuenta:
                        selectorFragment = new CuentaFragment();
                        break;
                }
                if (selectorFragment != null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,selectorFragment).commit();
                }

                return true;

            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new HomeFragment()).commit();

    }

    private void masButtonClicked() {

        setVisibility(selecionado);
        setAnimation(selecionado);
        if (!selecionado){
            selecionado = true;
        }else {
            selecionado = false;
        }

    }

    private void setVisibility(boolean selecionado) {
        if (!selecionado){
            btn_ingresos.setVisibility(View.VISIBLE);
            btn_gastos.setVisibility(View.VISIBLE);
        }else {
            btn_ingresos.setVisibility(View.GONE);
            btn_gastos.setVisibility(View.GONE);
        }
    }
    private void setAnimation(boolean selecionado) {

        Animation rotateOpen = AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim);
        Animation rotateClose = AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim);
        Animation fromBottom = AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim);
        Animation toBottom = AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim);
        if (!selecionado){
            btn_ingresos.startAnimation(fromBottom);
            btn_gastos.startAnimation(fromBottom);
            btn_mas.startAnimation(rotateOpen);
        }else {
            btn_ingresos.startAnimation(toBottom);
            btn_gastos.startAnimation(toBottom);
            btn_mas.startAnimation(rotateClose);
            rotateOpen.setFillAfter(false);
            rotateClose.setFillAfter(false);
            fromBottom.setFillAfter(false);
            toBottom.setFillAfter(false);
            btn_ingresos.setClickable(false);
            btn_gastos.setClickable(false);
        }

    }


}