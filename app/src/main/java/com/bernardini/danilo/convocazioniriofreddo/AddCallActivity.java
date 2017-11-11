package com.bernardini.danilo.convocazioniriofreddo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import static android.R.id.list;

public class AddCallActivity extends AppCompatActivity {

    private static final int SELECT_CONVOCATES = 1;
    private static final String CONVOCATES = "convocates";
    private static EditText mCallTime;
    private static TextView mConvocatesNumber;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private String date_time;
    private EditText mDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_call);
        getSupportActionBar().setTitle("Crea convocazione");
        mDateTime = (EditText) findViewById(R.id.date_time);
        mCallTime = (EditText) findViewById(R.id.call_time);
        mConvocatesNumber = (TextView) findViewById(R.id.convocates_number);

        Spinner homeSpinner = (Spinner) findViewById(R.id.home_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.teams, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        homeSpinner.setPrompt("Squadra di casa");
        homeSpinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.home_spinner_row_nothing_selected,
                        this));

        Spinner awaySpinner = (Spinner) findViewById(R.id.away_spinner);
        awaySpinner.setPrompt("Squadra ospite");
        awaySpinner.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        adapter,
                        R.layout.away_spinner_row_nothing_selected,
                        this));

        Button convocatesButton = (Button) findViewById(R.id.convocates_button);
        convocatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SelectConvocatesActivity.class);
                startActivityForResult(i, SELECT_CONVOCATES);
            }
        });
//        String[] convocatesList = getResources().getStringArray(R.array.players);
//        ListView convocates = (ListView) findViewById(R.id.convocates_listview);
//        final ArrayAdapter arrayAdapter = new ArrayAdapter(this,
//                android.R.layout.simple_list_item_1, convocatesList);
//        convocates.setAdapter(arrayAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_CONVOCATES) {
            if (resultCode == RESULT_OK) {
                String[] convocates = data.getStringArrayExtra(CONVOCATES);
                mConvocatesNumber.setText("Convocati: " + convocates.length);
                mConvocatesNumber.setTextColor(Color.parseColor("#212121"));
            }
        }
    }

    public void datePicker(View v){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {

                        date_time = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        //*************Call Time Picker Here ********************
                        timePicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void timePicker(){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,int minute) {

                        mHour = hourOfDay;
                        mMinute = minute;
                        String min = Integer.toString(minute);
                        if (min.equals("0"))
                            min = "00";
                        mDateTime.setText(date_time+", "+hourOfDay + ":" + min);
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    public void showCallTimePickerDialog(View v) {
        DialogFragment newFragment = new CallTimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public static class CallTimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            String min = Integer.toString(minute);
            if (min.equals("0"))
                min = "00";
            mCallTime.setText(hourOfDay + ":" + min);
        }
    }

}
