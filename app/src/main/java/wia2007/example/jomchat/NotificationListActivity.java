package wia2007.example.jomchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationListActivity extends AppCompatActivity {
    private ArrayList<NotificationItem> mNotificationList;
    private RecyclerView mRecyclerView;
    private NotificationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ImageView ivBack, ivHome, ivMessenger;
    CircleImageView ivProfilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);

        createNotificationList();
        buildRecyclerView();

        ivBack = findViewById(R.id.IVBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(NotificationListActivity.this, PostListActivity.class);
                startActivity(startintent);
            }
        });

        ivHome = findViewById(R.id.IVHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(NotificationListActivity.this, PostListActivity.class);
                startActivity(startintent);
            }
        });

        ivMessenger = findViewById(R.id.IVMessenger);
        ivMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(NotificationListActivity.this, MessengerListActivity.class);
                startActivity(startintent);
            }
        });

        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);
        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(NotificationListActivity.this, SettingActivity.class);
                startActivity(startintent);
            }
        });
    }

    public void createNotificationList() {
        mNotificationList = new ArrayList<>();
        mNotificationList.add(new NotificationItem(R.drawable.profile_photo1, "Mary liked your post.", "Today was a fine day so I woke up early and went for a jog. Suddenly, a dog came out of nowhere and chased after me, i then ran 50km/h to get rid of it. After that I went to the nearby coffee shop to have my breakfast. When I’m on the way home, i accidentally stepped on a dog’s shit. In conclusion, today is an awful day."));
        mNotificationList.add(new NotificationItem(R.drawable.profile_photo1, "Line 3", "Line 4"));
        mNotificationList.add(new NotificationItem(R.drawable.profile_photo1, "Line 5", "Line 6"));
        mNotificationList.add(new NotificationItem(R.drawable.profile_photo1, "Line 7", "Line 8"));
        mNotificationList.add(new NotificationItem(R.drawable.profile_photo1, "Line 9", "Line 10"));
        mNotificationList.add(new NotificationItem(R.drawable.profile_photo1, "Line 11", "Line 12"));
        mNotificationList.add(new NotificationItem(R.drawable.profile_photo1, "Line 13", "Line 14"));
        mNotificationList.add(new NotificationItem(R.drawable.profile_photo1, "Line 15", "Line 16"));
        mNotificationList.add(new NotificationItem(R.drawable.profile_photo1, "Line 17", "Line 18"));
        mNotificationList.add(new NotificationItem(R.drawable.profile_photo1, "Line 19", "Line 20"));
//        mNotificationList.add(new NotificationItem(R.drawable.profile_photo1, "Line 21", "Line 22"));
//        mNotificationList.add(new NotificationItem(R.drawable.profile_photo1, "Line 23", "Line 24"));
//        mNotificationList.add(new NotificationItem(R.drawable.profile_photo1, "Line 25", "Line 26"));
//        mNotificationList.add(new NotificationItem(R.drawable.profile_photo1, "Line 27", "Line 28"));
//        mNotificationList.add(new NotificationItem(R.drawable.profile_photo1, "Line 29", "Line 30"));
    }

    public void buildRecyclerView() {
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
                startActivity(intent);
            }
        });
    }
}