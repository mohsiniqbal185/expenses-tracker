package com.example.expensestracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Expense_description extends AppCompatActivity {
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_description);
        tv1= (TextView)findViewById(R.id.tv1_ed);
        TextView tv2 = (TextView)findViewById(R.id.tv2_expense_description);
        TextView tv3 = (TextView)findViewById(R.id.tv3_expense_description);
        TextView tv4 = (TextView)findViewById(R.id.tv4expense_description);
        Intent intent = getIntent();
        String name;
        String Amount=intent.getStringExtra("Amount");
        name = intent.getStringExtra("Name");
        String date = intent.getStringExtra("Date");
        String description = intent.getStringExtra("Description");
        tv2.setText("Amount: "+Amount);
        tv1.setText("Type of Expense: "+name);
        tv3.setText("Date and time of expense: "+date);
        tv4.setText("Description of expense: "+description);
        Button btn1= (Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2= new Intent(getApplicationContext(),Budget.class);
                startActivity(intent2);
            }
        });




    }
}