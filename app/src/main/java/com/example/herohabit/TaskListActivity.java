package com.example.herohabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import static com.example.herohabit.NewTaskActivity.NEW_TASK;

public class TaskListActivity extends AppCompatActivity {

    private static final int MAX_RADIOBUTTONS = 7;
    public static final int TASK_LIST = 1;
    private RadioGroup radioGroup = null;
    private RadioButton radioButton1 = null;
    private RadioButton radioButton2 = null;
    private RadioButton radioButton3 = null;
    private RadioButton radioButton4 = null;
    private RadioButton radioButton5 = null;
    private RadioButton radioButton6 = null;
    private RadioButton radioButton7 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasklist);
        findViewById(R.id.addTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTaskActivity(v);
            }
        });
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
                if (radioGroup.getChildCount() > MAX_RADIOBUTTONS) {
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
                }
            }
        }
    }
}