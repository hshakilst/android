package com.example.hshakilst.pubnubchat;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.history.PNHistoryItemResult;
import com.pubnub.api.models.consumer.history.PNHistoryResult;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private PubNub pubnub;
    private String userName;
    private ImageView btn;
    private EditText msgText;
    private String channel = "qwertyuiop";
    private SubscribeCallback subscribeCallback;
    private CoordinatorLayout coordinatorLayout;
    private MessageAdapter adapter;
    private List<Message> messages;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            userName = user.getDisplayName();
        }
        messages = new ArrayList<Message>();
        listView = (ListView) findViewById(R.id.chat_list_view);
        btn = (ImageView) findViewById(R.id.msg_send_btn);
        msgText = (EditText) findViewById(R.id.msg_edit_text);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_main_coordinator_layout);
        progressBar = (ProgressBar) findViewById(R.id.load_msg_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        pubnub = new PubNub(new PNConfiguration()
                .setPublishKey("")
                .setSubscribeKey("")
                .setSecure(true)
                .setUuid(userName));
        adapter = new MessageAdapter(getBaseContext(), R.layout.right_custom_list, messages);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
        pubnub.history().channel(channel).count(100).async(new PNCallback<PNHistoryResult>() {
            @Override
            public void onResponse(PNHistoryResult result, PNStatus status) {
                if(!status.isError()){
                    List<PNHistoryItemResult> historyItemResults =  result.getMessages();
                    for (PNHistoryItemResult res : historyItemResults){
                        JsonElement element = res.getEntry();
                        Gson gson = new Gson();
                        Message msg = gson.fromJson(element, Message.class);
                        if(!msg.getUserName().equals(userName)){
                            msg.setIsMine(false);
                        }
                        adapter.add(msg);
                    }
                }
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                    }
                });
            }


        });

        subscribeCallback = new SubscribeCallback() {
            @Override
            public void status(PubNub pubnub, final PNStatus status) {
                if (status.getCategory() == PNStatusCategory.PNDisconnectedCategory
                        || status.getStatusCode() == 0){
                    Snackbar.make(coordinatorLayout, "Connection failed! Code:"+status.getStatusCode(),
                            Snackbar.LENGTH_INDEFINITE).setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            status.retry();
                        }
                    }).show();
                }
                else if (status.getCategory() == PNStatusCategory.PNConnectedCategory ||
                        status.getCategory() == PNStatusCategory.PNReconnectedCategory){
                    Snackbar.make(coordinatorLayout, "Connection established!",
                            Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void message(PubNub pubnub, PNMessageResult message) {
                JsonElement jsonElement = message.getMessage();
                Gson gson = new Gson();
                final Message msg = gson.fromJson(jsonElement, Message.class);
                if(!msg.getUserName().equals(userName)) {
                    msg.setIsMine(false);
                }
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.add(msg);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {

            }
        };
        pubnub.addListener(subscribeCallback);
        pubnub.subscribe().channels(Arrays.asList(channel)).execute();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(msgText.getText().toString().equals("")){
                    Snackbar.make(coordinatorLayout, "Please write your message to send!",
                            Snackbar.LENGTH_LONG).show();
                }
                else{
                    final Message msg = new Message(userName, msgText.getText().toString(), true);
                    pubnub.publish().channel(channel).message(msg).shouldStore(true).async(new PNCallback<PNPublishResult>() {
                        @Override
                        public void onResponse(PNPublishResult result, final PNStatus status) {
                            if (!status.isError()){
                                Snackbar.make(coordinatorLayout, "Message delivered! Code:"+
                                        status.getStatusCode(), 1000).show();
                            }
                            else {
                                Snackbar.make(coordinatorLayout, "Connection failed! Code:"+
                                        status.getStatusCode(), Snackbar.LENGTH_INDEFINITE)
                                        .setAction("RETRY", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                status.retry();
                                            }
                                        }).show();
                            }
                        }
                    });
                }
                msgText.setText("");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser == null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(this.pubnub != null){
            this.pubnub.unsubscribe().channels(Arrays.asList(channel)).execute();
            this.pubnub.removeListener(subscribeCallback);
            this.pubnub.destroy();
            this.pubnub = null;
        }
    }
}
