package wia2007.example.jomchat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OwnPostAdapter extends RecyclerView.Adapter<OwnPostAdapter.OwnPostViewHolder> {
    private ArrayList<OwnPostItem> mOwnPostList;

    public static class OwnPostViewHolder extends RecyclerView.ViewHolder {


        public OwnPostViewHolder(View itemView) {
            super(itemView);
        }
    }

    public OwnPostAdapter(ArrayList<OwnPostItem> ownPostList) { mOwnPostList = ownPostList;}

    @Override
    public OwnPostAdapter.OwnPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.own_post_item, parent, false);
        OwnPostViewHolder opvh = new OwnPostViewHolder(v);
        return opvh;
    }

    @Override
    public void onBindViewHolder(OwnPostViewHolder holder, int position) {
        OwnPostItem currentItem = mOwnPostList.get(position);


    }

    @Override
    public int getItemCount() {
        return mOwnPostList.size();
    }
}
