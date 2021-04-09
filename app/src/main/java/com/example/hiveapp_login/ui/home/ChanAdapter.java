package com.example.hiveapp_login.ui.home;

import com.example.hiveapp_login.MainActivity;
import com.example.hiveapp_login.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hiveapp_login.model.Channel;
import com.example.hiveapp_login.ui.chat.ChatActivity;
import com.example.hiveapp_login.ui.login.LoginActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ChanAdapter extends RecyclerView.Adapter<ChanAdapter.ViewHolder> {
    private final String TAG = "ChanAdapter";
    private OnChanClick mCallback;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_channel_name;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_channel_name = itemView.findViewById(R.id.text_channel_name);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION)
                    {
                        Context context = v.getContext();
                        Channel item = items.get(pos) ;
                        mCallback.onChanClick(item.getChanid(),item.getName());
                    }
                }
            });

        }

        public void setItem(Channel item) {
            tv_channel_name.setText("# " + item.getName());
        }

    }

    ArrayList<Channel> items;

    public ChanAdapter() {
        items = new ArrayList<Channel>();
    }

    public ChanAdapter(OnChanClick listener) {
        this.mCallback = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.list_item_channel, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Channel item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Channel getItem(int position){
        return items.get(position);
    }

    public void addItem(Channel item) {
        items.add(item);
    }

    public void setItems(ArrayList<Channel> channels){
        items = channels;
    }


    //setItems(), getItem(), setItem() 메소드도 추가


}


