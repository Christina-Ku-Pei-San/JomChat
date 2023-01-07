package wia2007.example.jomchat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private ArrayList<PostItem> mPostList;
    private PostAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(PostAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
        PostViewHolder pvh = new PostViewHolder(v, mListener);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        PostItem currentItem = mPostList.get(position);
//        System.out.println("ImageResource "+currentItem.getImageResource());
        if (currentItem.getImageResource().equals("")) {
            holder.mImageView.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        else {
            Picasso.get().load(currentItem.getImageResource()).into(holder.mImageView);
        }
//        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
        if (currentItem.getImageResource2() == null) {
            holder.mImageView2.setVisibility(View.GONE);
        }
        else {
            Picasso.get().load(currentItem.getImageResource2()).into(holder.mImageView2);
        }
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mImageView2;


        public PostViewHolder(View itemView, final PostAdapter.OnItemClickListener listener){
            super(itemView);
            mImageView = itemView.findViewById(R.id.IVProfile);
            mTextView1 = itemView.findViewById(R.id.TVUsername);
            mTextView2 = itemView.findViewById(R.id.TVPostContent);
            mImageView2 = itemView.findViewById(R.id.IVPostPhoto);

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

    public PostAdapter(ArrayList<PostItem> postList){
        mPostList = postList;
    }

}
