package wia2007.example.jomchat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class OwnPostAdapter extends RecyclerView.Adapter<OwnPostAdapter.OwnPostViewHolder> {
    private ArrayList<OwnPostItem> mOwnPostList;
    private OwnPostAdapter.OnItemClickListener mListener;
    Context context;

    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/");

    public OwnPostAdapter() {
    }

    public OwnPostAdapter( Context context, ArrayList<OwnPostItem> mOwnPostList) {
        this.context = context;
        this.mOwnPostList = mOwnPostList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OwnPostAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public OwnPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.own_post_item, parent, false);
        context = parent.getContext();
        OwnPostViewHolder opvh = new OwnPostViewHolder(v, mListener);
        return opvh;
    }

    @Override
    public void onBindViewHolder(@NonNull OwnPostViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        OwnPostItem currentItem = mOwnPostList.get(position);
        String postID = currentItem.getPostID();
        if (currentItem.getImageResource().equals("")) {
            holder.mImageView.setImageResource(R.drawable.ic_baseline_account_circle_24);
        } else {
            Picasso.get().load(currentItem.getImageResource()).into(holder.mImageView);
        }

        String p_username = mOwnPostList.get(position).getText1();
        String p_content = mOwnPostList.get(position).getText2();
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());

        if (currentItem.getImageResource2().equals("")) {
            holder.mImageView2.setVisibility(View.GONE);
        } else {
            Picasso.get().load(currentItem.getImageResource2()).into(holder.mImageView2);
        }

        nrLikes(holder.like_num,mOwnPostList.get(position).getPostID());

        databaseReference.child("Likes").child(postID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SharedPreferences preferences = context.getSharedPreferences("user_prefs", context.MODE_PRIVATE);
                String username = preferences.getString("username", "");
                if (snapshot.hasChild(username)) {
                    holder.like_button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_liked, 0,0,0);
                    holder.like_button.setText("Liked");
                }
                else {
                    holder.like_button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_likes, 0,0,0);
                    holder.like_button.setText("Like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Post");
                builder.setMessage("Are You Sure To Delete This Post");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletePost(holder.getAdapterPosition());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        holder.like_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = context.getSharedPreferences("user_prefs", context.MODE_PRIVATE);
                String username = preferences.getString("username", "");
                String srch = holder.like_button.getText().toString();
                System.out.println(srch);

                if(holder.like_button.getText().toString().equals("Like")){
                    FirebaseDatabase.getInstance().getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/").child("Likes").child(postID).child(username).setValue(true);
                    //FirebaseDatabase.getInstance().getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/").child("Post").child(mPostList.get(position).getPostID()).child(firebaseUser.getUid()).setValue(true);
                    holder.like_button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_liked, 0,0,0);
                    holder.like_button.setText("Liked");

                }
                else {
                    //FirebaseDatabase.getInstance().getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/").child("Post").child(mPostList.get(position).getPostID()).child(firebaseUser.getUid()).removeValue();
                    FirebaseDatabase.getInstance().getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/").child("Likes").child(postID).child(username).removeValue();
                    holder.like_button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_likes, 0,0,0);
                    holder.like_button.setText("Like");
                }
            }
        });

        SharedPreferences preferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String commenter = preferences.getString("username", "");

        holder.comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                alert.setTitle("Add Comment");
                alert.setMessage("Type your comment");

                // Set an EditText view to get user input
                final EditText input = new EditText(context);
                alert.setView(input);

                alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (input.getText().toString().equals("")) {
                            Toast.makeText(context, "Please enter your comment", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            // Add the comment
                            addComment(postID, commenter, input.getText().toString());
                            notifyDataSetChanged();
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
        
        holder.share_button.setOnClickListener((v) -> {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) holder.mImageView2.getDrawable();
            if (bitmapDrawable == null) {
                //post without image
                shareTextOnly(p_username, p_content);

            } else if (bitmapDrawable != null & p_username.isEmpty() == true) {
                Bitmap bitmap = bitmapDrawable.getBitmap();
                shareImageOnly(p_username, bitmap);
            } else {
                //post with image
                //convert image to bitmap
                Bitmap bitmap = bitmapDrawable.getBitmap();
                shareImageAndText(p_username, p_content, bitmap);


            }
        });


    }

    private void deletePost(int adapterPosition) {
        String postID = mOwnPostList.get(adapterPosition).getPostID();

        databaseReference.child("Post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    if (dataSnapshot1.getKey().equals(postID)) {
                        dataSnapshot1.getRef().removeValue();
                        mOwnPostList.remove(adapterPosition);
                        Toast.makeText(context, "post deleted", Toast.LENGTH_SHORT).show();
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void nrLikes(TextView likes, String postid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://jomchat-9f535-default-rtdb.asia-southeast1.firebasedatabase.app/").child("Likes").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                likes.setText(snapshot.getChildrenCount()+" likes this.");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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

    private void shareImageOnly(String p_username, Bitmap bitmap) {
        //concentrate username and description to share
        String shareBody = "Username: " + p_username+"\n"+"#JomChatFSKTM";
        //String shareBody =p_username +"\n"+ p_content;
        //String shareBody = p_username;

        Uri uri = saveImageToShare(bitmap);

        //share intent
        Intent sIntent = new Intent(Intent.ACTION_SEND);
        sIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        sIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
        sIntent.setType("image/png");
        context.startActivity(Intent.createChooser(sIntent, "Share Via"));


    }

    private void shareTextOnly(String p_username, String p_content) {
        //concentrate username and description to share
        //String shareBody =p_username +"\n"+ p_content;
        String shareBody = "Username: " + p_username + "\n" + "Content: " + p_content +"\n"+"#JomChatFSKTM";

        //share intent
        Intent sIntent = new Intent(Intent.ACTION_SEND);
        sIntent.setType("text/plain");
        sIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");// in case shared via email
        sIntent.putExtra(Intent.EXTRA_TEXT, shareBody); //text to share
        context.startActivity(Intent.createChooser(sIntent, "Share Via"));

    }

    private void shareImageAndText(String p_username, String p_content, Bitmap bitmap) {
        //String shareBody =p_username +"\n"+ p_content;
        String shareBody = "Username: " + p_username + "\n" + "Content: " + p_content +"\n"+"#JomChatFSKTM";
        //first we will save this image in cache, get the saved image uri
        Uri uri = saveImageToShare(bitmap);

        //share intent
        Intent sIntent = new Intent(Intent.ACTION_SEND);
        sIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        sIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");
        sIntent.setType("image/png");
        context.startActivity(Intent.createChooser(sIntent, "Share Via"));

    }

    private Uri saveImageToShare(Bitmap bitmap) {
        File imageFolder = new File(context.getCacheDir(), "images");
        Uri uri = null;
        try {
            imageFolder.mkdirs(); //create if not exists
            File file = new File(imageFolder, "shared_image.png");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(context, "wia2007.example.jomchat", file);

        } catch (Exception e) {
            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return uri;
    }


    @Override
    public int getItemCount() {
        return mOwnPostList.size();
    }

    public static class OwnPostViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mImageView2;

        public Button like_button;
        public Button comment_button;
        public Button share_button;
        public Button delete_button;
        public TextView like_num;


        public OwnPostViewHolder(View itemView, final OwnPostAdapter.OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.IVProfile);
            mTextView1 = itemView.findViewById(R.id.TVUsername);
            mTextView2 = itemView.findViewById(R.id.TVPostContent);
            mImageView2 = itemView.findViewById(R.id.IVPostPhoto);

            like_button =itemView.findViewById(R.id.BtnLike);
            comment_button =itemView.findViewById(R.id.BtnComment);
            share_button = itemView.findViewById(R.id.BtnShare);
            delete_button = itemView.findViewById(R.id.BtnDelete);
            like_num = itemView.findViewById(R.id.TVLike);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public OwnPostAdapter(ArrayList<OwnPostItem> ownpostList) {
        mOwnPostList = ownpostList;
    }
}


