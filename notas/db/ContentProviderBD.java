package com.hgf.android.notas.db;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by hector on 15/6/16.
 */
public class ContentProviderBD extends android.content.ContentProvider {

    //Definición del CONTENT_URI
    private static final String uri =
            "content://com.hgf.android.notas.contentproviders/notas";

    public static final Uri CONTENT_URI = Uri.parse(uri);

    //Base de datos
    private ConnectionDB mDB;
    private static final String BD_NOMBRE = "NotesDB";
    private static final int BD_VERSION = 10;
    private static final String TABLA_NOTAS = "notas";

    //UriMatcher
    private static final int NOTAS = 1;
    private static final int NOTAS_ID = 2;
    private static final UriMatcher uriMatcher;

    //Inicializamos el UriMatcher
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI("com.hgf.android.notas.contentproviders", "notas", NOTAS);
        uriMatcher.addURI("com.hgf.android.notas.contentproviders", "notas/#", NOTAS_ID);
    }

    //Clase interna para declarar las constantes de columna
    public static final class Note implements BaseColumns
    {
        private Note() {}

        //Nombres de columnas
        public static final String COL_ID = "_idNote";
        public static final String COL_TITLE = "title";
        public static final String COL_CONTENT = "content";
        public static final String COL_DATE = "fecha";
        public static final String COL_LATITUDE = "latitud";
        public static final String COL_LONGITUDE = "longitud";
    }




    @Override
    public boolean onCreate() {
        mDB = new ConnectionDB(getContext(), BD_NOMBRE, null, BD_VERSION);

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

            //Si es una consulta a un ID concreto construimos el WHERE
            String where = selection;
            if(uriMatcher.match(uri) == NOTAS_ID){
                where = "_idNote=" + uri.getLastPathSegment();
            }

            SQLiteDatabase db = mDB.getWritableDatabase();

            Cursor c = db.query(TABLA_NOTAS, projection, where,
                    selectionArgs, null, null, sortOrder);

            return c;
    }

    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match)
        {
            case NOTAS:
                return "vnd.android.cursor.dir/vnd.hgf.notas";
            case NOTAS_ID:
                return "vnd.android.cursor.item/vnd.hgf.notas";
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long regId = 1;

        SQLiteDatabase db = mDB.getWritableDatabase();

        regId = db.insert(TABLA_NOTAS, null, values);

        Uri newUri = ContentUris.withAppendedId(CONTENT_URI, regId);

        return newUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int cont;

        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == NOTAS_ID){
            where = "_idNote=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = mDB.getWritableDatabase();

        cont = db.delete(TABLA_NOTAS, where, selectionArgs);

        return cont;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int cont;

        //Si es una consulta a un ID concreto construimos el WHERE
        String where = selection;
        if(uriMatcher.match(uri) == NOTAS_ID){
            where = "_idNote=" + uri.getLastPathSegment();
        }

        SQLiteDatabase db = mDB.getWritableDatabase();

        cont = db.update(TABLA_NOTAS, values, where, selectionArgs);

        return cont;
    }


}
