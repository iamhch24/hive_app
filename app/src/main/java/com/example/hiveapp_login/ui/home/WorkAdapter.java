package com.example.hiveapp_login.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hiveapp_login.R;
import com.example.hiveapp_login.model.WorkSpace;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder> {
    private final String TAG = "WorkAdapter";
    private OnWorkClick mCallback;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_workspace_name;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_workspace_name = itemView.findViewById(R.id.tv_workspace_name);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION)
                    {
                        Context context = v.getContext();

                        WorkSpace item = items.get(pos) ;

                        Toast.makeText(context, "go into workspace :" + item.getName(), Toast.LENGTH_LONG).show();
                        Snackbar.make(v, "go into workspace :" + item.getName(), Snackbar.LENGTH_LONG);
                        Log.d(TAG, "WorkId="+item.getWorkid());
                        Log.d(TAG, "WorkName="+item.getName());
                        Log.d(TAG, "WorkTitle="+item.getTitle());
                        mCallback.onWorkClick(item.getWorkid(),item.getName(),item.getTitle());
//                        Fragment ft = getSupportFragmentManager().findFragmentByTag(“HomeFragment”);
//                        Intent intent = new Intent( context, ChatActivity.class );
//
//                        intent.putExtra( "WorkId", item.getWorkid() );
//                        intent.putExtra( "WorkName", item.getName() );
//
//                        context.startActivity( intent );

                    }
                }
            });

        }

        public void setItem(WorkSpace item) {
            tv_workspace_name.setText("@ " + item.getName());
        }

    }

    ArrayList<WorkSpace> items;

    public WorkAdapter() {
        items = new ArrayList<WorkSpace>();
    }

    public WorkAdapter(OnWorkClick listener) {
        this.mCallback = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.list_item_workspace, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        WorkSpace item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public WorkSpace getItem(int position){
        return items.get(position);
    }

    public void addItem(WorkSpace item) {
        items.add(item);
    }

    public void setItems(ArrayList<WorkSpace> workspaces){
        items = workspaces;
    }


    //setItems(), getItem(), setItem() 메소드도 추가


}


