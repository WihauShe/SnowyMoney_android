package cn.cslg.snowymoney.module;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;

import java.util.ArrayList;

import cn.cslg.snowymoney.R;
import cn.cslg.snowymoney.dao.Connection;
import cn.cslg.snowymoney.view.GlideEngine;
import cn.cslg.snowymoney.view.ToastUtil;

public class ReportActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView reportUser;
    private EditText reportReason;
    private ImageView reportEvidence;
    private Button reportChoose,reportSubmit;
    private Photo photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        findViews();
        init();
    }

    private void findViews(){
        toolbar = findViewById(R.id.report_toolbar);
        reportUser = findViewById(R.id.report_user);
        reportReason = findViewById(R.id.report_reason);
        reportEvidence = findViewById(R.id.report_evidence);
        reportChoose = findViewById(R.id.report_choose);
        reportSubmit = findViewById(R.id.report_btn);
    }

    private void init(){
        toolbar.setTitle("填写举报");
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
        String userName = intent.getStringExtra("userName");
        reportUser.setText(userName);

        reportChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyPhotos.createAlbum(ReportActivity.this, false, GlideEngine.getInstance())
                        .setFileProviderAuthority("cn.cslg.snowymoney.fileprovider")
                        .start(1);
            }
        });

        reportSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                String reason = reportReason.getText().toString().trim();
                if(photo == null) flag =2;
                if(reason.isEmpty()) flag = 1;
                if(flag == 1)
                    ToastUtil.show(ReportActivity.this,"请输入理由");
                else if(flag == 2)
                    ToastUtil.show(ReportActivity.this,"请选择截图");
                else
                    new Report().execute(userId,reason,photo.path);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
            photo = resultPhotos.get(0);
            Glide.with(this).load(photo.uri).into(reportEvidence);
        }
    }

    class Report extends AsyncTask<String,Void,Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            String result = Connection.reportUser(params[0],params[1],params[2]);
            return result.equals("true");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) finish();
        }
    }
}
