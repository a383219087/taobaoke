package com.starnet.cqj.taobaoke.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/11/02.
 */

public abstract class BaseFragment extends RxFragment {


    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContentRes(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    abstract int getContentRes();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    public void toast(String res) {
        Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
    }

    public void toast(@StringRes int res) {
        Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
    }
}
