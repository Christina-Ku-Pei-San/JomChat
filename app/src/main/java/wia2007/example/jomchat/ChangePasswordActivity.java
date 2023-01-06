package wia2007.example.jomchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ChangePasswordActivity extends AppCompatActivity {
    ImageView ivBack, ivHome, ivMessenger, ivNotification;
    Button btnConfirm;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        username = getIntent().getStringExtra("username");

        ivBack = findViewById(R.id.IVBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ChangePasswordActivity.this, SettingActivity.class);
                startintent.putExtra("username", username);
                startActivity(startintent);
            }
        });

        ivHome = findViewById(R.id.IVHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ChangePasswordActivity.this, PostListActivity.class);
                startintent.putExtra("username", username);
                startActivity(startintent);
            }
        });

        ivMessenger = findViewById(R.id.IVMessenger);
        ivMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ChangePasswordActivity.this, MessengerListActivity.class);
                startintent.putExtra("username", username);
                startActivity(startintent);
            }
        });

        ivNotification = findViewById(R.id.IVNotification);
        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ChangePasswordActivity.this, NotificationListActivity.class);
                startintent.putExtra("username", username);
                startActivity(startintent);
            }
        });

        btnConfirm = findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ChangePasswordActivity.this, SettingActivity.class);
                startintent.putExtra("username", username);
                startActivity(startintent);
            }
        });
    }
}