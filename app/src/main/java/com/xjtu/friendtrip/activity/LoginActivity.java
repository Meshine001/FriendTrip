package com.xjtu.friendtrip.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.easyandroidanimations.library.BounceAnimation;
import com.easyandroidanimations.library.FoldAnimation;
import com.xjtu.friendtrip.Net.Mob;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.bean.Text;
import com.xjtu.friendtrip.util.MyStringRandomGen;
import com.xjtu.friendtrip.util.PrefUtils;
import com.xjtu.friendtrip.util.StoreBox;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class LoginActivity extends BaseActivity {


    private static final String TAG = LoginActivity.class.getName();

    public static final int REQUEST_LOGIN = 0;
    public static final boolean LOGIN_SUCCESS = true;


    private static final int VALIDATE_COUNT_DOWN = 1;


    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.register_layout)
    LinearLayout registerLayout;


    @BindView(R.id.login_scroll_view)
    ScrollView loginScrollView;

    boolean isRegitering = false;

    @BindView(R.id.phone_number)
    EditText phoneNumber;

    @BindView(R.id.validate_code)
    EditText validateCode;

    @BindView(R.id.get_validate_code)
    TextView getValidateCode;

    @BindView(R.id.set_password_layout)
    LinearLayout setPasswordLayout;
    @BindView(R.id.set_password)
    EditText setPassword;
    @BindView(R.id.set_password_again)
    EditText setPasswordAgain;


    SweetAlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initToolbar("登录");
        initDialog(this);
        username.setText(PrefUtils.getStringPreference(this,"username"));
    }


    @OnClick({R.id.register, R.id.get_validate_code, R.id.register_submit, R.id.login, R.id.set_password_submit})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                if (!validate()) return;
                login(username.getText().toString().trim(), password.getText().toString().trim());
                break;
            case R.id.register:
                showRegister(true);
                break;
            case R.id.get_validate_code:
                doGetValidateCode();
                break;
            case R.id.register_submit:
                if (!validate()) return;
                setPassword();
                //openSMSPage();
                break;
            case R.id.set_password_submit:
                if (!validate()) return;
                registerUser(phoneNumber.getText().toString().trim(),setPassword.getText().toString().trim());
                break;
        }
    }

    private void setPassword() {
        registerLayout.setVisibility(View.GONE);
        setPasswordLayout.setVisibility(View.VISIBLE);
    }

    private void login(String username, String password) {
        showProgressDialog();
        StoreBox.clearUserInfo(this);
        StoreBox.setUserNameAndPsd(this,username,password);
        dismissProgressDialog();
        setResult2Main();
        finish();
    }

    private void setResult2Main() {
        Intent data = new Intent();
        data.putExtra("result",LOGIN_SUCCESS);
        setResult(REQUEST_LOGIN);
    }


    int validateCountDownTotal = 59;
    Timer timer;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case VALIDATE_COUNT_DOWN:
                    validateCountDownTotal--;
                    if (validateCountDownTotal == 0) {
                        getValidateCode.setText("重新获取");
                        validateCountDownTotal = 59;
                        timer.cancel();
                        getValidateCode.setEnabled(true);
                    } else {
                        getValidateCode.setText(validateCountDownTotal + "秒后重新获取");
                    }

                    break;
            }
        }
    };

    private void doGetValidateCode() {
        getValidateCode.setEnabled(false);
        timer = new Timer("validate");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (validateCountDownTotal != 0) {
                    Message msg = new Message();
                    msg.what = VALIDATE_COUNT_DOWN;
                    handler.sendMessage(msg);
                }
            }
        }, 0, 1000);
    }

    void showRegister(boolean flag) {
        if (flag) {
            loginScrollView.setVisibility(View.GONE);
            registerLayout.setVisibility(View.VISIBLE);
//            new BounceAnimation(registerLayout).animate();
            isRegitering = true;

        } else {
            loginScrollView.setVisibility(View.VISIBLE);
            registerLayout.setVisibility(View.GONE);
            setPasswordLayout.setVisibility(View.GONE);
//            new BounceAnimation(loginScrollView).animate();
            isRegitering = false;
        }
    }

    @Override
    public void onBackPressed() {
        if (isRegitering) {
            showRegister(false);
            return;
        }
        super.onBackPressed();
    }

    boolean validate() {


        if (isRegitering) {
            if (!validatePhone()) {
                showErrDialog("手机格式有误");
                return false;
            }

            if (!validateValidateCode()) {
                showErrDialog("验证码错误");
                return false;
            }

            if (setPasswordLayout.getVisibility() == View.VISIBLE){
                if (setPassword.getText().length() == 0){
                    showErrDialog("密码不能为空");
                    return false;
                }

                if (!setPassword.getText().toString().trim().equals(setPasswordAgain.getText().toString().trim())){
                    showErrDialog("两次密码输入不一致");
                    return false;
                }
            }



        } else {
            if (username.getText().length() == 0) {
                showErrDialog("请输入用户名");
                return false;
            }
            if (password.getText().length() == 0) {
                showErrDialog("请输入密码");
                return false;
            }
        }

        return true;

    }

    private boolean validateValidateCode() {
        return true;
    }

    private boolean validatePhone() {
        return true;
    }

    void openSMSPage() {
        SMSSDK.initSDK(this, Mob.APP_KEY, Mob.APP_SECRET);
        //打开注册页面
        RegisterPage registerPage = new RegisterPage();
        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");

                    // 提交用户信息
                    //  registerUser(country, phone);
                }
            }
        });
        registerPage.show(this);
    }

    private void registerUser(String username,String password) {
        Log.i(TAG,"注册新用户:["+username+","+password+"]");

        showProgressDialog();


        String nick = "u_"+MyStringRandomGen.generateRandomString();
        StoreBox.clearUserInfo(this);
        StoreBox.setUserNameAndPsd(this,username,password);
        StoreBox.setUserNick(this,nick);
        dismissProgressDialog();
        setResult2Main();
        finish();

    }
}
