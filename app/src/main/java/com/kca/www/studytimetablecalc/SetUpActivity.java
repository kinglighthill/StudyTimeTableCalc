package com.kca.www.studytimetablecalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;

import static com.kca.www.studytimetablecalc.R.id.editText;

public class SetUpActivity extends AppCompatActivity {
    private Spinner spinner;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        spinner = (Spinner) findViewById(R.id.spinner_number_of_courses);
    }
    private NumberPicker.OnValueChangeListener onValueChangedListener = new NumberPicker.OnValueChangeListener(){
        @Override
        public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal){
            //LayoutInflater inflater;
            //View view = inflater.inflate(R.layout.activity_set_up,linearLayout,true);
            EditText editText1 = (EditText) findViewById(R.id.editText);
            EditText editText2 = (EditText) findViewById(R.id.editText2);
            for (int j=0; j<newVal; j++){
                linearLayout.addView(editText1);
                linearLayout.addView(editText2);
                linearLayout.setVisibility(View.VISIBLE);
            }
        }
    };
}
