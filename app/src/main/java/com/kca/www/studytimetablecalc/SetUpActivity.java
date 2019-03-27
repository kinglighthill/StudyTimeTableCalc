package com.kca.www.studytimetablecalc;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class SetUpActivity extends AppCompatActivity {
    private LinearLayout coursesLL;
    private LinearLayout dayLL;
    private EditText coursesET;
    private EditText daysET;
    private EditText courseET;
    private EditText priorityET;
    private EditText dayET;
    private EditText hourET;
    private Switch uploadSW;
    private TextView uploadTV;
    private Button generateButton;

    int numberOfCourses = 1;
    int numberOfDays = 1;
    ArrayList<View> coursePriorityList = new ArrayList<>();
    ArrayList<View> dayHourList = new ArrayList<>();
    ArrayList<StudyTimeTableModel> coursePriorityModelList = new ArrayList<>();
    ArrayList<StudyTimeTableModel> dayHourModelList = new ArrayList<>();

    private String[] days_arrays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private static ArrayList<String> days;
    private static ArrayList<String> selectedDays = new ArrayList<>();

    private static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        days = new ArrayList<>(Arrays.asList(days_arrays));

        coursesLL = findViewById(R.id.course_priority_LL);
        dayLL = findViewById(R.id.day_hour_LL);

        coursePriorityList.add(coursesLL);
        dayHourList.add(dayLL);

        courseET = findViewById(R.id.courseET);
        priorityET = findViewById(R.id.priorityET);
        dayET = findViewById(R.id.dayET);
        hourET = findViewById(R.id.hourET);

        coursesET = findViewById(R.id.coursesET);
        daysET = findViewById(R.id.daysET);
        coursesET.setOnClickListener(new EditTextListener(coursesET,"Select number of courses", 20));
        daysET.setOnClickListener(new EditTextListener(daysET,"Select number of reading days", 7,
                false));

        priorityET.setOnClickListener(new EditTextListener(priorityET,
                "Select course priority", 20, 0));
        dayET.setOnClickListener(new DaysEditTextListener(dayET));
        hourET.setOnClickListener(new HoursEditTextListener(hourET));

        uploadSW = findViewById(R.id.upload_SW);
        uploadSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    uploadTV.setVisibility(View.VISIBLE);
                }
                else {
                    uploadTV.setVisibility(View.GONE);
                }
            }
        });

        uploadTV = findViewById(R.id.upload_TV);
        uploadTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        generateButton = findViewById(R.id.generate);
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateModelLists();
                for (StudyTimeTableModel model : coursePriorityModelList) {
                    Toast.makeText(getBaseContext(), model.getCourse_day() + ", " + model.getPriority_hour(),
                            Toast.LENGTH_SHORT).show();
                }
                for (StudyTimeTableModel model : dayHourModelList) {
                    Toast.makeText(getBaseContext(), model.getCourse_day() + ", " + model.getPriority_hour(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            if (data == null) {
                Toast.makeText(getApplicationContext(), "Error loading image", Toast.LENGTH_SHORT).show();
                return;
            }
            uploadTV.setText(data.getData().toString());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        selectedDays.clear();
        coursePriorityList.clear();
        dayHourList.clear();
        super.onDestroy();
    }

    private ArrayList<String> getUnselectedDays(String selectedDay) {
        ArrayList<String> unselectedDays = new ArrayList<>(days);
        selectedDays.remove(selectedDay);
        unselectedDays.removeAll(selectedDays);
        /*if (!selectedDay.isEmpty() && !selectedDay.equals("Input Day")){
            unselectedDays.add(selectedDay);
        }*/
        return unselectedDays;
    }

    private void updateModelLists() {
        coursePriorityModelList.clear();
        dayHourModelList.clear();

        for (View view : coursePriorityList) {
            final EditText courseView = view.findViewById(R.id.courseET);
            final EditText priorityView = view.findViewById(R.id.priorityET);
            StudyTimeTableModel model = new StudyTimeTableModel(courseView.getText().toString(), priorityView.getText()
                    .toString());
            coursePriorityModelList.add(model);
        }
        for (View view : dayHourList) {
            final EditText dayView = view.findViewById(R.id.dayET);
            final EditText hourView = view.findViewById(R.id.hourET);
            StudyTimeTableModel model = new StudyTimeTableModel(dayView.getText().toString(), hourView.getTag()
                    .toString());
            dayHourModelList.add(model);
        }
    }

    private class EditTextListener implements View.OnClickListener {
        private EditText editText;
        private String title;
        private int maxValue;
        private int isPriority = -1;
        private boolean isCourse = true;

        public EditTextListener(EditText editText, String title, int maxValue) {
            this.editText = editText;
            this.title = title;
            this.maxValue = maxValue;
        }

        public EditTextListener(EditText editText, String title, int maxValue, int isPriority) {
            this.editText = editText;
            this.title = title;
            this.maxValue = maxValue;
            this.isPriority = isPriority;
        }

        public EditTextListener(EditText editText, String title, int maxValue, boolean isCourse) {
            this.editText = editText;
            this.title = title;
            this.maxValue = maxValue;
            this.isCourse = isCourse;
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
                    if (isPriority == -1) {
                        if(isCourse) {
                            numberOfCourses = value;
                            coursesLL.removeAllViews();
                            coursePriorityList.clear();
                            for(int i = 0; i < value; i++) {
                                if (i == value - 1) {
                                    View view = LayoutInflater.from(SetUpActivity.this)
                                            .inflate(R.layout.course_priority_view, null, false);
                                    final EditText priorityView = view.findViewById(R.id.priorityET);
                                    priorityView.setOnClickListener(new EditTextListener(priorityView,
                                            "Select course priority", 20, 0));
                                    coursesLL.addView(view);
                                    coursePriorityList.add(view);
                                }
                                else {
                                    View view = LayoutInflater.from(SetUpActivity.this)
                                            .inflate(R.layout.course_priority_view, null, false);
                                    final EditText priorityView = view.findViewById(R.id.priorityET);
                                    priorityView.setOnClickListener(new EditTextListener(priorityView,
                                            "Select course priority", 20, 0));
                                    coursesLL.addView(view);
                                    LayoutInflater.from(SetUpActivity.this)
                                            .inflate(R.layout.horizontal_rule, coursesLL, true);
                                    coursePriorityList.add(view);
                                }
                            }
                        }
                        else {
                            numberOfDays = value;
                            selectedDays.clear();
                            dayLL.removeAllViews();
                            dayHourList.clear();
                            for(int i = 0; i < value; i++) {
                                if (i == value - 1) {
                                    View view = LayoutInflater.from(SetUpActivity.this)
                                            .inflate(R.layout.day_hour_view, null, false);
                                    final EditText dayView = view.findViewById(R.id.dayET);
                                    dayView.setOnClickListener(new DaysEditTextListener(dayView));
                                    final EditText hourView = view.findViewById(R.id.hourET);
                                    hourView.setOnClickListener(new HoursEditTextListener(hourView));
                                    dayLL.addView(view);
                                    dayHourList.add(view);
                                }
                                else {
                                    View view = LayoutInflater.from(SetUpActivity.this)
                                            .inflate(R.layout.day_hour_view, null, false);
                                    final EditText dayView = view.findViewById(R.id.dayET);
                                    dayView.setOnClickListener(new DaysEditTextListener(dayView));
                                    final EditText hourView = view.findViewById(R.id.hourET);
                                    hourView.setOnClickListener(new HoursEditTextListener(hourView));
                                    dayLL.addView(view);
                                    LayoutInflater.from(SetUpActivity.this)
                                            .inflate(R.layout.horizontal_rule, dayLL, true);
                                    dayHourList.add(view);
                                }
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

    private class DaysEditTextListener implements View.OnClickListener {
        private EditText dayView;

        public DaysEditTextListener(EditText dayView) {
            this.dayView = dayView;
        }
        @Override
        public void onClick(final View v) {
            String day = dayView.getText().toString();
            ArrayList<String> unselectedDays = getUnselectedDays(day);

            final ArrayAdapter<String> adapter = new ArrayAdapter<>(SetUpActivity.this,
                    android.R.layout.simple_list_item_single_choice, unselectedDays);

            AlertDialog.Builder builder = new AlertDialog.Builder(SetUpActivity.this);
            builder.setTitle("Select A Day");
            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String selectedDay = adapter.getItem(which);
                    dayView.setText(selectedDay);
                    selectedDays.add(selectedDay);
                }
            });
            builder.create();
            builder.show();
        }
    }

    private class HoursEditTextListener implements View.OnClickListener {
        private EditText hourView;

        public HoursEditTextListener(EditText hourView) {
            this.hourView = hourView;
        }

        @Override
        public void onClick(final View v) {
            int hour = 2;
            int minute = 0;

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
                        String selectedHourStr = "";
                        String selectedMinuteStr = "";

                        if (selectedHour > 0) {
                            if (selectedHour == 1) {
                                selectedHourStr = String.valueOf(selectedHour) + "hr";
                            }
                            else {
                                selectedHourStr = String.valueOf(selectedHour) + "hrs";
                            }
                        }

                        if (selectedMinute > 0) {
                            if (selectedMinute == 1) {
                                selectedMinuteStr = String.valueOf(selectedMinute) + "min";
                            }
                            else {
                                selectedMinuteStr = String.valueOf(selectedMinute) + "mins";
                            }
                        }
                        hourView.setTag(selectedHour * 60 + selectedMinute);
                        hourView.setText(selectedHourStr + " " + selectedMinuteStr);
                    }
                }

            };
            TimePickerDialogFixedNougatSpinner mtimePicker =
                    new TimePickerDialogFixedNougatSpinner
                            (SetUpActivity.this, TimePickerDialog.THEME_HOLO_LIGHT,
                                    listener, hour, minute, true);
            mtimePicker.show();
        }
    }

}
