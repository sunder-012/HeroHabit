package com.example.herohabit;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

//import com.google.android.material.textfield.TextInputEditText;

public class TaskCreator extends AppCompatActivity {
    private EditText taskDescription;
    private Button buttonCreateTask;
    private Button buttonCancelTask;
    private ImageButton imageButtonCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskcreator);

        taskDescription = findViewById(R.id.textTaskTitle);
        buttonCreateTask = findViewById(R.id.buttonCreateTask);
        buttonCancelTask = findViewById(R.id.buttonCancelTask);
        imageButtonCalendar = findViewById(R.id.imageButtonCalendar);

        // Initially, disable the button
        buttonCreateTask.setEnabled(false);

        // Add a TextWatcher to the input field
        taskDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this example
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
                // Not needed for this example
            }
        });

        // Set an OnClickListener for the ImageButton to open the calendar with a chooser
        imageButtonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the task name
                taskName = inputTaskName.getText().toString();
                // Create an implicit intent to open the calendar app with an event with the tas name as title
                Intent calendarIntent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.Events.TITLE, taskName);
                // Create a chooser dialog
                Intent chooser = Intent.createChooser(calendarIntent, "Choose Calendar App");
                // Start the Activity
                if(chooser.resolveActivity( v.getContext().getPackageManager())!=null){
                    v.getContext().startActivity(chooser);
                } else{
                    Toast.makeText( v.getContext(), "No calendar apps found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCreateTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Get the task name
                taskName = inputTaskName.getText().toString();
                /*
                // Create a Unique Identifier for Mission
                String missionID = UUID.randomUUID().toString();
                 */
                // Create the insert String
                String insertQuery = "INSERT INTO " + TASKS + " (" + user_id + ", " + title + ") VALUES ('" + userId + "', " + taskName + ");";
                // Execute the query
                db.execSQL(insertQuery);
            }
        });
    }
}
