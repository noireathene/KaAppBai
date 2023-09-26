package com.example.kaappbai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

         DrawerLayout drawerLayout;
         NavigationView navigationView;
         TextView navHeaderName;
         ImageButton chat;
         ImageButton appointment;


            // Initialize UI elements
            drawerLayout = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view);
            navHeaderName = navigationView.getHeaderView(0).findViewById(R.id.nav_header_name);
            chat = findViewById(R.id.btnchat);
            appointment = findViewById(R.id.btnAppointmentRequest);

            // Set the user's name dynamically (you can retrieve it from Firebase)
            navHeaderName.setText("John Doe");

            // Set up the navigation drawer toggle
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();

            // Handle navigation item clicks
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    int id = menuItem.getItemId();
                    // Handle each menu item click here (e.g., open a new activity for settings)
                    if (id == R.id.nav_account) {
                        // Handle My Account
                    } else if (id == R.id.nav_settings) {
                        // Handle Settings
                    } else if (id == R.id.nav_about_us) {
                        // Handle About Us
                    }else if (id == R.id.nav_logout) {
                        // Handle Logout
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(homepage.this, login.class));
                        finish();
                    }
                    // Close the navigation drawer after handling the click
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
            });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homepage.this, appointment.class));
                finish();
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homepage.this, chatpage.class));
            }
        });
        }
    }

