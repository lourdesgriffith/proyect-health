package com.example.health.modelo;

public class turnoModelo {
    private String id;
    private String fecha;
    private String nombre_paciente;
    private String id_paciente;
    private String dni_paciente;
    private String hora_turno;

    public turnoModelo(String id, String fecha, String nombre, String idP, String dni, String hora) {
        this.id = id;
        this.fecha = fecha;
        this.nombre_paciente = nombre;
        this.id_paciente = idP;
        this.dni_paciente = dni;
        this.hora_turno = hora;
    }

    public String getId() {
        return id;
    }
    public String getFecha() {
        return fecha;
    }
    public String getNombre_paciente() {
        return nombre_paciente;
    }
    public String getId_paciente() {
        return id_paciente;
    }

    public String getDni_paciente() {
        return dni_paciente;
    }
    public String getHora_turno(){
        return hora_turno;
    }
}

