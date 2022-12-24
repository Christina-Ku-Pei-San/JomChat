package wia2007.example.jomchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    Button btnLogin, btnSignup, btnPostList, btnPost, btnMessengerList, btnMessenger, btnNotificationList, btnNotification, btnSetting, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.BtnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(startintent);
            }
        });

        btnSignup = findViewById(R.id.BtnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(startintent);
            }
        });

        btnPostList = findViewById(R.id.BtnPostList);
        btnPostList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MainActivity.this, PostListActivity.class);
                startActivity(startintent);
            }
        });

        btnPost = findViewById(R.id.BtnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MainActivity.this, PostActivity.class);
                startActivity(startintent);
            }
        });

        btnMessengerList = findViewById(R.id.BtnMessengerList);
        btnMessengerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MainActivity.this, MessengerListActivity.class);
                startActivity(startintent);
            }
        });

        btnMessenger = findViewById(R.id.BtnMessenger);
        btnMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MainActivity.this, MessengerActivity.class);
                startActivity(startintent);
            }
        });

        btnNotificationList = findViewById(R.id.BtnNotificationList);
        btnNotificationList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MainActivity.this, NotificationListActivity.class);
                startActivity(startintent);
            }
        });

        btnNotification = findViewById(R.id.BtnNotification);
        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivity(startintent);
            }
        });

        btnSetting = findViewById(R.id.BtnSetting);
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(startintent);
            }
        });

        btnProfile = findViewById(R.id.BtnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(startintent);
            }
        });
    }
}