package cn.cslg.snowymoney;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.astuetz.PagerSlidingTabStrip;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import cn.cslg.snowymoney.dao.ConnectServer;
import cn.cslg.snowymoney.dao.Connection;
import cn.cslg.snowymoney.dao.DBDao;
import cn.cslg.snowymoney.fragment.CallFragment;
import cn.cslg.snowymoney.fragment.CasualFragment;
import cn.cslg.snowymoney.fragment.LatestFragment;
import cn.cslg.snowymoney.fragment.LeafletFragment;
import cn.cslg.snowymoney.fragment.OthersFragment;
import cn.cslg.snowymoney.fragment.TeachFragment;
import cn.cslg.snowymoney.module.ChargeActivity;
import cn.cslg.snowymoney.module.CollectionActivity;
import cn.cslg.snowymoney.module.IssuedActivity;
import cn.cslg.snowymoney.module.LoginActivity;
import cn.cslg.snowymoney.module.PersonInfoActivity;
import cn.cslg.snowymoney.module.PublishActivity;
import cn.cslg.snowymoney.module.RegisterActivity;
import cn.cslg.snowymoney.module.SearchActivity;
import cn.cslg.snowymoney.view.CircleDrawable;
import cn.cslg.snowymoney.view.CircleImageView;
import cn.cslg.snowymoney.view.FragAdapter;
import cn.cslg.snowymoney.view.GlideImageLoader;
import cn.cslg.snowymoney.view.MyViewPager;
import cn.cslg.snowymoney.view.RecordAdapter;
import cn.cslg.snowymoney.view.ToastUtil;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;


public class MainActivity extends AppCompatActivity {
    private  final int REQUEST_EXTERNAL_STORAGE = 1;
    private  String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    private DBDao dbDao;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private Banner banner;
    private PagerSlidingTabStrip tabs;
    private MyViewPager viewPager;
    private LinearLayout loginLayout,unLoginLayout;
    private Button registerBtn,loginBtn;
    private CircleImageView userImg;
    private TextView userName,userCoin,userCharge,navHome,navPublish,navCollection,navIssued;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
        findViews();
        setListeners();
        init();
    }


    private void findViews(){
        //bind views
        drawerLayout = findViewById(R.id.main_draw);
        toolbar = findViewById(R.id.toolbar);
        banner = findViewById(R.id.banner);
        tabs = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.vp);
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
    }

    private void setListeners(){
        //set button user login's listener
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivityForResult(intent,0);
            }
        });
        //set button user register's listener
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });
        //set uerImg's listener
        userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PersonInfoActivity.class);
                startActivity(intent);
            }
        });
        //set button userCharge's listener
        userCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChargeActivity.class);
                startActivity(intent);
            }
        });

        //set navigation home's listener
        navHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        //set navigation publish's listener
        navPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MainActivity.this, PublishActivity.class);
                startActivity(intent);
            }
        });
        //set navigation history's listener
        navCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MainActivity.this, CollectionActivity.class);
                startActivity(intent);
            }
        });
        //set navigation logout's listener
        navIssued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(MainActivity.this, IssuedActivity.class);
                startActivity(intent);
            }
        });
    }


    private void init(){
        sp = getSharedPreferences("logInfo",MODE_PRIVATE);
        dbDao = new DBDao(this);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.unlogin);
        CircleDrawable circleDrawable = new CircleDrawable(drawable, MainActivity.this, 36);
        //init the left layout
        if(isLogin()) setData();
        else {
            unLoginLayout.setVisibility(View.VISIBLE);
            //set toolbar navigation icon
            toolbar.setNavigationIcon(circleDrawable);
        }
        //hide primary title
        toolbar.setTitle("");

        setSupportActionBar(toolbar);

        //set navigationIcon's onclick listener
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set banner
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.banner1);
        images.add(R.drawable.banner2);
        images.add(R.drawable.banner3);
        banner.setImageLoader(new GlideImageLoader()).setImages(images).start();

        //set titles
        String[] titles = {"最新","家教","传单","话务员","临时工","其他"};

        //set fragments
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new LatestFragment());
        fragments.add(new TeachFragment());
        fragments.add(new LeafletFragment());
        fragments.add(new CallFragment());
        fragments.add(new CasualFragment());
        fragments.add(new OthersFragment());
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(),titles,fragments);
        viewPager.setAdapter(adapter);
        tabs.setTextColor(Color.BLACK);
        tabs.setTextSize(40);
        tabs.setViewPager(viewPager);

    }
    private boolean isLogin(){
        long lastLogin  = sp.getLong("lastLogin",0);
        Date date = new Date();
        int days = (int) ((date.getTime() - lastLogin) / (1000*3600*24));
        boolean isLogin = sp.getBoolean("isLogin",false);
        if(lastLogin!=0 && days>7 && isLogin){
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("isLogin",false);
            editor.apply();
            ToastUtil.show(this,"身份已失效，请重新登录");
            return false;
        }
        return isLogin;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        //find SearchView
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        //init SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
        searchView.setSearchableInfo(info);
        searchView.setQueryHint("输入相关内容查找");


        searchView.setSubmitButtonEnabled(true);
        ImageView iv_submit =  searchView.findViewById(R.id.search_go_btn);
        iv_submit.setImageResource(R.drawable.ic_submit);
        //set SearchView's autocomplete
        //SearchView.SearchAutoComplete
        AutoCompleteTextView autoComplete = searchView.findViewById(R.id.search_src_text);
        autoComplete.setTextColor(getResources().getColor(R.color.colorText,null));
        autoComplete.setTextSize(18);
        autoComplete.setThreshold(1);

        //autoComplete.setAdapter();
        searchView.setSuggestionsAdapter(new RecordAdapter(this,null, true) );
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Cursor cursor = (Cursor)searchView.getSuggestionsAdapter().getItem(position);
                searchView.setQuery(cursor.getString(1),false);
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Cursor cursor = dbDao.getRecordByContent(query);
                if(!cursor.moveToFirst()) dbDao.insertRecord(query);
                searchView.clearFocus();
                searchView.onActionViewCollapsed();
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("query",query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        MenuItem chat = menu.findItem(R.id.action_chat);
        final Map<String,Boolean> supportedConversation = new HashMap<>();
        chat.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(isLogin()){
                    if(ConnectServer.isConnected){
                        supportedConversation.put(Conversation.ConversationType.PRIVATE.getName(),false);
                        supportedConversation.put(Conversation.ConversationType.GROUP.getName(), false);
                        //启动会话列表界面
                        RongIM.getInstance().startConversationList(MainActivity.this, supportedConversation);
                    } else ToastUtil.show(MainActivity.this, "服务器尚未连接");

                } else ToastUtil.show(MainActivity.this, "请先进行登录");
                return true;
            }
        });

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == resultCode){
            setData();
        }
    }

    private void setData(){
        unLoginLayout.setVisibility(View.GONE);
        loginLayout.setVisibility(View.VISIBLE);
        String userId = sp.getString("userId","");

        //set user's information
        String img = sp.getString("userImg","");
        String name = sp.getString("userName","");
        String balance = sp.getString("userBalance","");
        Cursor cursor = dbDao.getUserById(userId);
        if(!cursor.moveToFirst()) dbDao.insertUser(userId,name,img);
        if(img.equals("")) {
            userImg.setImageResource(R.drawable.user);
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.user);
            CircleDrawable circleDrawable = new CircleDrawable(drawable, MainActivity.this, 36);
            toolbar.setNavigationIcon(circleDrawable);
        }else {
            Glide.with(this).load(img).into(userImg);
            try {
                CircleDrawable circleDrawable = new CircleDrawable(Glide.with(this).asDrawable().submit().get(), MainActivity.this, 36);
                toolbar.setNavigationIcon(circleDrawable);
            }catch (NullPointerException | ExecutionException | InterruptedException e){
                e.printStackTrace();
            }
        }
        userName.setText(name);
        userCoin.setText(balance);

        new ConnectServer().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userId,name);
        //init user cahe
        initUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!isLogin()) {
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.unlogin);
            CircleDrawable circleDrawable = new CircleDrawable(drawable, MainActivity.this, 36);
            toolbar.setNavigationIcon(circleDrawable);
            unLoginLayout.setVisibility(View.VISIBLE);
            loginLayout.setVisibility(View.GONE);
            RongIM.getInstance().logout();
        }else{
            if(sp.getBoolean("isChanged",false)){
                String userId = sp.getString("userId","");
                new UpdateTask().execute(userId);
                setData();
            }
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
    private void initUser(){
        Cursor cursor = dbDao.getUsers();
        while (cursor.moveToNext()){
            String userId = cursor.getString(0);
            String userName = cursor.getString(1);
            String userImg = cursor.getString(2);
            RongIM.getInstance().refreshUserInfoCache( new UserInfo(userId, userName, Uri.parse(userImg)));
        }
    }

    public  void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}
