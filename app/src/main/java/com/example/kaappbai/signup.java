package com.example.kaappbai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity {


    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        EditText name, age, birthdate, email, reemail, password, repassword;
        Button signup;
        FirebaseFirestore firestore;
        FirebaseAuth auth;

        // Initialize Firestore
            firestore = FirebaseFirestore.getInstance();
            auth = FirebaseAuth.getInstance();

            // Initialize UI elements
            name = findViewById(R.id.name);
            age = findViewById(R.id.age);
            birthdate = findViewById(R.id.birthdate);
            email = findViewById(R.id.email);
            reemail = findViewById(R.id.conemail);
            password = findViewById(R.id.password);
            repassword = findViewById(R.id.conpassword);
            signup = findViewById(R.id.signupbtn);

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get user input
                    String nameStr = name.getText().toString().trim();
                    String ageStr = age.getText().toString().trim();
                    String birthdateStr = birthdate.getText().toString().trim();
                    String emailStr = email.getText().toString().trim();
                    String reemailStr = reemail.getText().toString().trim();
                    String passwordStr = password.getText().toString().trim();
                    String repasswordStr = repassword.getText().toString().trim();

                    // Check if any field is empty
                    if (TextUtils.isEmpty(nameStr) || TextUtils.isEmpty(ageStr) ||
                            TextUtils.isEmpty(birthdateStr) || TextUtils.isEmpty(emailStr) ||
                            TextUtils.isEmpty(reemailStr) || TextUtils.isEmpty(passwordStr) ||
                            TextUtils.isEmpty(repasswordStr)) {
                        Toast.makeText(signup.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    } else if (!emailStr.equals(reemailStr)) {
                        Toast.makeText(signup.this, "Emails should match", Toast.LENGTH_SHORT).show();
                    } else if (!passwordStr.equals(repasswordStr)) {
                        Toast.makeText(signup.this, "Passwords should match", Toast.LENGTH_SHORT).show();
                    } else {
                        // Create a map with user data
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("name", nameStr);
                        userData.put("age", ageStr);
                        userData.put("birthdate", birthdateStr);
                        userData.put("email", emailStr);

                        // Save user data to Firestore
                        firestore.collection("KaAppBai_USERS")
                                .add(userData)
                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        if (task.isSuccessful()) {
                                            // User data saved successfully
                                            Toast.makeText(signup.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), login.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            // TODO: Handle the next steps, e.g., user authentication
                                        } else {
                                            // Failed to save user data
                                            Toast.makeText(signup.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                                        }
                                    }


                                });
                    }
                    auth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(signup.this, "User registered successfully", Toast.LENGTH_LONG).show();
                                FirebaseUser firebaseUser = auth.getCurrentUser();

                                //send verification email
                                firebaseUser.sendEmailVerification();

                                //open login page after successful register
                                Intent intent = new Intent(signup.this, login.class);

                                //prevent user from returning to previous activity
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();


                            }
                        }
                    });
                }
            });

            //google authentication sign in


    }
}



