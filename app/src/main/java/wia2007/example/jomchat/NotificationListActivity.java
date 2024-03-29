package wia2007.example.jomchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationListActivity extends AppCompatActivity {
    private ArrayList<NotificationItem> mNotificationList;
    private RecyclerView mRecyclerView;
    private NotificationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ImageView ivHome, ivMessenger;
    private CircleImageView ivProfilePhoto;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/");

    String username, userURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);

        ivHome = findViewById(R.id.IVHome);
        ivMessenger = findViewById(R.id.IVMessenger);
        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);

        username = getIntent().getStringExtra("username");
        userURL = getIntent().getStringExtra("userURL");
//        Toast.makeText(getApplicationContext(), userURL,Toast.LENGTH_SHORT).show();

        if (userURL.equals("")) {
            ivProfilePhoto.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        else {
            Picasso.get().load(userURL).into(ivProfilePhoto);
        }

        mNotificationList = new ArrayList<>();
        databaseReference.child("Likes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String postID = data.getKey();
                    for (DataSnapshot data2 : data.getChildren()) {
                        String likerUsername = data2.getKey();
                        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot data3 : snapshot.getChildren()) {
                                    if (data3.getKey().equals(likerUsername)) {
                                        String likerURL = data3.child("userURL").getValue().toString();
                                        databaseReference.child("Post").addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot data4 : snapshot.getChildren()) {
                                                    if (data4.getKey().equals(postID)) {
                                                        String postContent = data4.child("imageContent").getValue().toString();
                                                        String postOwnerUsername = data4.child("userName").getValue().toString();
                                                        String postURL = "";
                                                        if (data4.hasChild("imageURL")) {
                                                            postURL = data4.child("imageURL").getValue().toString();
                                                        }
                                                        String postOwnerImageuri = "";
                                                        if (data3.getKey().equals(postOwnerUsername)) {
                                                            postOwnerImageuri = data3.child("userURL").getValue().toString();
                                                        }
//                                                        mNotificationList.add(new NotificationItem(postID, likerURL, likerUsername+" liked a post", postContent));
                                                        mNotificationList.add(new NotificationItem(postID, likerURL, postOwnerImageuri, postURL, likerUsername+" liked a post", postContent, postOwnerUsername));
                                                    }
                                                    mAdapter.notifyDataSetChanged();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRecyclerView = findViewById(R.id.RVNotificationItem);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new NotificationAdapter(mNotificationList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(NotificationListActivity.this, NotificationActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("userURL", userURL);
                intent.putExtra("postID", mNotificationList.get(position).getPostID());
                intent.putExtra("likerImageuri", mNotificationList.get(position).getImageResource());
                intent.putExtra("likerUsername", mNotificationList.get(position).getText1());
                intent.putExtra("postContent", mNotificationList.get(position).getText2());
                intent.putExtra("postOwnerUsername", mNotificationList.get(position).getText3());
                intent.putExtra("postOwnerImageuri", mNotificationList.get(position).getImageResource2());
                intent.putExtra("postURL", mNotificationList.get(position).getImageResource3());
                startActivity(intent);
            }
        });

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(NotificationListActivity.this, PostListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(NotificationListActivity.this, MessengerListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(NotificationListActivity.this, SettingActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });
    }

//    public void createNotificationList() {
//        mNotificationList = new ArrayList<>();
//        mNotificationList.add(new NotificationItem(R.drawable.ic_baseline_account_circle_24, "Mary liked your post.", "Today was a fine day so I woke up early and went for a jog. Suddenly, a dog came out of nowhere and chased after me, i then ran 50km/h to get rid of it. After that I went to the nearby coffee shop to have my breakfast. When I’m on the way home, i accidentally stepped on a dog’s shit. In conclusion, today is an awful day."));
//        mNotificationList.add(new NotificationItem(R.drawable.ic_baseline_account_circle_24, "Line 3", "Line 4"));
//        mNotificationList.add(new NotificationItem(R.drawable.ic_baseline_account_circle_24, "Line 5", "Line 6"));
//        mNotificationList.add(new NotificationItem(R.drawable.ic_baseline_account_circle_24, "Line 7", "Line 8"));
//        mNotificationList.add(new NotificationItem(R.drawable.ic_baseline_account_circle_24, "Line 9", "Line 10"));
//        mNotificationList.add(new NotificationItem(R.drawable.ic_baseline_account_circle_24, "Line 11", "Line 12"));
//        mNotificationList.add(new NotificationItem(R.drawable.ic_baseline_account_circle_24, "Line 13", "Line 14"));
//        mNotificationList.add(new NotificationItem(R.drawable.ic_baseline_account_circle_24, "Line 15", "Line 16"));
//        mNotificationList.add(new NotificationItem(R.drawable.ic_baseline_account_circle_24, "Line 17", "Line 18"));
//        mNotificationList.add(new NotificationItem(R.drawable.ic_baseline_account_circle_24, "Line 19", "Line 20"));
//        mNotificationList.add(new NotificationItem(R.drawable.profile_photo1, "Line 21", "Line 22"));
//        mNotificationList.add(new NotificationItem(R.drawable.profile_photo1, "Line 23", "Line 24"));
//        mNotificationList.add(new NotificationItem(R.drawable.profile_photo1, "Line 25", "Line 26"));
//        mNotificationList.add(new NotificationItem(R.drawable.profile_photo1, "Line 27", "Line 28"));
//        mNotificationList.add(new NotificationItem(R.drawable.profile_photo1, "Line 29", "Line 30"));
//    }

//    public void buildRecyclerView() {
//        mRecyclerView = findViewById(R.id.RVNotificationItem);
//        mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this);
//        mAdapter = new NotificationAdapter(mNotificationList);
//
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setAdapter(mAdapter);
//
//        mAdapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Intent intent = new Intent(NotificationListActivity.this, NotificationActivity.class);
//                intent.putExtra("username", username);
//                intent.putExtra("userURL", userURL);
//                startActivity(intent);
//            }
//        });
//    }
}