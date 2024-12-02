package com.example.health;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.health.modelo.odontologoDB;
import com.example.health.modelo.turnoAdapter;
import com.example.health.modelo.turnoModelo;

import java.util.List;

public class gestionarturnos extends AppCompatActivity {

    private RecyclerView turnos;
    private turnoAdapter adapter;
    private List<turnoModelo> listaTurnos;
    odontologoDB odb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gestionarturnos);

        turnos = findViewById(R.id.turnosView);
        turnos.setLayoutManager(new LinearLayoutManager(this));

        odb = new odontologoDB(this);
        listaTurnos = odb.obtenerTodosLosTurnos();

        turnoAdapter adapter = new turnoAdapter(
                this,
                listaTurnos,
                new turnoAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(turnoModelo item) {
                        String idt = item.getId();
                        AlertDialog.Builder builder = new AlertDialog.Builder(gestionarturnos.this);
                        builder.setMessage("¿Qué desea hacer con este turno?")
                                .setPositiveButton("Cancelarlo", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        odb.eliminarTurno(idt);
                                        Toast.makeText(gestionarturnos.this, "Se canceló con exito", Toast.LENGTH_SHORT).show();
                                        Intent gt = new Intent(getApplicationContext(), gestionarturnos.class);
                                        startActivity(gt);
                                        finish();
                                        dialog.dismiss();
                                    }
                                })
                                .setNegativeButton("Finalizarlo", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        odb.eliminarTurno(idt);
                                        Toast.makeText(gestionarturnos.this, "Se finalizó con exito", Toast.LENGTH_SHORT).show();
                                        Intent gt = new Intent(getApplicationContext(), gestionarturnos.class);
                                        startActivity(gt);
                                        finish();
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
        );
        turnos.setAdapter(adapter);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}