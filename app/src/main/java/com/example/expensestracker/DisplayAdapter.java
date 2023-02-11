//package com.example.expensestracker;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//public class DisplayAdapter extends BaseAdapter {
//    private Context mContext;
//    //list fields to be displayed
//    private ArrayList<Expense> arrayList;
//
//
//    public DisplayAdapter(Context c, ArrayList<Expense> arrayList, ArrayList<String> amount_expense, ArrayList<String> date_expense) {
//        this.mContext = c;
//        //transfer content from database to temporary memory
//        this.arrayList = arrayList;
//
//    }
//
//    @Override
//    public int getCount() {
//        return arrayList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return arrayList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//
//    public View getView(int pos, View convertView, ViewGroup parent) {
//        if (convertView==null){
//            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.listcell, null);
//            TextView type_expense = (TextView)convertView.findViewById(R.id.type_expense);
//            TextView amount_expense = (TextView)convertView.findViewById(R.id.amount_expense);
//            TextView date_expense = (TextView)convertView.findViewById(R.id.date_expense);
//
//
//
//        }
//    }
//}