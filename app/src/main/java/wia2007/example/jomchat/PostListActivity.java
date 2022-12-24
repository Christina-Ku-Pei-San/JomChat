package wia2007.example.jomchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostListActivity extends AppCompatActivity {
    FloatingActionButton search_button, add_button;
    private RecyclerView mRecyclerView;
    private PostAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ImageView ivMessenger, ivNotification;
    CircleImageView ivProfilePhoto;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        ArrayList<PostItem> post =new ArrayList<>();
        post.add(new PostItem(R.drawable.profile_pic,"Siti", "beautiful day", R.drawable.post_photo1));
        post.add(new PostItem(R.drawable.profile_pic2,"Angel", "Boring day", R.drawable.post_photo1));
        post.add(new PostItem(R.drawable.profile_photo1, "Adam", "I'm sad", 0));

        mRecyclerView = findViewById(R.id.RVPostItem);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager= new LinearLayoutManager(this);
        mAdapter = new PostAdapter(post);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        ivMessenger = findViewById(R.id.IVMessenger);
        ivMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(PostListActivity.this, MessengerListActivity.class);
                startActivity(startintent);
            }
        });

        ivNotification = findViewById(R.id.IVNotification);
        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(PostListActivity.this, NotificationListActivity.class);
                startActivity(startintent);
            }
        });

        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);
        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(PostListActivity.this, SettingActivity.class);
                startActivity(startintent);
            }
        });

        mAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(PostListActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });

        search_button = (FloatingActionButton)findViewById(R.id.FABSearch);
        search_button.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent s = new Intent(PostListActivity.this,SearchPostActivity.class);
                        startActivity(s);
                    }
                }
        );
        add_button = (FloatingActionButton)findViewById(R.id.FABAdd);
        add_button.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent a = new Intent(PostListActivity.this, AddPostActivity.class);
                        startActivity(a);
                    }
                }
        );
    }

}