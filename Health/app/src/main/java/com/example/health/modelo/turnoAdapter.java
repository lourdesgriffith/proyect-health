package com.example.health.modelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.health.R;

import java.util.List;

public class turnoAdapter extends RecyclerView.Adapter<turnoAdapter.TurnoViewHolder> {

    private Context context;
    private List<turnoModelo> listaTurnos;
    final turnoAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void OnItemClick (turnoModelo item);
    }

    public turnoAdapter(Context context, List<turnoModelo> listaTurnos, turnoAdapter.OnItemClickListener listener) {
        this.context = context;
        this.listaTurnos = listaTurnos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TurnoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.turno_card, parent, false);
        return new TurnoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TurnoViewHolder holder, int position) {
        turnoModelo turno = listaTurnos.get(position);
        holder.fechaTextView.setText(turno.getFecha());
        holder.idPTextView.setText(turno.getId_paciente());
        holder.pacienteTextView.setText(turno.getNombre_paciente());
        holder.idFTextView.setText(turno.getId());
        holder.dniTextView.setText(turno.getDni_paciente());
        holder.horaTextView.setText(turno.getHora_turno());
        holder.bind(turno, listener);
    }

    @Override
    public int getItemCount() {
        return listaTurnos.size();
    }

    public static class TurnoViewHolder extends RecyclerView.ViewHolder {
        TextView fechaTextView, idFTextView, pacienteTextView, idPTextView, dniTextView, horaTextView;

        public TurnoViewHolder(@NonNull View itemView) {
            super(itemView);
            fechaTextView = itemView.findViewById(R.id.fechaCarga);
            idPTextView = itemView.findViewById(R.id.idPaciente);
            pacienteTextView = itemView.findViewById(R.id.nombrePaciente);
            idFTextView = itemView.findViewById(R.id.idTurno);
            dniTextView = itemView.findViewById(R.id.dniPaciente);
            horaTextView = itemView.findViewById(R.id.horaTurno);
        }
        public void bind(final turnoModelo item, final turnoAdapter.OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(item);
                }
            });
        }
    }
}

