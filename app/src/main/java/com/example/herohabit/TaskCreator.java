package com.example.herohabit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;

public class TaskCreator extends AppCompatActivity {

    private TextInputEditText inputTaskName;
    private Button buttonCreateTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_creator);

        inputTaskName = findViewById(R.id.inputTaskName);
        buttonCreateTask = findViewById(R.id.buttonCreateTask);

        // Initially, disable the button
        buttonCreateTask.setEnabled(false);

        // Add a TextWatcher to the input field
        inputTaskName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Check if the input text is not empty
                boolean isInputNotEmpty = s.toString().trim().length() > 0;
                // Enable or disable the button based on the input text
                buttonCreateTask.setEnabled(isInputNotEmpty);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}