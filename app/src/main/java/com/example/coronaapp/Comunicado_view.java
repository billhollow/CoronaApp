package com.example.coronaapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.coronaapp.model.Comunicado;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Comunicado_view extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Declaración variables a utilizar
    EditText fecha, descripcion;
    Button aceptar,cancelar;
    DatabaseReference myRef;
    Comunicado comunicado;

    //menu copy1
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunicado_view);

        //menu copy 1
        NavigationView navigationMenu= findViewById(R.id.navigator);
        navigationMenu.setNavigationItemSelectedListener(this);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_bar_1);
        drawerLayout = findViewById(R.id.drawer_layout);

        navigationMenu.getMenu().getItem(2).setChecked(true);

        //Extracción de datos
        descripcion=(EditText)findViewById(R.id.txt_descripcion);
        fecha=findViewById(R.id.txt_fecha);
        aceptar=(Button)findViewById(R.id.btn_aceptar);
        cancelar=(Button)findViewById(R.id.btn_cancelar);
        comunicado= new Comunicado();

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        fecha.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                closeKeyboard();
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Comunicado_view.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String auxDate = day+"-"+month+"-"+year;
                        fecha.setText(auxDate);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });

        descripcion.setText(getIntent().getStringExtra("descripcion"));
        fecha.setText(getIntent().getStringExtra("fecha"));

        descripcion.setEnabled(false);
        fecha.setEnabled(false);

        aceptar.setVisibility(View.GONE);
        cancelar.setVisibility(View.GONE);
        establecerConexion();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_2,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.icon_delete:{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Borrar");
                builder.setMessage("¿Esta seguro de borrar este elemento?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myRef.child("Comunicado").child(getIntent().getStringExtra("id")).removeValue();
                        Toast.makeText(Comunicado_view.this,"Borrado",Toast.LENGTH_SHORT).show();
                        Intent borrado = new Intent(Comunicado_view.this, Comunicado_index.class);
                        startActivity(borrado);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            }
            case R.id.icon_update:{
                Toast.makeText(Comunicado_view.this,"Usted puede editar ahora",Toast.LENGTH_SHORT).show();
                descripcion.setEnabled(true);
                fecha.setEnabled(true);
                aceptar.setVisibility(View.VISIBLE);
                cancelar.setVisibility(View.VISIBLE);
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

    public void Cancelar(View view){
        restart();
    }
    public void Aceptar(View view){

        if(validador()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Actualizar");
            builder.setMessage("¿Esta seguro de actualizar este elemento?");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Comunicado n = new Comunicado();

                    n.setUid(getIntent().getStringExtra("id"));

                    n.setDescripcion(descripcion.getText().toString());
                    n.setFormattedDate(fecha.getText().toString());

                    myRef.child("Comunicado").child(getIntent().getStringExtra("id")).setValue(n);
                    Toast.makeText(Comunicado_view.this, "Actualizado", Toast.LENGTH_SHORT).show();
                    restart();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }



    }
    public void establecerConexion(){
        myRef= FirebaseDatabase.getInstance().getReference();
    }

    public void restart(){
        descripcion.setEnabled(false);
        fecha.setEnabled(false);
        aceptar.setVisibility(View.GONE);
        cancelar.setVisibility(View.GONE);
    }

    public boolean validador(){
        String auxdes = descripcion.getText().toString();
        if(auxdes.trim().isEmpty()){
            Toast.makeText(this, "La descripción no puede estar vacía", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(auxdes.trim().length() >= 1000){
            Toast.makeText(this, "La descripción no puede tener más de 1000 caracteres", Toast.LENGTH_SHORT).show();
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

    public void closeKeyboard(){
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

}
