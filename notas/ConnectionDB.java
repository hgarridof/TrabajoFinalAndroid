package com.hgf.android.notas;

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

     public ConnectionDB(Context context) {
        super(context, DB, null, 2);
    }
    //Inicializa la Base de Datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + table + " (" + table_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                title + " TEXT, " + content + " TEXT, "+ fecha +" TEXT)");

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

    //Insertar datos en la Base de Datos
    public void addNote(String titleadd, String contentadd, String fechaadd){
        ContentValues values = new ContentValues();
        values.put(title, titleadd);
        values.put(content, contentadd);
        values.put(fecha, fechaadd);
        this.getWritableDatabase().insert(table, null, values);
    }

    public void removeNotas(int id){
        String SQL = "DELETE FROM " + table + " WHERE " + table_id + "=" + id+ " ";
        this.getWritableDatabase().execSQL(SQL);

    }

    //Recupera las notas de la Base de Datos
    public Cursor getNotas(){
        String columnas[] ={table_id, title, content, fecha};
        Cursor c  = this.getReadableDatabase().query(table, columnas, null, null, null, null, null);
        return c;
    }

    public void modifyNote(int id, String titlemodify, String contentmodify){
        String SQL = "UPDATE " + table + " SET " + title +"=\"" + titlemodify + "\", " + content + "=\"" + contentmodify  + "\" WHERE " + table_id + "=" + id;
        this.getWritableDatabase().execSQL(SQL);

    }


}
















