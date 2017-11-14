package com.bernardini.danilo.convocazioniriofreddo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import static android.R.id.list;

public class AddCallActivity extends AppCompatActivity {

    private static final String TAG = "AddCallActivity";
    private static final String TEAM_NAME = "team_name";
    private static final int SELECT_CONVOCATES = 1;
    private static final String CONVOCATES = "convocates";
    private static TextView mConvocatesNumber;
    private String[] mConvocates;
    private Spinner mHome;
    private Spinner mAway;
    private EditText mMatchPlace;
    private EditText mDateTime;
    private EditText mCallPlace;
    private static EditText mCallTime;
    private EditText mNotes;

    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private String date_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_call);
        getSupportActionBar().setTitle("Crea convocazione");

        mHome = (Spinner) findViewById(R.id.home_spinner);
        mAway = (Spinner) findViewById(R.id.away_spinner);
        mDateTime = (EditText) findViewById(R.id.date_time);
        mMatchPlace = (EditText) findViewById(R.id.match_place);
        mCallPlace = (EditText) findViewById(R.id.call_place);
        mCallTime = (EditText) findViewById(R.id.call_time);
        mNotes = (EditText) findViewById(R.id.notes);
        mConvocatesNumber = (TextView) findViewById(R.id.convocates_number);

        ArrayList<String> teamsList = new ArrayList<>();
        DBManager dbManager = new DBManager(this);
        Cursor result = dbManager.queryTeams();
        result.moveToFirst();
        for (int i = 0; i < result.getCount(); i++){
            String team = result.getString(result.getColumnIndex(TEAM_NAME));
            teamsList.add(team);
            result.moveToNext();
        }
        if (!teamsList.isEmpty())
            Collections.sort(teamsList.subList(0, teamsList.size()));

        Spinner homeSpinner = (Spinner) findViewById(R.id.home_spinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                teamsList, android.R.layout.simple_spinner_item);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddCallActivity.this, android.R.layout.simple_spinner_item, teamsList);
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
                i.putExtra(CONVOCATES, mConvocates);
                startActivityForResult(i, SELECT_CONVOCATES);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_CONVOCATES) {
            if (resultCode == RESULT_OK) {
                mConvocates = data.getStringArrayExtra(CONVOCATES);
                mConvocatesNumber.setText("Convocati: " + mConvocates.length);
                mConvocatesNumber.setTextColor(Color.parseColor("#212121"));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_call, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_save:
                if (mHome.getSelectedItem() == null ||
                        mAway.getSelectedItem() == null ||
                        mDateTime.getText().toString().equals("")){
                    Toast.makeText(this, "Riempi i campi mancanti!", Toast.LENGTH_LONG).show();
                }
                else if (mConvocates == null){
                    Toast.makeText(this, "Seleziona i convocati!", Toast.LENGTH_LONG).show();
                }
                else {
                    DBManager dbManager = new DBManager(this);
                    dbManager.insertCall(mHome.getSelectedItem().toString(),
                            mAway.getSelectedItem().toString(), mDateTime.getText().toString(),
                            mMatchPlace.getText().toString(), mCallPlace.getText().toString(),
                            mCallTime.getText().toString(), mNotes.getText().toString());

                    for (String convocate : mConvocates) {
                        dbManager.insertPlayersCalled(convocate, mDateTime.getText().toString());
                    }
                    Toast.makeText(this, "Convocazione salvata", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
                        String day = Integer.toString(dayOfMonth);
                        if (day.length() == 1)
                            day = "0" + day;
                        monthOfYear += 1;
                        String month = Integer.toString(monthOfYear);
                        if (month.length() == 1)
                            month = "0" + month;
                        date_time = day + "/" + month + "/" + year;

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
                        String hour = Integer.toString(hourOfDay);
                        if (hour.length() == 1)
                            hour = "0" + hour;
                        mMinute = minute;
                        String min = Integer.toString(minute);
                        if (min.length() == 1)
                            min = "0" + min;
                        mDateTime.setText(date_time + ", " + hour + ":" + min);
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
            String hour = Integer.toString(hourOfDay);
            if (hour.length() == 1)
                hour = "0" + hour;
            String min = Integer.toString(minute);
            if (min.length() == 1)
                min = "0" + min;
            mCallTime.setText(hour + ":" + min);
        }
    }

}
