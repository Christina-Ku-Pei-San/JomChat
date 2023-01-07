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
import com.squareup.picasso.Picasso;

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
    private String userURL;

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

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()) {
                    if (data.getKey().equals(username)) {
                        userURL = data.child("userURL").getValue().toString();
                        if (userURL.equals("")) {
                            ivProfilePhoto.setImageResource(R.drawable.ic_baseline_account_circle_24);
                        }
                        else {
                            Picasso.get().load(userURL).into(ivProfilePhoto);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        if (userURL.equals("")) {
//            ivProfilePhoto.setVisibility(View.INVISIBLE);
//        }
//        else {
//            Picasso.get().load(userURL).into(ivProfilePhoto);
//        }

        ArrayList<PostItem> post = new ArrayList<>();
        databaseReference.child("Post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                post.clear();
                for (DataSnapshot data: snapshot.getChildren()) {
                    String postID = data.getKey();
                    String userurl = data.child("userURL").getValue().toString();
                    String username = data.child("userName").getValue().toString();
                    String content = data.child("imageContent").getValue().toString();
                    if (data.hasChild("imageURL")) {
                        String imageurl = data.child("imageURL").getValue().toString();
                        post.add(new PostItem(postID, userurl, username, content, imageurl));
//                        post.add(new PostItem(R.drawable.ic_baseline_account_circle_24, username, content, imageurl));
                    }
                    else {
                        post.add(new PostItem(postID, userurl, username, content, null));
//                        post.add(new PostItem(R.drawable.ic_baseline_account_circle_24, username, content, null));
                    }
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
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(PostListActivity.this, NotificationListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(PostListActivity.this, SettingActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        mAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(PostListActivity.this, PostActivity.class);
                intent.putExtra("userURL", userURL);
                intent.putExtra("username", username);
                intent.putExtra("postID", post.get(position).getPostID());
                intent.putExtra("postOwnerUsername", post.get(position).getText1());
                intent.putExtra("postOwnerImageuri", post.get(position).getImageResource());
                intent.putExtra("postContent", post.get(position).getText2());
                intent.putExtra("postURL", post.get(position).getImageResource2());
                startActivity(intent);
            }
        });

        search_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent s = new Intent(PostListActivity.this,SearchPostActivity.class);
                s.putExtra("username", username);
                s.putExtra("userURL", userURL);
                startActivity(s);
            }
        });

        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent a = new Intent(PostListActivity.this, AddPostActivity.class);
                a.putExtra("username", username);
                a.putExtra("userURL", userURL);
                startActivity(a);
            }
        });
    }
}