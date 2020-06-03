package cn.cslg.snowymoney.module;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.cslg.snowymoney.R;
import cn.cslg.snowymoney.dao.Connection;
import cn.cslg.snowymoney.entity.Job;
import cn.cslg.snowymoney.view.JobsAdapter;
import cn.cslg.snowymoney.view.ToastUtil;

public class SearchActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView searchList;
    private List<Job> jobs = new ArrayList<>();
    private JobsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
    }
    private void init(){
        toolbar = findViewById(R.id.search_toolbar);
        toolbar.setTitle("搜索结果");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchList = findViewById(R.id.search_list);

        searchList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        //init recyclerView
        adapter = new JobsAdapter(jobs);
        searchList.setAdapter(adapter);
        adapter.setItemClickListener(new JobsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(SearchActivity.this, JobDetailActivity.class);
                SharedPreferences sp = getSharedPreferences("logInfo", Context.MODE_PRIVATE);
                boolean isLogin = sp.getBoolean("isLogin",false);
                if(!isLogin) ToastUtil.show(SearchActivity.this,"请先进行登录");
                else {
                    String userId = sp.getString("userId","");
                    new BalanceTask().execute(position,userId);
                }
            }
        });

        Intent intent = getIntent();
        String constraint = intent.getStringExtra("query");
        //get jobs' data
        new JobTask().execute(constraint);
    }

    class JobTask extends AsyncTask<String,Void,List<Job>>{
        @Override
        protected List<Job> doInBackground(String... params) {
            String result = Connection.getJobs("8&constraint="+params[0]);
            Gson gson = new Gson();
            return gson.fromJson(result, new TypeToken<List<Job>>() {}.getType());
        }

        @Override
        protected void onPostExecute(List<Job> jobList) {
            super.onPostExecute(jobList);
            jobs.clear();
            jobs.addAll(jobList);
            adapter.notifyDataSetChanged();
        }
    }

    class BalanceTask extends AsyncTask<Object,Void,Boolean>{
        @Override
        protected Boolean doInBackground(Object... params) {
            int position  = (int)params[0];
            int jobId = jobs.get(position).getId();
            String result;
            if(jobs.get(position).getFlag()==1)
                result = Connection.changeBalance(params[1].toString(),"deduct","2",String.valueOf(jobId));
            else
                result = Connection.changeBalance(params[1].toString(),"deduct","1",String.valueOf(jobId));
            Gson gson = new Gson();
            Map<String,Boolean> map = gson.fromJson(result,new TypeToken<Map<String,Boolean>>(){}.getType());
            if(map.get("result")){
                SharedPreferences sp = getSharedPreferences("logInfo",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isChanged",true);
                Intent intent = new Intent(SearchActivity.this, JobDetailActivity.class);
                intent.putExtra("job", jobs.get(position));
                intent.putExtra("isCollected",map.get("exist"));
                startActivity(intent);
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(!aBoolean) ToastUtil.show(SearchActivity.this,"余额不足，请先进行充值");
        }
    }
}
