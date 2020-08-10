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

import com.example.coronaapp.model.Comunicado;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Comunicado_index extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private List <Comunicado> listaComunicado = new ArrayList <Comunicado> ();
    ArrayAdapter<Comunicado> comunicadoArrayAdapter;
    DatabaseReference myRef;
    ListView lista;
    private Comunicado comunicadoSelected;

    //menu copy1
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunicado_index);

        //menu copy 1
        NavigationView navigationMenu= findViewById(R.id.navigator);
        navigationMenu.setNavigationItemSelectedListener(this);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_bar_1);
        drawerLayout = findViewById(R.id.drawer_layout);

        navigationMenu.getMenu().getItem(2).setChecked(true);

        lista = findViewById(R.id.lista_comunicado);
        establecerConexion();
        mostrar();
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                comunicadoSelected = (Comunicado) parent.getItemAtPosition(position);
                Intent ver = new Intent(Comunicado_index.this, Comunicado_view.class);
                ver.putExtra("id",comunicadoSelected.getUid());
                ver.putExtra("fecha", comunicadoSelected.getFormattedDate());
                ver.putExtra("descripcion",comunicadoSelected.getDescripcion());
                startActivity(ver);
            }

        });
    }

    //conexión con la base de datos
    public void establecerConexion(){
        myRef= FirebaseDatabase.getInstance().getReference("Comunicado");
    }

    public void mostrar(){

        myRef= FirebaseDatabase.getInstance().getReference("Comunicado");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaComunicado.clear();
                for(DataSnapshot objSnapShot : dataSnapshot.getChildren()){
                    Comunicado n1 = objSnapShot.getValue(Comunicado.class);
                    listaComunicado.add(n1);
                    comunicadoArrayAdapter = new ArrayAdapter<Comunicado>(Comunicado_index.this, android.R.layout.simple_list_item_1,listaComunicado);
                    lista.setAdapter(comunicadoArrayAdapter);
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

                Intent agregar = new Intent(Comunicado_index.this, Comunicado_add.class);
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
