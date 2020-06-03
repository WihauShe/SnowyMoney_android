package cn.cslg.snowymoney.module;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.models.album.entity.Photo;

import java.util.ArrayList;
import java.util.Map;

import cn.cslg.snowymoney.R;
import cn.cslg.snowymoney.dao.Connection;
import cn.cslg.snowymoney.view.CircleImageView;
import cn.cslg.snowymoney.view.GlideEngine;

public class PersonInfoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ConstraintLayout personImgEdit,personNameEdit,personSexEdit,personAgeEdit,personSchoolEdit;
    private CircleImageView personImg;
    private TextView personName,personSex,personAge,personSchool,personCredit,personLogout;
    private Photo photo;
    private RequestManager mGlide;
    private SharedPreferences sp;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        findViews();
        setListeners();
        init();
    }

    private void findViews(){
        toolbar = findViewById(R.id.person_toolbar);
        personImgEdit = findViewById(R.id.person_img_edit);
        personNameEdit = findViewById(R.id.person_name_edit);
        personSexEdit = findViewById(R.id.person_sex_edit);
        personAgeEdit = findViewById(R.id.person_age_edit);
        personSchoolEdit = findViewById(R.id.person_school_edit);
        personImg = findViewById(R.id.person_img);
        personName = findViewById(R.id.person_name);
        personSex = findViewById(R.id.person_sex);
        personAge = findViewById(R.id.person_age);
        personCredit = findViewById(R.id.person_credit);
        personSchool = findViewById(R.id.person_school);
        personLogout = findViewById(R.id.person_logout);
    }

    private void setListeners(){
        personImgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyPhotos.createAlbum(PersonInfoActivity.this, true, GlideEngine.getInstance())
                        .setFileProviderAuthority("cn.cslg.snowymoney.fileprovider")
                        .start(1);
            }
        });
        personNameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View  dialogView = LayoutInflater.from(PersonInfoActivity.this).inflate(R.layout.person_name_edit,null);
                final EditText personNameEdited = dialogView.findViewById(R.id.person_name_edited);
                new AlertDialog.Builder(PersonInfoActivity.this).setView(dialogView)
                  .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = personNameEdited.getText().toString().trim();
                        if(!name.equals("") && !name.equals(personName.getText().toString())){
                            personName.setText(name);
                            //update userName
                            new UpdateTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userId,"name",name);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putBoolean("isChanged",true);
                            editor.apply();
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        personSexEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonInfoActivity.this);
                View  dialogView = LayoutInflater.from(PersonInfoActivity.this).inflate(R.layout.person_sex_spinner,null);
                final Spinner personSexEdited = dialogView.findViewById(R.id.person_sex_edited);
                String[] sexes = getResources().getStringArray(R.array.userSex);
                personSexEdited.setAdapter(new ArrayAdapter<>(PersonInfoActivity.this, R.layout.support_simple_spinner_dropdown_item,sexes));
                builder.setView(dialogView);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sex = personSexEdited.getSelectedItem().toString();
                        if(!sex.equals("") && !sex.equals(personSex.getText().toString())){
                            personSex.setText(sex);
                            //update userSex
                            new UpdateTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userId,"sex",sex);
                        }

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        personAgeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View  dialogView = LayoutInflater.from(PersonInfoActivity.this).inflate(R.layout.person_age_edit,null);
                final EditText personAgeEdited = dialogView.findViewById(R.id.person_age_edited);
                new AlertDialog.Builder(PersonInfoActivity.this).setView(dialogView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String age = personAgeEdited.getText().toString().trim();
                                if(!age.equals("") && !age.equals(personAge.getText().toString())){
                                    personAge.setText(age);
                                    //update userAge
                                    new UpdateTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userId,"age",age);
                                }

                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
        personSchoolEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonInfoActivity.this);
                View  dialogView = LayoutInflater.from(PersonInfoActivity.this).inflate(R.layout.person_school_spinner,null);
                final Spinner personSchoolEdited = dialogView.findViewById(R.id.person_school_edited);
                String[] schools = getResources().getStringArray(R.array.userSchool);
                personSchoolEdited.setAdapter(new ArrayAdapter<>(PersonInfoActivity.this, R.layout.support_simple_spinner_dropdown_item,schools));
                builder.setView(dialogView);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String school = personSchoolEdited.getSelectedItem().toString();
                        if(!school.equals(personSchool.getText().toString()) && !school.equals("")){
                            personSchool.setText(school);
                            //update userSchool
                            new UpdateTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userId,"school",school);
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        personLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("logInfo",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLogin",false);
                editor.apply();
                finish();
            }
        });
    }

    private void init(){
        mGlide = Glide.with(this);
        //init toolbar
        toolbar.setTitle("个人资料");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sp = getSharedPreferences("logInfo",MODE_PRIVATE);
        //set data
        userId = sp.getString("userId","");
        new getUserTask().execute(userId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            ArrayList<Photo> resultPhotos = data.getParcelableArrayListExtra(EasyPhotos.RESULT_PHOTOS);
            photo = resultPhotos.get(0);
            mGlide.load(photo.uri).into(personImg);
            //update userImg
            new UpdateTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,userId,"img",photo.uri.toString());
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("isChanged",true);
            editor.apply();
        }
    }

    class getUserTask extends AsyncTask<String,Object,Map<String,String>>{
        @Override
        protected Map<String,String> doInBackground(String... params) {
            String result = Connection.getUserInfo("getUser",params[0]);
            Gson gson = new Gson();
            return gson.fromJson(result,new TypeToken<Map<String, String>>() {}.getType());
        }

        @Override
        protected void onPostExecute(Map<String, String> map) {
            super.onPostExecute(map);
            if(map!=null){
                String img = map.get("img");
                if(img.equals("")) personImg.setImageResource(R.drawable.user);
                else Glide.with(PersonInfoActivity.this).load(img).into(personImg);
                personName.setText(map.get("name"));
                personSex.setText(map.get("sex"));
                personAge.setText(map.get("age"));
                personSchool.setText(map.get("school"));
                personCredit.setText(map.get("credit"));
            }
        }
    }

    class UpdateTask extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... params) {
            String result = Connection.updateUser(params[0],params[1],params[2]);
            System.out.println(result);
            return null;
        }
    }

}
