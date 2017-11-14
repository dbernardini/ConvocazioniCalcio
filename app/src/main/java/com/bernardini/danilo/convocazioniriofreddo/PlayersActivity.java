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

public class PlayersActivity extends AppCompatActivity {

    private static final String NAME = "name";
    private DBManager dbManager;
    private ArrayList<String> mPlayersList = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        getSupportActionBar().setTitle("Lista giocatori");

        final EditText playerEditText = (EditText) findViewById(R.id.add_player);
        dbManager = new DBManager(this);
        Cursor result = dbManager.queryPlayers();
        result.moveToFirst();
        for (int i = 0; i < result.getCount(); i++){
            String player = result.getString(result.getColumnIndex(NAME));
            mPlayersList.add(player);
            result.moveToNext();
        }

        ListView playersListView = (ListView) findViewById(R.id.players_list);
        if (!mPlayersList.isEmpty())
            Collections.sort(mPlayersList.subList(0, mPlayersList.size()));
        playersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                playerEditText.clearFocus();
//                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });
        playersListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String clicked = mPlayersList.get(i);
                startConfirmationDialog(clicked);
                return true;
            }
        });
        mAdapter = new ArrayAdapter<>(this, R.layout.list_item, mPlayersList);
        playersListView.setAdapter(mAdapter);


        Button addPlayerButton = (Button) findViewById(R.id.add_player_button);
        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBManager dbManager = new DBManager(getApplicationContext());
                String newPlayer = playerEditText.getText().toString();
                dbManager.insertPlayer(newPlayer);
                playerEditText.setText("");
//                playerEditText.clearFocus();
                mPlayersList.add(newPlayer);
                if (!mPlayersList.isEmpty())
                    Collections.sort(mPlayersList.subList(0, mPlayersList.size()));
                mAdapter.notifyDataSetChanged();
            }
        });

    }

    private void startConfirmationDialog(final String player){
        AlertDialog.Builder dialog = new AlertDialog.Builder(PlayersActivity.this)
                .setTitle("Elimina giocatore")
                .setMessage("Eliminare " + player + " dalla lista giocatori?")
                .setPositiveButton("Elimina", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dbManager.deletePlayer(player);
                        mPlayersList.remove(player);
//                        if (!mPlayersList.isEmpty())
//                            Collections.sort(mPlayersList.subList(0, mPlayersList.size()));
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


