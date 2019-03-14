package com.kca.www.studytimetablecalc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Set;

public class SetUpActivity extends AppCompatActivity {
    private Spinner spinner;
    private LinearLayout coursesLL;
    private LinearLayout dayLL;
    private EditText coursesET;
    private EditText daysET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        coursesLL = findViewById(R.id.course_priority_LL);
        dayLL = findViewById(R.id.day_hour_LL);

        coursesET = findViewById(R.id.coursesET);
        daysET = findViewById(R.id.daysET);
        coursesET.setOnClickListener(new EditTextListener(coursesET,"Select number of courses", 20));
        daysET.setOnClickListener(new EditTextListener(daysET,"Select number of reading days", 7,
                false));
    }

    private class EditTextListener implements View.OnClickListener {
        private EditText editText;
        private String title;
        private int maxValue;
        private boolean isCourse = true;

        public EditTextListener(EditText editText, String title, int maxValue) {
            this.editText = editText;
            this.title = title;
            this.maxValue = maxValue;
        }

        public EditTextListener(EditText editText, String title, int maxValue, boolean isCourse) {
            this.editText = editText;
            this.title = title;
            this.maxValue = maxValue;
            this.isCourse = isCourse;
        }

        public void setCourse(boolean course) {
            isCourse = course;
        }

        @Override
        public void onClick(final View v) {
            final NumberPicker numberPicker = new NumberPicker(SetUpActivity.this);
            numberPicker.setMaxValue(maxValue);
            numberPicker.setMinValue(1);


            AlertDialog.Builder builder = new AlertDialog.Builder(SetUpActivity.this);
            builder.setView(numberPicker);
            builder.setTitle(title);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int value = numberPicker.getValue();
                    editText.setText(String.valueOf(value));
                    if(isCourse) {
                        coursesLL.removeAllViews();
                        for(int i = 0; i < value; i++) {
                            if (i == value - 1) {
                                LayoutInflater.from(SetUpActivity.this)
                                        .inflate(R.layout.course_priority_view, coursesLL, true);
                            }
                            else {
                                LayoutInflater.from(SetUpActivity.this)
                                    .inflate(R.layout.course_priority_view, coursesLL, true);
                                LayoutInflater.from(SetUpActivity.this)
                                    .inflate(R.layout.horizontal_rule, coursesLL, true);
                            }
                        }
                    }

                    else {
                        dayLL.removeAllViews();
                        for(int i = 0; i < value; i++) {
                            if (i == value - 1) {
                                View view = LayoutInflater.from(SetUpActivity.this)
                                        .inflate(R.layout.day_hour_view, dayLL, true);
                                final EditText hourView = view.findViewById(R.id.hourET);
                                hourView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //Calendar currentTime = Calendar.getInstance();
                                        int hour = 2;
                                        int minute = 0;
                                        //TimePickerDialog mtimePicker;
                                        /*if(time!=0){
                                            hour = getHour(time);
                                            minute = getMinute(time);
                                        }*/

                                        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener()
                                        {
                                            @Override
                                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
                                            {
                                                if (selectedHour == 0 && selectedMinute == 0) {
                                                    Toast.makeText(getApplicationContext(),
                                                            "Time cannot be set to 00:00", Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    String selectedHourStr = String.valueOf(selectedHour);
                                                    if (String.valueOf(selectedHour).length() < 2)
                                                        selectedHourStr = "0" + String.valueOf(selectedHour);
                                                    if (selectedHour == 0)
                                                        selectedHourStr = "00";
                                                    //if(selectedHour<10) selectedHour = "0"+String.valueOf(selectedHour);
                                                    if (selectedMinute == 0)
                                                        hourView.setText(selectedHourStr + " : 00");
                                                    else if (selectedMinute < 10)
                                                        hourView.setText(selectedHourStr + " : 0" + selectedMinute);
                                                    else
                                                        hourView.setText(selectedHourStr + " : " + selectedMinute);
//                                                    time = (selectedHour * 3600) + (selectedMinute * 60);
//                                                    timeSet = true;
                                                }
                                            }

                                        };
                                        TimePickerDialogFixedNougatSpinner mtimePicker =
                                                new TimePickerDialogFixedNougatSpinner
                                                (SetUpActivity.this, TimePickerDialog.THEME_HOLO_LIGHT,
                                                        listener, hour, minute, true);
                                        mtimePicker.show();
                                    }
                                });
                            }
                            else {
                                View view = LayoutInflater.from(SetUpActivity.this)
                                        .inflate(R.layout.day_hour_view, dayLL, true);
                                final EditText hourView = view.findViewById(R.id.hourET);
                                hourView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //Calendar currentTime = Calendar.getInstance();
                                        int hour = 2;
                                        int minute = 0;
                                        //TimePickerDialog mtimePicker;
                                        /*if(time!=0){
                                            hour = getHour(time);
                                            minute = getMinute(time);
                                        }*/

                                        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener()
                                        {
                                            @Override
                                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
                                            {
                                                if (selectedHour == 0 && selectedMinute == 0) {
                                                    Toast.makeText(getApplicationContext(),
                                                            "Time cannot be set to 00:00", Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    String selectedHourStr = String.valueOf(selectedHour);
                                                    if (String.valueOf(selectedHour).length() < 2)
                                                        selectedHourStr = "0" + String.valueOf(selectedHour);
                                                    if (selectedHour == 0)
                                                        selectedHourStr = "00";
                                                    //if(selectedHour<10) selectedHour = "0"+String.valueOf(selectedHour);
                                                    if (selectedMinute == 0)
                                                        hourView.setText(selectedHourStr + " : 00");
                                                    else if (selectedMinute < 10)
                                                        hourView.setText(selectedHourStr + " : 0" + selectedMinute);
                                                    else
                                                        hourView.setText(selectedHourStr + " : " + selectedMinute);
//                                                    time = (selectedHour * 3600) + (selectedMinute * 60);
//                                                    timeSet = true;
                                                }
                                            }

                                        };
                                        TimePickerDialogFixedNougatSpinner mtimePicker =
                                                new TimePickerDialogFixedNougatSpinner
                                                        (SetUpActivity.this, TimePickerDialog.THEME_HOLO_LIGHT,
                                                                listener, hour, minute, true);
                                        mtimePicker.show();
                                    }
                                });
                                LayoutInflater.from(SetUpActivity.this)
                                    .inflate(R.layout.horizontal_rule, dayLL, true);
                            }
                        }
                    }
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create();
            builder.show();
        }
    }

}
