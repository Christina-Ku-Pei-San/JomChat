package wia2007.example.jomchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessengerAdapter extends RecyclerView.Adapter<MessengerAdapter.MessengerViewHolder> {
    private Context mContext;
    private ArrayList<MessengerItem> mMessengerList;
    private MessengerAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(MessengerAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class MessengerViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;

        public MessengerViewHolder(View itemView, final MessengerAdapter.OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.IVProfilePhoto);
            mTextView1 = itemView.findViewById(R.id.TVUsername);

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

    public MessengerAdapter(Context context, ArrayList<MessengerItem> messengerList) {
        mContext = context;
        mMessengerList = messengerList;
    }

    @Override
    public MessengerAdapter.MessengerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.messenger_item, parent, false);
        MessengerAdapter.MessengerViewHolder mvh = new MessengerAdapter.MessengerViewHolder(v, mListener);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MessengerAdapter.MessengerViewHolder holder, int position) {
        MessengerItem currentItem = mMessengerList.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
    }

    @Override
    public int getItemCount() {
        return mMessengerList.size();
    }

}
