package com.example.coronaapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coronaapp.model.Noticia;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    //Declaración variables a utilizar
    EditText titulo,tema,descripcion;
    Button aceptar,cancelar;
    FirebaseDatabase database;
    DatabaseReference myRef;
    Noticia noticia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Extracción de datos
        titulo=(EditText)findViewById(R.id.txt_titulo);
        tema=(EditText)findViewById(R.id.txt_tema);
        descripcion=(EditText)findViewById(R.id.txt_descripcion);
        aceptar=(Button)findViewById(R.id.btn_aceptar);
        cancelar=(Button)findViewById(R.id.btn_cancelar);
        noticia = new Noticia();

        //conexión con la base de datos
        database = FirebaseDatabase.getInstance();
        myRef= FirebaseDatabase.getInstance().getReference().child("Noticia");

        //listener boton aceptar
        aceptar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String tit = titulo.getText().toString();
                String tem = tema.getText().toString();
                String des = descripcion.getText().toString();
                noticia.setTitulo(tit);
                noticia.setTema(tem);
                noticia.setDescripcion(des);
                //databaseReference.child("Noticia").child(noticia.getUid()).setValue(noticia);
                myRef.push().setValue(noticia);
                Toast.makeText(MainActivity.this, "agregado", Toast.LENGTH_LONG).show();
                titulo.setText("");
                tema.setText("");
                descripcion.setText("");
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "cancelado", Toast.LENGTH_LONG).show();
            }
        });

        }
    @SuppressLint("NewApi")
    @Override
    protected void onStart() {
        super.onStart();
    }

}


