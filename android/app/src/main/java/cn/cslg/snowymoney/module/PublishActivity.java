package cn.cslg.snowymoney.module;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import cn.cslg.snowymoney.MainActivity;
import cn.cslg.snowymoney.R;
import cn.cslg.snowymoney.dao.ConnectServer;
import cn.cslg.snowymoney.dao.Connection;
import cn.cslg.snowymoney.dao.DBDao;
import cn.cslg.snowymoney.view.CircleImageView;
import cn.cslg.snowymoney.view.ToastUtil;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class PublishActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private LinearLayout loginLayout,unLoginLayout;
    private Button registerBtn,loginBtn;
    private CircleImageView userImg;
    private TextView userName,userCoin,userCharge,navHome,navPublish,navCollection,navIssued;
    private Spinner jobCategory,jobSalaryUnit,jobDurationUnit;
    private EditText jobSalary,jobDuration,jobPosition,jobDemands;
    private Button jobSubmit;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        findViews();
        setListeners();
        init();
    }

    private void findViews(){
        drawerLayout = findViewById(R.id.publish_draw);
        toolbar = findViewById(R.id.publish_toolbar);
        unLoginLayout = findViewById(R.id.unLogin_nav);
        loginLayout = findViewById(R.id.login_nav);
        loginBtn = findViewById(R.id.login_btn);
        registerBtn = findViewById(R.id.register_btn);
        userImg = findViewById(R.id.user_img);
        userName = findViewById(R.id.user_name);
        userCoin = findViewById(R.id.user_coin);
        userCharge = findViewById(R.id.user_charge);
        navHome = findViewById(R.id.nav_home);
        navPublish = findViewById(R.id.nav_publish);
        navCollection = findViewById(R.id.nav_collection);
        navIssued = findViewById(R.id.nav_issued);
        jobCategory = findViewById(R.id.job_category);
        jobSalary = findViewById(R.id.job_salary);
        jobSalaryUnit = findViewById(R.id.job_salary_unit);
        jobDuration = findViewById(R.id.job_duration);
        jobDurationUnit = findViewById(R.id.job_duration_unit);
        jobPosition = findViewById(R.id.job_position);
        jobDemands = findViewById(R.id.job_demands);
        jobSubmit = findViewById(R.id.job_submit);
    }

    private void setListeners(){
        //set button user login's listener
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublishActivity.this, LoginActivity.class);
                startActivityForResult(intent,0);
            }
        });
        //set button user register's listener
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublishActivity.this, RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });
        //set uerImg's listener
        userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublishActivity.this, PersonInfoActivity.class);
                startActivity(intent);
            }
        });
        //set button userCharge's listener
        userCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublishActivity.this, ChargeActivity.class);
                startActivity(intent);
            }
        });

        //set navigation home's listener
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(PublishActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //set navigation publish's listener
        navPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        //set navigation history's listener
        navCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(PublishActivity.this, CollectionActivity.class);
                startActivity(intent);
            }
        });
        //set navigation logout's listener
        navIssued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(PublishActivity.this, IssuedActivity.class);
                startActivity(intent);
            }
        });
        //set job submit button listener
        jobSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!sp.getBoolean("isLogin",false))
                    ToastUtil.show(PublishActivity.this,"您尚未登录");
                else{
                    String category = jobCategory.getSelectedItem().toString();
                    String salary = jobSalary.getText().toString();
                    String salaryUnit = jobSalaryUnit.getSelectedItem().toString();
                    salary += "/"+salaryUnit;
                    String duration = jobDuration.getText().toString();
                    String durationUnit = jobDurationUnit.getSelectedItem().toString();
                    int isLongTerm = 0;
                    if((durationUnit.equals("天")&&Integer.parseInt(duration)>=14)||durationUnit.equals("月"))
                        isLongTerm = 1;
                    duration += durationUnit;
                    String position = jobPosition.getText().toString();
                    String demands = jobDemands.getText().toString();
                    LocalDate localDate = LocalDate.now();
                    String date = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    System.out.println(date);
                    SharedPreferences sp = getSharedPreferences("logInfo",MODE_PRIVATE);
                    String publisherName = sp.getString("userName","");
                    String publisherId = sp.getString("userId","");
                    //post job to server
                    String[] params = new String[]{category,salary,duration,position,demands,date,String.valueOf(isLongTerm),publisherName,publisherId};
                    new addJobTask().execute(params);
                }

            }
        });
    }

    private void init(){
        toolbar.setTitle("兼职发布");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, 0,0){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

        };
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //set job's parameter adapter
        String[] categories = {"家教","传单","话务员","临时工","其他"};
        jobCategory.setAdapter(new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,categories));
        String[] salaryUnit = {"时","天"};
        jobSalaryUnit.setAdapter(new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,salaryUnit));
        String[] durationUnit = {"小时","天","月"};
        jobDurationUnit.setAdapter(new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,durationUnit));

        //set data
        loginLayout.setVisibility(View.VISIBLE);
        sp = getSharedPreferences("logInfo",MODE_PRIVATE);
        setData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == resultCode){
            unLoginLayout.setVisibility(View.GONE);
            loginLayout.setVisibility(View.VISIBLE);

            String userId = sp.getString("userId","");
            new ConnectServer().execute(userId);
            setData();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!sp.getBoolean("isLogin",false)) {
            unLoginLayout.setVisibility(View.VISIBLE);
            loginLayout.setVisibility(View.GONE);
            RongIM.getInstance().logout();
        }else{
            if(sp.getBoolean("isChanged",false)){
                String userId = sp.getString("userId","");
                String userName = sp.getString("userName","");
                new ConnectServer().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userId,userName);
                //init user cahe
                initUser();
                setData();
            }
        }
    }

    private void setData(){
        //get user's information
        String img = sp.getString("userImg","");
        String name = sp.getString("userName","");
        String balance = sp.getString("userBalance","");
        //set info
        if(img.equals("")) userImg.setImageResource(R.drawable.user);
        else Glide.with(this).load(img).into(userImg);
        userName.setText(name);
        userCoin.setText(balance);
    }

    private void initUser(){
        DBDao dbDao = new DBDao(this);
        Cursor cursor = dbDao.getUsers();
        while (cursor.moveToNext()){
            String userId = cursor.getString(0);
            String userName = cursor.getString(1);
            String userImg = cursor.getString(2);
            RongIM.getInstance().refreshUserInfoCache( new UserInfo(userId, userName, Uri.parse(userImg)));
        }
    }


    class UpdateTask extends AsyncTask<String,Void,String[]> {

        @Override
        protected String[] doInBackground(String... params) {
            String result = Connection.getUserInfo("login", params[0]);
            Gson gson = new Gson();
            return gson.fromJson(result, String[].class);
        }

        @Override
        protected void onPostExecute(String[] loginInfo) {
            super.onPostExecute(loginInfo);
            SharedPreferences.Editor editor = sp.edit();
            //put user's info
            editor.putString("userImg",loginInfo[0]);
            editor.putString("userName",loginInfo[1]);
            editor.putString("userBalance",loginInfo[3]);
            editor.apply();
        }
    }

    class addJobTask extends AsyncTask<String,Void,Boolean>{
        @Override
        protected Boolean doInBackground(String... params) {
            String result = Connection.addJob(params);
            return result.equals("true");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isChanged", true);
                editor.apply();
                ToastUtil.show(PublishActivity.this,"发布成功");
                finish();
                startActivity(new Intent(PublishActivity.this,MainActivity.class));
            }else ToastUtil.show(PublishActivity.this,"余额不足，发布失败");
        }
    }
}
