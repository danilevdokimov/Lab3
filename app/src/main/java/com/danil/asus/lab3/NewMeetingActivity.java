package com.danil.asus.lab3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class NewMeetingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);
        findViewById(R.id.ibtn_add_participant).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewParticipantDialog();
            }
        });
    }

    private void showNewParticipantDialog() {
        View dialog_view = getLayoutInflater().inflate(R.layout.new_participant_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title);
        builder.setView(dialog_view);
        builder.setPositiveButton(R.string.btn_add_txt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton(R.string.btn_cancel_txt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}
