package com.hgf.android.notas.ui;

import android.app.ActionBar;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import com.hgf.android.notas.Maps.MapsActivity;
import com.hgf.android.notas.db.ConnectionDB;
import com.hgf.android.notas.Notes.Note;
import com.hgf.android.notas.Adapter.NoteAdapter;
import com.hgf.android.notas.R;
import com.hgf.android.notas.db.ContentProviderBD;

import java.io.Serializable;
import java.io.SerializablePermission;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ListView lista;
    Button insertar;
    Button mapa;
    static List<Note> item;
    public static final String NOTA = "NOTA";


    ArrayAdapter<Note> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.listaNotasListView);

        insertar = (Button)findViewById(R.id.insertar);
        mapa  = (Button)findViewById(R.id.mapa);

        TextView mEmptyTextView;
        mEmptyTextView = (TextView)findViewById(android.R.id.empty);

        showNotes();
        if (item == null) {
            lista.setEmptyView(mEmptyTextView);
        }else {
            adapter = new NoteAdapter<Note>(
                    this, item);
            //Relacionando la lista con el adaptador

            lista.setAdapter(adapter);
            lista.setEmptyView(mEmptyTextView);

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Note tareaActual = adapter.getItem(position);
                    String msg = "Elegiste la Nota: " + tareaActual.getId() + "-" + tareaActual.getTitulo();
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, NoteViewData.class);
                    intent.putExtra("NotaId", tareaActual.getId());
                    intent.putExtra("NotaTitulo", tareaActual.getTitulo());
                    intent.putExtra("NotaCuerpo", tareaActual.getContexto());
                    Toast.makeText(MainActivity.this, String.valueOf(tareaActual.getLatitud()) + ", " + String.valueOf(tareaActual.getLongitud()), Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            });
            /*
            insertar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Insertar", Toast.LENGTH_LONG).show();
                    //Intent intent = new Intent(MainActivity.this, AddData.class);
                    //startActivity(intent);
                }
            });

            mapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent intent = new Intent(MainActivity.this, com.hgf.android.notas.Maps.MapsActivity.class);
                    //startActivity(intent);
                }
            });
            */
        }
    }





    public List<Note> showNotes(){

        String[] projection = new String[] {
                ContentProviderBD.Note.COL_ID,
                ContentProviderBD.Note.COL_TITLE,
                ContentProviderBD.Note.COL_CONTENT,
                ContentProviderBD.Note.COL_DATE,
                ContentProviderBD.Note.COL_LATITUDE,
                ContentProviderBD.Note.COL_LONGITUDE
        };

        Uri notasUri =  ContentProviderBD.CONTENT_URI;

        ContentResolver cr = getContentResolver();

        //Hacemos la consulta
        Cursor cur = cr.query(notasUri,
                projection, //Columnas a devolver
                null,       //Condición de la query
                null,       //Argumentos variables de la query
                null);      //Orden de los resultados

        if (!(cur.moveToFirst()) || cur.getCount() ==0){

        }
        else if (cur.moveToFirst()) {
            int id = 0;
            String titulo;
            String contenido;
            String fecha;
            double longitud;
            double latitud;

            int colId = cur.getColumnIndex(ContentProviderBD.Note.COL_ID);
            int colTitulo = cur.getColumnIndex(ContentProviderBD.Note.COL_TITLE);
            int colContenido = cur.getColumnIndex(ContentProviderBD.Note.COL_CONTENT);
            int colFecha = cur.getColumnIndex(ContentProviderBD.Note.COL_DATE);
            int colLat = cur.getColumnIndex(ContentProviderBD.Note.COL_LATITUDE);
            int colLon = cur.getColumnIndex(ContentProviderBD.Note.COL_LONGITUDE);

            item = new ArrayList<Note>();

            do {
                id = cur.getInt(colId);
                titulo = cur.getString(colTitulo);
                contenido = cur.getString(colContenido);
                fecha = cur.getString(colFecha);
                latitud = cur.getDouble(colLat);
                longitud = cur.getDouble(colLon);
                item.add(new Note(id, titulo, contenido, fecha, latitud, longitud));

            } while (cur.moveToNext());
            cur.close();
        }

        return item;

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
        if (id == R.id.action_Maps) {
            showNotes();
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
