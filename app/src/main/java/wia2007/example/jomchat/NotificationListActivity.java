package wia2007.example.jomchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class NotificationListActivity extends AppCompatActivity {
    private ArrayList<NotificationItem> mNotificationList;

    private RecyclerView mRecyclerView;
    private NotificationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);

        createNotificationList();
        buildRecyclerView();
    }

    public void changeItem(int position, String text) {
        mNotificationList.get(position).changeText1(text);
        mAdapter.notifyItemChanged(position);
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
                changeItem(position, "Clicked");
            }
        });
    }
}