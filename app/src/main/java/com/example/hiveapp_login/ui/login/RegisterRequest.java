package com.example.hiveapp_login.ui.login;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    //서버 URL 설정
    final static private String URL = "http://58.224.18.156:9015/app/register";
    private Map<String, String> map;
    //private Map<String, String>parameters;

    public RegisterRequest(String UserEmail, String UserPwd, String UserName, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("email", UserEmail);
        map.put("pwd", UserPwd);
        map.put("name", UserName);

    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}