package com.hgf.android.notas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

/**
 * Created by hector on 3/6/16.
 */
public class AddData extends Activity {

    EditText mTitle, mContent;
    Button mInsert;
    String mTitulo, mNota;
    ConnectionDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_data);

        mInsert = (Button) findViewById(R.id.saveButton);
        mTitle = (EditText) findViewById(R.id.titleEditText);
        mContent = (EditText) findViewById(R.id.bodyEditText);

        mInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDatos();
            }
        });
    }

    private void addDatos(){
        mTitulo = mTitle.getText().toString();
        mNota = mContent.getText().toString();
        String fecha = "";
        fecha = fechaActual();
        db = new ConnectionDB(this);
        db.addNote(mTitulo, mNota, fecha);
        //db.close();
        Intent intent = new Intent(AddData.this, MainActivity.class);
        startActivity(intent);
    }



    private String fechaActual(){
        String fecha = "";
        Date d = new Date();
        CharSequence s = "";
        s  = DateFormat.format("MMMM d, yyyy ", d.getTime());
        fecha = s.toString();
        return fecha;
    }

}











