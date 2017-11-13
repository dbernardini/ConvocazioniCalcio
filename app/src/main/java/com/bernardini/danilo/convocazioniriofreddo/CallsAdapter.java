package com.bernardini.danilo.convocazioniriofreddo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CallsAdapter extends ArrayAdapter {

    private List<Call> callsList;
    private int resource;
    private Context context;

    public CallsAdapter(Context context, int resource, List<Call> callsList) {
        super(context, resource, callsList);
        this.resource = resource;
        this.callsList = callsList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(resource, parent, false);
        TextView homeTextView = (TextView) row.findViewById(R.id.home);
        TextView awayTextView = (TextView) row.findViewById(R.id.away);
        TextView dateTextView = (TextView) row.findViewById(R.id.date);

        Call call = callsList.get(position);

        String home = call.getHome();
        String away = call.getAway();
        homeTextView.setText(home);
        awayTextView.setText(away);

        if(home.equals("Virtus Ri.Va.") || home.contains("Riofreddo"))
            homeTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        if(away.equals("Virtus Ri.Va.") || home.contains("Riofreddo"))
            awayTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

        String date = call.getDate();
        String year = date.substring(0,4);
        String month = date.substring(5,7);
        String day = date.substring(8,10);

        String monthLetters;
        switch (month){
            case "01":
                monthLetters = "gennaio";
                break;
            case "02":
                monthLetters = "febbraio";
                break;
            case "03":
                monthLetters = "marzo";
                break;
            case "04":
                monthLetters = "aprile";
                break;
            case "05":
                monthLetters = "maggio";
                break;
            case "06":
                monthLetters = "giugno";
                break;
            case "07":
                monthLetters = "luglio";
                break;
            case "08":
                monthLetters = "agosto";
                break;
            case "09":
                monthLetters = "settembre";
                break;
            case "10":
                monthLetters = "ottobre";
                break;
            case "11":
                monthLetters = "novembre";
                break;
            case "12":
                monthLetters = "dicembre";
                break;
            default:
                monthLetters = month;
                break;
        }

        dateTextView.setText(day + " " + monthLetters + " " + year);

        return row;
    }
}