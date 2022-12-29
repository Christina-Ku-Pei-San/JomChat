package wia2007.example.jomchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessengerActivity extends AppCompatActivity {
    private ArrayList<MessageItem> mMessageList;
    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ImageView ivBack, ivHome, ivNotification;
    CircleImageView ivProfilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        createMessageList();
        buildRecyclerView();

        ivBack = findViewById(R.id.IVBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerActivity.this, MessengerListActivity.class);
                startActivity(startintent);
            }
        });

        ivHome = findViewById(R.id.IVHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerActivity.this, PostListActivity.class);
                startActivity(startintent);
            }
        });

        ivNotification = findViewById(R.id.IVNotification);
        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerActivity.this, NotificationListActivity.class);
                startActivity(startintent);
            }
        });

        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);
        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerActivity.this, SettingActivity.class);
                startActivity(startintent);
            }
        });
    }

    public void createMessageList() {
        mMessageList = new ArrayList<>();
        mMessageList.add(new MessageItem("Hello, how are you?", "00:00", "sender"));
        mMessageList.add(new MessageItem("Hi", "00:01", "receiver"));
        mMessageList.add(new MessageItem("I'm fine, thank you", "00:02", "receiver"));
        mMessageList.add(new MessageItem("I'm fine too", "00:03", "sender"));
        mMessageList.add(new MessageItem("Line 5 Line 5 Line 5 Line 5 Line 5 Line 5 Line 5", "00:04", "sender"));
        mMessageList.add(new MessageItem("Line 6", "00:05", "sender"));
        mMessageList.add(new MessageItem("Line 7 Line 7 Line 7 Line 7 Line 7 Line 7 Line 7", "00:06", "receiver"));
        mMessageList.add(new MessageItem("Line 8", "00:07", "sender"));
        mMessageList.add(new MessageItem("Line 9", "00:08", "receiver"));
        mMessageList.add(new MessageItem("Line 10", "00:09", "receiver"));
        mMessageList.add(new MessageItem("Line 11", "00:10", "receiver"));
        mMessageList.add(new MessageItem("Line 12", "00:11", "sender"));
        mMessageList.add(new MessageItem("Line 13", "00:12", "sender"));
        mMessageList.add(new MessageItem("Line 14", "00:13", "receiver"));
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.RVMessageItem);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MessageAdapter(mMessageList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}