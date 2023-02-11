package com.example.expensestracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddExpense extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    EditText et1_add_expense,et2_add_expense,et3_add_expense;
    Button btn1_add_expense;
    Spinner spinner1;
    DBHelper db;
    Date date1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db=new DBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__expense);
        et1_add_expense=(EditText)findViewById(R.id.et1_add_expense);
        et2_add_expense=(EditText)findViewById(R.id.et2_add_expense);
        et3_add_expense=(EditText)findViewById(R.id.et3_add_expense);
        btn1_add_expense= (Button)findViewById(R.id.btn1_add_expense);
        spinner1=(Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.Expense_Type, android.R.layout.simple_selectable_list_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(this);
        et1_add_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View dialogView = View.inflate(AddExpense.this, R.layout.date_time_picker, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(AddExpense.this).create();

                dialogView.findViewById(R.id.date_time_set).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                        TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);

                        Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                                datePicker.getMonth(),
                                datePicker.getDayOfMonth(),
                                timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute());
                        date1 = calendar.getTime();
                        SimpleDateFormat sdf;
                        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        et1_add_expense.setText( sdf.format(calendar.getTime()));
                        alertDialog.dismiss();
                    }});
                alertDialog.setView(dialogView);
                alertDialog.show();
            }
        });
        et1_add_expense.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            final View dialogView = View.inflate(AddExpense.this, R.layout.date_time_picker, null);
            final AlertDialog alertDialog = new AlertDialog.Builder(AddExpense.this).create();
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.date_picker);
                TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker);
                if(hasFocus){
                    Calendar calendar = new GregorianCalendar(datePicker.getYear(),
                            datePicker.getMonth(),
                            datePicker.getDayOfMonth(),
                            timePicker.getCurrentHour(),
                            timePicker.getCurrentMinute());
                    date1 = calendar.getTime();
                    SimpleDateFormat sdf;
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    et1_add_expense.setText( sdf.format(calendar.getTime()));
                    alertDialog.dismiss();
                }
            }
        });
        btn1_add_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences(
                        "com.example.app", Context.MODE_PRIVATE);

                try {
                    date1 =formatter.parse(String.valueOf(et1_add_expense.getText()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(et1_add_expense.getText().toString().equals("")|| et2_add_expense.getText().toString().equals("")||et3_add_expense.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Enter all fields",Toast.LENGTH_LONG).show();
                }
                else {
                    int amount = Integer.parseInt(et3_add_expense.getText().toString());
                    String text = (spinner1.getSelectedItem().toString());
                    String description = (et2_add_expense.getText().toString());
                    String userEmail = prefs.getString("loggedInUser", "");


                    if (userEmail.length() > 0) {
                        db.addExpense(userEmail, amount, text, description, date1);
                    }
                    Intent intent1 = new Intent(getApplicationContext(),Budget.class);
                    startActivity(intent1);

                }

            }
        });
        Button btn2 = (Button)findViewById(R.id.btn2_add_expense);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Budget.class);
                startActivity(intent);
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
            Intent intent = new Intent(AddExpense.this,MainActivity.class);
            startActivity(intent);
        }
        return true;
    }
}