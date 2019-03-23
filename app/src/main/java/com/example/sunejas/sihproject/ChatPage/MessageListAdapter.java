package com.example.sunejas.sihproject.ChatPage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sunejas.sihproject.R;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ONE =1 ;
    private Context context;
    private List<ChatMessage> messagesItems;
    private static final int TYPE_TWO =2 ;

    public MessageListAdapter(Context context, List<ChatMessage> navDrawerItems) {
        this.context = context;
        this.messagesItems = navDrawerItems;
    }
    @Override
    public int getItemCount() {
        return messagesItems == null ? 0 : messagesItems.size();
    }

    // determine which layout to use for the row
    @Override
    public int getItemViewType(int position) {
        ChatMessage item = messagesItems.get(position);
        if (item.getType().equals( ChatMessage.ItemType.ONE_ITEM)) {
            return TYPE_ONE;
        } else if (item.getType().equals(ChatMessage.ItemType.TWO_ITEM)) {
            return TYPE_TWO;
        } else {
            return -1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ONE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_message_left, parent, false);
            return new ViewHolderOne(view);
        } else if (viewType == TYPE_TWO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_message_right, parent, false);
            return new ViewHolderTwo(view);
        } else {
            throw new RuntimeException("The type has to be ONE or TWO");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int listPosition) {
        Log.d("CheckTag",listPosition+"");
        switch (holder.getItemViewType()) {
            case TYPE_ONE:
                initLayoutOne((ViewHolderOne)holder, listPosition);
                break;
            case TYPE_TWO:
                initLayoutTwo((ViewHolderTwo) holder, listPosition);
                break;
            default:
                break;
        }
    }


    static class ViewHolderOne extends RecyclerView.ViewHolder {
        public TextView item , message;
        public ViewHolderOne(View itemView) {
            super(itemView);
            item = (TextView) itemView.findViewById(R.id.username);
            message = itemView.findViewById( R.id.message );
        }
    }

    static class ViewHolderTwo extends RecyclerView.ViewHolder {
        public TextView item , message;
        public ViewHolderTwo(View itemView) {
            super(itemView);
            item = (TextView) itemView.findViewById(R.id.lblMsgFrom);
            message = itemView.findViewById( R.id.txtMsg );

        }
    }

    private void initLayoutOne(ViewHolderOne holder, int pos) {
        holder.item.setText(messagesItems.get(pos).getSender()+"");
        holder.message.setText(messagesItems.get(pos).getBody()+"");
    }

    private void initLayoutTwo(ViewHolderTwo holder, int pos) {
        holder.item.setText(messagesItems.get(pos).getSender()+"");
        holder.message.setText(messagesItems.get(pos).getBody()+"");
    }



}
