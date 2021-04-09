package com.example.hiveapp_login.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hiveapp_login.MainActivity;
import com.example.hiveapp_login.R;
import com.example.hiveapp_login.ui.chat.ChatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";
    private EditText login_email, login_password;
    private Button login_button, join_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        login_email = findViewById( R.id.login_email );
        login_password = findViewById( R.id.login_password );
        login_button = findViewById( R.id.login_button );
        join_button = findViewById( R.id.join_button );

        login_email.requestFocus();
        login_email.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_UP) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    login_password.requestFocus();
                    return true;
                }
                return false;
            }
        });

        login_password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_UP) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                    login_button.callOnClick();
                    return true;
                }
                return false;
            }
        });

        join_button.setOnClickListener( new View.OnClickListener() { // 회원가입 버튼 클릭시 수행
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( LoginActivity.this, RegisterActivity.class );
                startActivity( intent );
            }
        });

        login_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserEmail = login_email.getText().toString();
                String UserPwd = login_password.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject( response );
                            String success = jsonResponse.getString( "isSuccess" );

                            if("success".equals(success)) {//로그인 성공시

                                String UserEmail = jsonResponse.getString( "UserEmail" );
                                String UserId = jsonResponse.getString( "UserId" );
                                String UserName = jsonResponse.getString( "UserName" );
                                String UserAvata = jsonResponse.getString( "UserAvata" );
                                String LastWorkId = jsonResponse.getString( "LastWorkId" );
                                String LastWorkName = jsonResponse.getString( "LastWorkName" );
                                String LastWorkTitle = jsonResponse.getString( "LastWorkTitle" );

                                Toast.makeText( getApplicationContext(), String.format("%s님 환영합니다.", UserName), Toast.LENGTH_SHORT ).show();
                                Log.d(TAG+":UserName:",UserName);
                                Log.d(TAG+":Title:",LastWorkTitle);
                                Intent intent = new Intent( LoginActivity.this, MainActivity.class );

                                intent.putExtra( "UserEmail", UserEmail );
                                intent.putExtra( "UserId", UserId );
                                intent.putExtra( "UserName", UserName );
                                intent.putExtra( "UserAvata", UserAvata );
                                intent.putExtra( "LastWorkId", LastWorkId );
                                intent.putExtra( "LastWorkName", LastWorkName );
                                intent.putExtra( "LastWorkTitle", LastWorkTitle );

                                startActivity( intent );

                            } else {//로그인 실패시
                                Toast.makeText( getApplicationContext(), "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT ).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText( getApplicationContext(), "에러발생", Toast.LENGTH_SHORT ).show();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest( UserEmail, UserPwd, responseListener );
                RequestQueue queue = Volley.newRequestQueue( LoginActivity.this );
                queue.add( loginRequest );
            }
        });
    }
}