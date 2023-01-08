package wia2007.example.jomchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class PostActivity extends AppCompatActivity {
    private ArrayList<CommentItem> mCommentList;
    private RecyclerView mRecyclerView;
    private CommentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    Button comment_button, share_button;
    static String p_username;
    static String p_content;

    ImageView ivBack, ivMessenger, ivNotification;
    CircleImageView ivProfilePhoto;


    CircleImageView ivProfile;
    static TextView tvPostOwnerUsername;
    static TextView tvPostContent;
    static ImageView ivPostPhoto;
    String username, userURL, postOwnerUsername, postOwnerImageuri, postID, postContent, postURL;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        userURL = getIntent().getStringExtra("userURL");
        username = getIntent().getStringExtra("username");
        postID = getIntent().getStringExtra("postID");
        postOwnerUsername = getIntent().getStringExtra("postOwnerUsername");
        postOwnerImageuri = getIntent().getStringExtra("postOwnerImageuri");
        postContent = getIntent().getStringExtra("postContent");
        postURL = getIntent().getStringExtra("postURL");

        ivProfile = findViewById(R.id.IVProfile);
        tvPostOwnerUsername = findViewById(R.id.TVUsername);
        tvPostContent = findViewById(R.id.TVPostContent);
        ivPostPhoto = findViewById(R.id.IVPostPhoto);
        ivBack = findViewById(R.id.IVBack);
        ivMessenger = findViewById(R.id.IVMessenger);
        ivNotification = findViewById(R.id.IVNotification);
        ivProfilePhoto = findViewById(R.id.IVProfilePhoto);

        if (userURL.equals("")) {
            ivProfilePhoto.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        else {
            Picasso.get().load(userURL).into(ivProfilePhoto);
        }

        if (postOwnerImageuri.equals("")) {
            ivProfile.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        else {
            Picasso.get().load(postOwnerImageuri).into(ivProfile);
        }

        tvPostOwnerUsername.setText(postOwnerUsername);
        tvPostContent.setText(postContent);

        if (postURL.equals("")) {
            ivPostPhoto.setVisibility(View.GONE);
        }
        else {
            Picasso.get().load(postURL).into(ivPostPhoto);
        }

        mCommentList = new ArrayList<>();
        databaseReference.child("Post").child(postID).child("comments").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()) {
                    String commenterURl = data.child("commenterURL").getValue().toString();
                    String commenterUsername = data.child("commenterUsername").getValue().toString();
                    String comment = data.child("comment").getValue().toString();
                    mCommentList.add(new CommentItem(commenterURl, commenterUsername, comment));
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRecyclerView = findViewById(R.id.RVCommentItem);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CommentAdapter(mCommentList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(PostActivity.this, PostListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivMessenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(PostActivity.this, MessengerListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(PostActivity.this, NotificationListActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startintent = new Intent(PostActivity.this, SettingActivity.class);
                startintent.putExtra("username", username);
                startintent.putExtra("userURL", userURL);
                startActivity(startintent);
            }
        });

        comment_button = findViewById(R.id.BtnComment);
        comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(PostActivity.this);

                alert.setTitle("Add Comment");
                alert.setMessage("Type your comment");

                // Set an EditText view to get user input
                final EditText input = new EditText(PostActivity.this);
                alert.setView(input);

                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (input.getText().toString().equals("")) {
                            Toast.makeText(PostActivity.this, "Please enter your comment", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            // Add the comment
                            addComment(postID, username, input.getText().toString());
                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        dialog.dismiss();
                    }
                });

                alert.show();
            }
        });

        share_button = findViewById(R.id.BtnShare);
        share_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                 p_username =  tvPostOwnerUsername.getText().toString().trim();
                 p_content =  tvPostContent.getText().toString().trim();

                BitmapDrawable bitmapDrawable = (BitmapDrawable) ivPostPhoto.getDrawable();
                if(bitmapDrawable ==null){
                    //post without image
                    shareTextOnly(p_username, p_content);

                }
                else if(bitmapDrawable !=null & p_username.isEmpty() == true){
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    shareImageOnly(p_username, bitmap);
                }
                else{
                    //post with image
                    //convert image to bitmap
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    shareImageAndText(p_username,p_content,bitmap);


                }

            }
        });
    }

    private void addComment(String postID, String commenter, String message) {
        databaseReference.child("Post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    if (dataSnapshot1.getKey().equals(postID)) {
                        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot data: snapshot.getChildren()) {
                                    if (data.getKey().equals(commenter)) {
                                        String commenterURL = data.child("userURL").getValue().toString();
                                        CommentItem comment = new CommentItem(commenterURL, commenter, message);
                                        databaseReference.child("Post").child(postID).child("comments").push().setValue(comment);
                                        mCommentList.add(comment);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }
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
    }

    private void shareTextOnly(String p_username, String p_content){
        //concentrate username and description to share
        String shareBody = "Username: "+p_username +"\n"+"Content: "+ p_content;
        //String shareBody =p_username +"\n"+ p_content;

        //share intent
        Intent sIntent = new Intent(Intent.ACTION_SEND);
        sIntent.setType("text/plain");
        sIntent.putExtra(Intent.EXTRA_SUBJECT,"Subject Here");// in case shared via email
        sIntent.putExtra(Intent.EXTRA_TEXT,shareBody); //text to share
        startActivity(Intent.createChooser(sIntent, "Share Via"));

    }
    private void shareImageOnly(String p_username, Bitmap bitmap){
        //concentrate username and description to share
        String shareBody = "Username: "+p_username ;
        //String shareBody =p_username +"\n"+ p_content;

        Uri uri = saveImageToShare(bitmap);

        //share intent
        Intent sIntent = new Intent(Intent.ACTION_SEND);
        sIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        sIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
        sIntent.setType("image/png");
        startActivity(Intent.createChooser(sIntent, "Share Via"));


    }

    private void shareImageAndText(String p_username, String p_content, Bitmap bitmap){
        String shareBody = "Username: "+p_username +"\n"+"Content: "+ p_content;
        //String shareBody =p_username +"\n"+ p_content;
        //first we will save this image in cache, get the saved image uri
        Uri uri = saveImageToShare(bitmap);

        //share intent
        Intent sIntent = new Intent(Intent.ACTION_SEND);
        sIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        sIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
        sIntent.setType("image/png");
        startActivity(Intent.createChooser(sIntent, "Share Via"));

    }
    private Uri saveImageToShare(Bitmap bitmap){
        File imageFolder = new File(getCacheDir(),"images" );
        Uri uri = null;
        try{
            imageFolder.mkdirs(); //create if not exists
            File file = new File(imageFolder,"shared_image.png" );
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(this,"wia2007.example.jomchat",file);

        }
        catch(Exception e){
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return uri;
    }


//    public void createCommentList() {
//        mCommentList = new ArrayList<>();
//        mCommentList.add(new CommentItem(R.drawable.ic_baseline_account_circle_24, "Adam", "So beautiful!"));
//        mCommentList.add(new CommentItem(R.drawable.ic_baseline_account_circle_24, "Line 3", "Line 4"));
//        mCommentList.add(new CommentItem(R.drawable.ic_baseline_account_circle_24, "Line 5", "Line 6"));
//        mCommentList.add(new CommentItem(R.drawable.ic_baseline_account_circle_24, "Line 7", "Line 8"));
//        mCommentList.add(new CommentItem(R.drawable.ic_baseline_account_circle_24, "Line 9", "Line 10"));
//        mCommentList.add(new CommentItem(R.drawable.ic_baseline_account_circle_24, "Line 11", "Line 12"));
//        mCommentList.add(new CommentItem(R.drawable.ic_baseline_account_circle_24, "Line 13", "Line 14"));
//        mCommentList.add(new CommentItem(R.drawable.ic_baseline_account_circle_24, "Line 15", "Line 16"));
//        mCommentList.add(new CommentItem(R.drawable.ic_baseline_account_circle_24, "Line 17", "Line 18"));
//        mCommentList.add(new CommentItem(R.drawable.ic_baseline_account_circle_24, "Line 19", "Line 20"));
//        mCommentList.add(new CommentItem(R.drawable.profile_photo1, "Line 21", "Line 22"));
//        mCommentList.add(new CommentItem(R.drawable.profile_photo1, "Line 23", "Line 24"));
//        mCommentList.add(new CommentItem(R.drawable.profile_photo1, "Line 25", "Line 26"));
//        mCommentList.add(new CommentItem(R.drawable.profile_photo1, "Line 27", "Line 28"));
//        mCommentList.add(new CommentItem(R.drawable.profile_photo1, "Line 29", "Line 30"));
//    }

//    public void buildRecyclerView() {
//        mRecyclerView = findViewById(R.id.RVCommentItem);
//        mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this);
//        mAdapter = new CommentAdapter(mCommentList);
//
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setAdapter(mAdapter);
//    }
}