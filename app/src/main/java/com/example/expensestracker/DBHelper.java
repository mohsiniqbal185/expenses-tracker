package com.example.expensestracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(@Nullable Context context) {
        super(context, "ExpenseTracker.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("Create table if not exists user" + "(id integer primary key autoincrement,email text,password text, full_name text, monthly_salary integer, percentage_budget integer,budget integer, sal_date date)");
        db.execSQL("Create table if not exists expense(id integer primary key autoincrement, user_id REFERENCES user(id) on delete cascade, amount integer, name text, description text, expense_date text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean insert(String email, String password, String fullname) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("full_name", fullname);
        long ins = db.insert("user", null, contentValues);
        if (ins == -1) return false;
        else return true;
    }

    public Boolean checkemail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where email=?", new String[]{email});
        if (cursor.getCount() > 0) return false;
        else return true;
    }

    public Boolean emailpassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user where email=? and password=?", new String[]{email, password});
        if (cursor.getCount() > 0) return true;
        else return false;
    }

    public void createTablesIfNotExist() {
        SQLiteDatabase db = this.getWritableDatabase();
        this.onCreate(db);
    }

    public boolean updateIncome(String email, Integer monthly_salary, Integer percentage_budget, Date date) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("monthly_salary", monthly_salary);
        contentValues.put("percentage_budget", percentage_budget);
        contentValues.put("budget",monthly_salary*percentage_budget/100);
        long ins = db.update("user", contentValues, "email=$1", new String[]{email});
        if (ins == -1) return false;
        else return true;
    }

    public long getUserIdFromEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        long userId = 0;
        Cursor cursor = db.rawQuery("select id from user where email=?", new String[]{email});
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                userId = cursor.getLong(cursor.getColumnIndex("id"));
            }
            cursor.close();
        }
        return userId;
    }

    public boolean addExpense(String userEmail, int amount, String name, String description, Date expense_date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("amount", amount);
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("expense_date", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(expense_date));
        contentValues.put("user_id", getUserIdFromEmail(userEmail));
        long ins = db.insert("expense", null, contentValues);
        if (ins == -1) return false;
        else return true;
    }
    public int getBudget(String email) {
        int budget = 0;
        Cursor cursor = this.getReadableDatabase().rawQuery("select * from user where email=?", new String[]{email});
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                 budget = cursor.getInt(cursor.getColumnIndex("budget"));
            }
        }
        return budget;



    }

    public long getExpense(String email) {

        Date today = new Date(); // Fri Jun 17 14:54:28 PDT 2016
        Calendar cal = Calendar.getInstance(); cal.setTime(today); // don't forget this if date is arbitrary e.g. 01-01-2014 int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 6 int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH); // 17 int dayOfYear = cal.get(Calendar.DAY_OF_YEAR); //169 int month = cal.get(Calendar.MONTH); // 5 int year = cal.get(Calendar.YEAR);
        cal.set(Calendar.DATE,1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND, 0);

        Date startOfMonth = cal.getTime();
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND,cal.getActualMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getActualMaximum(Calendar.MILLISECOND));

        Date endOfMonth = cal.getTime();

        long expense = 0;
        Cursor cursor = this.getReadableDatabase().rawQuery(
                "select SUM(amount) total_expense from expense inner join user on expense.user_id=user.id where user.email=$1 and strftime('%Y-%m-%d', expense.expense_date) between $2 AND $3",
                new String[]{email, new SimpleDateFormat("yyyy-MM-dd").format(startOfMonth), new SimpleDateFormat("yyyy-MM-dd").format(endOfMonth)});
        if (cursor != null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                expense = cursor.getLong(cursor.getColumnIndex("total_expense"));
            }
        }
        return expense;
    }
    public void delete_From_Database(String Email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from expense inner join user on expense.user_id where user.email=?",new String[]{Email});
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
        db.execSQL("DELETE FROM expense WHERE user_id="+id+"");
    }

}
