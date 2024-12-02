package com.example.health;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.health.modelo.odontologoDB;

public class registrar_pasiente extends AppCompatActivity {
    private EditText nomPac;
    private EditText corPac;
    private EditText dniPac;
    private EditText alePac;
    private EditText telPac;
    private EditText obraPac;
    private String id;


    odontologoDB odb;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrar_pasiente);

        nomPac=findViewById(R.id.nombrepaciente);
        corPac=findViewById(R.id.correopaciente);
        dniPac=findViewById(R.id.dnipaciente);
        alePac=findViewById(R.id.alergiapaciente);
        telPac=findViewById(R.id.telefonopaciente);
        obraPac =findViewById(R.id.obrasocial);
        corPac = findViewById(R.id.correopaciente);

        odb = new odontologoDB(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void guardarPaciente(View view) {
        String nombre = nomPac.getText().toString(); // gaurda los contenidos para cada cuadro texto
        String dni = dniPac.getText().toString();
        String alergia = alePac.getText().toString();
        String telefono = telPac.getText().toString();
        String obra = obraPac.getText().toString();
        String correo = corPac.getText().toString();
        boolean val = true;
        if(TextUtils.isEmpty(dni)){
            dniPac.setError("Faltan datos");
            val = false;
        }
        if(TextUtils.isEmpty(nombre)){
            nomPac.setError("Faltan datos");
            val = false;
        }
        if(TextUtils.isEmpty(telefono)){
            telPac.setError("Faltan datos");
            val = false;
        }
        if(TextUtils.isEmpty(alergia)){
            alePac.setError("Faltan datos");
            val = false;
        }
        if(TextUtils.isEmpty(obra)){
            obra = "No posee Obra Social";
        }
        if(val) {
                SQLiteDatabase db = odb.getWritableDatabase();
                ContentValues valores = new ContentValues();
                valores.put("nombre", nombre);
                valores.put("dni", dni);
                valores.put("obra",obra);
                valores.put("alergia",alergia);
                valores.put("telefono", telefono);
                valores.put("correo",correo);
                Long resultado = db.insert("paciente", null, valores);
                db.close();
                if(resultado>0){
                    Toast.makeText(this, "Listo", Toast.LENGTH_SHORT).show();
                    dniPac.setText("");
                    nomPac.setText("");
                    telPac.setText("");
                    alePac.setText("");
                    corPac.setText("");
                    obraPac.setText("");
                }
                else{
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                }

        }
    }
    public void buscarPaciente(View view) {
        String dni =dniPac.getText().toString();
        if(dni.equals("")) {
            dniPac.setError("Debes llenar este campo para la busqueda");
        }
        else{
            Cursor res = odb.obtenerPaciente(dni);  // budcador
            if (res.moveToFirst()) {
                @SuppressLint("Range") String dnip = res.getString(res.getColumnIndexOrThrow("dni"));
                @SuppressLint("Range") String nombre = res.getString(res.getColumnIndexOrThrow("nombre"));
                @SuppressLint("Range") String telefono = res.getString(res.getColumnIndexOrThrow("telefono"));
                @SuppressLint("Range") String correo = res.getString(res.getColumnIndexOrThrow("correo"));
                @SuppressLint("Range") String alergia = res.getString(res.getColumnIndexOrThrow("alergia"));
                @SuppressLint("Range") String obra = res.getString(res.getColumnIndexOrThrow("obra"));

                String id = res.getString(res.getColumnIndexOrThrow("id"));
                nomPac.setText(nombre);
                telPac.setText(telefono);
                dniPac.setText(dnip);
                corPac.setText(correo);
                alePac.setText(alergia);
                obraPac.setText(obra);
                this.id = id;
            } else {
                Toast.makeText(this, "Paciente no encontrado", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void eliminarPaciente(View view){
        String dni=dniPac.getText().toString();
        if(dni.equals("")){
            dniPac.setError("Debes llenar este campo para eliminar");
        }
        else {
            int res = odb.eliminarPaciente(dni);
            if(res>0){
                Toast.makeText(this, "Se elimino el paciente con exito", Toast.LENGTH_SHORT).show();
                dniPac.setText("");
                nomPac.setText("");
                telPac.setText("");
                alePac.setText("");
                corPac.setText("");
                obraPac.setText("");
            }
            else{
                Toast.makeText(this, "error al eliminar", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void modificarPaciente(View view){
        String nombre = nomPac.getText().toString();
        String correo = corPac.getText().toString();
        String dni = dniPac.getText().toString();
        String alergia = alePac.getText().toString();
        String telefono = telPac.getText().toString();
        String obra = obraPac.getText().toString();
        boolean val = true;
        if(TextUtils.isEmpty(dni)){
            dniPac.setError("Faltan datos");
            val = false;
        }
        if(TextUtils.isEmpty(nombre)){
            nomPac.setError("Faltan datos");
            val = false;
        }
        if(TextUtils.isEmpty(telefono)){
            telPac.setError("Faltan datos");
            val = false;
        }
        if(TextUtils.isEmpty(alergia)){
            alePac.setError("Faltan datos");
            val = false;
        }
        if(TextUtils.isEmpty(obra)){
            obra = "No posee Obra Social";
        }
        if(val) {
            int resultado = odb.modificarPaciente(id, dni, nombre, correo, telefono, alergia, obra); // llama al metodo modificar paciente y le envia los datos pac como paramtros

            if (resultado > 0) {
                Toast.makeText(this, "Paciente actualizado con éxito", Toast.LENGTH_SHORT).show();
                id = "";
                dniPac.setText("");
                nomPac.setText("");
                telPac.setText("");
                alePac.setText("");
                corPac.setText("");
                obraPac.setText("");
            } else {
                Toast.makeText(this, "Error al actualizar el paciente", Toast.LENGTH_SHORT).show();

            }

        }
    }
}