package com.bernardini.danilo.convocazioniriofreddo;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class CallActivity extends AppCompatActivity {

    private static final String TAG = "CallActivity";
    public static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 13;

    private static final String HOME = "home";
    private static final String AWAY = "away";
    private static final String DATE = "date";
    private static final String PLACE = "place";
    private static final String CALL_PLACE = "call_place";
    private static final String CALL_TIME = "call_time";
    private static final String NOTES = "notes";
    private static final String NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        getSupportActionBar().setTitle("Convocazione");

        Intent intent = getIntent();
        String date = intent.getStringExtra(DATE);

        String home = "";
        String away = "";
        String place = "";
        String callPlace = "";
        String callTime = "";
        String notes = "";
        ArrayList<String> convocates = new ArrayList<>();
        ArrayList<String> players = new ArrayList<>();

        TextView homeTextView = (TextView) findViewById(R.id.home);
        TextView awayTextView = (TextView) findViewById(R.id.away);
        TextView dateTextView = (TextView) findViewById(R.id.date);
        TextView placeTextView = (TextView) findViewById(R.id.place);
        TextView callPlaceTextView = (TextView) findViewById(R.id.call_place);
        TextView callTimeTextView = (TextView) findViewById(R.id.call_time);
        TextView notesTextView = (TextView) findViewById(R.id.notes);
        GridView convocatesGridView = (GridView) findViewById(R.id.convocates_list);

        DBManager dbManager = new DBManager(this);
        Cursor cursor = dbManager.queryCall(date);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++){
            home = cursor.getString(cursor.getColumnIndex(HOME));
            away = cursor.getString(cursor.getColumnIndex(AWAY));
            place = cursor.getString(cursor.getColumnIndex(PLACE));
            callPlace = cursor.getString(cursor.getColumnIndex(CALL_PLACE));
            callTime = cursor.getString(cursor.getColumnIndex(CALL_TIME));
            notes = cursor.getString(cursor.getColumnIndex(NOTES));
            cursor.moveToNext();
        }

        cursor = dbManager.queryPlayers();
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++){
            String player = cursor.getString(cursor.getColumnIndex(NAME));
            players.add(player);
            cursor.moveToNext();
        }

        cursor = dbManager.queryPlayersCalled(date);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            String player = cursor.getString(cursor.getColumnIndex(NAME));
            convocates.add(player);
            cursor.moveToNext();
        }

        String day = date.substring(8,10);
        String month = date.substring(5,7);
        String year = date.substring(0,4);
        String time = date.substring(11,16);
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

        homeTextView.setText(home);
        awayTextView.setText(away);
        dateTextView.setText(day + " " + monthLetters + " " + year + ", ore " + time);
        placeTextView.setText(place);
        callPlaceTextView.setText("Ritrovo: " + callPlace);
        callTimeTextView.setText("Orario: " + callTime);
        notesTextView.setText(notes);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.called_player_item, players);
        convocatesGridView.setAdapter(adapter);

        convocatesGridView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

        convocatesGridView.setEnabled(false);

        for (String convocate : convocates){
            int index = players.indexOf(convocate);
            convocatesGridView.setItemChecked(index, true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_share:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                } else {
                    takeScreenShot();
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void takeScreenShot(){
        LinearLayout rootView = (LinearLayout) findViewById(R.id.layout);
        rootView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);

        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
        File file = store(bitmap, now + ".jpg");
        shareImage(file);
    }

    private File store(Bitmap bm, String fileName){
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        File dir = new File(dirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    private void shareImage(File file){
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, "Condividi con"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Nessuna app per la condivisione disponibile", Toast.LENGTH_SHORT).show();
        }
    }
}
