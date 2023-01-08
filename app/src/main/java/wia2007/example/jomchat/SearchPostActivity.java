package wia2007.example.jomchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchPostActivity extends AppCompatActivity {
    private ArrayList<PostItem> mPostList;
    private RecyclerView mRecyclerView;
    private PostAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ImageView ivBack, ivMessenger, ivNotification;
    private CircleImageView ivProfilePhoto;
    private EditText etSearch;

    private String username, userURL;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post);

        ivBack = findViewById(R.id.IVBack);
        ivMessenger = findViewById(R.id.IVMessenger);
        ivNotification = findViewById(R.id.IVNotification);
        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);
        etSearch = findViewById(R.id.ETSearch);

        username = getIntent().getStringExtra("username");
        userURL = getIntent().getStringExtra("userURL");

        if (userURL.equals("")) {
            ivProfilePhoto.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        else {
            Picasso.get().load(userURL).into(ivProfilePhoto);
        }

        mPostList = new ArrayList<>();
//        databaseReference.child("Post").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot data: snapshot.getChildren()) {
//                    String postID = data.getKey();
//                    String postUsername = data.child("userName").getValue().toString();
//                    String content = data.child("imageContent").getValue().toString();
//                    String imageurl;
//                    if (data.hasChild("imageURL")) {
//                        imageurl = data.child("imageURL").getValue().toString();
//                    }
//                    else {
//                        imageurl = "";
//                    }
//
//                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot data: snapshot.getChildren()) {
//                                if (data.getKey().equals(postUsername)) {
//                                    String postUserURL = data.child("userURL").getValue().toString();
//                                    mPostList.add(new PostItem(postID, postUserURL, postUsername, content, imageurl));
//                                }
//                            }
//                            mAdapter.notifyDataSetChanged();
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        mRecyclerView = findViewById(R.id.RVPostItem);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager= new LinearLayoutManager(this);
        mAdapter = new PostAdapter(mPostList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SearchPostActivity.this, PostActivity.class);
                intent.putExtra("userURL", userURL);
                intent.putExtra("username", username);
                intent.putExtra("postID", mPostList.get(position).getPostID());
                intent.putExtra("postOwnerUsername", mPostList.get(position).getText1());
                intent.putExtra("postOwnerImageuri", mPostList.get(position).getImageResource());
                intent.putExtra("postContent", mPostList.get(position).getText2());
                intent.putExtra("postURL", mPostList.get(position).getImageResource2());
                startActivity(intent);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String search = etSearch.getText().toString();
                mPostList.clear();
                if (search.isEmpty()) {
                    mAdapter.notifyDataSetChanged();
                }
                else {
                    databaseReference.child("Post").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot data: snapshot.getChildren()) {
                                String content = data.child("imageContent").getValue().toString();
                                String postUsername = data.child("userName").getValue().toString();
                                if (content.contains(search) || postUsername.contains(search)) {
                                    String postID = data.getKey();
                                    String imageurl;
                                    if (data.hasChild("imageURL")) {
                                        imageurl = data.child("imageURL").getValue().toString();
                                    }
                                    else {
                                        imageurl = "";
                                    }

                                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot data: snapshot.getChildren()) {
                                                if (data.getKey().equals(postUsername)) {
                                                    String postUserURL = data.child("userURL").getValue().toString();
                                                    mPostList.add(new PostItem(postID, postUserURL, postUsername, content, imageurl));
                                                }
                                            }
                                            mAdapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(SearchPostActivity.this, PostListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(SearchPostActivity.this, MessengerListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(SearchPostActivity.this, NotificationListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(SearchPostActivity.this, SettingActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

    }
}