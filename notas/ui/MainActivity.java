package com.hgf.android.notas.ui;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
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


/*
*
*Función principal
* Futuras Modificaciones:
*   Añadir la opción de abrir las notas desde el mapa
*   Mejorar el aspecto del menú y de la lista
*   Añadir la opción de introducir objetos en las notas
*
*/

public class MainActivity extends AppCompatActivity {

    ListView lista;
    Button insertar, mapa, salir;
    static List<Note> item;
    public static final String NOTA = "NOTA";


    ArrayAdapter<Note> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLista();
        SDKVersion();

    }
    //Comprobamos la versión del SDK

    private void SDKVersion() {
        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        if (Build.VERSION.SDK_INT == 9) {
            botonInsertar();
            botonMapa();
            botonSalir();
        }
    }

    //Función para recoger la infrmación de todas las notas registradas en la base de datos
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

    public void setLista(){
        lista = (ListView) findViewById(R.id.listaNotasListView);

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
        }
    }


    //Función para ejecutar los botones para la versón 9 del JDK
    public void botonInsertar(){
        insertar = (Button)findViewById(R.id.insertar);
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddData.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void botonMapa(){

        mapa  = (Button)findViewById(R.id.mapa);
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void botonSalir(){

        salir  = (Button)findViewById(R.id.salir);
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    //Funciones del menú para las versiones superiores al JDK9
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
            finish();
        }
        if (id == R.id.action_Maps) {
            showNotes();
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.Salir){
            MainActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
