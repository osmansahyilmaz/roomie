package com.example.roomie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.roomie.Expense;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExpenseAdapter extends ArrayAdapter<Expense> {
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public ExpenseAdapter(Context context, List<Expense> expenses) {
        super(context, 0, expenses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_expense, parent, false);
        }

        TextView textViewDescription = convertView.findViewById(R.id.textViewDescription);
        TextView textViewAmount = convertView.findViewById(R.id.textViewAmount);

        Expense expense = getItem(position);
        textViewDescription.setText(expense.getDescription());

        return convertView;
    }
}