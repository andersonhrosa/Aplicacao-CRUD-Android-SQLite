package com.example.formulario;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private EditText edtvlr1;
    private EditText edtvlr2;
    private EditText edtvlr3;
    private EditText edtvlr4;

    private ImageButton btprox;
    private ImageButton btlast;

    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtvlr1 = (EditText) findViewById(R.id.edtvlr1);
        edtvlr2 = (EditText) findViewById(R.id.edtvlr2);
        edtvlr3 = (EditText) findViewById(R.id.edtvlr3);
        edtvlr4 = (EditText) findViewById(R.id.edtvlr4);

        btprox = (ImageButton) findViewById(R.id.imageButton3);
        btlast = (ImageButton) findViewById(R.id.imageButton4);

        mDatabaseHelper = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        btprox.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
                overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
            }
        });

        btlast.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
                overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.create) {
            String newEntry1 = edtvlr1.getText().toString();
            String newEntry2 = edtvlr2.getText().toString();
            String newEntry3 = edtvlr3.getText().toString();
            String newEntry4 = edtvlr4.getText().toString();

            if (edtvlr1.length() != 0 && edtvlr2.length() != 0 && edtvlr3.length() != 0 && edtvlr4.length() != 0) {
                AddData(newEntry1, newEntry2, newEntry3, newEntry4);
                edtvlr1.setText("");
                edtvlr2.setText("");
                edtvlr3.setText("");
                edtvlr4.setText("");

            } else {
                Toast.makeText(getApplicationContext(),"Voce deve preencher todos os campos!",Toast.LENGTH_SHORT).show();
            }
            return true;

        } else if (id == R.id.read) {
            String cod = edtvlr1.getText().toString();
            if (edtvlr1.length() != 0 ) {
                Cursor data = mDatabaseHelper.getData(cod);
                if(data.getCount() > 0) {
                    data.moveToNext();
                    edtvlr2.setText(data.getString(2));
                    edtvlr3.setText(data.getString(3));
                    edtvlr4.setText(data.getString(4));
                } else {
                    Toast.makeText(getApplicationContext(),"Consulta Inválida, Código Inexistente!",Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(),"Voce deve preencher o campo de código para realizar a consulta!",Toast.LENGTH_SHORT).show();
            }

            return true;

        } else if (id == R.id.update) {
            String cod = edtvlr1.getText().toString();
            String name = edtvlr2.getText().toString();
            String address = edtvlr3.getText().toString();
            String phone = edtvlr4.getText().toString();
            String[] argumentos = { cod };

            if (edtvlr1.length() != 0 && edtvlr2.length() != 0 && edtvlr3.length() != 0 && edtvlr4.length() != 0) {
                UpdateData(argumentos, cod, name, address, phone);
            } else {
                Toast.makeText(getApplicationContext(),"Voce deve preencher todos os campos!",Toast.LENGTH_SHORT).show();
            }
            return true;

        } else if (id == R.id.delete) {
            String cod = edtvlr1.getText().toString();
            String[] args = { cod };
            RemoveData(args);
            return true;

        } else if (id == R.id.readall) {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
            overridePendingTransition(R.transition.slide_in, R.transition.slide_out);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void AddData(String newEntry1, String newEntry2, String newEntry3, String newEntry4) {
        boolean insertData = mDatabaseHelper.addData(newEntry1, newEntry2, newEntry3, newEntry4);

        if (insertData) {
            Toast.makeText(getApplicationContext(),"Dados Inseridos com Sucesso!",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),"Algo errado aconteceu!",Toast.LENGTH_SHORT).show();
        }
    }

    public void RemoveData(String[] codigo) {
        int numLinhas = mDatabaseHelper.removeData(codigo);

        if (numLinhas>0) {
            Toast.makeText(getApplicationContext(),"Dados Removidos com Sucesso! " + numLinhas + " Linha(s) Removida(s)",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),"Nenhuma linha removida!",Toast.LENGTH_SHORT).show();
        }
    }

    public void UpdateData(String[] codigo,String cod, String name, String address, String phone) {
        int numLinhas = mDatabaseHelper.updateData(codigo, cod, name, address, phone);

        if (numLinhas>0) {
            Toast.makeText(getApplicationContext(),"Dados Atualizados com Sucesso! " + numLinhas + " Linha(s) Afetada(s)",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),"Nenhum dado foi atualizado!",Toast.LENGTH_SHORT).show();
        }
    }
}