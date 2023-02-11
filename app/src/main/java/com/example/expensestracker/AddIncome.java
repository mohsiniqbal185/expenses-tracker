package com.example.expensestracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddIncome extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    EditText et1_add_income,et2_add_income;
    Spinner spinner1;
    Button btn1;
    DBHelper db;
    Date date1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__income);
        db= new DBHelper(this);
        et1_add_income=(EditText)findViewById(R.id.et1_add_income);
        spinner1=(Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.percentage, android.R.layout.simple_selectable_list_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);

        Button btn2 = (Button)findViewById(R.id.btn2_add_income);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),Budget.class);
                startActivity(intent1);
            }
        });
        btn1=(Button)findViewById(R.id.btn1_add_income);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int budget=0;
                try {
                    if(et1_add_income.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"Fill all fields",Toast.LENGTH_LONG).show();
                    }
                    else{
                        SharedPreferences prefs = getSharedPreferences(
                                "com.example.app", Context.MODE_PRIVATE);
                        int percentage_budget = Integer.parseInt(spinner1.getSelectedItem().toString());
                        int Income = Integer.parseInt(et1_add_income.getText().toString());
                        budget = Income * percentage_budget / 100;
                        String userEmail = prefs.getString("loggedInUser", "");
                        if( userEmail.length()>0) {
                            db.updateIncome(userEmail, Income, percentage_budget, date1);

                        }
                            Intent intent = new Intent(getApplicationContext(),Budget.class);
                            startActivity(intent);
                    }

                }

                catch (NumberFormatException e){
                    Toast.makeText(AddIncome.this, "Enter valid number",Toast.LENGTH_SHORT).show();
                }




            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.menu1){
            Intent intent = new Intent(AddIncome.this,MainActivity.class);
            startActivity(intent);
        }
        return true;
    }

}