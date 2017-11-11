package com.bernardini.danilo.convocazioniriofreddo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addCallButton = (Button) findViewById(R.id.add_call_button);
        addCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddCallActivity.class);
                startActivity(i);
            }
        });

        Button showPlayersButton = (Button) findViewById(R.id.players_list_button);
        showPlayersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PlayersActivity.class);
                startActivity(i);
            }
        });
    }
}
