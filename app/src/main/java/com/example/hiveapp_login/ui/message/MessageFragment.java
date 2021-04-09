package com.example.hiveapp_login.ui.message;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hiveapp_login.MainActivity;
import com.example.hiveapp_login.R;
import com.example.hiveapp_login.model.ChanChat;
import com.example.hiveapp_login.model.Channel;
import com.example.hiveapp_login.model.User;
import com.example.hiveapp_login.ui.home.ChanAdapter;
import com.example.hiveapp_login.ui.home.ChanRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompHeader;
import static com.example.hiveapp_login.Constants.avatas;

public class MessageFragment extends Fragment {

    private StompClient stompClient;
    private final String TAG = "MessageFragment";
    private EditText et_chat_message;
    private ImageView iv_chat_send;
    private MessageAdapter adapter;
    private RecyclerView recyclerView;
    private String st_chanid;
    private String st_channame;
    private String st_userid;
    private String st_username;
    private TextView tvTitle;
    private ArrayList<User> members;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_chat, container, false);

        stompClient = ((MainActivity) getActivity()).getStompClient();
        st_chanid = ((MainActivity)getActivity()).getChanid();
        st_channame = ((MainActivity)getActivity()).getChanname();
        st_userid = ((MainActivity)getActivity()).getUserid();
        st_username = ((MainActivity)getActivity()).getUsername();
        members = ((MainActivity)getActivity()).getMembers();

        tvTitle = ((MainActivity) getActivity()).findViewById(R.id.tvTitle);
        tvTitle.setText("# "+st_channame);

        et_chat_message = root.findViewById(R.id.et_chat_message);
        iv_chat_send = root.findViewById(R.id.iv_chat_send);

        recyclerView = root.findViewById(R.id.rv_chat_messages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MessageAdapter();
        recyclerView.setAdapter(adapter);

//        et_chat_message.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                iv_chat_send.callOnClick();
//                v.clearFocus();
//                v.setFocusable(false);
//                v.setText("");
//                v.setFocusableInTouchMode(true);
//                v.setFocusable(true);
//                v.requestFocus();
//                return false;
//            }
//        });


        et_chat_message.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_UP) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    iv_chat_send.callOnClick();
                    return true;
                }
                return false;
            }
        });

        iv_chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = et_chat_message.getText().toString();
                et_chat_message.setText("");
                et_chat_message.requestFocus();
                sendStomp(msg);
            }
        });

        getApiData();
        subStomp();
        et_chat_message.requestFocus();
        return root;
    }

    public void sendStomp(String msg ) {
//        Editable msg = etInput.getText();
        JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
        try {
            sObject.put("type", "CHAT");
            sObject.put("sender", st_username);
            sObject.put("content", msg);
            sObject.put("chanid", st_chanid);
            sObject.put("userid", st_userid);
            sObject.put("roomseq", null);
            sObject.put("otherid", null);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String message = sObject.toString();

        stompClient.send("/app/channel.sendMessage/"+st_chanid, message).subscribe();
    }


    private void subStomp() {
        SimpleDateFormat CurDateFormat = new SimpleDateFormat ( "MM월 dd일");
        SimpleDateFormat CurTimeFormat = new SimpleDateFormat("HH시 mm분");
        Date today = new Date();
        String today_mmdd = CurDateFormat.format(today);
        String today_time = CurTimeFormat.format(today);

        stompClient.topic("/topic/channel/"+st_chanid).subscribe(topicMessage -> {
            String response = topicMessage.getPayload().toString();
            JSONObject obj = new JSONObject( response );
            getActivity().runOnUiThread(() -> {
                try {
//                    String SenderId = obj.getString("userid");
//                    String photo = "";
//                    for( User m : members ){
//                        if(m.getUserid().equals(SenderId)){
//                            photo = m.getPhoto();
//                        }
//                    }
//                    String avata = avatas[0];
//
//                    if(photo != null && photo !=""){
//                        for(int j=0; j<avatas.length;j++){
//                            if(photo.contains(avatas[j])){
//                                avata = avatas[j];
//                            }
//                        }
//                    }

                    ChanChat item = new ChanChat(obj.getString("sender"), obj.getString("userid"), "avata", obj.getString("content"), today_mmdd, today_time);
                    adapter.addItem(item);
                    recyclerView.setAdapter(adapter);
//                    recyclerView.scrollToPosition(adapter.getItemCount()-1);
//                    adapter.notifyDataSetChanged();
                    Log.d(TAG,"subscribe :: /topic/channel/"+st_chanid+"/"+obj.getString("content"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            Log.d(TAG,"subscribe :: /topic/channel/"+st_chanid);
        });
    }

    public void getApiData(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject( response );
                    String success = jsonResponse.getString( "success" );

                    if("success".equals(success)) {//성공시
//                        Toast.makeText( getActivity(), "data 받기 성공", Toast.LENGTH_SHORT ).show();
                        JSONArray jsonArray = jsonResponse.getJSONArray("data");
                        ArrayList<ChanChat> chanchats = new ArrayList<ChanChat>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

//                            String avata = avatas[0];
//                            String photo = obj.getString("senderavata");
//
//                            if(photo != null && photo !=""){
//                                for(int j=0; j<avatas.length;j++){
//                                    if(photo.contains(avatas[j])){
//                                        avata = avatas[j];
//                                    }
//                                }
//                            }

//                            int resId = getResources().getIdentifier(avata, "drawable","com.example.hiveapp_login");
//                            Log.d(TAG, "avata, resId : "+avata+","+resId);
                            ChanChat item = new ChanChat(obj.getString("sendername"), obj.getString("senderid"), "avata", obj.getString("content"),obj.getString("day"), obj.getString("time"));
                            chanchats.add(item);
                        }
                        ///
                        Log.d(TAG, "chanchats size: "+chanchats.size());
                        adapter.setItems(chanchats);
                        adapter.notifyDataSetChanged();

                    } else {//로그인 실패시
                        Toast.makeText( getActivity(), "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT ).show();
                        return;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText( getActivity(), "에러발생", Toast.LENGTH_SHORT ).show();
                }
            }
        };
        Toast.makeText(getActivity(), st_chanid, Toast.LENGTH_LONG);
        Log.d(TAG+":UserId",st_userid);
        Log.d(TAG+":ChanId",st_chanid);
        MessageRequest messagerequest = new MessageRequest( st_userid,st_chanid, responseListener );
        RequestQueue queue = Volley.newRequestQueue( getActivity() );
        queue.add( messagerequest );
    }
}