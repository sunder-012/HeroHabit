package com.example.herohabit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    SQLiteDatabase dbTasks;
    private static final int SIGNIN_REQUEST_CODE = 1;
    public static final int TASKLIST_REQUEST_CODE = 2;
    private Button signIn;
    private Button logIn;
    private EditText usernameText;
    private EditText passwordText;
    private static String user = "undefined";
    private static String pass = "abcd*1234";

    //DATABASE ATTRIBUTES
    private static final String USERS = "users";
    private static final String ID = "id";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EXPERIENCE = "xp";
    private static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS " + USERS + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USERNAME + " TEXT, "
            + PASSWORD + " TEXT, "
            + EXPERIENCE + " INTEGER);";

    private static final String TASKS = "tasks";
    private static final String TASK_ID = "id";
    private static final String USER_ID = "user_id";
    private static final String TTTLE = "title";
    private static final String CREATE_TABLE_TASKS = "CREATE TABLE IF NOT EXISTS " + TASKS + " ("
            + TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + USER_ID + " INTEGER, "
            + TTTLE + " TEXT);";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Context context = this;
        context.deleteDatabase("UserDatabase.db");*/

        signIn = findViewById(R.id.btnSignIn);
        logIn = findViewById(R.id.btnLogIn);
        usernameText = findViewById(R.id.editTextTextPersonName);
        passwordText = findViewById(R.id.editTextTextPassword);
        db=openOrCreateDatabase("UserDatabase.db", Context.MODE_PRIVATE,null);
        db.execSQL(CREATE_TABLE_USERS);

        dbTasks=openOrCreateDatabase("TaskDatabase.db", Context.MODE_PRIVATE,null);
        dbTasks.execSQL(CREATE_TABLE_TASKS);

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

                //ArrayAdapter<String> adaptador;
                User user = new User();
                //String getUserQuery = "SELECT * FROM users where username =" + usernameText + " and password =" + passwordText;
                String selectQuery = "SELECT * FROM " + USERS + " WHERE " + USERNAME + " = '" + usernameText.getText() + "' AND " + PASSWORD + " = '" + passwordText.getText() + "';";

                // Ejecutar la consulta
                Cursor cursor = db.rawQuery(selectQuery, null);

                if (cursor != null && cursor.moveToFirst()) {
                    int id = cursor.getInt(0);

                    cursor.close();
                    Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
                    intent.putExtra("user_id", id);
                    setResult(RESULT_OK, intent);
                    startActivityForResult(intent, TASKLIST_REQUEST_CODE);
                    // El usuario y la contraseña son correctos, puedes acceder a los datos de la fila
                    /*int id = cursor.getInt(0);
                    int experiencia = cursor.getInt(4);*/
                    // Haz algo con los datos, por ejemplo, muestra información o realiza alguna acción
                    //Log.d("Database", "Usuario encontrado - ID: " + id + ", Experiencia: " + experiencia);
                    // Cierra el cursor cuando hayas terminado de usarlo


                } else {
                    // Usuario o contraseña incorrectos, o no se encontró el usuario
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /*@Override
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
    }*/
}