package com.example.hiveapp_login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hiveapp_login.model.User;
import com.example.hiveapp_login.model.WorkSpace;
import com.example.hiveapp_login.ui.home.MemberRequest;
import com.example.hiveapp_login.ui.home.OnChanClick;
import com.example.hiveapp_login.ui.home.OnWorkClick;
import com.example.hiveapp_login.ui.home.WorkAdapter;
import com.example.hiveapp_login.ui.home.WorkRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompHeader;
import static com.example.hiveapp_login.Constants.Ip;

public class MainActivity extends AppCompatActivity implements OnWorkClick {

    private final String TAG = "MainActivity";
    private WorkAdapter adapter;
    private RecyclerView recyclerView;
    private NavController navController;

    private String userid;
    private String useremail;
    private String username;
    private String useravata;
    private String workid;
    private String workname;
    private String worktitle;
    private String chanid;
    private String channame;
    private ArrayList<User> members;
    private boolean bDrawer;
    private Button bnTitle;
    private TextView tvTitle;
    private DrawerLayout drawerlayout;
    private View drawerView;
    private StompClient stompClient;
    private List<StompHeader> headerList;
    private final String wsServerUrl = "ws://"+Ip+"/hive/websocket";
//    private final String wsServerUrl = "ws://192.168.123.104:9012/hive/websocket";


    @Override
    public void onWorkClick(String V1, String V2, String V3) {
        workid = V1;
        workname = V2;
        worktitle = V3;
        bnTitle.setText(worktitle);
        tvTitle.setText(workname);
        Log.d(TAG, "onWorkClick:WorkId="+workid);

        navController.popBackStack();
        navController.navigate(R.id.navigation_home);

        drawerlayout.closeDrawer(drawerView);
        getApiMembers();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerlayout = findViewById(R.id.drawer_layout);
        drawerView = findViewById(R.id.drawer);
        bDrawer = false;
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_message, R.id.navigation_search, R.id.navigation_profile)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        View myTitle = getLayoutInflater().inflate(R.layout.view_toolbar,null);
        ActionBar ab = getSupportActionBar();
        ab.setCustomView(myTitle);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(false);
        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayShowHomeEnabled(false);
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        Intent intent = getIntent();
        useremail = intent.getStringExtra("UserEmail");
        userid = intent.getStringExtra("UserId");
        username = intent.getStringExtra("UserName");
        useravata = intent.getStringExtra("UserAvata");
        workid = intent.getStringExtra("LastWorkId");
        workname = intent.getStringExtra("LastWorkName");
        worktitle = intent.getStringExtra("LastWorkTitle");

        ((TextView)findViewById(R.id.tv_username)).setText(username);
        ((TextView)findViewById(R.id.tv_useremail)).setText(useremail);

//        Toast.makeText(this, workid, Toast.LENGTH_LONG).show();

        bnTitle = (Button)findViewById(R.id.bnTitle);
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        Log.d(TAG+":bnTitle1:",bnTitle.getText().toString());
        bnTitle.setText(worktitle);
        Log.d(TAG+":bnTitle2:",bnTitle.getText().toString());
        Log.d(TAG+":tvTitle1:",tvTitle.getText().toString());
        tvTitle.setText("@ "+workname);
        Log.d(TAG+":tvTitle2:",tvTitle.getText().toString());

        adapter = new WorkAdapter(this);

        initStomp();
//        subStomp();

//        JSONObject sObject = new JSONObject();//배열 내에 들어갈 json
//        try {
//            sObject.put("type", "CHAT");
//            sObject.put("content", "Hi, good to see you!");
//            sObject.put("sender", "안드로이드");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        String msg = sObject.toString();

//        sendStomp(msg);


        bnTitle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!bDrawer)
                    drawerlayout.openDrawer(drawerView);
                else
                    drawerlayout.closeDrawer(drawerView);
            }
        });
        drawerlayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        recyclerView = this.findViewById(R.id.rv_workspace);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        getApiData();
        getApiMembers();
    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {      }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
            bDrawer = true;
        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
            bDrawer = false;
        }

        @Override
        public void onDrawerStateChanged(int newState) {        }
    };


    public void initStomp(){
        stompClient= Stomp.over(Stomp.ConnectionProvider.OKHTTP, wsServerUrl);

        stompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {

                case OPENED:
                    Log.d(TAG, "Stomp connection opened");
                    break;

                case ERROR:
                    Log.e(TAG, "Error", lifecycleEvent.getException());
                    break;

                case CLOSED:
                    Log.d(TAG, "Stomp connection closed");
                    break;
            }
        });

        // add Header
        stompClient.connect();
    }

//    private void sendStomp(String msg) {
//        stompClient.send("/app/chat.sendMessage", msg).subscribe();
//
//    }
//
//    private void subStomp() {
//        stompClient.topic("/topic/public").subscribe(topicMessage -> {
//            Log.d(TAG,topicMessage.getPayload().toString());
//        });
//    }

    public String getUseravata() {
        return useravata;
    }

    public String getUserid(){
        return userid;
    }

    public String getUseremail(){
        return useremail;
    }

    public String getUsername(){
        return username;
    }

    public String getWorkid(){
        return workid;
    }

    public String getChanid() {
        return chanid;
    }

    public void setChanid(String chanid) {
        this.chanid = chanid;
    }

    public String getChanname() {
        return channame;
    }

    public void setChanname(String channame) {
        this.channame = channame;
    }

    public NavController getNavController() {
        return navController;
    }

    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    public StompClient getStompClient() {
        return stompClient;
    }

    public void setStompClient(StompClient stompClient) {
        this.stompClient = stompClient;
    }

    public ArrayList<User> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    protected void getApiData(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject( response );
                    String success = jsonResponse.getString( "success" );

                    if("success".equals(success)) {//성공시
//                        Toast.makeText(this, "data 받기 성공", Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonResponse.getJSONArray("data");
                        ArrayList<WorkSpace> workspaces = new ArrayList<WorkSpace>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            workspaces.add(new WorkSpace(obj.getString("workid"), obj.getString("name"),obj.getString("title")));
                        }
                        ///

                        adapter.setItems(workspaces);
                        recyclerView.setAdapter(adapter);

                    } else {//로그인 실패시
//                        Toast.makeText( this, "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT ).show();
                        return;
                    }
                }catch (Exception e){
                    e.printStackTrace();
  //                  Toast.makeText( this, "에러발생", Toast.LENGTH_SHORT ).show();
                }
            }
        };
        Toast.makeText(this, workid, Toast.LENGTH_LONG);
//        Log.d(TAG+":UserId",);
//        Log.d(TAG+":WorkId",((MainActivity)getActivity()).getWorkid());
        WorkRequest workrequest = new WorkRequest( userid, responseListener );
        RequestQueue queue = Volley.newRequestQueue( this );
        queue.add( workrequest );
    }

    protected void getApiMembers(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject( response );
                    String success = jsonResponse.getString( "success" );

                    if("success".equals(success)) {//성공시
//                        Toast.makeText(this, "data 받기 성공", Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = jsonResponse.getJSONArray("data");
                        members = new ArrayList<User>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            members.add(new User(obj.getString("userid"), obj.getString("name"),obj.getString("photo")));
                        }
                        Log.d(TAG,"members size="+members.size());
                        ///
                    } else {//로그인 실패시
                   //     Toast.makeText( g, "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT ).show();
                        return;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                 //   Toast.makeText( (MainActivity)getActivity(), "에러발생", Toast.LENGTH_SHORT ).show();
                }
            }
        };
//        Toast.makeText(this, workid, Toast.LENGTH_LONG);
//        Log.d(TAG+":UserId",);
//        Log.d(TAG+":WorkId",((MainActivity)getActivity()).getWorkid());
        MemberRequest memberrequest = new MemberRequest( userid, workid, responseListener );
        RequestQueue queue = Volley.newRequestQueue( this );
        queue.add( memberrequest );
    }
}