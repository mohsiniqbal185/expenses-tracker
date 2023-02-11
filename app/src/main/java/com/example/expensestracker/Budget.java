package com.example.expensestracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Budget extends AppCompatActivity {


    TextView tv_1,tv_2;
    Button btn1_budget,btn2_budget;
    DBHelper db1;
    private SQLiteDatabase dataBase;

    //variables to hold staff records
    private ArrayList<String> s_no = new ArrayList<String>();
    private ArrayList<String> type_expense = new ArrayList<String>();
    private ArrayList<String> amount_expense = new ArrayList<String>();
    private ArrayList<String> date_expense = new ArrayList<String>();
    private ListView userList;
    private AlertDialog.Builder build;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        btn1_budget=(Button)findViewById(R.id.btn1_budget);
        btn2_budget=(Button)findViewById(R.id.btn2_budget);
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2= (TextView)findViewById(R.id.tv_2);
        db1= new DBHelper(this);
        SharedPreferences prefs = getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        String userEmail = prefs.getString("loggedInUser", "");
        int budget =db1.getBudget(userEmail);
        Long Expense = db1.getExpense(userEmail);
        if (budget>0) {
            if (Expense / budget >= 0.8 && Expense / budget < 1) {
                tv_1.setText("Rs. " + db1.getBudget(userEmail));
                tv_2.setText("Rs. " + db1.getExpense(userEmail) + "\n***You are getting close to Your Budget");
                tv_2.setTextColor(Color.parseColor("#FF0000"));
            } else if (Expense / budget >= 1) {
                tv_1.setText("Rs. " + db1.getBudget(userEmail));
                tv_2.setText("Rs. " + db1.getExpense(userEmail) + "\n***You are getting over Your Budget");
                tv_2.setTextColor(Color.parseColor("#FF0000"));
            } else {
                tv_1.setText("Rs. " + db1.getBudget(userEmail));
                tv_2.setText("Rs. " + db1.getExpense(userEmail));
                tv_2.setTextColor(Color.parseColor("#8BC34A"));

            }
        }

        userList = (ListView) findViewById(R.id.listview1);
        SQLiteDatabase db = db1.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  expense.id as _id, expense.amount, expense.name, expense.expense_date ,expense.description FROM expense inner join user on expense.user_id = user.id where user.email=$1  order by expense.expense_date desc",
                new String[]{userEmail});


        userList.setAdapter(new CursorAdapter(Budget.this, cursor, false) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.listcell, parent, false);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView expense = (TextView) view.findViewById(R.id.type_expense);
                TextView amount = (TextView) view.findViewById(R.id.amount_expense);
                TextView date = (TextView) view.findViewById(R.id.date_expense);

                // Extract properties from cursor
                Long amt = cursor.getLong(cursor.getColumnIndexOrThrow("amount"));
                String dt = cursor.getString(cursor.getColumnIndexOrThrow("expense_date"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                // Populate fields with extracted properties
                if (!cursor.moveToFirst())
                    cursor.moveToFirst();
                expense.setText(String.valueOf(name));
                date.setText(String.valueOf(dt));
                amount.setText(String.valueOf(amt));

            }

        });
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (cursor != null && cursor.getCount() > 0) {

                        int id1 = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                        if (id1 == id) {
                            Long amt = cursor.getLong(cursor.getColumnIndexOrThrow("amount"));
                            String dt = cursor.getString(cursor.getColumnIndexOrThrow("expense_date"));
                            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                            String description = cursor.getString(cursor.getColumnIndex("description"));
                            Intent intent = new Intent(Budget.this, Expense_description.class);
                            intent.putExtra("Amount", String.valueOf(amt));
                            intent.putExtra("Date", String.valueOf(dt));
                            intent.putExtra("Name", String.valueOf(name));
                            intent.putExtra("Description", String.valueOf(description));
                            startActivity(intent);
                        }

                    }

                }





        });
        btn1_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Budget.this, AddIncome.class);

                startActivity(intent1);

            }

        });

        btn2_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2= new Intent(Budget.this, AddExpense.class);
                startActivity(intent2);
            }
        });
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
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            // Setting Alert Dialog Title
            alertDialogBuilder.setTitle("Confirm Exit..!!!");

            alertDialogBuilder.setMessage("Are you sure,You want to log out?");
            alertDialogBuilder.setCancelable(false);

            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    SharedPreferences prefs = getSharedPreferences(
                            "com.example.app", Context.MODE_PRIVATE);
                    String userEmail = prefs.getString("loggedInUser", "");
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.clear();
                    editor.commit();
                    Intent intent = new Intent(Budget.this,MainActivity.class);
                    startActivity(intent);
                }
            });

            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });


            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        return true;
        }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                         Budget.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    
}