package com.example.hiveapp_login.ui.message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hiveapp_login.MainActivity;
import com.example.hiveapp_login.R;
import com.example.hiveapp_login.model.ChanChat;
import com.example.hiveapp_login.model.Channel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civ_avatar;
        TextView tv_user_name;
        TextView tv_time_sent;
        TextView tv_forwarded_from;
        TextView tv_forwarded_from_user;
        TextView tv_message_body;

        public ViewHolder(View itemView) {
            super(itemView);

            civ_avatar = itemView.findViewById(R.id.civ_avatar);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_time_sent = itemView.findViewById(R.id.tv_time_sent);
            tv_forwarded_from = itemView.findViewById(R.id.tv_forwarded_from);
            tv_forwarded_from_user = itemView.findViewById(R.id.tv_forwarded_from_user);
            tv_message_body = itemView.findViewById(R.id.tv_message_body);

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

        public void setItem(ChanChat item) {
            String avata = item.getSenderavata();

//            if(avata!=null && avata!=""){
//                int resid = Integer.parseInt(avata);
//                civ_avatar.setImageResource(resid);
//            }
//            else{
//                civ_avatar.setImageResource(R.drawable.avata_00);
//            }
            civ_avatar.setImageResource(R.drawable.avata_00);
            tv_user_name.setText(item.getSendername());
            tv_time_sent.setText(item.getTime());
//            tv_forwarded_from =
//            tv_forwarded_from_user =
            tv_message_body.setText(item.getContent());

        }

    }

    ArrayList<ChanChat> items;

    public MessageAdapter() {
        items = new ArrayList<ChanChat>();
    }

    public MessageAdapter(ArrayList<ChanChat> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.list_item_message, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ChanChat item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void addItem(ChanChat item) {
        items.add(item);
    }

    void setItems(ArrayList<ChanChat> chanchats){
        items = chanchats;
    }


    //setItems(), getItem(), setItem() 메소드도 추가


}


