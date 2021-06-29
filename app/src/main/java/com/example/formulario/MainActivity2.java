package com.example.formulario;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    private ImageButton btfirst;
    private ImageButton btant;
    private ListView mListView;
    private DatabaseHelper mDatabaseHelper;
    private static final String TAG = "MainActivity2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btfirst = (ImageButton) findViewById(R.id.imageButton1);
        btant = (ImageButton) findViewById(R.id.imageButton2);

        mDatabaseHelper = new DatabaseHelper(this);
        mListView = (ListView) findViewById(R.id.listView);

        populateListView();

        btant.setOnClickListener (new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.transition.slide2_in_dir, R.transition.slide2_out_esq);

            }
        });

        btfirst.setOnClickListener (new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.transition.slide2_in_dir, R.transition.slide2_out_esq);
            }
        });
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        Cursor data = mDatabaseHelper.getAllData();
        ArrayList<String> jogadores = new ArrayList<>();
        jogadores.add("Código Nome Endereço Telefone");
        while(data.moveToNext()) {
            String linha = "    " + data.getString(1) + "    " + data.getString(2) + "    " +
                    data.getString(3) + "    " + data.getString(4);
            jogadores.add(linha);
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jogadores);
        mListView.setAdapter(adapter);
    }
}