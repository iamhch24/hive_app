package com.example.hiveapp_login.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hiveapp_login.R;
import com.example.hiveapp_login.model.Channel;
import com.example.hiveapp_login.ui.chat.ChatRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private ChatAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_chat );


        Intent intent = getIntent();

        String ChanId = intent.getStringExtra("ChanId");
        String ChanName = intent.getStringExtra("ChanName");

        getSupportActionBar().setTitle("Enjoy Hive :: "+ChanName);


//        recyclerView = findViewById(R.id.recyclerView);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//
//        recyclerView.setLayoutManager(layoutManager);

//        getData();
    }

    protected void getData(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject( response );
                    String success = jsonResponse.getString( "success" );

                    if("success".equals(success)) {//성공시
                        //Toast.makeText( getApplicationContext(), "data 받기 성공", Toast.LENGTH_SHORT ).show();
                        JSONArray jsonArray = jsonResponse.getJSONArray("data");
                        ArrayList<Channel> channels = new ArrayList<Channel>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            channels.add(new Channel(obj.getString("chanid"), obj.getString("name")));
                        }
                        ///
                        adapter = new ChatAdapter(channels);
                        recyclerView.setAdapter(adapter);

                    } else {//로그인 실패시
                        Toast.makeText( getApplicationContext(), "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT ).show();
                        return;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText( getApplicationContext(), "에러발생", Toast.LENGTH_SHORT ).show();
                }
            }
        };
        ChatRequest chatrequest = new ChatRequest( responseListener );
        RequestQueue queue = Volley.newRequestQueue( getApplicationContext() );
        queue.add( chatrequest );
    }
}