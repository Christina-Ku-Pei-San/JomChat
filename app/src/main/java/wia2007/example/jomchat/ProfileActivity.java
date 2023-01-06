package wia2007.example.jomchat;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    ImageView ivBack, ivHome, ivMessenger, ivNotification;
    CircleImageView ivProfilePhoto;
    ImageButton IBEditProfile; //added Image Button to Edit Profile
    TextView displayNickName, displayUserName, displayUserYear, displayUserDepartment;
    Button BtnViewPost;

    String username;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = getIntent().getStringExtra("username");

        ivBack = findViewById(R.id.IVBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ProfileActivity.this, SettingActivity.class);
                startintent.putExtra("username", username);
                startActivity(startintent);
            }
        });

        ivHome = findViewById(R.id.IVHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ProfileActivity.this, PostListActivity.class);
                startintent.putExtra("username", username);
                startActivity(startintent);
            }
        });

        ivMessenger = findViewById(R.id.IVMessenger);
        ivMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ProfileActivity.this, MessengerListActivity.class);
                startintent.putExtra("username", username);
                startActivity(startintent);
            }
        });

        ivNotification = findViewById(R.id.IVNotification);
        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ProfileActivity.this, NotificationListActivity.class);
                startintent.putExtra("username", username);
                startActivity(startintent);
            }
        });

        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);
        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ProfileActivity.this, SettingActivity.class);
                startintent.putExtra("username", username);
                startActivity(startintent);
            }
        });

        //added Edit Profile Button
        IBEditProfile = findViewById(R.id.IBEditProfile);
        IBEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ProfileActivity.this, ViewProfileActivity.class);
                startintent.putExtra("username", username);
                startActivity(startintent);
            }
        });

        BtnViewPost = findViewById(R.id.BtnViewPost);
        BtnViewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent( ProfileActivity.this, PostListActivity.class);
                startintent.putExtra("username", username);
                startActivity(startintent);
            }
        });
    }
}