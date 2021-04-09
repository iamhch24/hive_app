package com.example.hiveapp_login.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hiveapp_login.MainActivity;
import com.example.hiveapp_login.R;
import com.example.hiveapp_login.model.Channel;
import com.example.hiveapp_login.ui.login.LoginActivity;
import com.example.hiveapp_login.ui.login.LoginRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnChanClick {

//    private HomeViewModel homeViewModel;
    private final String TAG = "HomeFragment";
    private ChanAdapter adapter;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.rv_channel);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new ChanAdapter(this);
        getApiData();
        return root;
    }

    public void getApiData(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonResponse = new JSONObject( response );
                    String success = jsonResponse.getString( "success" );

                    if("success".equals(success)) {//성공시
                        //Toast.makeText( getActivity(), "data 받기 성공", Toast.LENGTH_SHORT ).show();
                        JSONArray jsonArray = jsonResponse.getJSONArray("data");
                        ArrayList<Channel> channels = new ArrayList<Channel>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            channels.add(new Channel(obj.getString("chanid"), obj.getString("name")));
                        }
                        ///
                        adapter.setItems(channels);
                        recyclerView.setAdapter(adapter);

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
        Toast.makeText(getActivity(), ((MainActivity)getActivity()).getWorkid(), Toast.LENGTH_LONG);
        Log.d(TAG+":UserId",((MainActivity)getActivity()).getUserid());
        Log.d(TAG+":WorkId",((MainActivity)getActivity()).getWorkid());
        ChanRequest chanrequest = new ChanRequest( ((MainActivity)getActivity()).getUserid(),((MainActivity)getActivity()).getWorkid(), responseListener );
        RequestQueue queue = Volley.newRequestQueue( getActivity() );
        queue.add( chanrequest );
    }

    @Override
    public void onChanClick(String V1, String V2) {
        ((MainActivity)getActivity()).setChanid(V1);
        ((MainActivity)getActivity()).setChanname(V2);

        Log.d(TAG, "OnClick:Channame="+V2);

        ((MainActivity)getActivity()).getNavController().popBackStack();
        ((MainActivity)getActivity()).getNavController().navigate(R.id.navigation_message);

    }
}


