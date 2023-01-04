package wia2007.example.jomchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<MessageItem> messagesArrayList;

    private int ITEM_SEND = 1;
    private int ITEM_RECEIVE = 2;

    public MessageAdapter(Context context, ArrayList<MessageItem> messagesArrayList) {
        this.context = context;
        this.messagesArrayList = messagesArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_SEND) {
            View view = LayoutInflater.from(context).inflate(R.layout.message_item_sender,parent,false);
            return new SenderViewHolder(view);
        }
        else {
            View view= LayoutInflater.from(context).inflate(R.layout.message_item_receiver,parent,false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageItem messages = messagesArrayList.get(position);
        if (holder.getClass() == SenderViewHolder.class) {
            SenderViewHolder viewHolder = (SenderViewHolder) holder;
            viewHolder.textViewmessaage.setText(messages.getMessage());
            viewHolder.timeofmessage.setText(messages.getCurrenttime());
        }
        else {
            ReceiverViewHolder viewHolder=(ReceiverViewHolder) holder;
            viewHolder.textViewmessaage.setText(messages.getMessage());
            viewHolder.timeofmessage.setText(messages.getCurrenttime());
        }
    }

    @Override
    public int getItemViewType(int position) {
        MessageItem messages = messagesArrayList.get(position);
        if (messages.getSenderORreceiver().equals("sender")) {
            return ITEM_SEND;
        }
        else {
            return ITEM_RECEIVE;
        }
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }

    class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView textViewmessaage;
        TextView timeofmessage;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewmessaage=itemView.findViewById(R.id.sendermessage);
            timeofmessage=itemView.findViewById(R.id.timeofmessage);
        }
    }

    class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView textViewmessaage;
        TextView timeofmessage;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewmessaage=itemView.findViewById(R.id.sendermessage);
            timeofmessage=itemView.findViewById(R.id.timeofmessage);
        }
    }
}
