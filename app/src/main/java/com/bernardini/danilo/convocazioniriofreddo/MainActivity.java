package com.bernardini.danilo.convocazioniriofreddo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DBManager";

    private static final String HOME = "home";
    private static final String AWAY = "away";
    private static final String DATE = "date";
    private static final String PLACE = "place";
    private static final String CALL_PLACE = "call_place";
    private static final String CALL_TIME = "call_time";
    private static final String NOTES = "notes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button addCallButton = (Button) findViewById(R.id.add_call_button);
//        addCallButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), AddCallActivity.class);
//                startActivity(i);
//            }
//        });

        ListView callsList = (ListView) findViewById(R.id.calls_list);

        final ArrayList<Call> calls = new ArrayList<>();

        DBManager dbManager = new DBManager(this);

        Cursor result = dbManager.queryPlayers();
        result.moveToFirst();
        if (result.getCount() == 0){
            dbManager.insertPlayer("Alivernini Marco");
            dbManager.insertPlayer("Artibani Francesco");
            dbManager.insertPlayer("Artibani Giuseppe");
            dbManager.insertPlayer("Basile Gianluca");
            dbManager.insertPlayer("Cicchetti Simone");
            dbManager.insertPlayer("Cococcia Angelo");
            dbManager.insertPlayer("Colombi Alfredo");
            dbManager.insertPlayer("Conti Stefano");
            dbManager.insertPlayer("Costanzo Salvatore");
            dbManager.insertPlayer("D'Antimi Alessio");
            dbManager.insertPlayer("D'Antimi Matteo");
            dbManager.insertPlayer("D'Antimi Stefano");
            dbManager.insertPlayer("D'Auria Andrea");
            dbManager.insertPlayer("De Santis Filippo");
            dbManager.insertPlayer("De Santis Mattia");
            dbManager.insertPlayer("Dessì Flavio");
            dbManager.insertPlayer("Giustini Daniele");
            dbManager.insertPlayer("Giustini Patrizio");
            dbManager.insertPlayer("Masci Antonio");
            dbManager.insertPlayer("Palma Mario");
            dbManager.insertPlayer("Portieri Emanuele");
            dbManager.insertPlayer("Rainaldi Daniele (78)");
            dbManager.insertPlayer("Rainaldi Daniele (97)");
            dbManager.insertPlayer("Saccucci Valerio");
            dbManager.insertPlayer("Sebastiani Alessandro");
            dbManager.insertPlayer("Sebastiani Francesco");
        }
        result = dbManager.queryCalls();
        result.moveToFirst();
        if (result.getCount() == 0){
            dbManager.insertCall("Virtus Ri.Va.", "Cavaliere Camerata","25/11/2017, 15:30","La Crocetta","Bar","14:30","Puntuali");
            dbManager.insertCall("Arsoli", "Virtus Ri.Va.","03/12/2017, 15:30","Arsoli","Bar","14:30","");
            dbManager.insertCall("Virtus Ri.Va.", "Rocca Santo Stefano","10/12/2017, 15:00","La Crocetta","Bar","14:30","");
            dbManager.insertCall("Città di Castelmadama 1968", "Virtus Ri.Va.","17/12/2017, 15:00","La Crocetta","Bar","14:30","");
            dbManager.insertCall("Virtus Ri.Va.", "Arcinazzo Romano","14/01/2018, 15:30","La Crocetta","Bar","14:30","");
            dbManager.insertCall("Villa Gordiani", "Virtus Ri.Va.","25/11/2017, 15:30","La Crocetta","Bar","14:30","");

            dbManager.insertCall("Virtus Ri.Va.", "Cavaliere Camerata","25/11/2018, 15:30","La Crocetta","Bar","14:30","");
            dbManager.insertCall("Arsoli", "Virtus Ri.Va.","03/12/2018, 15:30","Arsoli","Bar","14:30","");
            dbManager.insertCall("Virtus Ri.Va.", "Rocca Santo Stefano","10/12/2018, 15:00","La Crocetta","Bar","14:30","");
            dbManager.insertCall("Città di Castelmadama 1968", "Virtus Ri.Va.","17/12/2018, 15:00","La Crocetta","Bar","14:30","");
            dbManager.insertCall("Virtus Ri.Va.", "Arcinazzo Romano","14/01/2017, 15:30","La Crocetta","Bar","14:30","");
            dbManager.insertCall("Villa Gordiani", "Virtus Ri.Va.","25/11/2018, 15:30","La Crocetta","Bar","14:30","");

            dbManager.insertCall("Virtus Ri.Va.", "Cavaliere Camerata","25/11/2019, 15:30","La Crocetta","Bar","14:30","");
            dbManager.insertCall("Arsoli", "Virtus Ri.Va.","03/12/2019, 15:30","Arsoli","Bar","14:30","");
            dbManager.insertCall("Virtus Ri.Va.", "Rocca Santo Stefano","10/12/2019, 15:00","La Crocetta","Bar","14:30","");
            dbManager.insertCall("Città di Castelmadama 1968", "Virtus Ri.Va.","17/12/2019, 15:00","La Crocetta","Bar","14:30","");
            dbManager.insertCall("Virtus Ri.Va.", "Arcinazzo Romano","14/01/2019, 15:30","La Crocetta","Bar","14:30","");
            dbManager.insertCall("Villa Gordiani", "Virtus Ri.Va.","25/11/2019, 15:30","La Crocetta","Bar","14:30","");
        }
        result = dbManager.queryPlayersCalled();
        result.moveToFirst();
        if (result.getCount() == 0) {
            dbManager.insertPlayersCalled("Alivernini Marco", "25/11/2017, 15:30");
            dbManager.insertPlayersCalled("Artibani Francesco", "25/11/2017, 15:30");
            dbManager.insertPlayersCalled("Artibani Giuseppe", "25/11/2017, 15:30");
            dbManager.insertPlayersCalled("Basile Gianluca", "25/11/2017, 15:30");
            dbManager.insertPlayersCalled("Cicchetti Simone", "25/11/2017, 15:30");
            dbManager.insertPlayersCalled("Cococcia Angelo", "25/11/2017, 15:30");
            dbManager.insertPlayersCalled("Colombi Alfredo", "25/11/2017, 15:30");
            dbManager.insertPlayersCalled("Conti Stefano", "25/11/2017, 15:30");
            dbManager.insertPlayersCalled("Costanzo Salvatore", "25/11/2017, 15:30");
            dbManager.insertPlayersCalled("D'Antimi Alessio", "25/11/2017, 15:30");
            dbManager.insertPlayersCalled("D'Antimi Matteo", "25/11/2017, 15:30");
            dbManager.insertPlayersCalled("D'Antimi Stefano", "25/11/2017, 15:30");
            dbManager.insertPlayersCalled("D'Auria Andrea", "25/11/2017, 15:30");
            dbManager.insertPlayersCalled("De Santis Filippo", "25/11/2017, 15:30");
            dbManager.insertPlayersCalled("De Santis Mattia", "25/11/2017, 15:30");
            dbManager.insertPlayersCalled("Dessì Flavio", "25/11/2017, 15:30");
            dbManager.insertPlayersCalled("Giustini Daniele", "25/11/2017, 15:30");
            dbManager.insertPlayersCalled("Giustini Patrizio", "25/11/2017, 15:30");

//            dbManager.insertPlayersCalled("Masci Antonio", "25/11/2017, 15:30");
//            dbManager.insertPlayersCalled("Palma Mario", "25/11/2017, 15:30");
//            dbManager.insertPlayersCalled("Portieri Emanuele", "25/11/2017, 15:30");
//            dbManager.insertPlayersCalled("Rainaldi Daniele (78)", "25/11/2017, 15:30");
//            dbManager.insertPlayersCalled("Rainaldi Daniele (97)", "25/11/2017, 15:30");
//            dbManager.insertPlayersCalled("Saccucci Valerio", "25/11/2017, 15:30");
//            dbManager.insertPlayersCalled("Sebastiani Alessandro", "25/11/2017, 15:30");
//            dbManager.insertPlayersCalled("Sebastiani Francesco", "25/11/2017, 15:30");
        }


        Cursor cursor = dbManager.queryCalls();
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++){
            String home = cursor.getString(cursor.getColumnIndex(HOME));
            String away = cursor.getString(cursor.getColumnIndex(AWAY));
            String date = cursor.getString(cursor.getColumnIndex(DATE));
            String place = cursor.getString(cursor.getColumnIndex(PLACE));
            String callPlace = cursor.getString(cursor.getColumnIndex(CALL_PLACE));
            String callTime = cursor.getString(cursor.getColumnIndex(CALL_TIME));
            String notes = cursor.getString(cursor.getColumnIndex(NOTES));
            Call call = new Call(home,away,place,date,callPlace,callTime,notes);
            calls.add(call);
            cursor.moveToNext();
        }

        CallsAdapter callsAdapter = new CallsAdapter(this, R.layout.call_row, calls);
        callsList.setAdapter(callsAdapter);
        
        callsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Call call = calls.get(i);
                Intent callIntent = new Intent(getApplicationContext(), CallActivity.class);
                callIntent.putExtra(DATE, call.getDate());
                startActivity(callIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_players_list:
                Intent playersIntent = new Intent(getApplicationContext(), PlayersActivity.class);
                startActivity(playersIntent);
                return true;
            case R.id.action_info:
                Intent infoIntent = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(infoIntent);
                return true;
            case R.id.action_add_call:
                Intent i = new Intent(getApplicationContext(), AddCallActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
