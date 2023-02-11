package com.example.expensestracker;

import java.util.Date;

public class Expense {
    String type;
    int amount;
    Date date;
    public Expense(String type , int amount, Date date){
        this.type= type;
        this.amount = amount;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
