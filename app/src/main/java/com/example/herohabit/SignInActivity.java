package com.example.herohabit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {

    private Button signIn;
    private EditText usernameText;
    private EditText passwordText;
    final UserRegister userRegistration = new UserRegister(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        signIn = findViewById(R.id.btnSignIn);
        usernameText = findViewById(R.id.editTextTextPersonName);
        passwordText = findViewById(R.id.editTextTextPassword);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    long rowId = userRegistration.addUser(username, password);

                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    intent.putExtra("username", usernameText.getText().toString());
                    intent.putExtra("password", passwordText.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();

                    if (rowId != -1) {
                        Toast.makeText(SignInActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        // Optionally, you can navigate to the login page here.
                    } else {
                        Toast.makeText(SignInActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Error: No se seleccionó ninguna provincia", Toast.LENGTH_SHORT).show();
        }
    }
}

