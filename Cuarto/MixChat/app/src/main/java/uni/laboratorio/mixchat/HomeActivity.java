package uni.laboratorio.mixchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import uni.laboratorio.mixchat.Fragments.AjustesFragment;
import uni.laboratorio.mixchat.Fragments.ChatFragment;
import uni.laboratorio.mixchat.Fragments.PerfilFragment;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment selectorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bottomNavigationView = findViewById(R.id.bottom_navegation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                selectorFragment = null;

                switch (item.getItemId()) {

                    case R.id.perfil:
                        selectorFragment = new PerfilFragment();
                        break;

                    case R.id.chat:
                        selectorFragment = new ChatFragment();
                        break;

                    case R.id.ajustes:
                        selectorFragment = new AjustesFragment();
                        break;
                }

                if (selectorFragment!=null){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectorFragment).commit();
                }
                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ChatFragment()).commit();

    }
}