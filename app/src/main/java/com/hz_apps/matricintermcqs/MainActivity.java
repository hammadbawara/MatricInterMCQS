package com.hz_apps.matricintermcqs;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigation;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottomNavigation);


        bottomNavigation.setOnItemSelectedListener(item -> {
            NavController navController = Navigation.findNavController(MainActivity.this,R.id.navHostFragment);
            switch (item.getItemId()){
                case R.id.home_bottomNav:
                    navController.popBackStack(R.id.fragment_Saved, true);
                    navController.popBackStack(R.id.fragment_more, true);
                    navController.navigate(R.id.chooseSubject);
                    break;
                case R.id.saved_bottomNav:
                    navController.popBackStack(R.id.chooseSubject, true);
                    navController.popBackStack(R.id.fragment_more, true);
                    navController.navigate(R.id.fragment_Saved);
                    break;
                case R.id.more_bottomNav:
                    navController.popBackStack(R.id.fragment_Saved, true);
                    navController.popBackStack(R.id.chooseSubject, true);
                    navController.navigate(R.id.fragment_more);

                    break;
            }
            return true;
        });
    }
}