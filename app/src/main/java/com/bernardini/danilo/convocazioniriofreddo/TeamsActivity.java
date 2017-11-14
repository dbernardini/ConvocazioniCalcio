package com.bernardini.danilo.convocazioniriofreddo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class TeamsActivity extends AppCompatActivity {

    private static final String TEAM_NAME = "team_name";
    private DBManager dbManager;
    private ArrayList<String> mTeamsList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        getSupportActionBar().setTitle("Lista squadre");

        final EditText teamEditText = (EditText) findViewById(R.id.add_team);
        dbManager = new DBManager(this);
        Cursor result = dbManager.queryTeams();
        result.moveToFirst();
        for (int i = 0; i < result.getCount(); i++){
            String team = result.getString(result.getColumnIndex(TEAM_NAME));
            mTeamsList.add(team);
            result.moveToNext();
        }

        ListView teamsListView = (ListView) findViewById(R.id.teams_list);
        if (!mTeamsList.isEmpty())
            Collections.sort(mTeamsList.subList(0, mTeamsList.size()));
        teamsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                teamEditText.clearFocus();
//                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });
        teamsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String clicked = mTeamsList.get(i);
                startConfirmationDialog(clicked);
                return true;
            }
        });
        mAdapter = new ArrayAdapter<>(this, R.layout.list_item, mTeamsList);
        teamsListView.setAdapter(mAdapter);


        Button addTeamButton = (Button) findViewById(R.id.add_team_button);
        addTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBManager dbManager = new DBManager(getApplicationContext());
                String newTeam = teamEditText.getText().toString();
                dbManager.insertTeam(newTeam);
                teamEditText.setText("");
//                teamEditText.clearFocus();
                mTeamsList.add(newTeam);
                if (!mTeamsList.isEmpty())
                    Collections.sort(mTeamsList.subList(0, mTeamsList.size()));
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    private void startConfirmationDialog(final String team){
        AlertDialog.Builder dialog = new AlertDialog.Builder(TeamsActivity.this)
                .setTitle("Elimina squadra")
                .setMessage("Eliminare " + team + " dalla lista delle squadre?")
                .setPositiveButton("Elimina", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dbManager.deleteTeam(team);
                        mTeamsList.remove(team);
//                        if (!mTeamsList.isEmpty())
//                            Collections.sort(mTeamsList.subList(0, mTeamsList.size()));
                        mAdapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                });
        dialog.show();
    }
}


