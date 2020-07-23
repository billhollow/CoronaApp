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

import com.example.coronaapp.model.Situacion;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@SuppressWarnings("SpellCheckingInspection")
public class Situacion_add extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Declaración variables a utilizar
    EditText infectados, muertos, fecha;
    Button aceptar,cancelar;
    DatabaseReference myRef;
    Situacion situacion;

    private String formattedDate;
    private Date date;

    //menu copy1
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_situacion_add);

        //menu copy 1
        NavigationView navigationMenu= findViewById(R.id.navigator);
        navigationMenu.setNavigationItemSelectedListener(this);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_bar_1);
        drawerLayout = findViewById(R.id.drawer_layout);

        date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        formattedDate = df.format(date);


        //Extracción de datos
        infectados=(EditText)findViewById(R.id.txt_infectados);
        muertos=(EditText)findViewById(R.id.txt_muertos);
        fecha=(EditText)findViewById(R.id.txt_fecha);
        aceptar=(Button)findViewById(R.id.btn_aceptar);
        cancelar=(Button)findViewById(R.id.btn_cancelar);
        situacion= new Situacion();

        //conexión con la base de datos
        myRef= FirebaseDatabase.getInstance().getReference().child("Situacion");

        //listener boton aceptar
        aceptar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                    String inf = infectados.getText().toString();
                    String mue = muertos.getText().toString();
                    String fec = fecha.getText().toString();

                    date = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    formattedDate = df.format(date);

                    situacion.setUid(UUID.randomUUID().toString());
                    situacion.setInfectados(Integer.parseInt(inf));
                    situacion.setMuertos(Integer.parseInt(mue));
                    situacion.setDate(date);
                    situacion.setFormattedDate(formattedDate);

                    //databaseReference.child("Noticia").child(noticia.getUid()).setValue(noticia);
                    myRef.child(situacion.getUid()).setValue(situacion);

                    Toast.makeText(Situacion_add.this, "agregado", Toast.LENGTH_SHORT).show();
                    limpiar();

            }
        });
    }

    public void index(View view){
        Intent index = new Intent(this, Situacion_index.class);
        startActivity(index);
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    public void limpiar(){
        infectados.setText("");
        muertos.setText("");
        fecha.setText("");
    }

}


