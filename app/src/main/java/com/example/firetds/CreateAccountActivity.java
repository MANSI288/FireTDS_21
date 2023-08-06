package com.example.firetds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccountActivity extends AppCompatActivity {
    //public Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        TextView toolbar = findViewById(R.id.CreateAccountToolbar);
        //setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("CreateAccount");

        TextView UserName,Password;
        EditText textPassword, EditUserName;
        Button save;
        UserName=findViewById(R.id.UserName);
        Password=findViewById(R.id.Password);
        textPassword=findViewById(R.id.editTextPassword);
        EditUserName=findViewById(R.id.EditUserName);
        save=findViewById(R.id.CreateButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = EditUserName.getText().toString().trim();
                String password = textPassword.getText().toString().trim();

                if (!username.isEmpty() && !password.isEmpty()) {
                    User user = new User(username, password);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("TDSAccount");
                    myRef.push().setValue(user);
                    Intent intent = new Intent(CreateAccountActivity.this, WelcomeActivity.class);
                    startActivity(intent);

                    Toast.makeText(CreateAccountActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateAccountActivity.this, "Please enter both username and password.", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
