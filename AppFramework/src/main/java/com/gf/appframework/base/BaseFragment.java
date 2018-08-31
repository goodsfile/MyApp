package com.gf.appframework.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gf.appframework.fragmentation.SupportFragment;

/**
 * @author: goodsfile
 * @date: 2018/8/31
 * @desc:
 * @version:
 * @update:
 */
public abstract class BaseFragment extends SupportFragment {
    protected Activity mCurrentAttachActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mCurrentAttachActivity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(getContentViewResId(), container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
    }

    /**
     * 获取contentView资源id
     * @return
     */
    protected abstract int getContentViewResId();

    /**
     * 进行初始化操作
     */
    protected abstract void init();

    /**
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 跳转之后不带返回值
     *
     * @param targetClass
     * @param data
     */
    public void startActivity(Class<?> targetClass, Bundle data) {
        Intent intent = new Intent(mCurrentAttachActivity, targetClass);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivity(intent);
    }


}
