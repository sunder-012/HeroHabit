package com.example.herohabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int SIGNIN_REQUEST_CODE = 1;
    private static final int MAINWINDOW_REQUEST_CODE = 2;
    private Button signIn;
    private Button logIn;
    private EditText usernameText;
    private EditText passwordText;
    private static String user = "undefined";
    private static String pass = "abcd*1234";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signIn = findViewById(R.id.btnSignIn);
        logIn = findViewById(R.id.btnLogIn);
        usernameText = findViewById(R.id.editTextTextPersonName);
        passwordText = findViewById(R.id.editTextTextPassword);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivityForResult(intent, SIGNIN_REQUEST_CODE);
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.equalsIgnoreCase(usernameText.getText().toString()) && pass.equalsIgnoreCase(passwordText.getText().toString())){
                    Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
                    intent.putExtra("username", usernameText.getText().toString());
                    setResult(RESULT_OK, intent);
                    startActivityForResult(intent, MAINWINDOW_REQUEST_CODE);
                }else{
                    /*Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
                    setResult(RESULT_CANCELED, intent);
                    startActivityForResult(intent, MAINWINDOW_REQUEST_CODE);*/
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGNIN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                user = data.getStringExtra("username");
                pass = data.getStringExtra("password");
            } else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(this, "Error en sign in", Toast.LENGTH_SHORT).show();
            }
        }
    }
}