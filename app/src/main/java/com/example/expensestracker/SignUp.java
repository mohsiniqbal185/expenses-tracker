package com.example.expensestracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    EditText et1_SignUp,et2_SignUp,et3_SignUp,et4_SignUp;
    Button btn1_SignUp, btn2_SignUp;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        db=new DBHelper(this);
         et1_SignUp= (EditText)findViewById(R.id.et1_SignUp);
         et2_SignUp= (EditText)findViewById(R.id.et2_SignUp);
         et3_SignUp= (EditText)findViewById(R.id.et3_SignUp);
         et4_SignUp= (EditText)findViewById(R.id.et4_SignUp);
         btn1_SignUp=(Button)findViewById(R.id.btn1_SignUp);
         btn2_SignUp=(Button)findViewById(R.id.btn2_SignUp);
        Intent intent= getIntent();
        btn1_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = et2_SignUp.getText().toString();
                String s2 = et3_SignUp.getText().toString();
                String s3 = et4_SignUp.getText().toString();
                String s4 = et1_SignUp.getText().toString();
                if(s1.equals("")|| s2.equals("") || s3.equals("")){
                    Toast.makeText(getApplicationContext(),"Fill all fields",Toast.LENGTH_LONG).show();
                }
                else{
                    if(s2.equals(s3)){
                        Boolean checkemail=db.checkemail(s1);
                        if(checkemail==true){
                            Boolean insert = db.insert(s1,s2,s4);
                            if(insert==true){

                                SharedPreferences prefs = getSharedPreferences(
                                        "com.example.app", Context.MODE_PRIVATE);

                                prefs.edit().putString("loggedInUser",s1).apply();
                                Toast.makeText(getApplicationContext(),"SignUp successful",Toast.LENGTH_LONG).show();
                                Intent intent1= new Intent(getApplicationContext(),Budget.class);
                                startActivity(intent1);
                            }
                        }
                    else{
                        Toast.makeText(getApplicationContext(),"Email already exists",Toast.LENGTH_LONG).show();
                    }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Passwords do not match",Toast.LENGTH_LONG);

                    }
                }

            }
        });
        btn2_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent2);
            }
        });



    }
}