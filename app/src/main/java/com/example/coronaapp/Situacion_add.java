package com.example.coronaapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.coronaapp.model.Situacion;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    ArrayList<Situacion> listaSituacion =  new ArrayList<Situacion>();


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

        navigationMenu.getMenu().getItem(1).setChecked(true);

        date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        formattedDate = df.format(date);


        //Extracción de datos
        infectados=(EditText)findViewById(R.id.txt_infectados);
        muertos=(EditText)findViewById(R.id.txt_muertos);
        fecha=findViewById(R.id.txt_fecha);
        aceptar=(Button)findViewById(R.id.btn_aceptar);
        cancelar=(Button)findViewById(R.id.btn_cancelar);
        situacion= new Situacion();

        fecha.setText(formattedDate);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        myRef= FirebaseDatabase.getInstance().getReference("Situacion");
        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listaSituacion = new ArrayList<Situacion>();
                        for(DataSnapshot dsp : dataSnapshot.getChildren()){
                            Situacion n1 = dsp.getValue(Situacion.class);
                            listaSituacion.add(n1);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                }
        );
        fecha.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                closeKeyboard();
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Situacion_add.this, new DatePickerDialog.OnDateSetListener() {
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
        //conexión con la base de datos
        myRef= FirebaseDatabase.getInstance().getReference().child("Situacion");

        //listener boton aceptar
        aceptar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(validador()) {

                    String inf = infectados.getText().toString();
                    String mue = muertos.getText().toString();
                    String fec = fecha.getText().toString();

                    situacion.setUid(UUID.randomUUID().toString());
                    situacion.setInfectados(Double.parseDouble(inf));
                    situacion.setMuertos(Double.parseDouble(mue));
                    situacion.setFormattedDate(fec);

                    //databaseReference.child("Noticia").child(noticia.getUid()).setValue(noticia);
                    myRef.child(situacion.getUid()).setValue(situacion);

                    Toast.makeText(Situacion_add.this, "agregado", Toast.LENGTH_SHORT).show();
                    limpiar();
                }

            }
        });
    }

    public void index(View view){
        Intent index = new Intent(this, Situacion_index.class);
        startActivity(index);
    }

    public boolean validador(){

        String auxinf = infectados.getText().toString();
        String auxmue = muertos.getText().toString();
        String auxfec = fecha.getText().toString();

        for(int i=0;i<listaSituacion.size();i++){
            if(listaSituacion.get(i).getFormattedDate().equals(auxfec)){
                Toast.makeText(Situacion_add.this, "la fecha: "+auxfec+ " ya esta en uso", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        if(auxinf.trim().isEmpty()){
            Toast.makeText(this, "la cantidad de infectados no puede estar vacía", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(auxinf.trim().length() >= 15){
            Toast.makeText(this, "No se admiten más de 15 digitos en la cantidad de infectados", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(auxmue.trim().isEmpty()){
            Toast.makeText(this, "la cantidad de muertos no puede estar vacía", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(auxmue.trim().length() >= 15){
            Toast.makeText(this, "No se admiten más de 15 digitos en la cantidad de muertos", Toast.LENGTH_SHORT).show();
            return false;
        }



        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    public void limpiar(){
        infectados.setText("");
        muertos.setText("");
    }

}



