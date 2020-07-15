package com.example.coronaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coronaapp.model.Noticia;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Noticias_index extends AppCompatActivity {

    private List <Noticia> listaNoticia = new ArrayList <Noticia> ();
    ArrayAdapter<Noticia> noticiaArrayAdapter;
    DatabaseReference myRef;
    ListView lista;
    private Noticia noticiaSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_index);
        lista = findViewById(R.id.lista_noticia);
        establecerConexion();
        mostrar();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                noticiaSelected = (Noticia) parent.getItemAtPosition(position);
           //     Toast.makeText(Noticias_index.this, "Noticia: "+noticiaSelected.getTitulo(), Toast.LENGTH_SHORT).show();
                Intent ver = new Intent(Noticias_index.this, Noticia_view.class);
                ver.putExtra("id",noticiaSelected.getUid());
                ver.putExtra("titulo",noticiaSelected.getTitulo());
                ver.putExtra("tema",noticiaSelected.getTema());
                ver.putExtra("descripcion",noticiaSelected.getDescripcion());
                startActivity(ver);
              }

            });
    }

    //conexión con la base de datos
    public void establecerConexion(){
        myRef= FirebaseDatabase.getInstance().getReference("Noticia");
    }

    public void mostrar(){

        myRef= FirebaseDatabase.getInstance().getReference("Noticia");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaNoticia.clear();
                for(DataSnapshot objSnapShot : dataSnapshot.getChildren()){
                    Noticia n1 = objSnapShot.getValue(Noticia.class);
                    listaNoticia.add(n1);
                    noticiaArrayAdapter = new ArrayAdapter<Noticia>(Noticias_index.this, android.R.layout.simple_list_item_1,listaNoticia);
                    lista.setAdapter(noticiaArrayAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_1,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.icon_add:{
                Toast.makeText(Noticias_index.this,"Agregando",Toast.LENGTH_SHORT).show();
                Intent agregar = new Intent(Noticias_index.this, MainActivity.class);
                startActivity(agregar);
                break;
            }
            default: break;
        }
        return true;

    }
}
