package cn.cslg.snowymoney.module;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import cn.cslg.snowymoney.R;
import cn.cslg.snowymoney.dao.ConnectServer;
import cn.cslg.snowymoney.dao.Connection;
import cn.cslg.snowymoney.dao.DBDao;
import cn.cslg.snowymoney.entity.Job;
import cn.cslg.snowymoney.view.ToastUtil;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class JobDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView jobCategory,jobSalary,jobDuration,jobPosition,jobDemands,jobPublisher,jobDate;
    private Button collectBtn,contactBtn;
    private Job job;
    private DBDao dbDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        findViews();
        init();
    }

    private void findViews(){
        toolbar = findViewById(R.id.job_detail_toolbar);
        jobCategory = findViewById(R.id.job_category);
        jobSalary = findViewById(R.id.job_salary);
        jobDuration = findViewById(R.id.job_duration);
        jobPosition = findViewById(R.id.job_position);
        jobDemands = findViewById(R.id.job_demands);
        jobPublisher = findViewById(R.id.job_publisher);
        jobDate = findViewById(R.id.job_date);
        collectBtn = findViewById(R.id.job_collect);
        contactBtn = findViewById(R.id.job_contact);
    }

    private void init(){
        toolbar.setTitle("兼职详情");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        boolean isCollected = intent.getBooleanExtra("isCollected",false);
        job = (Job)intent.getSerializableExtra("job");
        //set view's data
        jobCategory.setText(job.getCategory());
        jobSalary.setText(job.getSalary());
        jobDuration.setText(job.getDuration());
        jobPosition.setText(job.getPosition());
        jobDemands.setText(job.getDemands());
        jobPublisher.setText(job.getPublisherName());
        jobDate.setText(job.getDate());

        jobPublisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobDetailActivity.this,UserInfoActivity.class);
                intent.putExtra("userId",job.getPublisherId());
                intent.putExtra("userName",job.getPublisherName());
                startActivity(intent);
            }
        });
        if(isCollected){
            collectBtn.setText("已收藏");
            collectBtn.setBackgroundColor(getColor(R.color.colorNavSelect));
        }else {
            collectBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sp = getSharedPreferences("logInfo",MODE_PRIVATE);
                    String userId = sp.getString("userId","");
                    //update user's collection
                    new CollectionTask().execute(String.valueOf(job.getId()),userId);
                    collectBtn.setText("已收藏");
                    collectBtn.setOnClickListener(null);
                }
            });
        }

        dbDao = new DBDao(this);
        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectServer.isConnected){
                    String userId = job.getPublisherId();
                    String userName = job.getPublisherName();
                    String userImg = "android.resource://" + getApplicationContext().getPackageName() + "/" + R.drawable.user;
                    Cursor cursor = dbDao.getUserById(userId);
                    if(!cursor.moveToFirst()){
                        dbDao.insertUser(userId,userName,userImg);
                        RongIM.getInstance().refreshUserInfoCache(new UserInfo(userId,userName, Uri.parse(userImg)));
                    }
                    RongIM.getInstance().startPrivateChat(JobDetailActivity.this, job.getPublisherId(), job.getPublisherName());
                } else
                    ToastUtil.show(JobDetailActivity.this,"连接聊天失败,请先登录");
            }
        });
    }

    class CollectionTask extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... params) {
            Connection.addCollection(params[0],params[1]);
            return null;
        }
    }
}
