package com.xjtu.friendtrip.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.activity.LoginActivity;

import butterknife.ButterKnife;

/**
 * Created by Ming on 2016/5/25.
 */
public class MeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_me,container,false);
        ButterKnife.bind(this,view);

        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);

        return view;
    }

}
