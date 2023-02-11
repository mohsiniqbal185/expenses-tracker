package com.example.expensestracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static String EmailID , Password;
    EditText et1_main,et2_main;
    Button btn1_main,btn2_main;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db= new DBHelper(this);
        db.createTablesIfNotExist();


        SharedPreferences prefs = getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        String userEmail = prefs.getString("loggedInUser", "");
        if( userEmail.length()>0)
        {
            Intent intent2 = new Intent(MainActivity.this, Budget.class);
            startActivity(intent2);

        }
        et1_main=(EditText)findViewById(R.id.et1_main);
        et2_main=(EditText)findViewById(R.id.et2_main);
        btn1_main=(Button)findViewById(R.id.btn1_main);
        btn2_main=(Button)findViewById(R.id.btn2_main);
    }


    public void SignUp(View view) {
        Intent intent1=new Intent(MainActivity.this, SignUp.class);
        startActivity(intent1);
    }

    public void Log_into_Account(View view) {
        Intent intent2 = new Intent(MainActivity.this, Budget.class);
        String emailID= et1_main.getText().toString();
        String password= et2_main.getText().toString();
        Boolean checkemailpass = db.emailpassword(emailID,password);
        if(checkemailpass==true){

            SharedPreferences prefs = getSharedPreferences(
                    "com.example.app", Context.MODE_PRIVATE);

            prefs.edit().putString("loggedInUser",emailID).apply();
            Toast.makeText(getApplicationContext(),"Successfully Signed in",Toast.LENGTH_LONG).show();
            startActivity(intent2);
        }
        else{
            Toast.makeText(getApplicationContext(),"Email or password is incorrect",Toast.LENGTH_LONG).show();
        }
    }


}