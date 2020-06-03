package cn.cslg.snowymoney.module;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.Date;

import cn.cslg.snowymoney.R;
import cn.cslg.snowymoney.dao.Connection;
import cn.cslg.snowymoney.dao.DBDao;
import cn.cslg.snowymoney.view.AccountAdapter;
import cn.cslg.snowymoney.view.ToastUtil;

public class LoginActivity extends AppCompatActivity {
    private ContentResolver resolver;
    private AutoCompleteTextView userId;
    private EditText userPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init(){
        //find views
        userId = findViewById(R.id.user_account);
        userPass = findViewById(R.id.user_pass);
        Button loginBtn = findViewById(R.id.login_btn);
        Button cancelBtn = findViewById(R.id.cancel_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = userId.getText().toString().trim();
                String pass = userPass.getText().toString().trim();
                //check login
                new Login().execute(account,pass);

            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1);
                finish();
            }
        });
        userId.setThreshold(1);
        AccountAdapter adapter = new AccountAdapter(this,null,true);
        userId.setAdapter(adapter);
    }


    class Login extends AsyncTask<String,Void,Boolean>{
        @Override
        protected Boolean doInBackground(String... params) {
            String result = Connection.getUserInfo("login",params[0]);
            Gson gson = new Gson();
            String[] loginInfo = gson.fromJson(result,String[].class);
            String password = loginInfo[2];
            if(params[1].equals(password)){
                DBDao dbDao = new DBDao(LoginActivity.this);
                Cursor cursor = dbDao.getUserById(params[0]);
                if(!cursor.moveToFirst()) dbDao.insertAccount(params[0]);
                SharedPreferences sharedPreferences = getSharedPreferences("logInfo",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLogin",true);
                //put user's info
                editor.putString("userId",params[0]);
                editor.putString("userImg",loginInfo[0]);
                editor.putString("userName",loginInfo[1]);
                editor.putString("userBalance",loginInfo[3]);
                Date date = new Date();
                editor.putLong("lastLogin",date.getTime());
                editor.apply();
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) {
                setResult(0);
                finish();
            }
            else ToastUtil.show(LoginActivity.this,"用户名或密码错误");
        }
    }
}

