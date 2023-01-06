package wia2007.example.jomchat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostListActivity extends AppCompatActivity {
    FloatingActionButton search_button, add_button, feedback_button;
    private RecyclerView mRecyclerView;
    private PostAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ImageView ivMessenger, ivNotification;
    CircleImageView ivProfilePhoto;

    private String username;

//    SwipeRefreshLayout swipeRefreshLayout;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);
//
//        swipeRefreshLayout = findViewById(R.id.SRLPostListAct);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//               swipeRefreshLayout.setRefreshing(false);
//
//            }
//        });

        ivMessenger = findViewById(R.id.IVMessenger);
        ivNotification = findViewById(R.id.IVNotification);
        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);
        search_button = (FloatingActionButton)findViewById(R.id.FABSearch);
        add_button = (FloatingActionButton)findViewById(R.id.FABAdd);

        ArrayList<PostItem> post = new ArrayList<>();
        databaseReference.child("Post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()) {
                    String username = data.child("userName").getValue().toString();
                    String content = data.child("imageContent").getValue().toString();
                    post.add(new PostItem(R.drawable.ic_baseline_account_circle_24, username, content, R.drawable.post_photo1));
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRecyclerView = findViewById(R.id.RVPostItem);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager= new LinearLayoutManager(this);
        mAdapter = new PostAdapter(post);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        username = getIntent().getStringExtra("username");

        ivMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(PostListActivity.this, MessengerListActivity.class);
                startintent.putExtra("username", username);
                startActivity(startintent);
            }
        });

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(PostListActivity.this, NotificationListActivity.class);
                startintent.putExtra("username", username);
                startActivity(startintent);
            }
        });

        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(PostListActivity.this, SettingActivity.class);
                startintent.putExtra("username", username);
                startActivity(startintent);
            }
        });

        mAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(PostListActivity.this, PostActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        search_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent s = new Intent(PostListActivity.this,SearchPostActivity.class);
                s.putExtra("username", username);
                startActivity(s);
            }
        });

        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PostListActivity.this, AddPostActivity.class);
                a.putExtra("username", username);
                startActivity(a);
            }
        });
    }
}