package com.example.kaappbai;

/*import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class appointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        EditText nameEditText, emailEditText, dateEditText, courseEditText, messageEditText;
        Button submit;
        FirebaseFirestore db;

        // Initialize UI elements
        nameEditText = findViewById(R.id.appointmentName);
        courseEditText = findViewById(R.id.course);
        emailEditText = findViewById(R.id.appointmentEmail);
        dateEditText = findViewById(R.id.appointmentDate);
        messageEditText = findViewById(R.id.appointmentNote);
        submit = findViewById(R.id.btnSubmitAppoint);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input
                String name = nameEditText.getText().toString();
                String course = courseEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String message = messageEditText.getText().toString();

                //send email to guidance
                // Create an email
                Email from = new Email(email);
                Email to = new Email("yotszxc@gmail.com");
                String subject = "New Appointment Request";
                String body = String.format(
                        "Name: %s\nEmail: %s\nDate: %s\nTime: %s\nMessage: %s",
                        name, email, date, course, message
                );

                com.sendgrid.Content content = new com.sendgrid.Content("text/plain", body);
                com.sendgrid.Mail mail = new com.sendgrid.Mail(from, subject, to, content);

                // Initialize SendGrid
                SendGrid sg = new SendGrid("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");

                // Send the email
                Request request = new Request();
                try {
                    request.setMethod(Method.POST);
                    request.setEndpoint("mail/send");
                    request.setBody(mail.build());

                    Response response = sg.api(request);

                    // Check if the email was sent successfully
                    if (response.getStatusCode() == 202) {
                        // Email sent successfully
                        // Show a confirmation message to the user
                        // You can also add error handling here
                    } else {
                        // Handle errors
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Create a new appointment request document in Firestore
                Map<String, Object> appointmentRequest = new HashMap<>();
                appointmentRequest.put("name", name);
                appointmentRequest.put("email", email);
                appointmentRequest.put("course", course);
                appointmentRequest.put("date", date);
                appointmentRequest.put("message", message);

                // Add the document to the "appointmentRequests" collection
                db.collection("appointmentRequests")
                        .add(appointmentRequest)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // Document added successfully
                                // Show a confirmation message to the user
                                // You can also add error handling here
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle errors
                            }
                        });

                // Clear input fields
                nameEditText.setText("");
                emailEditText.setText("");
                dateEditText.setText("");
                courseEditText.setText("");
                messageEditText.setText("");
            }
        });
    }
}
*/

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sendgrid.Method;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.objects.Email;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.MediaType;

public class appointment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        EditText nameEditText, emailEditText, dateEditText, courseEditText, messageEditText;
        Button submit;
        FirebaseFirestore db;

        // Initialize UI elements
        nameEditText = findViewById(R.id.appointmentName);
        courseEditText = findViewById(R.id.course);
        emailEditText = findViewById(R.id.appointmentEmail);
        dateEditText = findViewById(R.id.appointmentDate);
        messageEditText = findViewById(R.id.appointmentNote);
        submit = findViewById(R.id.btnSubmitAppoint);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input
                String name = nameEditText.getText().toString();
                String course = courseEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String message = messageEditText.getText().toString();

                // Send email using SendGrid (your existing SendGrid code)

                // Make an HTTP request using OkHttp
                OkHttpClient client = new OkHttpClient();

                // Replace "YOUR_URL_HERE" with the actual URL where you want to send the data
                String apiUrl = "https://api.sendgrid.com/v3/mail/send";

                // Create JSON request body (adjust as needed)
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                String requestBody = "{\"name\":\"" + name + "\",\"email\":\"" + email + "\",\"course\":\"" + course + "\",\"date\":\"" + date + "\",\"message\":\"" + message + "\"}";
                RequestBody jsonBody = RequestBody.create(requestBody, JSON);

                // Create the HTTP request
                Request request = new Request.Builder()
                        .url(apiUrl)
                        .post(jsonBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        // Handle a successful response
                        // For example, you can show a toast message
                        Toast.makeText(appointment.this, "Request sent successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // Handle errors
                        // For example, you can show an error message
                        Toast.makeText(appointment.this, "Request failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Create a new appointment request document in Firestore (your existing Firestore code)

                // Clear input fields
                nameEditText.setText("");
                emailEditText.setText("");
                dateEditText.setText("");
                courseEditText.setText("");
                messageEditText.setText("");
            }
        });
    }
}
