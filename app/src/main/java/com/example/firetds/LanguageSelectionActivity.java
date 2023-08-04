package com.example.firetds;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

public class LanguageSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        //add header functionality
        Toolbar toolbar_back = findViewById(R.id.header);
        setSupportActionBar(toolbar_back);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        RadioGroup languageRadioGroup = findViewById(R.id.languageRadioGroup);
        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(v -> {
            // Get the selected language from the radio group
            int selectedRadioButtonId = languageRadioGroup.getCheckedRadioButtonId();
            String selectedLanguage;

            if (selectedRadioButtonId == R.id.radioEnglish) {
                selectedLanguage = "en"; // English
            } else if (selectedRadioButtonId == R.id.radioFrench) {
                selectedLanguage = "fr"; // French
            } else {
                selectedLanguage = "en"; // Default to English
            }

            // Save the selected language preference in SharedPreferences
            saveSelectedLanguage(selectedLanguage);

            // Update the app's locale based on the selected language
            updateAppLocale(selectedLanguage);

            // Reload the main screen or the desired activity to apply the language change
            Intent intent = new Intent(LanguageSelectionActivity.this, WelcomeActivity.class);
            startActivity(intent);
            finish(); // Finish this activity to prevent going back to the language selection screen
        });
    }

    // Method to save the selected language in SharedPreferences
    private void saveSelectedLanguage(String selectedLanguage) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("selected_language", selectedLanguage);
        editor.apply();
    }

    // Method to update the app's locale based on the selected language
    private void updateAppLocale(String selectedLanguage) {
        Locale locale = new Locale(selectedLanguage);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}

