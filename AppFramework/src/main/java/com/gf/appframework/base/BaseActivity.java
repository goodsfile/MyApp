package com.gf.appframework.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gf.appframework.fragmentation.SupportActivity;

/**
 * @author: goodsfile
 * @date: 2018/8/31
 * @desc:
 * @version:
 * @update:
 */
public abstract class BaseActivity extends SupportActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResId());
        init();
        initListener();
    }


    /**
     * 获取contentView资源id
     * @return
     */
    protected abstract int getContentViewResId();


    /**
     * 初始化操作
     */
    protected abstract void init();



    /**
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 跳转不带返回值
     *
     * @param targetClass
     * @param data
     */
    public void startActivity(Class<?> targetClass, Bundle data) {
        Intent intent = new Intent(this, targetClass);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivity(intent);
    }

    /**
     * 跳转之后带有返回值
     *
     * @param targetClass
     * @param data
     * @param requestCode
     */
    public void startActivityForResult(Class<?> targetClass, Bundle data, int requestCode) {
        Intent intent = new Intent(this, targetClass);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivityForResult(intent, requestCode);
    }

}
