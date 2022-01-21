package com.example.obassop.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.obassop.Activities.FotoActivity;
import com.example.obassop.Dao.RoomDB;
import com.example.obassop.Data.SubservicosData;
import com.example.obassop.R;

import java.util.List;

import static com.example.obassop.R.drawable.ic_baseline_check_circle_24;
import static com.example.obassop.R.drawable.ic_baseline_create_24;

public class SubservicosAdapter extends RecyclerView.Adapter<SubservicosAdapter.ViewHolder> {

    private List<SubservicosData> dataList;
    private Activity context;
    private RoomDB database;
    int ServicoID, ObraID, BlocoID;

    public SubservicosAdapter(Activity context, List<SubservicosData> dataList) {
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_subservicos, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubservicosData data = dataList.get(position);
        database = RoomDB.getInstance(context);
        holder.tv_subservico_Num.setText(data.getSubservicoNum());
        holder.tv_subservicoTexto.setText(data.getSubservicoTexto());
        holder.tv_subservicoUnidade.setText(data.getSubservicoUnidade());
        holder.tv_subservicoQtd.setText(data.getSubsubservicoQtd().toString());
        holder.pb_subservico.setProgress((int) data.getSubservicoPercent());
        holder.tv_subservicopercent.setText((int) data.getSubservicoPercent()+" %");

        if (data.getSubservicoObs().length() > 0) {
            holder.bt_Obs_Subservico.setImageResource(ic_baseline_check_circle_24);
        } else holder.bt_Obs_Subservico.setImageResource(ic_baseline_create_24);


        holder.bt_Foto_Subservico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.bt_Obs_Subservico.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SubservicosData data = dataList.get(holder.getAdapterPosition());
                database = RoomDB.getInstance(context);
                int _SubservicoID = data.getSubservicoID();
                int _ServicoID = data.getServicoID();
                int _BlocoID = data.getBlocoID();
                int _ObraID = data.getObraID();

                String sText = data.getSubservicoObs();
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);
                dialog.show();

                EditText editText = dialog.findViewById(R.id.edit_text);
                Button btUpdate = dialog.findViewById(R.id.bt_update);

                editText.setText(sText);

                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        String uText = editText.getText().toString().trim();
                        database.subservicosDao().UpdateObsSubservicoByID(uText, _SubservicoID);
                        dataList.clear();
                        dataList.addAll(database.subservicosDao().selectObsSubservicoByBlocoID(_ServicoID));
                        notifyDataSetChanged();
                    }
                });
            }
        });

        holder.bt_Foto_Subservico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FotoActivity.class);
                SubservicosData data = dataList.get(position);
                intent.putExtra("SubservicoID", data.getSubservicoID());
                intent.putExtra("ServicoID", data.getServicoID());
                intent.putExtra("BlocoID", data.getBlocoID());
                intent.putExtra("ObraID", data.getObraID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_subservicoTexto, tv_subservico_Num, tv_subservicoQtd, tv_subservicoUnidade, tv_subservicopercent;
        ImageView bt_Foto_Subservico, bt_Obs_Subservico;
        ProgressBar pb_subservico;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_subservicoTexto = itemView.findViewById(R.id.tv_subservicoTexto);
            tv_subservico_Num = itemView.findViewById(R.id.tv_subservicoNum);
            tv_subservicopercent = itemView.findViewById(R.id.tv_subservicopercent);
            bt_Foto_Subservico = itemView.findViewById(R.id.bt_foto_subservico);
            bt_Obs_Subservico = itemView.findViewById(R.id.bt_Obs_subservico);
            tv_subservicoQtd = itemView.findViewById(R.id.tv_subservicoQtd);
            tv_subservicoUnidade = itemView.findViewById(R.id.tv_subservicoUnidade);
            pb_subservico = itemView.findViewById(R.id.pb_subservico);

        }
    }
}
