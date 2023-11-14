package com.example.herohabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.herohabit.NewTaskActivity.NEW_TASK;

public class TaskListActivity extends AppCompatActivity {
    SQLiteDatabase db;
    private static final int MAX_RADIOBUTTONS = 7;
    public static final int TASK_LIST = 1;
    private RadioGroup radioGroup = null;
    private ProgressBar progressBar = null;
    private RadioButton radioButton = null;
    private int progress = 0;
    private int level = 1;
    private int next = 30;
    private TextView nameView = null;
    private TextView lvlView = null;
    private TextView nextLvlView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklist);

        nameView = findViewById(R.id.name);
        lvlView = findViewById(R.id.lvl);
        nextLvlView = findViewById(R.id.nextLvl);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setMax(30);

        //Comprobamos que no se haya sobrepasado el límite de misiones antes de nada
        if (radioGroup.getChildCount() < 7){
            findViewById(R.id.addTask).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newTaskActivity(v);
                }
            });
        } else {
            Toast.makeText(getBaseContext(), "Max task reached.", Toast.LENGTH_SHORT).show();
        }

        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioGroup.removeView(findViewById(checkedId));
                updateProgress();
            }
        });


    }
    private void updateProgress(){
        progress++;
        progressBar.setProgress(progress);

        if (progress >= progressBar.getMax()){
            increaseLevel();
        }

    }

    private void increaseLevel(){
        Toast.makeText(getBaseContext(), "You reached a new level!", Toast.LENGTH_SHORT).show();

        level++;
        lvlView.setText(level);
        // UPDATE level

        next = next - level;
        nextLvlView.setText(next);
    }

    private void newTaskActivity(View view) {
        Intent intent = new Intent (TaskListActivity.this, NewTaskActivity.class);
        startActivityForResult(intent, NEW_TASK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            //Si el intent viene del login
            if (requestCode == REQUEST_NEW_USER) {
                int userID = data.getIntExtra("USER_ID", -1);
                User u = getUserById(userID);
                u.setMissionList(getMissionListById(userID));

                //Info de usuario seteada en la view
                nameView.setText(u.getUsername());
                lvlView.setText(u.getLevel());
                progressBar.setProgress(u.getExperience());
                nextLvlView.setText(progressBar.getMax() - progressBar.getProgress());

                //Añadiendo las misiones al radioGroup
                for (Mission mission : u.getMissionList()) {
                    if (mission != null) {
                        if (radioGroup.getChildCount() < MAX_RADIOBUTTONS) {
                            RadioButton radioButton = new RadioButton(this);
                            radioButton.setText(mission.getTitle());
                            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                                    RadioGroup.LayoutParams.WRAP_CONTENT,
                                    RadioGroup.LayoutParams.WRAP_CONTENT
                            );
                            params.weight = 1;
                            radioButton.setLayoutParams(params);
                            radioGroup.addView(radioButton);
                        } else {
                            Toast.makeText(getBaseContext(), "Max task reached.", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
                //Si el intent viene de una nueva misión
            } else if (requestCode == REQUEST_NEW_MISSION) {
                int missionID = data.getIntExtra("MISSION_ID", -1);
                Mission mission = getMissionById(missionID);

                //Añadiendo la misión al radioGroup
                if (mission != null) {
                    if (radioGroup.getChildCount() < MAX_RADIOBUTTONS) {
                        RadioButton radioButton = new RadioButton(this);
                        radioButton.setText(mission.getTitle());
                        RadioGroup radioGroup = findViewById(R.id.radioGroup);
                        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                                RadioGroup.LayoutParams.WRAP_CONTENT,
                                RadioGroup.LayoutParams.WRAP_CONTENT
                        );
                        params.weight = 1;
                        radioButton.setLayoutParams(params);
                        radioGroup.addView(radioButton);
                    } else {
                        Toast.makeText(getBaseContext(), "Max task reached.", Toast.LENGTH_SHORT).show();
                    }
                }
            }


        }
    }
    //Consulta del usuario a partir de su id
    private User getUserById(int userID) {
        db = openOrCreateDatabase("UserDatabase.db", Context.MODE_PRIVATE,null);
        Cursor cursor = null;
        User user = null;
        String selectQuery = "SELECT * FROM " + USERS + " WHERE " + USER_ID + " = " + userID;

        try {
            cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst() && cursor!=null) {
                user = new User();
                user.setUserID(cursor.getInt(cursor.getColumnIndex("userID")));
                user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                user.setLevel(cursor.getInt(cursor.getColumnIndex("level")));
                user.setExperience(cursor.getInt(cursor.getColumnIndex("experience")));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return user;
    }

    //Consulta de una misión a partir de su id
    private Mission getMissionById(int missionID) {
        db = openOrCreateDatabase("UserDatabase.db", Context.MODE_PRIVATE,null);
        Cursor cursor = null;
        Mission mission = null;
        String selectQuery = "SELECT * FROM " + TASKS + " WHERE " + MISSION_ID + " = " + missionID;

        try {
            cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst() && cursor!=null) {
                mission = new Mission();
                mission.setMissionID(cursor.getInt(cursor.getColumnIndex("id")));
                mission.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                mission.setUserID(cursor.getString(cursor.getColumnIndex("userID")));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return mission;
    }

    //Consulta de todas las misiones coincidentes con un id de usuario
    private ArrayList<Mission> getMissionListById(int userID) {
        db = openOrCreateDatabase("TaskDatabase.db", Context.MODE_PRIVATE,null);
        Cursor cursor = null;
        ArrayList<Mission> missionList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TASKS + " WHERE " + USER_ID + " = " + userID;

        try {
            cursor = db.rawQuery(selectQuery, null);

            while (cursor.moveToFirst() && cursor!=null) {
                Mission mission = new Mission();
                mission.setMissionID(cursor.getInt(cursor.getColumnIndex("id")));
                mission.setUserID(cursor.getInt(cursor.getColumnIndex("userID")));
                mission.setTitle(cursor.getString(cursor.getColumnIndex("title")));

                missionList.add(mission);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return missionList;
    }
}