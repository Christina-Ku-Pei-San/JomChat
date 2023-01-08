package wia2007.example.jomchat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class OwnPostListActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_own_post_list);

        ivMessenger = findViewById(R.id.IVMessenger);
        ivNotification = findViewById(R.id.IVNotification);
        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);
        search_button = (FloatingActionButton)findViewById(R.id.FABSearch);
        add_button = (FloatingActionButton)findViewById(R.id.FABAdd);

        username = getIntent().getStringExtra("username");

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

        ArrayList<PostItem> post = new ArrayList<>();
        databaseReference.child("Post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()) {
                    String postUsername = data.child("userName").getValue().toString();
                    if (postUsername.equals(username)) {
                        String postID = data.getKey();
                        String content = data.child("imageContent").getValue().toString();
                        String imageurl;
                        if (data.hasChild("imageURL")) {
                            imageurl = data.child("imageURL").getValue().toString();
                        } else {
                            imageurl = "";
                        }

                        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot data : snapshot.getChildren()) {
                                    if (data.getKey().equals(postUsername)) {
                                        String postUserURL = data.child("userURL").getValue().toString();
                                        post.add(new PostItem(postID, postUserURL, postUsername, content, imageurl));
                                    }
                                }
                                mAdapter.notifyDataSetChanged();
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

        mRecyclerView = findViewById(R.id.RVPostItem);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager= new LinearLayoutManager(this);
        mAdapter = new PostAdapter(post);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        ivMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(OwnPostListActivity.this, MessengerListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(OwnPostListActivity.this, NotificationListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(OwnPostListActivity.this, SettingActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        mAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(OwnPostListActivity.this, PostActivity.class);
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
                Intent s = new Intent(OwnPostListActivity.this,SearchPostActivity.class);
                s.putExtra("username", username);
                s.putExtra("userURL", userURL);
                startActivity(s);
            }
        });

        add_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent a = new Intent(OwnPostListActivity.this, AddPostActivity.class);
                a.putExtra("username", username);
                a.putExtra("userURL", userURL);
                startActivity(a);
            }
        });
    }

    }
