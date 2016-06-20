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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.easyandroidanimations.library.BounceAnimation;
import com.easyandroidanimations.library.FoldAnimation;
import com.google.gson.Gson;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.xjtu.friendtrip.Net.Config;
import com.xjtu.friendtrip.Net.LoginJson;
import com.xjtu.friendtrip.Net.Mob;
import com.xjtu.friendtrip.Net.RegistJson;
import com.xjtu.friendtrip.Net.RequestUtil;
import com.xjtu.friendtrip.R;
import com.xjtu.friendtrip.application.MyApplication;
import com.xjtu.friendtrip.bean.User;
import com.xjtu.friendtrip.util.CommonUtil;
import com.xjtu.friendtrip.util.MyStringRandomGen;
import com.xjtu.friendtrip.util.PrefUtils;
import com.xjtu.friendtrip.util.StoreBox;


import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends BaseActivity {


    private static final String TAG = LoginActivity.class.getName();

    public static final int REQUEST_LOGIN = 0;
    public static final boolean LOGIN_SUCCESS = true;


    private static final int VALIDATE_COUNT_DOWN = 1;
    private static final int VALIDATE_SUCCESS = 2;
    private static final int VALIDATE_FAILED = 3;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initToolbar("登录");
        initDialog(this);
        username.setText(PrefUtils.getStringPreference(this, "username"));
        initSMSSDK();
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
                checkUserExsit();
                break;
            case R.id.register_submit:
                //validateSMSCode();
                setPassword();
                break;
            case R.id.set_password_submit:
                if (!validate()) return;
                registerUser(phoneNumber.getText().toString().trim(), setPassword.getText().toString().trim());
                break;
        }
    }

    private void checkUserExsit() {
        String url = Config.CHECK_EXSIT + "/" + phoneNumber.getText().toString().trim() + "/registerResult";
        Ion.with(this).load("GET", url).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                Log.i(TAG, "检查用户是否存在:" + result);
                if (result.equals("notExits")) {
                    doGetValidateCode();
                } else {
                    showErrDialog("用户已存在");
                }
            }
        });


    }

    private void validateSMSCode() {
        SMSSDK.submitVerificationCode("86", phoneNumber.getText().toString().trim(), validateCode.getText().toString().trim());
        showProgressDialog();
    }

    private void setPassword() {
        registerLayout.setVisibility(View.GONE);
        setPasswordLayout.setVisibility(View.VISIBLE);
    }

    private void login(final String username, final String password) {
        showProgressDialog();
        String body = new Gson().toJson(new LoginJson(username, password));
        CommonUtil.printRequest("登录", body);
        Ion.with(this).load("POST", Config.LOGIN).setStringBody(body).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                CommonUtil.printResponse(result);
                if (RequestUtil.isRequestSuccess(result)) {
                    loginSuccess(result);
                } else {
                    loginFailed(result);
                }
            }
        });


    }

    private void loginFailed(String result) {
        dismissProgressDialog();
        showErrDialog(RequestUtil.handRequestErr(result));
    }

    private void loginSuccess(String result) {
        JSONObject jo = JSON.parseObject(result);
        User u = RequestUtil.requestToUser(jo.getString("data"));
        StoreBox.clearUserInfo(this);
        Log.i(TAG, u.toString());
        StoreBox.saveUserInfo(this, u);
        MyApplication.setUser(u);
        dismissProgressDialog();
        setResult(RESULT_OK);
        finish();
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
                case VALIDATE_SUCCESS:
                    dismissProgressDialog();
                    setPassword();
                    break;
                case VALIDATE_FAILED:
                    dismissProgressDialog();
                    showErrDialog("验证失败");
                    break;
            }
        }
    };

    private void doGetValidateCode() {
        SMSSDK.getVerificationCode("86", phoneNumber.getText().toString().trim(), new OnSendMessageHandler() {
            @Override
            public boolean onSendMessage(String country, String phone) {
                return false;
            }
        });
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

            if (setPasswordLayout.getVisibility() == View.VISIBLE) {
                if (setPassword.getText().length() == 0) {
                    showErrDialog("密码不能为空");
                    return false;
                }

                if (!setPassword.getText().toString().trim().equals(setPasswordAgain.getText().toString().trim())) {
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

    void initSMSSDK() {
        SMSSDK.initSDK(this, Mob.APP_KEY, Mob.APP_SECRET);
        EventHandler eh = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Message msg = new Message();
                        msg.what = VALIDATE_SUCCESS;
                        handler.sendMessage(msg);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        Log.i(TAG, data.toString());

                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).printStackTrace();
                    Message msg = new Message();
                    msg.what = VALIDATE_FAILED;
                    handler.sendMessage(msg);
                }
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调

    }

    private void registerUser(final String username, final String password) {
        Log.i(TAG, "注册新用户:[" + username + "," + password + "]");

        showProgressDialog();


        final String nick = "u_" + MyStringRandomGen.generateRandomString();
        String body = new Gson().toJson(
                new RegistJson(
                        username, password, nick
                )
        );
        Log.i(TAG, "注册:" + Config.REGIST + "\n" + "Data:" + body);
        Ion.with(this).load("POST", Config.REGIST).setStringBody(body).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                Log.i(TAG, "注册完成:" + result);
                if (RequestUtil.isRequestSuccess(result)) {
                    registSuccess(result);
                } else {
                    registFailed(result);
                }
            }
        });
    }

    private void registFailed(String result) {
        dismissProgressDialog();
        showErrDialog("注册失败");
    }

    private void registSuccess(String result) {
        loginSuccess(result);
    }

}
