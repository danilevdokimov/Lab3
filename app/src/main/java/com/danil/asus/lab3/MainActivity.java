package com.danil.asus.lab3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.danil.asus.lab3.user.data.requests.MeetingsRequest;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> meetingsTitles;
    private ListView meetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        meetings = (ListView) findViewById(R.id.meetings);
        meetings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showMeetingInfo(position);
            }
        });
        updateMeetings();
    }

    public void initializeList(List<String> meetingsTitles) {
        this.meetingsTitles = meetingsTitles;
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1,
                meetingsTitles);
        meetings.setAdapter(adapter);
    }

    private void updateMeetings() {
        MeetingsRequest meetingsRequest = new MeetingsRequest(this);
        meetingsRequest.execute();
    }

    private void showMeetingInfo(int position) {
        String meetingTitle = meetingsTitles.get(position);
        Intent intent = new Intent(this, MeetingInfoActivity.class);
        intent.putExtra("title", meetingTitle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_meeting:
                Intent intent = new Intent(this, NewMeetingActivity.class);
                startActivity(intent);
                break;
            case R.id.find:
                showFindDialog();
                break;
            case R.id.update:
                break;
            case R.id.about:
                showAboutDialog();
                break;
            case R.id.exit:
                showExitDialog();
                break;
        }
        return true;
    }

    private void showAboutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.about_title);
        builder.setMessage(R.string.about_message);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
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

    private void showFindDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.dialog_find, null);
        builder.setTitle(R.string.find_dialog_title).setView(view)
                .setPositiveButton(R.string.btn_find, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String fragment = ((EditText) view.findViewById(R.id.et_find)).getText().toString();
                        executeFindRequest(fragment);
                    }
                })
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();
    }

    private void executeFindRequest(String fragment) {
        Intent intent = new Intent(this, MeetingInfoActivity.class);
        intent.putExtra("fragment", fragment);
        startActivity(intent);
    }
}