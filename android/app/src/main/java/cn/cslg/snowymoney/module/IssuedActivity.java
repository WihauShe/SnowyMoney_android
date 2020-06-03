package cn.cslg.snowymoney.module;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.cslg.snowymoney.MainActivity;
import cn.cslg.snowymoney.R;
import cn.cslg.snowymoney.dao.ConnectServer;
import cn.cslg.snowymoney.dao.Connection;
import cn.cslg.snowymoney.dao.DBDao;
import cn.cslg.snowymoney.entity.Job;
import cn.cslg.snowymoney.view.CircleImageView;
import cn.cslg.snowymoney.view.JobsAdapter;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class IssuedActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private LinearLayout loginLayout, unLoginLayout;
    private Button registerBtn, loginBtn;
    private CircleImageView userImg;
    private TextView userName,userCoin, userCharge, navHome, navPublish, navCollection, navIssued;
    private RecyclerView issuedList;
    private SharedPreferences sp;
    private List<Job> issues = new ArrayList<>();
    private JobsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issued);
        findViews();
        setListeners();
        init();
    }

    private void findViews() {
        drawerLayout = findViewById(R.id.issued_draw);
        toolbar = findViewById(R.id.issued_toolbar);
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
        issuedList = findViewById(R.id.issued_list);
    }

    private void setListeners() {
        //set button user login's listener
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IssuedActivity.this, LoginActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        //set button user register's listener
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IssuedActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        //set uerImg's listener
        userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IssuedActivity.this, PersonInfoActivity.class);
                startActivity(intent);
            }
        });
        //set button userCharge's listener
        userCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IssuedActivity.this, ChargeActivity.class);
                startActivity(intent);
            }
        });

        //set navigation home's listener
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(IssuedActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //set navigation publish's listener
        navPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(IssuedActivity.this, PublishActivity.class);
                startActivity(intent);
            }
        });
        //set navigation history's listener
        navCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(IssuedActivity.this, CollectionActivity.class);
                startActivity(intent);
            }
        });
        //set navigation logout's listener
        navIssued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

    }

    private void init() {
        toolbar.setTitle("发布列表");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, 0, 0) {
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


        adapter = new JobsAdapter(issues);
        adapter.setItemClickListener(new JobsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(IssuedActivity.this,JobDetailActivity.class);
                intent.putExtra("job",issues.get(position));
                startActivity(intent);
            }
        });
        adapter.setItemLongClickListener(new JobsAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                showPopupMenu(issuedList.getChildAt(position),position);
            }
        });
        issuedList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        issuedList.setAdapter(adapter);

        //set data
        loginLayout.setVisibility(View.VISIBLE);
        sp = getSharedPreferences("logInfo", MODE_PRIVATE);
        setData();
        //get issues
        String userId = sp.getString("userId","");
        new getIssuesTask().execute(userId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resultCode) {
            unLoginLayout.setVisibility(View.GONE);
            loginLayout.setVisibility(View.VISIBLE);

            String userId = sp.getString("userId", "");
            new ConnectServer().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userId);
            setData();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (!sp.getBoolean("isLogin", false)) {
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
    private void setData() {
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

    private void showPopupMenu(View v, final int position){
        PopupMenu popupMenu = new PopupMenu(this,v);
        getMenuInflater().inflate(R.menu.menu_issued,popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                new delIssueTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,issues.get(position).getId(),position);
                return true;
            }
        });
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

    class getIssuesTask extends AsyncTask<String,Void,List<Job>>{
        @Override
        protected List<Job> doInBackground(String... params) {
            String result = Connection.getJobs("7&publisherId="+params[0]);
            Gson gson = new Gson();
            return gson.fromJson(result, new TypeToken<List<Job>>() {}.getType());
        }

        @Override
        protected void onPostExecute(List<Job> jobList) {
            super.onPostExecute(jobList);
            issues.clear();
            issues.addAll(jobList);
            adapter.notifyDataSetChanged();
        }
    }

    class delIssueTask extends AsyncTask<Integer,Void,Void>{
        @Override
        protected Void doInBackground(Integer... params) {
            Connection.deleteJob(params[0].toString());
            issues.remove((int)params[1]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
        }
    }
}

