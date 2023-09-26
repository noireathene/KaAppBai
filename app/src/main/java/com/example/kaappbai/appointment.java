package com.example.kaappbai;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class appointment extends Activity {

    private EditText nameEditText, courseEditText, emailEditText, dateEditText, messageEditText;
    private Button submit;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        nameEditText = findViewById(R.id.appointmentName);
        courseEditText = findViewById(R.id.course);
        emailEditText = findViewById(R.id.appointmentEmail);
        dateEditText = findViewById(R.id.appointmentDate);
        messageEditText = findViewById(R.id.appointmentNote);
        submit = findViewById(R.id.btnSubmitAppoint);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Capture user input
                String name = nameEditText.getText().toString();
                String course = courseEditText.getText().toString();
                String userEmail = emailEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String message = messageEditText.getText().toString();

                // Construct the email message
                String counselorEmail = "yotszxc@gmail.com"; // Replace with the actual counselor's email
                String subject = "Appointment Request";
                String emailMessage = "Name: " + name + "\n" +
                        "Course: " + course + "\n" +
                        "Email: " + userEmail + "\n" +
                        "Date: " + date + "\n" +
                        "Message: " + message;

                // Send the email to the server
                sendEmailToServer(counselorEmail, subject, emailMessage);

                // Save the request in Firebase Firestore
                saveAppointmentRequest(name, course, userEmail, date, message);
            }
        });
    }

    private void saveAppointmentRequest(String name, String course, String userEmail, String date, String message) {
        // Use the Firebase Firestore instance 'db' to save the appointment request data
        // Define a Firestore document and save the data as follows:

        // Create a Firestore document reference (you can use a collection name and a document ID)
        String collectionName = "appointmentRequests"; // Replace with your desired collection name
        String documentId = "unique_document_id"; // Replace with a unique identifier, e.g., appointment timestamp

        // Create a Firestore document reference
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(collectionName)
                .document(documentId)
                .set(createAppointmentData(name, course, userEmail, date, message), SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       // Toast.makeText("Appointment request successful", Toast.LENGTH_SHORT);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the failure to save data
                    }
                });
    }

    private Map<String, Object> createAppointmentData(String name, String course, String userEmail, String date, String message) {
        // Create a map with appointment request data
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("course", course);
        data.put("userEmail", userEmail);
        data.put("date", date);
        data.put("message", message);
        return data;
    }


    private void sendEmailToServer(String counselorEmail, String subject, String message) {
        // Create an instance of the Retrofit interface
        EmailApi emailApi = RetrofitClient.getInstance().create(EmailApi.class);

        // Create an EmailData object with the email data
        EmailData emailData = new EmailData();
        emailData.setRecipientEmail(counselorEmail);
        emailData.setSubject(subject);
        emailData.setMessage(message);

        // Make the HTTP POST request to send the email data
        Call<Void> call = emailApi.sendEmail(emailData);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Email sent successfully on the server
                } else {
                    // Email sending failed on the server
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Handle the failure to make the HTTP request
                // This could be due to network issues or server unavailability
            }
        });
    }

    // Implement the saveAppointmentRequest function as shown in previous responses

    // Implement the RetrofitClient class as shown in previous responses
}
