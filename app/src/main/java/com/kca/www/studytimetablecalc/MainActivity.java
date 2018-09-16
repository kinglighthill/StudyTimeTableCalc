package com.kca.www.studytimetablecalc;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.R.attr.start;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        View view1 = findViewById(R.id.generateStudyTimeTable);
        View view2 = findViewById(R.id.myStudyTimeTable);
        view1.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.help:
                return  true;
            case R.id.setting:
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View messageView = inflater.inflate(R.layout.fragment_change__password, null);
                final AlertDialog.Builder messageAlertBuilder = new AlertDialog.Builder(this);
                messageAlertBuilder.setView(messageView);
                messageAlertBuilder.setPositiveButton("Send", null)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                final AlertDialog messageAlertDialog = messageAlertBuilder.create();
                messageAlertDialog.show();
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.add(R.id.change_password_placeholder, new Change_Password());
//                transaction.commit();
                //Intent settingIntent = new Intent(this,change_password.getClass());
                //startActivity(settingIntent);
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void generateStudyTimeTable(View v){
        Intent setUpIntent = new Intent(this,SetUpActivity.class);
        startActivity(setUpIntent);
    }

    public void myStudyTimeTable(View v){

    }

    public void save(View v){

    }
}
