package com.bridgesafe.bridge.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bridgesafe.bridge.R;
import com.bridgesafe.bridge.util.AppManager;
import com.bridgesafe.bridge.util.CustomDialog;
import com.bridgesafe.bridge.util.StatusBarUtil;
import com.bridgesafe.bridge.util.ToastUtils;
import com.bridgesafe.bridge.view.IconfontTextView;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/9/11.
 */

public class BaseActivity extends AppCompatActivity {
    protected final static int DATA_LOAD_ING = 0x001;
    protected final static int DATA_LOAD_COMPLETE = 0x002;
    protected final static int DATA_LOAD_FAIL = 0x003;

    public static Handler handler = new Handler();
    public String theme_bg_tex;

    /**
     * 上下文 当进入activity时必须 mContext = this 方可使用，否则会报空指针
     */
    public Context mContext;

    /**
     * 加载等待效果
     */
    public ProgressDialog progress;
    protected String ukey;
    protected String easemob_id;
    protected String isvip;
    private CustomDialog dialog;
    public String openid;
    public String lat, lng;

    /**
     * 初始化创建
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;
        AppManager.getAppManager().addActivity(this);
        int color = getResources().getColor(R.color.white);
        StatusBarUtil.setColor(this, color, 0);
        StatusBarUtil.setLightMode(this);
        // RxBus.get().register(this);
        //  ukey = UtilPreference.getStringValue(this, "ukey");

    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
    }

    /**
     * 重回前台显示调用
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Activity销毁，关闭加载效果
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // RxBus.get().unregister(this);
    }


    /**
     * Activity暂停，关闭加载效果
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (progress != null) {
            progress.dismiss();
        }
    }

    /**
     * Activity停止，关闭加载效果
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (progress != null) {
            progress.dismiss();
        }
    }

    /**
     * 设置显示右侧返回按钮
     */
    public void setBackView() {
        View backView = findViewById(R.id.back_view);
        if (backView == null) {
            return;
        }
        backView.setVisibility(View.VISIBLE);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 设置显示标题
     *
     * @param txt
     */
    public void setTitle(String txt, int color) {
        TextView title = (TextView) findViewById(R.id.title);
        if (title == null) {
            return;
        }
        title.setVisibility(View.VISIBLE);
        title.setText(txt);
        title.setTextColor(color);
    }

    /**
     * 只显示右侧文字以及点击事件
     *
     * @param txt
     * @param onClickListener
     */
    public void setRightText(String txt, View.OnClickListener onClickListener) {
        TextView toolbar_righ_tv = (TextView) findViewById(R.id.toolbar_righ_tv);
        if (toolbar_righ_tv == null) {
            return;
        }
        ImageView toolbar_righ_iv = (ImageView) findViewById(R.id.toolbar_righ_iv);
        if (toolbar_righ_iv == null) {
            return;
        }
        toolbar_righ_iv.setVisibility(View.GONE);
        toolbar_righ_tv.setVisibility(View.VISIBLE);
        toolbar_righ_tv.setText(txt);
        toolbar_righ_tv.setOnClickListener(onClickListener);
    }

//    public void setRightText02(String txt, View.OnClickListener onClickListener) {
//        IconfontTextView toolbar_righ_tv = (IconfontTextView) findViewById(R.id.toolbar_righ_tv);
//        if (toolbar_righ_tv == null) {
//            return;
//        }
//        ImageView toolbar_righ_iv = (ImageView) findViewById(R.id.toolbar_righ_iv);
//        if (toolbar_righ_iv == null) {
//            return;
//        }
//        toolbar_righ_iv.setVisibility(View.GONE);
//        toolbar_righ_tv.setVisibility(View.VISIBLE);
//        toolbar_righ_tv.setText(txt);
//        toolbar_righ_tv.setOnClickListener(onClickListener);
//    }

    /**
     * 右侧只显示一个图片
     *
     * @param resId
     * @param onClickListener
     */
    public void setRightImage(int resId, View.OnClickListener onClickListener) {
        TextView toolbar_righ_tv = (TextView) findViewById(R.id.toolbar_righ_tv);
        if (toolbar_righ_tv == null) {
            return;
        }
        toolbar_righ_tv.setVisibility(View.GONE);
        ImageView toolbar_righ_iv = (ImageView) findViewById(R.id.toolbar_righ_iv);
        if (toolbar_righ_iv == null) {
            return;
        }
        toolbar_righ_iv.setVisibility(View.VISIBLE);
        toolbar_righ_iv.setImageResource(resId);
        toolbar_righ_iv.setOnClickListener(onClickListener);
    }


    /**
     * 显示文字和图片，可以设置文字内容及字体颜色，图片资源
     *
     * @param txt
     * @param txtColor
     * @param resId
     * @param onClickListener
     */
    public void setRightTextAndImage(String txt, int txtColor, int resId, View.OnClickListener onClickListener) {
        TextView toolbar_righ_tv = (TextView) findViewById(R.id.toolbar_righ_tv);
        if (toolbar_righ_tv == null) {
            return;
        }
        toolbar_righ_tv.setVisibility(View.VISIBLE);
        toolbar_righ_tv.setTextColor(txtColor);

        ImageView toolbar_righ_iv = (ImageView) findViewById(R.id.toolbar_righ_iv);
        if (toolbar_righ_iv == null) {
            return;
        }
        toolbar_righ_iv.setVisibility(View.VISIBLE);
        toolbar_righ_iv.setImageResource(resId);

        toolbar_righ_iv.setOnClickListener(onClickListener);
        toolbar_righ_tv.setOnClickListener(onClickListener);
    }

    /**
     * 触发手机返回按钮
     */
    @Override
    public void onBackPressed() {
        AppManager.getAppManager().finishActivity();
    }


    /**
     * 显示字符串消息
     *
     * @param message
     */
//    public void showProgress(String message) {
//        if (progress != null) {
//            progress.dismiss();
//        }
//        progress = new ProgressDialog(BaseActivity.this);
//        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progress.setMessage(message);
//        progress.setCanceledOnTouchOutside(false);
//        progress.setCancelable(true);
//        progress.show();
//    }

    /**
     * 显示字符串消息
     *
     * @param message
     */
    public void showProgress(String message) {
        // dialog = new CustomDialog(getActivity());
        dialog = new CustomDialog(this).builder()
                .setGravity(Gravity.CENTER).setTitle01("提示", getResources().getColor(R.color.sd_color_black))//可以不设置标题颜色，默认系统颜色
                .setSubTitle(message);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1000);
    }

    /**
     * 隐藏字符串消息
     */
    public void disShowProgress() {
        if (progress != null) {
            progress.dismiss();
        }
    }
//
//    public void showProgress01(String message) {
//        // dialog = new CustomDialog(getActivity());
//        dialog = new CustomDialog(this).builder()
//                .setGravity(Gravity.CENTER).setTitle("提示", getResources().getColor(R.color.sd_color_black))//可以不设置标题颜色，默认系统颜色
//                .setSubTitle(message);
//        dialog.show();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                dialog.dismiss();
//                toActivityFinish(Login_RegisterActivity.class);
//                MyActivityManager.getInstance().finishAllActivity();
//            }
//        }, 1000);
//
//    }

    /**
     * 提示信息
     */
    public void showErrorMsg(String message) {
        final String str = message;
        BaseActivity.handler.post(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(), str,
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    /**
     * 提示信息号或请求失败信息
     * <p>
     * showErrorRequestFair
     */
    public void showE404() {
        BaseActivity.handler.post(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "手机信号差或服务器维护，请稍候重试。谢谢！", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 提示信息
     */
    public void showMsgAndDisProgress(String message) {
        final String str = message;
        BaseActivity.handler.post(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(), str,
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                disShowProgress();
            }
        });
    }

    public <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }

    /**
     * 文本View
     */
    public TextView textView(int textview) {
        return (TextView) findViewById(textview);
    }

    /**
     * 文本button
     */
    public Button button(int id) {
        return (Button) findViewById(id);
    }

    /**
     * 文本button
     */
    public ImageView imageView(int id) {
        return (ImageView) findViewById(id);
    }

    /**
     * 文本editText
     */
    public EditText editText(int id) {
        return (EditText) findViewById(id);
    }

    /**
     * 显示数据加载状态
     *
     * @param loading
     * @param datas
     * @param type
     */
    public void viewSwitch(View loading, View datas, int type) {
        switch (type) {
            case DATA_LOAD_ING:
                datas.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                break;
            case DATA_LOAD_COMPLETE:
                datas.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                break;
            case DATA_LOAD_FAIL:
                datas.setVisibility(View.GONE);
                loading.setVisibility(View.GONE);
                break;
        }
    }

    protected void toFinish() {
        finish();
    }

    public void toActivityFinish(Class activity) {
        Intent intent = new Intent(mContext, activity);
        startActivity(intent);
        toFinish();
    }

    public void toActivity(Class activity) {
        Intent intent = new Intent(mContext, activity);
        startActivity(intent);
    }

    public void toActivity(Class activity, Bundle bundle) {
        Intent intent = new Intent(mContext, activity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void showToast(String msg) {
        ToastUtils.showToast(mContext, msg);
    }

//    public void showShare(String url) {
//        Resources res = mContext.getResources();
//        Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher);
//        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//
//        // title标题，微信、QQ和QQ空间等平台使用
//        oks.setTitle("测试分享");
//        // titleUrl QQ和QQ空间跳转链接
//        oks.setTitleUrl("http://sharesdk.cn");
//        // text是分享文本，所有平台都需要这个字段
//        oks.setText("我是分享文本");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        // oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//        oks.setImageData(bmp);
//        // url在微信、微博，Facebook等平台中使用
//        oks.setUrl("http://sharesdk.cn");
//        // comment是我对这条分享的评论，仅在人人网使用
//        oks.setComment("我是测试评论文本");
//        // 启动分享GUI
//        oks.show(mContext);
//    }
}