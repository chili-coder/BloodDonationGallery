package com.sohaghlab.blooddonationgallery;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

  private DrawerLayout drawerLayout;
  public Toolbar toolbar;
  private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(toolbar);

        /// getSupportActionBar().setTitle("BDG");

        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar_main);
        navigationView=findViewById(R.id.navigationbar);


       ActionBarDrawerToggle  toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout
               , toolbar , R.string.navigation_draw_open,R.string.navigation_draw_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();




    }
}