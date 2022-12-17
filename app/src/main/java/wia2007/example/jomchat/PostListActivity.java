package wia2007.example.jomchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PostListActivity extends AppCompatActivity {
    FloatingActionButton search_button, add_button;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        ArrayList<PostItem> post =new ArrayList<>();
        post.add(new PostItem(R.drawable.profile_pic,"Siti", "beautiful day", R.drawable.post_photo1));
        post.add(new PostItem(R.drawable.profile_pic2,"Angel", "Boring day", R.drawable.post_photo1));

        mRecyclerView = findViewById(R.id.rcv);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager= new LinearLayoutManager(this);
        mAdapter = new PostAdapter(post);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        search_button = (FloatingActionButton)findViewById(R.id.search);
        search_button.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent s = new Intent(PostListActivity.this,SearchPostActivity.class);
                        startActivity(s);
                    }
                }
        );
        add_button = (FloatingActionButton)findViewById(R.id.add);
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