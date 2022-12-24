package wia2007.example.jomchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;

public class SettingActivity extends AppCompatActivity {
    ImageView ivHome, ivMessenger, ivNotification;
    Button btnViewProfile, btnChangePassword, btnLogout;
    Switch swDarkMode;

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

        swDarkMode = findViewById(R.id.SwDarkMode);

        btnChangePassword = findViewById(R.id.BtnChangePassword);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(SettingActivity.this, ChangePasswordActivity.class);
                startActivity(startintent);
            }
        });

        btnLogout = findViewById(R.id.BtnLogout);
    }
}