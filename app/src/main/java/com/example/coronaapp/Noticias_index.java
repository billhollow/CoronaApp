package com.example.coronaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.coronaapp.model.Noticia;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Noticias_index extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private List <Noticia> listaNoticia = new ArrayList <Noticia> ();
    ArrayAdapter<Noticia> noticiaArrayAdapter;
    DatabaseReference myRef;
    ListView lista;
    private Noticia noticiaSelected;

    //menu copy1
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias_index);

        //menu copy 1
        NavigationView navigationMenu= findViewById(R.id.navigator);
        navigationMenu.setNavigationItemSelectedListener(this);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_bar_1);
        drawerLayout = findViewById(R.id.drawer_layout);

        navigationMenu.getMenu().getItem(0).setChecked(true);

        lista = findViewById(R.id.lista_noticia);
        establecerConexion();
        mostrar();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                noticiaSelected = (Noticia) parent.getItemAtPosition(position);
                Intent ver = new Intent(Noticias_index.this, Noticia_view.class);
                ver.putExtra("id",noticiaSelected.getUid());
                ver.putExtra("titulo",noticiaSelected.getTitulo());
                ver.putExtra("tema",noticiaSelected.getTema());
                ver.putExtra("fecha", noticiaSelected.getFormattedDate());
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.noticias:{
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent index= new Intent(this, Noticias_index.class);
                startActivity(index);
                break;
            }
            case R.id.situacion:{
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent index= new Intent(this, Situacion_index.class);
                startActivity(index);
                break;
            }
            case R.id.comunicados:{
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent index= new Intent(this, Comunicado_index.class);
                startActivity(index);
                break;
            }
            case R.id.delivery:{
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent index= new Intent(this, Delivery_index.class);
                startActivity(index);
                break;
            }
            case R.id.recomendaciones:{
                drawerLayout.closeDrawer(GravityCompat.START);
                Intent index= new Intent(this, Recomendacion_index.class);
                startActivity(index);
                break;
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.icon_add:{

                Intent agregar = new Intent(Noticias_index.this, MainActivity.class);
                startActivity(agregar);
                break;
            }
            case android.R.id.home:{
                if(drawerLayout.isOpen()){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else{
                    drawerLayout.openDrawer(GravityCompat.START);
                    closeKeyboard();
                }
                break;
            }
            default: break;
        }
        return true;
    }


    public void closeKeyboard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}
