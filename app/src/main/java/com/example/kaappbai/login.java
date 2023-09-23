package com.example.kaappbai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends AppCompatActivity {



    private PreferenceManager preferenceManager;
    private static final String TAG = "GoogleActivity";

    private static final int RC_SIGN_IN = 2;

    private GoogleSignInClient mGoogleSignInClient;

    FirebaseAuth mAuth;

    //FIREBASE AUTH LISTENER
    FirebaseAuth.AuthStateListener mAuthlistener;

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthlistener);
    }

        Button btnenter;
        EditText email, password;
        TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // getSupportActionBar().hide(); //This line hides the action bar

        preferenceManager = new PreferenceManager(getApplicationContext());

        mAuth = FirebaseAuth.getInstance();

        // Initialize mAuthlistener
        mAuthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in, go to the home screen
                    startActivity(new Intent(login.this, homepage.class));
                    finish(); // Finish the login activity
                }
            }
        };

        // Initialize Firebase Authentication
        //FirebaseAuth auth = FirebaseAuth.getInstance();

        // Initialize UI elements
        email = findViewById(R.id.etemail);
        password = findViewById(R.id.etpass);
        btnenter = findViewById(R.id.btnlogin);
        signup = findViewById(R.id.signup);

        // Check if a user is already signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // If a user is already signed in, navigate to the home screen
            startActivity(new Intent(login.this, homepage.class));
            finish(); // Finish the login activity
        }

        btnenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input
                String emailStr = email.getText().toString().trim();
                String passwordStr = password.getText().toString().trim();

                // Check if any field is empty
                if (TextUtils.isEmpty(emailStr) || TextUtils.isEmpty(passwordStr)) {
                    Toast.makeText(login.this, "Both email and password are required", Toast.LENGTH_SHORT).show();
                } else {
                    // Authenticate the user with Firebase Authentication
                    mAuth.signInWithEmailAndPassword(emailStr, passwordStr)
                            .addOnCompleteListener(new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // User authenticated successfully
                                        Toast.makeText(login.this, "Login successful", Toast.LENGTH_SHORT).show();
                                        // TODO: Handle the next steps after successful login
                                        // For example, navigate to the home screen
                                        startActivity(new Intent(login.this, homepage.class));
                                        finish(); // Finish the login activity to prevent going back
                                    } else {
                                        // Authentication failed
                                        Toast.makeText(login.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        // Handle signup text click to navigate to the signup activity
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this, signup.class));
            }
        });
    }
}
