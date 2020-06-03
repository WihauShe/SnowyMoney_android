package cn.cslg.snowymoney.dao;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class ConnectServer extends AsyncTask<String,Object,String> {
    public static boolean isConnected = false;

    @Override
    protected String doInBackground(String[] params) {
        String result = Connection.getUserToken(params[0],params[1]);
        System.out.println(result);
        Gson gson = new Gson();
        Map<String,String> map = gson.fromJson(result,new TypeToken<Map<String,String>>(){}.getType());
        return map.get("token");
    }

    @Override
    protected void onPostExecute(String token) {
        super.onPostExecute(token);
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String s) {
                isConnected = true;
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }
}
