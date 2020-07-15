package com.example.coronaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coronaapp.model.Noticia;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

@SuppressWarnings("SpellCheckingInspection")
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
        myRef= FirebaseDatabase.getInstance().getReference().child("Noticia");

        //listener boton aceptar
        aceptar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(validador()){
                    String tit = titulo.getText().toString();
                    String tem = tema.getText().toString();
                    String des = descripcion.getText().toString();
                    noticia.setUid(UUID.randomUUID().toString());
                    noticia.setTitulo(tit);
                    noticia.setTema(tem);
                    noticia.setDescripcion(des);
                    //databaseReference.child("Noticia").child(noticia.getUid()).setValue(noticia);
                    myRef.child(noticia.getUid()).setValue(noticia);
                    Toast.makeText(MainActivity.this, "agregado", Toast.LENGTH_SHORT).show();
                    titulo.setText("");
                    tema.setText("");
                    descripcion.setText("");
                }

            }
        });

    }

    public void index_noticia(View view){
        Intent index = new Intent(this, Noticias_index.class);
        startActivity(index);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public boolean validador(){
        String tit = titulo.getText().toString();
        String tem = tema.getText().toString();
        String des = descripcion.getText().toString();

        if(tit.trim().isEmpty()){
            Toast.makeText(MainActivity.this, "el título no puede estar vacío", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tem.trim().isEmpty()){
            Toast.makeText(MainActivity.this, "el tema no puede estar vacío", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(des.trim().isEmpty()){
            Toast.makeText(MainActivity.this, "La descripción no puede estar vacia", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}


