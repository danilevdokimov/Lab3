package com.danil.asus.lab3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.danil.asus.lab3.user.data.UserData;
import com.danil.asus.lab3.user.data.requests.PasswordRequest;

public class StartActivity extends AppCompatActivity {

    private EditText mUserFio;
    private EditText mUserPost;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mUserFio = (EditText) findViewById(R.id.et_fio);
        mUserPost = (EditText) findViewById(R.id.et_post);
        mPassword = (EditText) findViewById(R.id.et_password);
        findViewById(R.id.btn_enter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mUserFio.getText().toString().equals("") && !mUserPost.getText().toString().equals("")) {
                    checkAuthentication();
                } else {
                    showWarning();
                }
            }
        });
    }

    private void showWarning() {
        Toast.makeText(this, R.string.warning, Toast.LENGTH_SHORT).show();
    }

    private void checkAuthentication() {
        String userPassword = mPassword.getText().toString();
        PasswordRequest passwordRequest = new PasswordRequest(this);
        passwordRequest.execute(userPassword);
    }

    public void saveUserData() {
        String userFio = mUserFio.getText().toString();
        String userPost = mUserPost.getText().toString();
        UserData.setUserFio(userFio);
        UserData.setUserPost(userPost);
    }

    public void showMeetings() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}