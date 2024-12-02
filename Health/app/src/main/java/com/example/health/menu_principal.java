package com.example.health;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.health.modelo.odontologoDB;

public class menu_principal extends AppCompatActivity {
   private EditText horas;
    odontologoDB odb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_principal);
        odb = new odontologoDB(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void Horas (View view){  //este metodo es para el boton de horas
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogFView = inflater.inflate(R.layout.horarios, null);
        builder.setView(dialogFView);

        horas = dialogFView.findViewById(R.id.cargarhorarios);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button aceptar = dialogFView.findViewById(R.id.boton_listo);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button cancelar = dialogFView.findViewById(R.id.boton_cancelar);
        AlertDialog dialog = builder.create();

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String h = horas.getText().toString();
                SQLiteDatabase db = odb.getWritableDatabase();
                ContentValues valores = new ContentValues();
                valores.put("hora", h); // aqui hacemos la insersion para la tabla hora
                Long resultado = db.insert("hora", null, valores);
                db.close();
                if(resultado>0) {
                    Toast.makeText(getApplicationContext(), "Listo", Toast.LENGTH_SHORT).show();
                    horas.setText("");
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
      public void registar_pasiente(View view){
        Intent rp= new Intent (this, registrar_pasiente.class);
        startActivity(rp);

      }
      public void agenda (View view) {
        Intent a=new Intent(this, Turnos.class);
        startActivity(a);
      }
      public void gestionarturnod (View view){
        Intent cp=new Intent(this, gestionarturnos.class);
        startActivity(cp);
      }
}