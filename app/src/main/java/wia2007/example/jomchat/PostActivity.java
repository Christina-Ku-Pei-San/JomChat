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

public class PostActivity extends AppCompatActivity {
    private ArrayList<CommentItem> mCommentList;
    private RecyclerView mRecyclerView;
    private CommentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ImageView ivBack, ivMessenger, ivNotification;
    CircleImageView ivProfilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        createCommentList();
        buildRecyclerView();

        ivBack = findViewById(R.id.IVBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(PostActivity.this, PostListActivity.class);
                startActivity(startintent);
            }
        });

        ivMessenger = findViewById(R.id.IVMessenger);
        ivMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(PostActivity.this, MessengerListActivity.class);
                startActivity(startintent);
            }
        });

        ivNotification = findViewById(R.id.IVNotification);
        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(PostActivity.this, NotificationListActivity.class);
                startActivity(startintent);
            }
        });

        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);
        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(PostActivity.this, SettingActivity.class);
                startActivity(startintent);
            }
        });
    }

    public void createCommentList() {
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