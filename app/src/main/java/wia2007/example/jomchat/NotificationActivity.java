package wia2007.example.jomchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {
    private ArrayList<CommentItem> mCommentList;

    private RecyclerView mRecyclerView;
    private CommentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        createNotificationList();
        buildRecyclerView();
    }

    public void createNotificationList() {
        mCommentList = new ArrayList<>();
        mCommentList.add(new CommentItem(R.drawable.profile_photo1, "Adam", "So beautiful!"));
        mCommentList.add(new CommentItem(R.drawable.profile_photo1, "Line 3", "Line 4"));
        mCommentList.add(new CommentItem(R.drawable.profile_photo1, "Line 5", "Line 6"));
        mCommentList.add(new CommentItem(R.drawable.profile_photo1, "Line 7", "Line 8"));
        mCommentList.add(new CommentItem(R.drawable.profile_photo1, "Line 9", "Line 10"));
        mCommentList.add(new CommentItem(R.drawable.profile_photo1, "Line 11", "Line 12"));
        mCommentList.add(new CommentItem(R.drawable.profile_photo1, "Line 13", "Line 14"));
        mCommentList.add(new CommentItem(R.drawable.profile_photo1, "Line 15", "Line 16"));
        mCommentList.add(new CommentItem(R.drawable.profile_photo1, "Line 17", "Line 18"));
        mCommentList.add(new CommentItem(R.drawable.profile_photo1, "Line 19", "Line 20"));
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