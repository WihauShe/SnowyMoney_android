package cn.cslg.snowymoney.module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.cslg.snowymoney.R;
import cn.cslg.snowymoney.dao.Connection;
import cn.cslg.snowymoney.entity.AuthResult;
import cn.cslg.snowymoney.entity.Money;
import cn.cslg.snowymoney.entity.PayResult;
import cn.cslg.snowymoney.view.ChargeAdapter;
import cn.cslg.snowymoney.view.MoneyInputListener;
import cn.cslg.snowymoney.view.ToastUtil;

public class ChargeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView chargeList;
    private TextView moneyTotalText;
    private RadioButton aliPay,weChat;
    private Button chargeBtn;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private String orderIngfo = "";
    private String tradeNo = "";
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);

                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Gson gson = new Gson();
                        Map<String,Object> result = gson.fromJson(resultInfo,new TypeToken<Map<String,Object>>(){}.getType());
                        Map<String,String> tradeInfo = (Map<String, String>) result.get("alipay_trade_app_pay_response");
                        tradeNo = tradeInfo.get("out_trade_no");
                        System.out.println(tradeNo);
                        new ResultTask().execute(tradeNo);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtil.show(ChargeActivity.this,"支付失败");
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        ToastUtil.show(ChargeActivity.this, "授权成功");
                    } else {
                        // 其他状态值则为授权失败
                        ToastUtil.show(ChargeActivity.this, "授权失败");
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);
        findViews();
        init();
    }

    private void findViews(){
        toolbar = findViewById(R.id.charge_toolbar);
        moneyTotalText = findViewById(R.id.money_total_text);
        chargeList =  findViewById(R.id.charge_list);
        aliPay = findViewById(R.id.ali_pay);
        weChat = findViewById(R.id.we_chat);
        chargeBtn = findViewById(R.id.charge_btn);
    }

    private void init(){

        toolbar.setTitle("充值页面");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //防止recycleview闪烁
        ((SimpleItemAnimator) chargeList.getItemAnimator()).setSupportsChangeAnimations(false);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        List<Money> data = new ArrayList<>();
        data.add(new Money("5元", false));
        data.add(new Money("10元", false));
        data.add(new Money("30元", false));
        data.add(new Money("50元", false));
        data.add(new Money("100元", false));
        ChargeAdapter adapter = new ChargeAdapter(data, this);
        adapter.setMoneyInputListener(new MoneyInputListener() {
            @Override
            public void onGetMoneyInput(String money) {
                moneyTotalText.setText(money);
            }
        });
        chargeList.setLayoutManager(manager);
        chargeList.setAdapter(adapter);

        chargeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aliPay.isChecked()){
                    SharedPreferences sp = getSharedPreferences("logInfo",MODE_PRIVATE);
                    String userId = sp.getString("userId","");
                    String tmp = moneyTotalText.getText().toString().trim();
                    String total = tmp.substring(0,tmp.length()-1);
                    new OrderTask().execute(userId,total);
                }
            }
        });
    }

    class OrderTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... params) {
            return  Connection.getOrder(params[0],params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
            orderIngfo = result;
            Runnable payRunnable = new Runnable() {
                @Override
                public void run() {
                    PayTask alipay = new PayTask(ChargeActivity.this);
                    Map <String,String> result = alipay.payV2(orderIngfo,true);
                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;
                    System.out.println(msg.obj.toString());
                    mHandler.sendMessage(msg);
                }
            };
            // 异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }
    }

    class ResultTask extends AsyncTask<String,Void,Boolean>{
        @Override
        protected Boolean doInBackground(String... params) {
            String result = Connection.sendResult(params[0]);
            return result.equals("true");
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean) {
                SharedPreferences sp = getSharedPreferences("logInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isChanged",true);
                editor.apply();
                ToastUtil.show(ChargeActivity.this, "充值成功");
            }
            else ToastUtil.show(ChargeActivity.this, "充值失败");
        }
    }
}
