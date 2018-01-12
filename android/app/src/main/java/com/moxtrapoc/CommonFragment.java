package com.moxtrapoc;


import android.support.v4.app.Fragment;

/**
 * Created by celestial on 11/01/18.
 */

public class CommonFragment {
    public static Fragment chatFragment;
    public static void setChatFragment(Fragment chat){
        chatFragment = chat;
    }
    public static Fragment getChatFragment(){
        return chatFragment;
    }
}
