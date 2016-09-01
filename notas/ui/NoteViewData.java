package com.hgf.android.notas.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hgf.android.notas.Maps.MapsActivity;
import com.hgf.android.notas.db.ConnectionDB;
import com.hgf.android.notas.R;
import com.hgf.android.notas.db.ContentProviderBD;

/**
 * Created by hector on 3/6/16.
 * Clase para la interfaz gráfica que muestra las notas y permite borrarlas
 */
public class NoteViewData extends AppCompatActivity {

    TextView mTitle, mContent;
    Button mEdit, mDelete;
    String mTitulo, mNota;
    int mId;
    ConnectionDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_data);

        Intent intent = getIntent();
        final int id = intent.getIntExtra("NotaId", 0);
        final String titulo = intent.getStringExtra("NotaTitulo");
        final String cuerpo = intent.getStringExtra("NotaCuerpo");


        mEdit = (Button) findViewById(R.id.EditButton);
        mDelete = (Button) findViewById(R.id.deleteButton);
        mTitle = (TextView) findViewById(R.id.titleEditText);
        mContent = (TextView) findViewById(R.id.bodyEditText);

        mTitle.setText(titulo);
        mContent.setText(cuerpo);
        mId = id;

        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //db.close();
                Intent intent = new Intent(NoteViewData.this, AddData.class);
                intent.putExtra("NotaId", id);
                intent.putExtra("NotaTitulo", titulo);
                intent.putExtra("NotaCuerpo", cuerpo);
                startActivity(intent);
            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });


    }
    private  void deleteData(){
        /*db = new ConnectionDB(this);
        db.removeNotas(mId);*/
        ContentResolver cr = getContentResolver();
        cr.delete(ContentProviderBD.CONTENT_URI,
                ContentProviderBD.Note.COL_ID + " = " + mId, null);
        String msg = "La nota " + mId +" ha sido borrada";
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(NoteViewData.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nota, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Main) {
            Intent intent = new Intent(NoteViewData.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.action_Maps) {
            Intent intent = new Intent(NoteViewData.this, MapsActivity.class);
            startActivity(intent);
            finish();
        }
        if (id == R.id.Salir){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}











