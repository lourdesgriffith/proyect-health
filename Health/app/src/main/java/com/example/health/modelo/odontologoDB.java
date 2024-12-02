package com.example.health.modelo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class odontologoDB extends SQLiteOpenHelper {   // consultas para todas las tablas
    private static final String db_name = "odontologiaDB";


    public odontologoDB(Context context) {
        super(context, db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String logIn = "CREATE TABLE usuario(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "correo TEXT," +
                "clave TEXT" +
                ");";
        String paciente = "CREATE TABLE paciente (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "obra TEXT NOT NULL," +
                "dni TEXT UNIQUE NOT NULL," +
                "telefono TEXT NOT NULL," +
                "alergia TEXT NOT NULL," +

                "correo TEXT NOT NULL" +
                ");";
        String turno = "CREATE TABLE turno (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id_paciente INTEGER NOT NULL," +
                "fecha DATE NOT NULL," +
                "hora INTEGER NOT NULL," +
                "FOREIGN KEY (id_paciente) REFERENCES paciente(id) ON DELETE CASCADE," +
                "FOREIGN KEY (hora) REFERENCES hora(id) ON DELETE CASCADE" +
                ");";
        String hora = "CREATE TABLE hora (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "hora TEXT NOT NULL" +
                ");";

        db.execSQL(logIn);
        db.execSQL(paciente);
        db.execSQL(turno);
        db.execSQL(hora);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS turno");
        db.execSQL("DROP TABLE IF EXISTS hora");
        db.execSQL("DROP TABLE IF EXISTS paciente");
        db.execSQL("DROP TABLE IF EXISTS logIn");
        onCreate(db);
    }

    public boolean buscar(String nombre, String clave){   // me va devolver los datos y true o flase
        SQLiteDatabase db = this.getWritableDatabase();  // inicializamos la sentencia para la base de datos
        String[]columnas = {"id"}; // creo un vector  de tipo string llamado columnas
        String select = "correo = ? AND clave = ?"; // es la condicion de la query
        String[] selection = {nombre, clave}; // llamamos un arrray, nombre y clave, siempre deben estar en orden
        Cursor cursor = db.query(
                "usuario", // consulta de la tabla usuario
                columnas,
                select,
                selection, // reemplaza los "?" por el array selection
                null,
                null,
                null
        );
        boolean validar = cursor.getCount()>0; // verificamos si los datos existen, si se incrementa validar=1 o true
        cursor.close(); // cierra la consulta
        db.close(); // cierra la bd
        return validar; // retorna la variable validar, que seria 1 o 0
    }

    public Cursor obtenerPaciente(String dni) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("paciente", null, "dni = ?", new String[]{dni}, null, null, null);
    }

    public int eliminarPaciente(String dni) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("paciente", "dni = ?", new String[]{dni});

    }

    public List<String> pacientes() {
        List<String> listaPacientes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, nombre, dni FROM paciente", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String dni = cursor.getString(cursor.getColumnIndexOrThrow("dni"));
                listaPacientes.add(id + " - " + nombre + " - " + dni);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return listaPacientes;
    }

    public List<String> horarios() {
        List<String> hora = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id,hora FROM hora WHERE ocupado = 1", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id")); // va guardar el id y la hora
                String horas = cursor.getString(cursor.getColumnIndexOrThrow("hora"));

                hora.add(id + " - " + horas);
            } while (cursor.moveToNext()); // va dar vueltas hasta que no quede registros en la tabla
        }

        cursor.close();
        return hora;
    }

    public int modificarPaciente(String id, String dni, String nombre, String correo, String telefono, String alergia, String obra) { // recibe los daots de los parametros enviado
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();

        valores.put("nombre", nombre);
        valores.put("correo", correo);
        valores.put("dni",dni);
        valores.put("telefono", telefono);
        valores.put("alergia", alergia);
        valores.put("obra", obra);

        int filasActualizadas = db.update("paciente", valores, "id = ?", new String[]{id}); // declaramos a que tabla vamos hacer la modifc, insertamos los valores, la condicion, y el array para remplazar ?
        db.close();

        return filasActualizadas; // Retorna el número de filas afectadas
    }

    public int modificarHora() { // recibe los daots de los parametros enviado
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("hora", null, null);
    }


    public List<turnoModelo> obtenerTodosLosTurnos() {
        List<turnoModelo> listaTurnos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT t.id AS turno_id, t.fecha, h.hora AS hora_turno, p.id AS paciente_id, p.nombre, p.dni " +
                "FROM turno AS t " +
                "INNER JOIN paciente AS p ON t.id_paciente = p.id " +
                "INNER JOIN hora AS h ON t.hora = h.id " +
                "ORDER BY t.id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("turno_id"));
                @SuppressLint("Range") String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                @SuppressLint("Range") String paciente = cursor.getString(cursor.getColumnIndex("nombre"));
                @SuppressLint("Range") String idP = cursor.getString(cursor.getColumnIndex("paciente_id"));
                String dni = cursor.getString(cursor.getColumnIndexOrThrow("dni"));
                String hora = cursor.getString(cursor.getColumnIndexOrThrow("hora_turno"));
                listaTurnos.add(new turnoModelo(id, fecha, paciente, idP, dni, hora));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return listaTurnos;
    }

    public int eliminarTurno(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("turno", "id = ?", new String[]{id});
    }
}

