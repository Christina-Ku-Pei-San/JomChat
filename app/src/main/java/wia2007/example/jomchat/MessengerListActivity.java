package wia2007.example.jomchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessengerListActivity extends AppCompatActivity {
    private ArrayList<MessengerItem> mMessengerList;
    private RecyclerView mRecyclerView;
    private MessengerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ImageView ivHome, ivNotification;
    CircleImageView ivProfilePhoto;
    EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_list);

        createNotificationList();
        buildRecyclerView();

        ivHome = findViewById(R.id.IVHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerListActivity.this, PostListActivity.class);
                startActivity(startintent);
            }
        });

        ivNotification = findViewById(R.id.IVNotification);
        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerListActivity.this, NotificationListActivity.class);
                startActivity(startintent);
            }
        });

        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);
        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerListActivity.this, SettingActivity.class);
                startActivity(startintent);
            }
        });

        etSearch = findViewById(R.id.ETSearch);
    }

    public void createNotificationList() {
        mMessengerList = new ArrayList<>();
        mMessengerList.add(new MessengerItem(R.drawable.profile_photo1, "John", "Bye bye"));
        mMessengerList.add(new MessengerItem(R.drawable.profile_photo1, "Jasmin", "Okay. See you later!"));
        mMessengerList.add(new MessengerItem(R.drawable.profile_photo1, "Alex", "Let's go"));
        mMessengerList.add(new MessengerItem(R.drawable.profile_photo1, "Line 9", "Line 10"));
        mMessengerList.add(new MessengerItem(R.drawable.profile_photo1, "Line 11", "Line 12"));
        mMessengerList.add(new MessengerItem(R.drawable.profile_photo1, "Line 13", "Line 14"));
        mMessengerList.add(new MessengerItem(R.drawable.profile_photo1, "Line 15", "Line 16"));
        mMessengerList.add(new MessengerItem(R.drawable.profile_photo1, "Line 17", "Line 18"));
        mMessengerList.add(new MessengerItem(R.drawable.profile_photo1, "Line 19", "Line 20"));
//        mMessengerList.add(new MessengerItem(R.drawable.profile_photo1, "Line 21", "Line 22"));
//        mMessengerList.add(new MessengerItem(R.drawable.profile_photo1, "Line 23", "Line 24"));
//        mMessengerList.add(new MessengerItem(R.drawable.profile_photo1, "Line 25", "Line 26"));
//        mMessengerList.add(new MessengerItem(R.drawable.profile_photo1, "Line 27", "Line 28"));
//        mMessengerList.add(new MessengerItem(R.drawable.profile_photo1, "Line 29", "Line 30"));
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.RVMessengerItem);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MessengerAdapter(mMessengerList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MessengerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(MessengerListActivity.this, MessengerActivity.class);
                startActivity(intent);
            }
        });
    }
}