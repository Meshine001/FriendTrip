package com.xjtu.friendtrip.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.easyandroidanimations.library.BounceAnimation;
import com.xjtu.friendtrip.Net.Mob;
import com.xjtu.friendtrip.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class LoginActivity extends BaseActivity {


    private static final String TAG = LoginActivity.class.getName() ;

    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.register_layout)
    LinearLayout registerLayout;


    @BindView(R.id.login_scroll_view)
    ScrollView loginScrollView;

    boolean isRegitering = false;

    SweetAlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initToolbar("登录");
    }

    @OnClick({R.id.register,R.id.register_submit})
    void onClick(View view){
        switch(view.getId()){
            case R.id.register:
                showRegister(true);
                break;
            case R.id.register_submit:
                if (!validate())return;
                registerUser(username.getText().toString().trim(),password.getText().toString().trim());
                //openSMSPage();
                break;
        }
    }

    void showRegister(boolean flag){
        if (flag){
            loginScrollView.setVisibility(View.GONE);
            registerLayout.setVisibility(View.VISIBLE);
            new BounceAnimation(registerLayout).animate();
            isRegitering = true;
        }else {
            loginScrollView.setVisibility(View.VISIBLE);
            registerLayout.setVisibility(View.GONE);
            new BounceAnimation(loginScrollView).animate();
            isRegitering = false;
        }
    }

    @Override
    public void onBackPressed() {
        if (isRegitering){
            showRegister(false);
            return;
        }
        super.onBackPressed();
    }

    boolean validate(){
        dialog  = new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE);
        dialog.setTitleText("验证错误！");
        if (username.getText().length() == 0){
            dialog.setContentText("请输入用户名");
            dialog.show();
            return false;
        }
        if (password.getText().length() == 0){
            dialog.setContentText("请输入密码");
            dialog.show();
            return false;
        }


        return true;
    }

    void openSMSPage(){
        SMSSDK.initSDK(this, Mob.APP_KEY, Mob.APP_SECRET);
        //打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
        // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");

                     // 提交用户信息
                  //  registerUser(country, phone);
                }
            }
        });
        registerPage.show(this);
    }

    private void registerUser(String username, String password) {
        dialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setTitleText("注册中,请稍后...");
        dialog.setCancelable(false);
        dialog.show();
        Log.i(TAG,"[");
    }
}
