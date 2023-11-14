package com.example.herohabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    private static final int MAX_RADIOBUTTONS = 7;
    public static final int TASK_LIST = 1;
    private RadioGroup radioGroup = null;
    private ProgressBar progressBar = null;
    private RadioButton radioButton = null;
    private int progress = 0;
    private int level = 1;
    private TextView lvl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklist);

        radioGroup = findViewById(R.id.radioGroup);
        progressBar = findViewById(R.id.progressBar);

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
        lvl = findViewById(R.id.lvl);
        level++;
        lvl.setText(level);
    }

    private void newTaskActivity(View view) {
        Intent intent = new Intent (TaskListActivity.this, NewTaskActivity.class);
        startActivityForResult(intent, NEW_TASK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (/*requestCode == CODE && */resultCode == RESULT_OK) {
            // Get the data returned from NewTaskActivity
            String param1 = data.getStringExtra("param1");

            // Create a new RadioButton with the returned data within the RadioGroup
            if (param1 != null && !param1.isEmpty()) {
                if (radioGroup.getChildCount() < MAX_RADIOBUTTONS) {
                    RadioButton radioButton = new RadioButton(this);
                    radioButton.setText(param1);
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