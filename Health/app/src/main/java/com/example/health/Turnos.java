package com.example.health;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.health.modelo.odontologoDB;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Turnos extends AppCompatActivity {
    private CalendarView calendar;
    private TextView fecha;
    private Spinner pacientes;
    private Spinner horas;
    private String idPaciente = "", idhorario = "";
    odontologoDB odb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_turnos);

        calendar = findViewById(R.id.calendario);
        fecha = findViewById(R.id.fechaTXT);
        pacientes = findViewById(R.id.spinner);
        horas = findViewById(R.id.Hora);

        odb = new odontologoDB(this);
        String idDefecto = "0";
        String nombre = "Seleccione un paciente";
        String h = "Seleccione un horario";

        List<String> listaPacientes = odb.pacientes();
        listaPacientes.add(0, idDefecto + " - " + nombre);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, listaPacientes);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        pacientes.setAdapter(adapter);

        List<String> hora = odb.horarios();
        hora.add(0, idDefecto + " - " + h);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.spinner_item, hora);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        horas.setAdapter(adapter1);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String fechaFormateada = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month, dayOfMonth);
                fecha.setText(fechaFormateada);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void guardarturno(View view) {
        boolean val = true;
        String seleccionado = pacientes.getSelectedItem().toString();
        String[] partes = seleccionado.split(" - "); // esto va separar los datos del spinner

        String seleccionar =horas.getSelectedItem().toString();
        String[] part=seleccionar.split(" - ");

        String date = fecha.getText().toString(); // guardamos en la variable date

        if (TextUtils.isEmpty(date)){
            fecha.setError("Seleccione una fecha");
            val = false;
        }

        if (seleccionado.startsWith("0 -")) {
            Toast.makeText(this, "Por favor, selecciona un paciente válido.", Toast.LENGTH_SHORT).show();
            val = false;
        }

        if (seleccionar.startsWith("0 -")) {
            Toast.makeText(this, "Por favor, selecciona un horario válido.", Toast.LENGTH_SHORT).show();
            val = false;
        }

        if(val){
            idPaciente = partes[0]; // separa los datos
            String nombrePaciente = partes[1];
            String dniPaciente = partes[2];

            idhorario = part[0];
            String horasPaciente =part[1];

            SQLiteDatabase db = odb.getWritableDatabase();
            ContentValues valores = new ContentValues(); // creamos como un contenedor
            valores.put("id_paciente",idPaciente);
            valores.put("fecha",date);
            valores.put("hora",idhorario);
            Long resultado = db.insert("turno", null, valores);
            if(resultado>0){
                Toast.makeText(this, "Listo", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show(); // si no inserta ningun dato sale error
            }
            db.close();
        }
    }

}