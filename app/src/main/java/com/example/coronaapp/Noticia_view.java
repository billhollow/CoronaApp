package com.example.coronaapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.coronaapp.model.Noticia;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Noticia_view extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    EditText titulo,tema,descripcion, fecha;
    Button aceptar,cancelar;
    DatabaseReference myRef;

    //menu copy1
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia_view);

        //menu copy 1
        NavigationView navigationMenu= findViewById(R.id.navigator);
        navigationMenu.setNavigationItemSelectedListener(this);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_bar_1);
        drawerLayout = findViewById(R.id.drawer_layout);

        //Extracción de datos
        titulo=(EditText)findViewById(R.id.txt_titulo);
        tema=(EditText)findViewById(R.id.txt_tema);
        fecha=findViewById(R.id.txt_fecha);
        descripcion=(EditText)findViewById(R.id.txt_descripcion);
        aceptar=(Button)findViewById(R.id.btn_aceptar);
        cancelar=(Button)findViewById(R.id.btn_cancelar);

        titulo.setText(getIntent().getStringExtra("titulo"));
        tema.setText(getIntent().getStringExtra("tema"));
        fecha.setText(getIntent().getStringExtra("fecha"));
        descripcion.setText(getIntent().getStringExtra("descripcion"));

        titulo.setEnabled(false);
        tema.setEnabled(false);
        fecha.setEnabled(false);
        descripcion.setEnabled(false);
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
                        myRef.child("Noticia").child(getIntent().getStringExtra("id")).removeValue();
                        Toast.makeText(Noticia_view.this,"Borrado",Toast.LENGTH_SHORT).show();
                        Intent borrado = new Intent(Noticia_view.this, Noticias_index.class);
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
                Toast.makeText(Noticia_view.this,"Usted puede editar ahora",Toast.LENGTH_SHORT).show();
                titulo.setEnabled(true);
                tema.setEnabled(true);
                descripcion.setEnabled(true);
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

        if(validador()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Actualizar");
            builder.setMessage("¿Esta seguro de actualizar este elemento?");
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Noticia n = new Noticia();
                    n.setUid(getIntent().getStringExtra("id"));
                    n.setTitulo(titulo.getText().toString());
                    n.setTema(tema.getText().toString());
                    n.setFormattedDate(fecha.getText().toString());
                    n.setDescripcion(descripcion.getText().toString());
                    myRef.child("Noticia").child(getIntent().getStringExtra("id")).setValue(n);
                    Toast.makeText(Noticia_view.this,"Actualizado",Toast.LENGTH_SHORT).show();
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
        titulo.setEnabled(false);
        tema.setEnabled(false);
        descripcion.setEnabled(false);
        aceptar.setVisibility(View.GONE);
        cancelar.setVisibility(View.GONE);
    }

    public boolean validador(){
        String tit = titulo.getText().toString();
        String tem = tema.getText().toString();
        String des = descripcion.getText().toString();

        if(tit.trim().isEmpty()){
            Toast.makeText(Noticia_view.this, "el título no puede estar vacío", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tem.trim().isEmpty()){
            Toast.makeText(Noticia_view.this, "el tema no puede estar vacío", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(des.trim().isEmpty()){
            Toast.makeText(Noticia_view.this, "La descripción no puede estar vacia", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.noticias:{
                Intent index= new Intent(this, Noticias_index.class);
                startActivity(index);
                break;
            }
            case R.id.situacion:{
                Intent index= new Intent(this, Situacion_index.class);
                startActivity(index);
                break;
            }
            case R.id.comunicados:{
                Intent index= new Intent(this, Main2Activity.class);
                startActivity(index);
                Toast.makeText(this, "Gonna die, you know! C", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.delivery:{
                Toast.makeText(this, "Gonna die, you know! D", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.recomendaciones:{
                Toast.makeText(this, "Gonna die, you know! R", Toast.LENGTH_SHORT).show();
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
