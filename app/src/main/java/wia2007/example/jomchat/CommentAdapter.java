package wia2007.example.jomchat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private ArrayList<CommentItem> mCommentList;

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public CommentViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.IVProfilePhoto);
            mTextView1 = itemView.findViewById(R.id.TVUsername);
            mTextView2 = itemView.findViewById(R.id.TVComment);
        }
    }

    public CommentAdapter(ArrayList<CommentItem> commentList) {
        mCommentList = commentList;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        CommentViewHolder cvh = new CommentViewHolder(v);
        return cvh;
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        CommentItem currentItem = mCommentList.get(position);
        if (currentItem.getCommenterURL().equals("")) {
            holder.mImageView.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }
        else {
            Picasso.get().load(currentItem.getCommenterURL()).into(holder.mImageView);
        }
        holder.mTextView1.setText(currentItem.getCommenterUsername());
        holder.mTextView2.setText(currentItem.getComment());
    }

    @Override
    public int getItemCount() {
        return mCommentList.size();
    }

}
