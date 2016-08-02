package com.hgf.android.notas.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.hgf.android.notas.Notes.Note;
import com.hgf.android.notas.R;

import java.util.List;

/**
 * Created by hector on 6/6/16.
 */
public class NoteAdapter<N> extends ArrayAdapter<Note> {

    public NoteAdapter(Context context, List<Note> objects) {
        super(context, 0, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        //Obteniendo una instancia del inflater
        LayoutInflater inflater = (LayoutInflater)getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //Salvando la referencia del View de la fila
        View listItemView = convertView;

        //Comprobando si el View no existe
        if (null == convertView) {
            //Si no existe, entonces inflarlo con simple_list_item
            listItemView = inflater.inflate(
                    R.layout.simple_list_item,
                    parent,
                    false);
        }

        //Obteniendo instancias de los text views
        TextView titulo = (TextView)listItemView.findViewById(R.id.titleTextView);
        TextView cuerpo = (TextView)listItemView.findViewById(R.id.SummaryTextView);
        TextView fecha = (TextView)listItemView.findViewById(R.id.dateTextView);

        //Obteniendo instancia de la Tarea en la posición actual
        Note nota = (Note)getItem(position);



        titulo.setText(nota.getTitulo());
        cuerpo.setText(nota.getContexto());
        fecha.setText(nota.getFecha());

        //Devolver al ListView la fila creada
        return listItemView;

    }
}

