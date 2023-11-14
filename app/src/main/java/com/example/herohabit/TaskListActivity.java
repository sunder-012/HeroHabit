package com.example.herohabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView lvlView = null;
    private TextView nextLvlView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklist);

        radioGroup = findViewById(R.id.radioGroup);

        progressBar = findViewById(R.id.progressBar);
        //SELECT XP IN PROGRESS progressBar.setProgress()
        // progress =
        progressBar.setMax(30);

        //SELECT LVL level =
        //SELECT nextLVL next =

        findViewById(R.id.addTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTaskActivity(v);
            }
        });
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
        lvlView = findViewById(R.id.lvl);
        level++;
        lvlView.setText(level);
        // UPDATE level
        nextLvlView = findViewById(R.id.nextLvl);
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

            db=openOrCreateDatabase("TaskDatabase.db", Context.MODE_PRIVATE,null);
            Task task = "SELECT * FROM " + TASKS + "WHERE " + USER_ID + " = " + task.getUserID();

            // Create a new RadioButton with the returned data within the RadioGroup
            if (task != null && !task.isEmpty()) {
                if (radioGroup.getChildCount() < MAX_RADIOBUTTONS) {
                    RadioButton radioButton = new RadioButton(this);
                    radioButton.setText(task.getTitulo());
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