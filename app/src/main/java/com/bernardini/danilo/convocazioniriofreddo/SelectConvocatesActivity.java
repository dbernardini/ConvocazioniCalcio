package com.bernardini.danilo.convocazioniriofreddo;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;

public class SelectConvocatesActivity extends AppCompatActivity {

    private static final String TAG = "SelectConvocateActivity";
    private static final String CONVOCATES = "convocates";
    private static final String NAME = "name";
    private ArrayList<String> mPlayersList = new ArrayList<>();
    ArrayList<String> selectedItems = new ArrayList<>();
    private DBManager dbManager;
    private String[] mConvocates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_convocates);

        ListView convocatesListView = (ListView) findViewById(R.id.convocates_listview);
        convocatesListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        dbManager = new DBManager(this);
        Cursor result = dbManager.queryPlayers();
        result.moveToFirst();
        for (int i = 0; i < result.getCount(); i++){
            String player = result.getString(result.getColumnIndex(NAME));
            mPlayersList.add(player);
            result.moveToNext();
        }
        if (!mPlayersList.isEmpty())
            Collections.sort(mPlayersList.subList(0, mPlayersList.size()));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.call_list_item,
                R.id.checkedTextView, mPlayersList);
        convocatesListView.setAdapter(adapter);
        convocatesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = ((TextView)view).getText().toString();
                Log.i(TAG, selectedItem);
                if (selectedItems.contains(selectedItem)){
                    selectedItems.remove(selectedItem);
                }
                else {
                    selectedItems.add(selectedItem);
                }
            }
        });

        Intent intent = getIntent();
        mConvocates = intent.getStringArrayExtra(CONVOCATES);
        if (mConvocates != null){
            for (int i = 0; i < mConvocates.length; i++){
                String player = mConvocates[i];
                int index = mPlayersList.indexOf(player);
                convocatesListView.setItemChecked(index, true);
                selectedItems.add(player);
            }
        }

    }

    public void showSelectedItems(View v){
        mConvocates = selectedItems.toArray(new String[selectedItems.size()]);
        Intent i = getIntent();
        i.putExtra(CONVOCATES, mConvocates);
        setResult(RESULT_OK, i);
    }


}
