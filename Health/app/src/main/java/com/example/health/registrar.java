package com.example.health;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.health.modelo.odontologoDB;

public class registrar extends AppCompatActivity {

    private EditText nombreUs;
    private EditText correoUs;
    private EditText clavedos;
    private EditText rrclave;
    private CheckBox condiciones;
    private String nombre,clave,correo, rclave;  //  variable para guardar los datos de los cuadros de texto y consultar
    odontologoDB odb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar);
        nombreUs=findViewById(R.id.nombreusuariotxt); // en esta variable, llamamoos a los id del cuadro de texto de la activity
        correoUs=findViewById(R.id.correotxt);
        clavedos=findViewById(R.id.clavetxt);
        rrclave = findViewById(R.id.repetirClave);
        condiciones = findViewById(R.id.checkBox);

        odb = new odontologoDB(this); // crea un objeto llamado odb
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void validar(View view){
        nombre = nombreUs.getText().toString(); // variable nombre es igual al contenido del cuadro de texto
        correo = correoUs.getText().toString();
        clave = clavedos.getText().toString();
        rclave = rrclave.getText().toString();
        boolean val = true;
        if(nombre.equals("")){
            nombreUs.setError("Completar el campo");
            val = false;
        }
        if(correo.equals("")){
            correoUs.setError("Completar el campo");
            val = false;
        }
        if(clave.equals("")){
            clavedos.setError("Completar el campo");
            val = false;
        }
        if(rclave.equals("")){
            rrclave.setError("Completar el campo");
            val = false;
        }
        if(condiciones.isSelected()){
            Toast.makeText(this, "No aceptó las condiciones", Toast.LENGTH_SHORT).show();
            val = false;
        }
        if(val){
            registrar(); // llamamos al metrodo registrar
        }
    }
    public boolean registrar () {
        SQLiteDatabase db = odb.getWritableDatabase(); // setencia para la consulta para base de datos
        ContentValues valores= new ContentValues(); // creamos como un contenedor para subir los datos
        valores.put("nombre", nombre); // aqui vamos a declarar que valores vamos a insertar en la columna de  base de datos
        valores.put("correo",correo);
        valores.put("clave",clave);
        Long resultado=db.insert("usuario", null,valores); // hacemos la insercion para la tabla usuario y le enviamos los valores
        db.close(); // se cierra la base de dato
        Toast.makeText(this, "Listo", Toast.LENGTH_SHORT).show();
        return  resultado != -1;
    }

}