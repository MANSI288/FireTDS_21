package com.example.firetds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    private EditText Username, Password;
    private Button btnSignIn;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);





        // mDatabase = FirebaseDatabase.getInstance().getReference("TDSAccount");
        Username = findViewById(R.id.enterUserName);
        Password = findViewById(R.id.enterPassword);
        btnSignIn = findViewById(R.id.buttonSignIn);
        Button createAccountButton = findViewById(R.id.buttonSignUp);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = Username.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if (!username.isEmpty() && !password.isEmpty()) {
                    // Check if the provided username and password exist in the database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("TDSAccount");
                    myRef.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            boolean isFound = false;
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                User user = snapshot.getValue(User.class);
                                Log.d("MyTag", "The value of myVariable is: " + username);
                                Log.d("MyTag", "The value of myVariable is: " + user.getUsername());
                                Log.d("MyTag", "The value of myVariable is: " + password);
                                Log.d("MyTag", "The value of myVariable is: " + user.getPassword());

                                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                                    isFound = true;
                                    // Sign in successful, go to the welcome activity or user profile page
                                    Intent intent = new Intent(Login.this, HomeActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(Login.this, "Sign in successful!", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                            if (!isFound) {
                                Toast.makeText(Login.this, "Invalid username or password.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(Login.this, "Failed to read database.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(Login.this, "Please enter both username and password.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }


}