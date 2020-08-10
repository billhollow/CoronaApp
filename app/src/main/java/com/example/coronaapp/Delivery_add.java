package com.example.coronaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.coronaapp.model.Delivery;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

@SuppressWarnings("SpellCheckingInspection")
public class Delivery_add extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Declaración variables a utilizar
    EditText nombre,contacto,descripcion;
    Button aceptar,cancelar;
    DatabaseReference myRef;
    Delivery delivery;

    //menu copy1
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_add);

        //menu copy 1
        NavigationView navigationMenu= findViewById(R.id.navigator);
        navigationMenu.setNavigationItemSelectedListener(this);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_bar_1);
        drawerLayout = findViewById(R.id.drawer_layout);

        navigationMenu.getMenu().getItem(3).setChecked(true);


        //Extracción de datos
        nombre=(EditText)findViewById(R.id.txt_nombre);
        contacto=(EditText)findViewById(R.id.txt_contacto);
        descripcion=(EditText)findViewById(R.id.txt_descripcion);
        aceptar=(Button)findViewById(R.id.btn_aceptar);
        cancelar=(Button)findViewById(R.id.btn_cancelar);
        delivery = new Delivery();

        //conexión con la base de datos
        myRef= FirebaseDatabase.getInstance().getReference().child("Delivery");

        //listener boton aceptar
        aceptar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(validador()){

                    String nom = nombre.getText().toString();
                    String con = contacto.getText().toString();
                    String des = descripcion.getText().toString();

                    delivery.setUid(UUID.randomUUID().toString());
                    delivery.setNombre(nom);
                    delivery.setContacto(con);
                    delivery.setDescripcion(des);

                    myRef.child(delivery.getUid()).setValue(delivery);
                    Toast.makeText(Delivery_add.this, "agregado", Toast.LENGTH_SHORT).show();
                    nombre.setText("");
                    contacto.setText("");
                    descripcion.setText("");
                }
            }
        });
    }

    public void index(View view){
        Intent index = new Intent(this, Delivery_index.class);
        startActivity(index);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public boolean validador(){
        String nom = nombre.getText().toString();
        String con = contacto.getText().toString();
        String des = descripcion.getText().toString();


        if(nom.trim().isEmpty()){
            Toast.makeText(this, "el nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(nom.trim().length() >= 50){
            Toast.makeText(this, "el nombre no puede tener más de 50 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(con.trim().isEmpty()){
            Toast.makeText(this, "el contacto no puede estar vacío", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(con.trim().length() >= 100){
            Toast.makeText(this, "el contacto no puede tener más de 100 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(des.trim().isEmpty()){
            Toast.makeText(this, "La descripción no puede estar vacia", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(des.trim().length() >= 1000){
            Toast.makeText(this, "la descripción no puede ser mayor a 1000 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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


