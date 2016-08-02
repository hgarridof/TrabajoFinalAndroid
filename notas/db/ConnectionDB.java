package com.hgf.android.notas.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hector on 3/6/16.
 */
public class ConnectionDB extends SQLiteOpenHelper{

    //Strings Finales de los diferentes datos que se guardan en la Base de Datos
    public static final String table_id = "_idNote";
    public static final String title ="title";
    public static final String content = "content";
    public static final String image = "image";
    public static final String fecha = "fecha";
    //Nombre de la Base de Datos y de la tabla
    private static final String DB = "NotesDB";
    private static final String table = "notas";
    private static final String latitude = "latitud";
    private static final String longitud = "longitud";

    public ConnectionDB(Context contexto, String nombre,SQLiteDatabase.CursorFactory
            factory, int version) {

        super(contexto, nombre, factory, version);
    }

    //Inicializa la Base de Datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + table + " (" + table_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                title + " TEXT, " + content + " TEXT, "+ fecha +" TEXT, " + latitude + " TEXT, " + longitud + " TEXT)");


    }
    //Actualización de la Base de Datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table );
        onCreate(db);
    }

    public void close(){
        this.close();
    }


}
















