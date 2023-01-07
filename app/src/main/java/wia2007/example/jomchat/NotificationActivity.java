package wia2007.example.jomchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationActivity extends AppCompatActivity {
    private ArrayList<CommentItem> mCommentList;
    private RecyclerView mRecyclerView;
    private CommentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ImageView ivBack, ivHome, ivMessenger;
    CircleImageView ivProfilePhoto;

    String username, userURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ivBack = findViewById(R.id.IVBack);
        ivHome = findViewById(R.id.IVHome);
        ivMessenger = findViewById(R.id.IVMessenger);
        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);

        username = getIntent().getStringExtra("username");
        userURL = getIntent().getStringExtra("userURL");

        if (userURL.equals("")) {
            ivProfilePhoto.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        else {
            Picasso.get().load(userURL).into(ivProfilePhoto);
        }

        createNotificationList();
        buildRecyclerView();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(NotificationActivity.this, NotificationListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(NotificationActivity.this, PostListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(NotificationActivity.this, MessengerListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(NotificationActivity.this, SettingActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });
    }

    public void createNotificationList() {
        mCommentList = new ArrayList<>();
        mCommentList.add(new CommentItem(R.drawable.ic_baseline_account_circle_24, "Adam", "So beautiful!"));
        mCommentList.add(new CommentItem(R.drawable.ic_baseline_account_circle_24, "Line 3", "Line 4"));
        mCommentList.add(new CommentItem(R.drawable.ic_baseline_account_circle_24, "Line 5", "Line 6"));
        mCommentList.add(new CommentItem(R.drawable.ic_baseline_account_circle_24, "Line 7", "Line 8"));
        mCommentList.add(new CommentItem(R.drawable.ic_baseline_account_circle_24, "Line 9", "Line 10"));
        mCommentList.add(new CommentItem(R.drawable.ic_baseline_account_circle_24, "Line 11", "Line 12"));
        mCommentList.add(new CommentItem(R.drawable.ic_baseline_account_circle_24, "Line 13", "Line 14"));
        mCommentList.add(new CommentItem(R.drawable.ic_baseline_account_circle_24, "Line 15", "Line 16"));
        mCommentList.add(new CommentItem(R.drawable.ic_baseline_account_circle_24, "Line 17", "Line 18"));
        mCommentList.add(new CommentItem(R.drawable.ic_baseline_account_circle_24, "Line 19", "Line 20"));
//        mCommentList.add(new CommentItem(R.drawable.profile_photo1, "Line 21", "Line 22"));
//        mCommentList.add(new CommentItem(R.drawable.profile_photo1, "Line 23", "Line 24"));
//        mCommentList.add(new CommentItem(R.drawable.profile_photo1, "Line 25", "Line 26"));
//        mCommentList.add(new CommentItem(R.drawable.profile_photo1, "Line 27", "Line 28"));
//        mCommentList.add(new CommentItem(R.drawable.profile_photo1, "Line 29", "Line 30"));
    }

    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.RVCommentItem);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CommentAdapter(mCommentList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}