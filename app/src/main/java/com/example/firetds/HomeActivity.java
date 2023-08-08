package com.example.firetds;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // Find the existing toolbar by ID
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Set the existing toolbar as a support ActionBar
        setSupportActionBar(toolbar);

        // Set a custom title for the activity (empty string to remove the default label)
        getSupportActionBar().setTitle("");

        // Enable the back button (up button)
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        ImageView testWaterIcon = findViewById(R.id.testWaterImageView);
        if (testWaterIcon != null) {
            testWaterIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToTestActivity();
                }
            });
        }

        ImageView historyIcon = findViewById(R.id.historyImageView);
        if (historyIcon != null) {
            historyIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToHistoryList();
                }
            });
        }

        ImageView languageIcon = findViewById(R.id.languageImageView);
        if (languageIcon != null) {
            languageIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToLanguageChange();
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
            if (item.getItemId() == R.id.menu_predictions) {
                navigateToPredictionsActivity();
                return true;
            } else if (item.getItemId() == R.id.menu_log_out) {
                Intent intent = new Intent(HomeActivity.this, Login.class);
                startActivity(intent);
                return true;
            } else if (item.getItemId() == R.id.contact_support) {
                openSupportEmailClient();

                return true;
            }
            return false;
        });

        popupMenu.show();
    }

    private void openSupportEmailClient() {
        String emailAddress = "support@example.com";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + emailAddress));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Support Request");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void navigateToMyProfileActivity() {
        Intent intent = new Intent(HomeActivity.this, MyProfileActivity.class);
        startActivity(intent);
    }



    private void navigateToPredictionsActivity() {
        Intent intent = new Intent(HomeActivity.this, PredictionActivity.class);
        startActivity(intent);
    }



    public void goToTestActivity() {
        Intent intent = new Intent(HomeActivity.this, TestActivity.class);
        startActivity(intent);
    }
    public void goToHistoryList() {
        Intent intent = new Intent(HomeActivity.this, HistoryList.class);
        startActivity(intent);
    }

    public void goToLanguageChange() {
        Intent intent = new Intent(HomeActivity.this, LanguageSelectionActivity.class);
        startActivity(intent);
    }



}
