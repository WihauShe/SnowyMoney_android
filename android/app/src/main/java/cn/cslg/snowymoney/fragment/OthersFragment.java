package cn.cslg.snowymoney.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baoyz.widget.PullRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.cslg.snowymoney.R;
import cn.cslg.snowymoney.dao.Connection;
import cn.cslg.snowymoney.entity.Job;
import cn.cslg.snowymoney.module.JobDetailActivity;
import cn.cslg.snowymoney.view.JobsAdapter;
import cn.cslg.snowymoney.view.ToastUtil;


public class OthersFragment extends Fragment {
    private View view;
    private PullRefreshLayout refreshLayout;
    private RecyclerView jobList;
    private List<Job> jobs = new ArrayList<>();
    private JobsAdapter adapter;

    public OthersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.jobs_others, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViews();
        init();
    }

    private void findViews(){
        refreshLayout = view.findViewById(R.id.refresh_layout);
        jobList = view.findViewById(R.id.job_list);
    }

    private void init() {
        //init refreshLayout
        refreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_MATERIAL);
        refreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });

        //init jobs' RecyclerView
        jobList.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        adapter = new JobsAdapter(jobs);
        jobList.setAdapter(adapter);
        adapter.setItemClickListener(new JobsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SharedPreferences sp = getActivity().getSharedPreferences("logInfo", Context.MODE_PRIVATE);
                boolean isLogin = sp.getBoolean("isLogin",false);
                if(!isLogin) ToastUtil.show(getContext(),"请先进行登录");
                else {
                    String userId = sp.getString("userId","");
                    new BalanceTask().execute(position,userId);
                }
            }
        });

        //get job data
        new JobTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    class JobTask extends AsyncTask<Void, Void, List<Job>> {
        @Override
        protected List<Job> doInBackground(Void... params) {
            String result = Connection.getJobs("6");
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
                SharedPreferences sp = getActivity().getSharedPreferences("logInfo",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isChanged",true);
                editor.apply();
                Intent intent = new Intent(getActivity(), JobDetailActivity.class);
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
            if(!aBoolean) ToastUtil.show(getContext(),"余额不足，请先进行充值");
        }
    }
}
