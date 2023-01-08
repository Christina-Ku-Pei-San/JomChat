package wia2007.example.jomchat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

/*
public class OwnPostAdapter extends RecyclerView.Adapter<OwnPostAdapter.OwnPostViewHolder> {
    private ArrayList<OwnPostItem> mOwnPostList;
    private OwnPostAdapter.OnItemClickListener mListener;
    Context context;

    public OwnPostAdapter() {
    }

    public OwnPostAdapter(ArrayList<OwnPostItem> mOwnPostList, Context context) {
        this.mOwnPostList = mOwnPostList;
        this.context = context;
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
        context=parent.getContext();
        OwnPostViewHolder opvh = new OwnPostViewHolder(v, mListener);
        return opvh;
    }

    @Override
    public void onBindViewHolder(@NonNull OwnPostViewHolder holder, int position) {
        OwnPostItem currentItem = mOwnPostList.get(position);
        if (currentItem.getImageResource().equals("")) {
            holder.mImageView.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        else {
            Picasso.get().load(currentItem.getImageResource()).into(holder.mImageView);
        }

        String p_username = mOwnPostList.get(position).getText1();
        String p_content = mOwnPostList.get(position).getText2();
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());

        if (currentItem.getImageResource2().equals("")) {
            holder.mImageView2.setVisibility(View.GONE);
        }
        else {
            Picasso.get().load(currentItem.getImageResource2()).into(holder.mImageView2);
        }

        holder.share_button.setOnClickListener((v)->{
            BitmapDrawable bitmapDrawable = (BitmapDrawable) holder.mImageView2.getDrawable();
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
        });


    }

    private void shareImageOnly(String p_username, Bitmap bitmap){
        //concentrate username and description to share
        String shareBody = "Username: "+p_username ;
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
    private void shareTextOnly(String p_username, String p_content){
        //concentrate username and description to share
        //String shareBody =p_username +"\n"+ p_content;
        String shareBody = "Username: "+p_username +"\n"+"Content: "+ p_content;

        //share intent
        Intent sIntent = new Intent(Intent.ACTION_SEND);
        sIntent.setType("text/plain");
        sIntent.putExtra(Intent.EXTRA_SUBJECT,"Subject Here");// in case shared via email
        sIntent.putExtra(Intent.EXTRA_TEXT,shareBody); //text to share
        context.startActivity(Intent.createChooser(sIntent, "Share Via"));

    }
    private void shareImageAndText(String p_username, String p_content, Bitmap bitmap){
        //String shareBody =p_username +"\n"+ p_content;
        String shareBody = "Username: "+p_username +"\n"+"Content: "+ p_content;
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
    private Uri saveImageToShare(Bitmap bitmap){
        File imageFolder = new File(context.getCacheDir(),"images" );
        Uri uri = null;
        try{
            imageFolder.mkdirs(); //create if not exists
            File file = new File(imageFolder,"shared_image.png" );
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            stream.flush();
            stream.close();
            uri = FileProvider.getUriForFile(context,"wia2007.example.jomchat",file);

        }
        catch(Exception e){
            Toast.makeText(context,""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return uri;
    }


    @Override
    public int getItemCount() {
        return mOwnPostList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mImageView2;

        public Button share_button;


        public PostViewHolder(View itemView, final PostAdapter.OnItemClickListener listener){
            super(itemView);
            mImageView = itemView.findViewById(R.id.IVProfile);
            mTextView1 = itemView.findViewById(R.id.TVUsername);
            mTextView2 = itemView.findViewById(R.id.TVPostContent);
            mImageView2 = itemView.findViewById(R.id.IVPostPhoto);

            share_button =itemView.findViewById(R.id.BtnShare);

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

    public OwnPostAdapter(ArrayList<OwnPostItem> ownpostList){
        mOwnPostList = ownpostList;
    }
*/

