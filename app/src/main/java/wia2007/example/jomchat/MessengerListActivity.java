package wia2007.example.jomchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessengerListActivity extends AppCompatActivity {
    private ArrayList<MessengerItem> mMessengerList;
    private RecyclerView mRecyclerView;
    private MessengerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ImageView ivHome, ivNotification;
    private CircleImageView ivProfilePhoto;
    private EditText etSearch;

    private String musername;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_list);

        ivHome = findViewById(R.id.IVHome);
        ivNotification = findViewById(R.id.IVNotification);
        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);
        etSearch = findViewById(R.id.ETSearch);

        mMessengerList = new ArrayList<>();
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()) {
                    if (!data.getKey().equals(musername)) {
                        mMessengerList.add(new MessengerItem(R.drawable.profile_photo1, data.getKey()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mRecyclerView = findViewById(R.id.RVMessengerItem);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MessengerAdapter(this, mMessengerList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MessengerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent startintent = new Intent(MessengerListActivity.this, MessengerActivity.class);
                startintent.putExtra("username", musername);
                startintent.putExtra("receiverusername", mMessengerList.get(position).getText1());
                startintent.putExtra("imageuri", mMessengerList.get(position).getImageResource());
                startActivity(startintent);
            }
        });

        musername = getIntent().getStringExtra("username");

        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = etSearch.getText().toString();
                Toast.makeText(getApplicationContext(), search,Toast.LENGTH_SHORT).show();

                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mMessengerList.clear();
                        for (DataSnapshot data: snapshot.getChildren()) {
                            if (!data.getKey().equals(musername)) {
                                if (data.getKey().startsWith(search)) {
                                    mMessengerList.add(new MessengerItem(R.drawable.profile_photo1, data.getKey()));
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerListActivity.this, PostListActivity.class);
                startintent.putExtra("username", musername);
                startActivity(startintent);
            }
        });

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerListActivity.this, NotificationListActivity.class);
                startintent.putExtra("username", musername);
                startActivity(startintent);
            }
        });

        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerListActivity.this, SettingActivity.class);
                startintent.putExtra("username", musername);
                startActivity(startintent);
            }
        });
    }
}