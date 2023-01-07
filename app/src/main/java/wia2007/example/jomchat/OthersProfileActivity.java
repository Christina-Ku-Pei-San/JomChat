package wia2007.example.jomchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class OthersProfileActivity extends AppCompatActivity {
    ImageView ivBack, ivHome, ivNotification;
    CircleImageView ivProfilePhoto, ivProfilePic;
    TextView displayUsername, displayName, displayUserYear, displayUserDepartment;

    String username, receiverusername, userURL, receiverURL, name, year, department;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_profile);

        ivBack = findViewById(R.id.IVBack);
        ivHome = findViewById(R.id.IVHome);
        ivNotification = findViewById(R.id.IVNotification);
        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);
        ivProfilePic = findViewById(R.id.IVOthersProfilePic);
        displayUsername = findViewById(R.id.displayOthersUsername);
        displayName = findViewById(R.id.displayOthersName);
        displayUserYear = findViewById(R.id.displayOthersUserYear);
        displayUserDepartment = findViewById(R.id.displayOthersUserDepartment);

        username = getIntent().getStringExtra("username");

        userURL = getIntent().getStringExtra("userURL");
        if (userURL.equals("")) {
            ivProfilePhoto.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        else {
            Picasso.get().load(userURL).into(ivProfilePhoto);
        }

        receiverURL = getIntent().getStringExtra("receiverURL");
        if (receiverURL.equals("")) {
            ivProfilePic.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        else {
            Picasso.get().load(receiverURL).into(ivProfilePic);
        }

        receiverusername = getIntent().getStringExtra("receiverusername");
        displayUsername.setText(receiverusername);

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()) {
                    if (data.getKey().equals(receiverusername)) {
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
                Intent startintent = new Intent(OthersProfileActivity.this, MessengerActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("receiverusername", receiverusername);
                startintent.putExtra("userURL", userURL);
                startintent.putExtra("receiverURL", receiverURL);
                startActivity(startintent);
            }
        });

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(OthersProfileActivity.this, PostListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(OthersProfileActivity.this, NotificationListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(OthersProfileActivity.this, SettingActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });
    }
}