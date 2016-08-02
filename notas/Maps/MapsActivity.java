package com.hgf.android.notas.Maps;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hgf.android.notas.Notes.Note;
import com.hgf.android.notas.R;
import com.hgf.android.notas.db.ContentProviderBD;
import com.hgf.android.notas.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hector on 24/6/16.
 */


public class MapsActivity extends Activity {

    GoogleMap mMap;
    MapFragment mMapFragment;

    double longitud;
    double latitud;
    List<Note> item;
    Note mNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        showNotes();
        setUpMapIfNeeded();


        for(int index = 0; index < item.size(); index++){
            LatLng nota = null;
            mNote = item.get(index);
            Double lat = mNote.getLatitud();
            double lon = mNote.getLongitud();

            nota = new LatLng(lat, lon);
            Log.d("ERROR ",mNote.getTitulo()+" LatLon: " + nota.toString());

            setMarker(nota, mNote.getTitulo()); // Agregamos el marcador verde

        }


    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setUpMapIfNeeded() {
// Configuramos el objeto GoogleMaps con valores iniciales.
        if (mMap == null) {
            //Instanciamos el objeto mMap a partir del MapFragment definido bajo el Id "map"
            mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            mMap = mMapFragment.getMap();

            // Chequeamos si se ha obtenido correctamente una referencia al objeto GoogleMap
            if (mMap != null) {
                // El objeto GoogleMap ha sido referenciado correctamente
                //ahora podemos manipular sus propiedades

                //Seteamos el tipo de mapa
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


                //Activamos la capa o layer MyLocation
                mMap.setMyLocationEnabled(true);


            }
        }
    }

    private void setMarker(LatLng position, String titulo) {

        // Agregamos marcadores para indicar sitios de interéses.
        mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(titulo));  //Agrega un titulo al marcador

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

    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

}
