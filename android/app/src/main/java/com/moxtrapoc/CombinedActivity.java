package com.moxtrapoc;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.ReactMethod;
import com.facebook.soloader.SoLoader;
import com.moxtra.sdk.ChatClient;
import com.moxtra.sdk.chat.controller.ChatController;
import com.moxtra.sdk.chat.model.Chat;
import com.moxtra.sdk.chat.repo.ChatRepo;
import com.moxtra.sdk.client.ChatClientDelegate;
import com.moxtra.sdk.common.ApiCallback;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by celestial on 12/12/17.
 */

public class CombinedActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "jHr8ZrYTuHI";
    private static final String CLIENT_SECRET = "liJO5EpHvrM";
    private static final String ORG_ID = null;
    private static final String BASE_DOMAIN = "sandbox.moxtra.com";
    private static final String KEY_TOPIC = "Jitesh's chat";

    private static final String TAG = "ChatActivity";
    private final Handler mHandler = new Handler();
    private ChatClientDelegate mChatClientDelegate;
    private ChatController mChatController;
    private ChatRepo mChatRepo;
    private Chat mChat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.combine_layout);
//        Button mButton = (Button) findViewById(R.id.HomeBtn);
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//            }
//        });
        SoLoader.init(this, /* native exopackage */ false);
        linkWithUniqueID("amy@example.com");
    }

    public void linkWithUniqueID(final String uniqueId) {
        Log.d(TAG, "Start to linkWithUniqueID...");
        Toast.makeText(getApplicationContext(), "initializing", Toast.LENGTH_LONG).show();
        ChatClient.linkWithUniqueId(uniqueId, CLIENT_ID, CLIENT_SECRET, ORG_ID, BASE_DOMAIN,
                new ApiCallback<ChatClientDelegate>() {
                    @Override
                    public void onCompleted(ChatClientDelegate ccd) {
                        Log.i(TAG, "Linked to Moxtra account successfully.");
                        startChatListActivity();
                    }

                    @Override
                    public void onError(int errorCode, String errorMsg) {
                        Toast.makeText(getApplicationContext(), "Failed to link to Moxtra account.", Toast.LENGTH_LONG).show();
                        Log.e(TAG, "Failed to link to Moxtra account, errorCode=" + errorCode + ", errorMsg=" + errorMsg);
                    }
                }
        );
    }


    public void startChatListActivity() {
        Log.i(TAG, "startChatListActivity");
        Toast.makeText(getApplicationContext(), "Done!!!", Toast.LENGTH_LONG).show();
        mChatClientDelegate = ChatClient.getClientDelegate();
        if (mChatClientDelegate == null) {
            Log.e(TAG, "Unlinked, ChatClient is null.");
        }
        startChat();
    }


    public void startChat() {
        final List<String> uniqueIdList = new ArrayList<>();
        uniqueIdList.add("bob@example.com");
        mChatRepo = mChatClientDelegate.createChatRepo();
        List chatList = mChatRepo.getList();
        Log.d(TAG, ""+chatList);
        mChatRepo.createGroupChat(KEY_TOPIC, new ApiCallback<Chat>() {
            @Override
            public void onCompleted(Chat chat) {
                Log.i(TAG, "Create group chat successfully.");
                mChat = chat;
                mChat.getChatDetail().inviteMembers(ORG_ID, uniqueIdList, new ApiCallback<Void>() {
                    @Override
                    public void onCompleted(Void result) {
                        Log.i(TAG, "Invite members successfully.");
                        Toast.makeText(getApplicationContext(), "Invite members successfully!!!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(int errorCode, String errorMsg) {
                        Log.e(TAG, "Failed to invite members, errorCode=" + errorCode + ", errorMsg=" + errorMsg);
                        Toast.makeText(getApplicationContext(), "Failed to Invite members!!!", Toast.LENGTH_LONG).show();
                    }
                });
                showChatFragment();
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                Log.e(TAG, "Failed to create group chat, errorCode=" + errorCode + ", errorMsg=" + errorMsg);
            }
        });
    }

    private void showChatFragment() {
        mChatController = mChatClientDelegate.createChatController(mChat);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Fragment fragment = mChatController.createChatFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.chatUI_frame, fragment).commit();
                ReactFragment reactFragment = new ReactFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.menu_frame, reactFragment).commit();
            }
        });
    }
}
