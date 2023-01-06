package wia2007.example.jomchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

public class SettingActivity extends AppCompatActivity {
    ImageView ivHome, ivMessenger, ivNotification;
    Button btnViewProfile, btnChangePassword, btnFeedback, btnLogout;
    Switch swDarkMode;
    boolean nightMODE;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ivHome = findViewById(R.id.IVHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(SettingActivity.this, PostListActivity.class);
                startActivity(startintent);
            }
        });

        ivMessenger = findViewById(R.id.IVMessenger);
        ivMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(SettingActivity.this, MessengerListActivity.class);
                startActivity(startintent);
            }
        });

        ivNotification = findViewById(R.id.IVNotification);
        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(SettingActivity.this, NotificationListActivity.class);
                startActivity(startintent);
            }
        });

        btnViewProfile = findViewById(R.id.BtnViewProfile);
        btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(SettingActivity.this, ProfileActivity.class);
                startActivity(startintent);
            }
        });

        getSupportActionBar().hide();

        swDarkMode = findViewById(R.id.SwDarkMode);

        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMODE = sharedPreferences.getBoolean("night", false);

        if (nightMODE){
            swDarkMode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        swDarkMode.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if (nightMODE){
                  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                  editor = sharedPreferences.edit();
                  editor.putBoolean("night", false);
              } else{
                  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                  editor = sharedPreferences.edit();
                  editor.putBoolean("night", true);
              }
              editor.apply();
          }
        });

        btnChangePassword = findViewById(R.id.BtnChangePassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(SettingActivity.this, ChangePasswordActivity.class);
                startActivity(startintent);
            }
        });

        btnFeedback = findViewById(R.id.BtnFeedback);
        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(SettingActivity.this, FeedbackActivity.class);
                startActivity(startintent);
            }
        });

        btnLogout = findViewById(R.id.BtnLogout);
    }
}