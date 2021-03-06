package com.bridgesafe.bridge.ui.login;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bridgesafe.bridge.R;
import com.bridgesafe.bridge.ui.MainActivity;
import com.bridgesafe.bridge.ui.base.BaseActivity;
import com.elvishew.xlog.XLog;
import com.unistrong.yang.zb_permission.ZbPermission;

public class WelComeActivity extends BaseActivity {
    private View view;
    private Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_wel_come);
        view = View.inflate(this, R.layout.activity_wel_come, null);
        setContentView(view);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        animation = AnimationUtils.loadAnimation(this, R.anim.splash_alpha);

        initUI();
    }

    private void initUI() {
        view.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }   //在动画开始时使用

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }  //在动画重复时使用

            @Override
            public void onAnimationEnd(Animation arg0) {
                // toActivityFinish(LoginActivity.class);//
                Bundle bundle = new Bundle();
                bundle.putString("isLogin", "0");
                toActivity(LoginActivity.class, bundle);
                finish();

            }
        });
    }
}
