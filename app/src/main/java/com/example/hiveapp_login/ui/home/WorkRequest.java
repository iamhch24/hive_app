package com.example.hiveapp_login.ui.home;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import static com.example.hiveapp_login.Constants.Ip;

public class WorkRequest extends StringRequest {

    //서버 URL 설정
    final static private String URL = "http://"+Ip+"/app/workspaces";
    private Map<String, String> map;

    public WorkRequest(String UserId, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<String, String>();
        map.put("UserId", UserId);
    }

    @Override //response를 UTF8로 변경해주는 소스코드
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String utf8String = new String(response.data, "UTF-8");
            return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            // log error
            return Response.error(new ParseError(e));
        } catch (Exception e) {
            // log error
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}