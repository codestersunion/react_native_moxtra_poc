package com.moxtrapoc;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


interface EmptyFragment {
    View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState);
}