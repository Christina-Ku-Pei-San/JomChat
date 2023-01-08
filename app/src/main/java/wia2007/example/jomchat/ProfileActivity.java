package wia2007.example.jomchat;

import androidx.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    ImageView ivBack, ivHome, ivMessenger, ivNotification;
    CircleImageView ivProfilePhoto, ivProfilePic;
    ImageButton IBEditProfile; //added Image Button to Edit Profile
    TextView displayUsername, displayName, displayUserYear, displayUserDepartment;
    Button BtnViewPost;

    String username, name, year, department, userURL;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivBack = findViewById(R.id.IVBack);
        ivHome = findViewById(R.id.IVHome);
        ivMessenger = findViewById(R.id.IVMessenger);
        ivNotification = findViewById(R.id.IVNotification);
        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);
        ivProfilePic = findViewById(R.id.IVProfilePic);
        //added Edit Profile Button
        IBEditProfile = findViewById(R.id.IBEditProfile);
        displayUsername = findViewById(R.id.displayUsername);
        displayName = findViewById(R.id.displayName);
        displayUserYear = findViewById(R.id.displayUserYear);
        displayUserDepartment = findViewById(R.id.displayUserDepartment);
        BtnViewPost = findViewById(R.id.BtnViewPost);

        userURL = getIntent().getStringExtra("userURL");
        if (userURL.equals("")) {
            ivProfilePic.setImageResource(R.drawable.ic_baseline_account_circle_24);
            ivProfilePhoto.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        else {
            Picasso.get().load(userURL).into(ivProfilePic);
            Picasso.get().load(userURL).into(ivProfilePhoto);
        }

        username = getIntent().getStringExtra("username");
        displayUsername.setText(username);

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()) {
                    if (data.getKey().equals(username)) {
                        name = data.child("name").getValue().toString();
                        year = data.child("year").getValue().toString();
                        department = data.child("department").getValue().toString();
                        displayName.setText(name);
                        displayUserYear.setText(year);
                        displayUserDepartment.setText(department);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ProfileActivity.this, SettingActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ProfileActivity.this, PostListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ProfileActivity.this, MessengerListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(ProfileActivity.this, NotificationListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        IBEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = displayName.getText().toString();
                year = displayUserYear.getText().toString();
                department = displayUserDepartment.getText().toString();
                Intent startintent = new Intent(ProfileActivity.this, ViewProfileActivity.class);
                startintent.putExtra("userURL", userURL);
                startintent.putExtra("username", username);
                startintent.putExtra("name", name);
                startintent.putExtra("year", year);
                startintent.putExtra("department", department);
                startActivity(startintent);
            }
        });

        BtnViewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent( ProfileActivity.this, OwnPostListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });
    }
}