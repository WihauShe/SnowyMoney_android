package cn.cslg.snowymoney;

import android.app.Application;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this,"pvxdm17jpehwr");
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String userId) {
                return null;
            }
        }, true);
    }
}
