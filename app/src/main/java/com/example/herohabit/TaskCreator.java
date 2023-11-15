package com.example.herohabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

    SQLiteDatabase dbTasks;
    private EditText taskDescription;
    private Button buttonCreateTask;
    private Button buttonCancelTask;
    private ImageButton imageButtonCalendar;
    private int userId;
    private String taskName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskcreator);

        taskDescription = findViewById(R.id.textTaskTitle);
        buttonCreateTask = findViewById(R.id.buttonCreateTask);
        buttonCancelTask = findViewById(R.id.buttonCancelTask);
        imageButtonCalendar = findViewById(R.id.imageButtonCalendar);

        dbTasks=openOrCreateDatabase("TaskDatabase.db", Context.MODE_PRIVATE,null);
        // Initially, disable the button
        buttonCreateTask.setEnabled(false);

        Intent intent = getIntent();
        if (intent.hasExtra("user_id")) {
            userId = intent.getIntExtra("user_id", -1); // -1 is the default value if the key is not found
        } else {
            Toast.makeText(this, "User ID not provided", Toast.LENGTH_SHORT).show();
        }

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
                taskName = taskDescription.getText().toString();
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
                taskName = taskDescription.getText().toString();
                // Create the insert String
                String insertQuery = "INSERT INTO tasks ( user_id ,  title) VALUES ('" + userId + "', '" + taskName + "');";
                // Execute the query
                dbTasks.execSQL(insertQuery);
            }
        });

        buttonCancelTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}