package wia2007.example.jomchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnPost, btnProfile, btnNotificationList, btnNotification, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPost = findViewById(R.id.BtnPost);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MainActivity.this, PostActivity.class);
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
        login = findViewById(R.id.gogo);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lg = new Intent(MainActivity.this, SignUpActivityActivity.class);
                startActivity(lg);
            }
        });
    }
}