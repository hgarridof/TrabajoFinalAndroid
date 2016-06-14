package com.hgf.android.notas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.widget.Toast.*;


public class MainActivity extends AppCompatActivity {

    ListView lista;
    ConnectionDB db;
    static List<Notas> item;
    public static final String NOTA = "NOTA";


    ArrayAdapter<Notas> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.listaNotasListView);

        showNotes();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Notas tareaActual = adapter.getItem(position);
                String msg = "Elegiste la Nota: "+tareaActual.getId()+"-"+tareaActual.getTitulo();
                Toast.makeText(MainActivity.this,msg, LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, ModifyData.class);
                intent.putExtra("NotaId", tareaActual.getId());
                intent.putExtra("NotaTitulo", tareaActual.getTitulo());
                intent.putExtra("NotaCuerpo", tareaActual.getContexto());
                startActivity(intent);
            }
        });

    }



    private void showNotes(){
        db = new ConnectionDB(this);
        Cursor c = db.getNotas();
        String title = "", content = "", fecha ="";
        int id = 0;
        item = new ArrayList<Notas>();


        //Recorremos todos los registros
        if (c.moveToFirst()) {
            do {
                id = c.getInt(0);
                title = c.getString(1).toString();
                content = c.getString(2).toString();
                fecha = c.getString(3).toString();
                item.add(new Notas(id, title, content, fecha));
            } while (c.moveToNext());
        }
        adapter = new NotaAdapter<Notas>(
                this, item);
        //Relacionando la lista con el adaptador
        lista.setAdapter(adapter);

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
        if (id == R.id.action_AddData) {
            Intent intent = new Intent(MainActivity.this, AddData.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
