package cn.cslg.snowymoney.module;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import cn.cslg.snowymoney.R;
import cn.cslg.snowymoney.dao.Connection;
import cn.cslg.snowymoney.dao.DBDao;
import cn.cslg.snowymoney.view.CircleImageView;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class UserInfoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CircleImageView userImg;
    private TextView userName,userSex,userAge,userSchool,userCredit,userReport,userContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        findViews();
        init();
    }

    private void findViews(){
        toolbar = findViewById(R.id.user_info_toolbar);
        userImg = findViewById(R.id.user_img);
        userName = findViewById(R.id.user_name);
        userSex = findViewById(R.id.user_sex);
        userAge = findViewById(R.id.user_age);
        userSchool = findViewById(R.id.user_school);
        userCredit = findViewById(R.id.user_credit);
        userReport = findViewById(R.id.user_report);
        userContact = findViewById(R.id.user_contact);
    }

    private void init(){
        toolbar.setTitle("用户资料");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String userId = intent.getStringExtra("userId");
        final String userName = intent.getStringExtra("userName");
        //get and set data
        new InfoTask().execute(userId);

        userReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this,ReportActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });

        userContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBDao dbDao = new DBDao(UserInfoActivity.this);
                String userImg = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.drawable.user;
                Cursor cursor = dbDao.getUserById(userId);
                if(!cursor.moveToFirst()){
                    dbDao.insertUser(userId,userName,userImg);
                    RongIM.getInstance().refreshUserInfoCache(new UserInfo(userId,userName, Uri.parse(userImg)));
                }
                RongIM.getInstance().startPrivateChat(UserInfoActivity.this,userId,userName);
            }
        });

    }

    class InfoTask extends AsyncTask<String,Object,Map<String,String>>{

        @Override
        protected Map<String,String> doInBackground(String[] params) {
            String result = Connection.getUserInfo("getUser",params[0]);
            Gson gson = new Gson();
            return gson.fromJson(result,new TypeToken<Map<String, String>>() {}.getType());
        }

        @Override
        protected void onPostExecute(Map<String,String> map) {
            super.onPostExecute(map);
            if(map!=null){
                userName.setText(map.get("name"));
                userSex.setText(map.get("sex"));
                userAge.setText(map.get("age"));
                userSchool.setText(map.get("school"));
                userCredit.setText(map.get("credit"));
            }
        }
    }

}
