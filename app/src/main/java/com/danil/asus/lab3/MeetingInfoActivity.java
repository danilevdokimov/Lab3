package com.danil.asus.lab3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.danil.asus.lab3.user.data.UserData;
import com.danil.asus.lab3.user.data.requests.impl.AcceptRequest;
import com.danil.asus.lab3.user.data.requests.impl.FindRequest;
import com.danil.asus.lab3.user.data.requests.impl.MeetingRequest;
import com.danil.asus.lab3.user.data.requests.impl.RemoveRequest;
import com.danil.asus.shared.Meeting;
import com.danil.asus.shared.Participant;

public class MeetingInfoActivity extends AppCompatActivity {
    private Meeting currentMeeting;
    private Participant currentUser = new Participant(UserData.getUserFio(), UserData.getUserPost());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_info);
        String title = getIntent().getStringExtra("title");
        if (title != null) {
            MeetingRequest meetingRequest = new MeetingRequest(this);
            meetingRequest.execute(title);
        } else {
            String fragment = getIntent().getStringExtra("fragment");
            FindRequest findRequest = new FindRequest(this);
            findRequest.execute(fragment);
        }
        findViewById(R.id.btn_accept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentMeeting.getParticipants().contains(currentUser)) {
                    showAlreadyMassage();
                } else {
                    executeAcceptRequest();
                }
            }
        });
        findViewById(R.id.btn_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirm();
            }
        });
    }

    private void showConfirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.remove_dialog_title)
                .setMessage(R.string.remove_massage)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executeRemoveRequest();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();
    }

    private void executeRemoveRequest() {
        RemoveRequest removeRequest = new RemoveRequest(this);
        removeRequest.execute(currentMeeting.getTitle());
    }

    private void executeAcceptRequest() {
        AcceptRequest acceptRequest = new AcceptRequest(this);
        acceptRequest.execute(currentMeeting.getTitle(), currentUser);
    }

    private void showAlreadyMassage() {
        Toast.makeText(this, R.string.already_massage, Toast.LENGTH_SHORT).show();
    }

    public void showMeetingInfo(Meeting meeting) {
        currentMeeting = meeting;
        ((TextView) findViewById(R.id.tv_title)).setText(meeting.getTitle());
        ((TextView) findViewById(R.id.tv_discription)).setText(meeting.getDescription());
        ((TextView) findViewById(R.id.tv_start_date)).setText(meeting.getStartDate() == null ? "Unknown"
                : meeting.getStartDate().toString());
        ((TextView) findViewById(R.id.tv_end_date)).setText(meeting.getEndDate() == null ? "Unknown"
                : meeting.getEndDate().toString());
        ((TextView) findViewById(R.id.tv_priority)).setText(meeting.getPriority());
        ((TextView) findViewById(R.id.tv_participants)).setText(meeting.getParticipantsAsString());
    }
}
