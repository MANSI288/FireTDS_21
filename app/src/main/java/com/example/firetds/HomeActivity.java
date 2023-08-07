package com.example.firetds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    public Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Find the testButton view by ID
        testButton = findViewById(R.id.testButton);

        // Find the existing toolbar by ID
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Set the existing toolbar as the support ActionBar
        setSupportActionBar(toolbar);

        // Set a custom title for the activity (empty string to remove the default label)
        getSupportActionBar().setTitle("");

        // Enable the back button (up button)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Check if the testButton is not null before setting the click listener
        if (testButton != null) {
            testButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToTestActivity();
                }
            });
        }

        // Set click listener for the profile icon to show the dropdown menu
        ImageView profileIcon = findViewById(R.id.profile_icon);
        if (profileIcon != null) {
            profileIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDropdownMenu(v);
                }
            });
        }
    }

    public void showDropdownMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.dropdown_menu, popupMenu.getMenu());

        // Handle menu item click events
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_data_history) {
                navigateToDataHistoryActivity(); // Call the method to handle "Data History" action
                return true;
            } else if (item.getItemId() == R.id.menu_predictions) {
                navigateToPredictionsActivity();
                return true;
            }
            else if (item.getItemId() == R.id.menu_language) {
                navigateToLanguageActivity();
                return true;
            }
            else if (item.getItemId() == R.id.menu_log_out) {
                Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });

        popupMenu.show();
    }

    private void navigateToMyProfileActivity() {
        Intent intent = new Intent(HomeActivity.this, MyProfileActivity.class);
        startActivity(intent);
    }

    private void navigateToDataHistoryActivity() {
        // Handle the action for "Data History" here.
        Intent intent = new Intent(HomeActivity.this, HistoryList.class);
        startActivity(intent);
    }

    private void navigateToPredictionsActivity() {
        Intent intent = new Intent(HomeActivity.this, PredictionActivity.class);
        startActivity(intent);
    }

    private void navigateToLanguageActivity() {
        Intent intent = new Intent(HomeActivity.this, LanguageSelectionActivity.class);
        startActivity(intent);
    }

    public void goToTestActivity() {
        Intent intent = new Intent(HomeActivity.this, TestActivity.class);
        startActivity(intent);
    }
}
