package wia2007.example.jomchat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
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
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessengerActivity extends AppCompatActivity {
    private ArrayList<MessageItem> mMessageList;
    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    private CircleImageView ivProfilePhoto;
    private EditText mgetmessage, etSearch;
    private ImageButton msendmessagebutton;
    private ImageView ivBack, ivHome, ivNotification, mimageviewofspecificuser, ivFavourite;
    private TextView mnameofspecificuser;

    private String enteredmessage, receivername, receiverusername, username, senderroom, receiverroom, currenttime, userURL, receiverURL;
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
        mimageviewofspecificuser = findViewById(R.id.specificuserimageinimageview);
        mnameofspecificuser = findViewById(R.id.Nameofspecificuser);
        ivFavourite = findViewById(R.id.favourite);
        etSearch = findViewById(R.id.ETSearch);
        mgetmessage = findViewById(R.id.getmessage);
        msendmessagebutton = findViewById(R.id.imageviewsendmessage);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");


        username = getIntent().getStringExtra("username");
        receiverusername = getIntent().getStringExtra("receiverusername");
        userURL = getIntent().getStringExtra("userURL");
        receiverURL = getIntent().getStringExtra("receiverURL");

        if (userURL.equals("")) {
            ivProfilePhoto.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        else {
            Picasso.get().load(userURL).into(ivProfilePhoto);
        }

        if (receiverURL.equals("")) {
            mimageviewofspecificuser.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        else {
            Picasso.get().load(receiverURL).into(mimageviewofspecificuser);
        }

        mnameofspecificuser.setText(receiverusername);

        databaseReference.child("Favourite").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    if (dataSnapshot1.getKey().equals(receiverusername)) {
                        ivFavourite.setImageResource(R.drawable.ic_baseline_star_24_fav);
                        break;
                    }
                    else {
                        ivFavourite.setImageResource(R.drawable.ic_baseline_star_24_notfav);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        senderroom = username + receiverusername;
        receiverroom = receiverusername + username;

        mMessageList = new ArrayList<>();
        databaseReference.child("chats").child(senderroom).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mMessageList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    MessageItem message = snapshot1.getValue(MessageItem.class);
                    mMessageList.add(new MessageItem(message.getMessage(), message.getTimestamp(), message.getCurrenttime(), message.getSenderORreceiver(), message.getUsername(), message.getReceiverusername()));
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRecyclerView = findViewById(R.id.RVMessageItem);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setStackFromEnd(true);
        mAdapter = new MessageAdapter(MessengerActivity.this, mMessageList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mimageviewofspecificuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerActivity.this, OthersProfileActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("receiverusername", receiverusername);
                startintent.putExtra("userURL", userURL);
                startintent.putExtra("receiverURL", receiverURL);
                startActivity(startintent);
            }
        });

        mnameofspecificuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerActivity.this, OthersProfileActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("receiverusername", receiverusername);
                startintent.putExtra("userURL", userURL);
                startintent.putExtra("receiverURL", receiverURL);
                startActivity(startintent);
            }
        });

        ivFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("Favourite").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            if (dataSnapshot1.getKey().equals(receiverusername)) {
                                dataSnapshot1.getRef().removeValue();
                                ivFavourite.setImageResource(R.drawable.ic_baseline_star_24_notfav);
                                Toast.makeText(getApplicationContext(), "Removed from Favourites", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        databaseReference.child("Favourite").child(username).child(receiverusername).setValue(receiverURL);
                        ivFavourite.setImageResource(R.drawable.ic_baseline_star_24_fav);
                        Toast.makeText(getApplicationContext(), "Added to Favourites", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String search = etSearch.getText().toString();
                databaseReference.child("chats").child(senderroom).child("messages").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mMessageList.clear();
                        for (DataSnapshot data: snapshot.getChildren()) {
                            if (data.child("message").getValue().toString().contains(search)) {
                                MessageItem message = data.getValue(MessageItem.class);
                                mMessageList.add(new MessageItem(message.getMessage(), message.getTimestamp(), message.getCurrenttime(), message.getSenderORreceiver(), message.getUsername(), message.getReceiverusername()));
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
                    MessageItem messagesender = new MessageItem(enteredmessage, date.getTime(), currenttime, "sender", username, receiverusername);
                    MessageItem messagereceiver = new MessageItem(enteredmessage, date.getTime(), currenttime, "receiver", username, receiverusername);
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
//                    mAdapter.notifyDataSetChanged();

                    
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    mgetmessage.setText(null);


                }

            }
        });

        databaseReference.child("users").child(receiverusername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                receivername = snapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerActivity.this, MessengerListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerActivity.this, PostListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerActivity.this, NotificationListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(MessengerActivity.this, SettingActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });
    }
}