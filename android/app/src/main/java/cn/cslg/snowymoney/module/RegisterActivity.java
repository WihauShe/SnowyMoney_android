package cn.cslg.snowymoney.module;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import cn.cslg.snowymoney.R;
import cn.cslg.snowymoney.dao.Connection;


public class RegisterActivity extends AppCompatActivity {
    private EditText userAccount,userPass,userName,userAge;
    private Spinner userSex,userSchool;
    private Button cancel,save,reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        setListeners();
        init();
    }
    private void findViews(){
        userAccount = findViewById(R.id.user_account);
        userPass = findViewById(R.id.user_password);
        userName = findViewById(R.id.user_name);
        userAge = findViewById(R.id.user_age);
        userSex = findViewById(R.id.user_sex);
        userSchool = findViewById(R.id.user_school);
        cancel = findViewById(R.id.cancel_btn);
        save = findViewById(R.id.submit_btn);
        reset = findViewById(R.id.reset_btn);
    }
    private void setListeners(){
        //cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //save button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = userAccount.getText().toString();
                String password = userPass.getText().toString();
                String name = userName.getText().toString();
                String sex = userSex.getSelectedItem().toString();
                String age = userAge.getText().toString();
                String school = userSchool.getSelectedItem().toString();
                //post user to server
                new RegisterTask().execute(id,name,password,sex,age,school);
            }
        });
        //reset button
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAccount.setText("");
                userPass.setText("");
                userName.setText("");
                userAge.setText("");
                userSex.setSelection(0);
                userSchool.setSelection(0);
            }
        });
    }

    private void init(){
        String[] sexes = getResources().getStringArray(R.array.userSex);
        userSex.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item,sexes));
        String[] schools = getResources().getStringArray(R.array.userSchool);
        userSchool.setAdapter(new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,schools));
    }

    class RegisterTask extends AsyncTask<String,Void,Boolean>{
        @Override
        protected Boolean doInBackground(String... params) {
            String result = Connection.addUser(params[0],params[1],params[2],params[3],params[4],params[5]);
            if(result.equals("true")){
                SharedPreferences sharedPreferences = getSharedPreferences("logInfo",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLogin",true);
                editor.putString("userId",params[0]);
                editor.putString("userImg","");
                editor.putString("userName",params[1]);
                editor.putString("userBalance","20");
                Date date = new Date();
                editor.putLong("lastLogin",date.getTime());
                editor.apply();
                setResult(1);
                finish();
            }
            return null;
        }
    }
}
