package cn.cslg.snowymoney.module;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import cn.cslg.snowymoney.R;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.push.RongPushClient;

public class ChatActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
    }

    private void init(){
        String targetId = getIntent().getData().getQueryParameter("targetId");
        String title = getIntent().getData().getQueryParameter("title");
        toolbar = findViewById(R.id.chat_toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentManager fragmentManage = getSupportFragmentManager();
        ConversationFragment fragement = (ConversationFragment) fragmentManage.findFragmentById(R.id.chat);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(RongPushClient.ConversationType.PRIVATE.getName().toLowerCase())
                .appendQueryParameter("targetId", targetId)
                .appendQueryParameter("title",title).build();

        fragement.setUri(uri);
    }
}
