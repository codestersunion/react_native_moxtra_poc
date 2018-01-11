package com.moxtrapoc;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.facebook.react.ReactFragmentActivity;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.moxtra.sdk.ChatClient;
import com.moxtra.sdk.chat.controller.ChatController;
import com.moxtra.sdk.chat.model.Chat;
import com.moxtra.sdk.chat.repo.ChatRepo;
import com.moxtra.sdk.client.ChatClientDelegate;
import com.moxtra.sdk.common.ApiCallback;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by aaronhaser on 1/10/18.
 */

public class MoxtraChatManager extends SimpleViewManager<FrameLayout> {

    private static final String CLIENT_ID = "jHr8ZrYTuHI";
    private static final String CLIENT_SECRET = "liJO5EpHvrM";
    private static final String ORG_ID = null;
    private static final String BASE_DOMAIN = "sandbox.moxtra.com";
    private static final String KEY_TOPIC = "Jitesh's chat";

    private static final String TAG = "ChatActivity";
    private static ChatClientDelegate mChatClientDelegate;
    private static ChatController mChatController;
    private static ChatRepo mChatRepo;
    private static Chat mChat;

    @Override
    public String getName() {
        return "MoxtraChatView";
    }

    @Override
    protected FrameLayout createViewInstance(ThemedReactContext reactContext) {
        linkWithUniqueID("amy@example.com", new Promise() {
            @Override
            public void resolve(@Nullable Object value) {

            }

            @Override
            public void reject(String code, String message) {

            }

            @Override
            public void reject(String code, Throwable e) {

            }

            @Override
            public void reject(String code, String message, Throwable e) {

            }

            @Override
            public void reject(String message) {

            }

            @Override
            public void reject(Throwable reason) {

            }
        });
        FrameLayout frameLayout = new FrameLayout(reactContext);
        Fragment fragment = mChatController.createChatFragment();
        ((ReactFragmentActivity) reactContext.getCurrentActivity())
                .getSupportFragmentManager()
                .beginTransaction()
                .add(frameLayout.getId(), fragment).commit();
        return frameLayout;
    }


    private void linkWithUniqueID(final String uniqueId, final Promise promise) {
        Log.d(TAG, "Start to linkWithUniqueID...");
        ChatClient.linkWithUniqueId(uniqueId, CLIENT_ID, CLIENT_SECRET, ORG_ID, BASE_DOMAIN,
                new ApiCallback<ChatClientDelegate>() {
                    @Override
                    public void onCompleted(ChatClientDelegate ccd) {
                        Log.i(TAG, "Linked to Moxtra account successfully.");
                        startChatListActivity(promise);
                    }

                    @Override
                    public void onError(int errorCode, String errorMsg) {
                        Log.e(TAG, "Failed to link to Moxtra account, errorCode=" + errorCode + ", errorMsg=" + errorMsg);
                    }
                }
        );
    }


    private void startChatListActivity(Promise promise) {
        Log.i(TAG, "startChatListActivity");
        mChatClientDelegate = ChatClient.getClientDelegate();
        if (mChatClientDelegate == null) {
            Log.e(TAG, "Unlinked, ChatClient is null.");
        }
        startChat(promise);
    }


    private void startChat(final Promise promise) {
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
                    }

                    @Override
                    public void onError(int errorCode, String errorMsg) {
                        Log.e(TAG, "Failed to invite members, errorCode=" + errorCode + ", errorMsg=" + errorMsg);
                    }
                });
                showChatFragment(promise);
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                Log.e(TAG, "Failed to create group chat, errorCode=" + errorCode + ", errorMsg=" + errorMsg);
            }
        });
    }

    private void showChatFragment(final Promise promise) {
        mChatController = mChatClientDelegate.createChatController(mChat);
        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                android.support.v4.app.Fragment fragment = mChatController.createChatFragment();
//                View view = fragment.getView();
                //its getting null here
//                promise.resolve(view);
            }
        });
    }
}
