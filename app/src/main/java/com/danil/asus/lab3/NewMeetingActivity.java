package com.danil.asus.lab3;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.danil.asus.lab3.user.data.requests.impl.NewMeetingRequest;
import com.danil.asus.shared.Meeting;
import com.danil.asus.shared.Participant;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewMeetingActivity extends AppCompatActivity {

    private boolean isSetStartDate;
    private boolean set = false;
    private Calendar today = Calendar.getInstance();
    private Meeting newMeeting = new Meeting();
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
    private EditText newTitle;
    private EditText newDescription;
    private Spinner newPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);
        newTitle = ((EditText) findViewById(R.id.et_title));
        newDescription = ((EditText) findViewById(R.id.et_description));
        newPriority = ((Spinner) findViewById(R.id.sp_priority));
        findViewById(R.id.ibtn_start_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSetStartDate = true;
                showDatePickerDialog();
            }
        });
        findViewById(R.id.ibtn_end_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSetStartDate = false;
                showDatePickerDialog();
            }
        });
        findViewById(R.id.ibtn_add_participant).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewParticipantDialog();
            }
        });
        findViewById(R.id.btn_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAllFieldsFilled()) {
                    newMeeting.setTitle(newTitle.getText().toString());
                    newMeeting.setDescription(newDescription.getText().toString());
                    newMeeting.setPriority(newPriority.getSelectedItem().toString());
                    executeNewMeetingRequest();
                } else {
                    showWarningMassage();
                }
            }
        });
        findViewById(R.id.btn_new_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMianMenu();
            }
        });
    }

    private void openMianMenu() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void executeNewMeetingRequest() {
        NewMeetingRequest newMeetingRequest = new NewMeetingRequest(this);
        newMeetingRequest.execute(newMeeting);
    }

    private void showWarningMassage() {
        Toast.makeText(this, R.string.not_fill_fields, Toast.LENGTH_SHORT).show();
    }

    private boolean isAllFieldsFilled() {
        return !"".equals(newTitle.getText().toString()) && !"".equals(newDescription.getText().toString())
                && newMeeting.getStartDate() != null && newMeeting.getEndDate() != null;
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                today.set(year, monthOfYear, dayOfMonth);
            }
        }, today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showTimePickerDialog();
            }
        });
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                today.set(Calendar.HOUR_OF_DAY, hourOfDay);
                today.set(Calendar.MINUTE, minute);
                if (set) {
                    if (isSetStartDate) {
                        newMeeting.setStartDate(today.getTime());
                        String startDate = dateFormat.format(today.getTime());
                        ((EditText) findViewById(R.id.et_start_date)).setText(startDate);
                    } else {
                        newMeeting.setEndDate(today.getTime());
                        String endDate = dateFormat.format(today.getTime());
                        ((EditText) findViewById(R.id.et_end_date)).setText(endDate);
                    }
                    set = false;
                }
            }
        }, today.get(Calendar.HOUR), today.get(Calendar.MINUTE), true);
        timePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                set = true;
                dialog.cancel();
            }
        });
        timePickerDialog.show();
    }

    private void showNewParticipantDialog() {
        final View dialog_view = getLayoutInflater().inflate(R.layout.new_participant_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title);
        builder.setView(dialog_view);
        builder.setPositiveButton(R.string.btn_add_txt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newFio = ((EditText) dialog_view.findViewById(R.id.et_new_fio)).getText().toString();
                String newPost = ((EditText) dialog_view.findViewById(R.id.et_new_post)).getText().toString();
                if (!"".equals(newFio) && !"".equals(newPost)) {
                    Participant participant = new Participant(newFio, newPost);
                    newMeeting.addParticipant(participant);
                    ((EditText) findViewById(R.id.et_participants)).setText(newMeeting.getParticipantsAsString());
                }
            }
        });
        builder.setNegativeButton(R.string.btn_cancel_txt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }
}
