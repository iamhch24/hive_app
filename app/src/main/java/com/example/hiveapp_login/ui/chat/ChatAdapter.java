package com.example.hiveapp_login.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hiveapp_login.R;
import com.example.hiveapp_login.model.Channel;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.text_channel_name);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION)
                    {
                        Toast.makeText(v.getContext(), "item clicked. pos=" + pos, Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

        public void setItem(Channel item) {
            textView.setText(item.getName());

        }



    }

    ArrayList<Channel> items;

    public ChatAdapter() {
        items = new ArrayList<Channel>();
    }

    public ChatAdapter(ArrayList<Channel> items) {
        this.items = items;
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

    void addItem(Channel item) {
        items.add(item);
    }

    void setItems(ArrayList<Channel> channels){
        items = channels;
    }


    //setItems(), getItem(), setItem() 메소드도 추가


}


