package wia2007.example.jomchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessengerActivity extends AppCompatActivity {
    private ArrayList<MessageItem> mMessageList;
    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button btnViewProfile;
    private CircleImageView ivProfilePhoto;
    private EditText mgetmessage;
    private ImageButton msendmessagebutton;
    private ImageView ivBack, ivHome, ivNotification, mimageviewofspecificuser;
    private TextView mnameofspecificuser;

    private String enteredmessage, mreceivername, mreceiverusername, musername, senderroom, receiverroom, currenttime;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        ivBack = findViewById(R.id.IVBack);
        ivHome = findViewById(R.id.IVHome);
        ivNotification = findViewById(R.id.IVNotification);
        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);
        btnViewProfile = findViewById(R.id.BtnViewProfile);
        mgetmessage = findViewById(R.id.getmessage);
        msendmessagebutton = findViewById(R.id.imageviewsendmessage);
        mimageviewofspecificuser = findViewById(R.id.specificuserimageinimageview);
        mnameofspecificuser = findViewById(R.id.Nameofspecificuser);

        mMessageList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.RVMessageItem);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MessageAdapter(MessengerActivity.this, mMessageList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");

        musername = getIntent().getStringExtra("username");
        mreceiverusername = getIntent().getStringExtra("receiverusername");

        senderroom = musername + mreceiverusername;
        receiverroom = mreceiverusername + musername;

        mnameofspecificuser.setText(mreceiverusername);
//        String uri = getIntent().getStringExtra("imageuri");
//        if(uri.isEmpty()) {
//            Toast.makeText(getApplicationContext(),"null is recieved",Toast.LENGTH_SHORT).show();
//        }
//        else {
//            Picasso.get().load(uri).into(mimageviewofspecificuser);
//        }

        mAdapter = new MessageAdapter(MessengerActivity.this, mMessageList);
        databaseReference.child("chats").child(senderroom).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mMessageList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    MessageItem message = snapshot1.getValue(MessageItem.class);
                    mMessageList.add(new MessageItem(message.getMessage(), message.getTimestamp(), message.getCurrenttime(), message.getSenderORreceiver()));
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        msendmessagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredmessage = mgetmessage.getText().toString();
                if (enteredmessage.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter message first", Toast.LENGTH_SHORT).show();
                }
                else {
                    Date date = new Date();
                    currenttime = simpleDateFormat.format(calendar.getTime());
//                    MessageItem message = new MessageItem(enteredmessage, musername, date.getTime(), currenttime);
                    MessageItem messagesender = new MessageItem(enteredmessage, date.getTime(), currenttime, "sender");
                    MessageItem messagereceiver = new MessageItem(enteredmessage, date.getTime(), currenttime, "receiver");
                    databaseReference.child("chats")
                            .child(senderroom)
                            .child("messages")
                            .push().setValue(messagesender).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    databaseReference.child("chats")
                                            .child(receiverroom)
                                            .child("messages")
                                            .push()
                                            .setValue(messagereceiver).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                }
                                            });
                                }
                            });
                    mAdapter.notifyDataSetChanged();
                    mgetmessage.setText(null);
                }
            }
        });

        databaseReference.child("users").child(mreceiverusername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mreceivername = snapshot.getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerActivity.this, ProfileActivity.class);
                startintent.putExtra("username", musername);
                startintent.putExtra("receiverusername", mreceivername);
                startActivity(startintent);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerActivity.this, MessengerListActivity.class);
                startintent.putExtra("username", musername);
                startActivity(startintent);
            }
        });

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerActivity.this, PostListActivity.class);
                startintent.putExtra("username", musername);
                startActivity(startintent);
            }
        });

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerActivity.this, NotificationListActivity.class);
                startintent.putExtra("username", musername);
                startActivity(startintent);
            }
        });

        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerActivity.this, SettingActivity.class);
                startintent.putExtra("username", musername);
                startActivity(startintent);
            }
        });
    }
}