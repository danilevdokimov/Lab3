package com.danil.asus.lab3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.danil.asus.lab3.user.data.requests.impl.PasswordRequest;

public class StartActivity extends AppCompatActivity {

    private EditText mUserFio;
    private EditText mUserPost;
    private EditText mPassword;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mUserFio = (EditText) findViewById(R.id.et_fio);
        mUserPost = (EditText) findViewById(R.id.et_post);
        mPassword = (EditText) findViewById(R.id.et_password);
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
        mUserFio.setText(sharedPreferences.getString(Constants.USER_FIO_PREF, ""));
        mUserPost.setText(sharedPreferences.getString(Constants.USER_POST_PREF, ""));
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
        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog();
            }
        });
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.exit_title);
        builder.setMessage(R.string.exit_message);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
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
        sharedPreferences.edit().putString(Constants.USER_FIO_PREF, mUserFio.getText().toString())
                .putString(Constants.USER_POST_PREF, mUserPost.getText().toString()).commit();
    }

    public void showMeetings() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
