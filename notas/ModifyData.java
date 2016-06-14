package com.hgf.android.notas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by hector on 3/6/16.
 */
public class ModifyData extends AppCompatActivity {

    EditText mTitle, mContent;
    Button mInsert, mDelete;
    String mTitulo, mNota;
    int mId;
    ConnectionDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_data);

        Intent intent = getIntent();
        int id = intent.getIntExtra("NotaId", 0);
        String titulo = intent.getStringExtra("NotaTitulo");
        String cuerpo = intent.getStringExtra("NotaCuerpo");


        mInsert = (Button) findViewById(R.id.saveButton);
        mDelete = (Button) findViewById(R.id.deleteButton);
        mTitle = (EditText) findViewById(R.id.titleEditText);
        mContent = (EditText) findViewById(R.id.bodyEditText);


        mTitle.setText(titulo);
        mContent.setText(cuerpo);
        mId = id;

        mInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyDatos();
            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });


    }

    private void modifyDatos(){
        mTitulo = mTitle.getText().toString();
        mNota = mContent.getText().toString();
        db = new ConnectionDB(this);
        db.modifyNote(mId, mTitulo, mNota);
        //db.close();
        Intent intent = new Intent(ModifyData.this, MainActivity.class);
        startActivity(intent);
    }

    private  void deleteData(){
        db = new ConnectionDB(this);
        db.removeNotas(mId);
        Intent intent = new Intent(ModifyData.this, MainActivity.class);
        startActivity(intent);
    }

}











