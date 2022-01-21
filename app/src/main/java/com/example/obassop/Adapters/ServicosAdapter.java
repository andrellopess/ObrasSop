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
import com.example.obassop.Activities.SubservicosActivity;
import com.example.obassop.Dao.RoomDB;
import com.example.obassop.Data.ServicosData;
import com.example.obassop.R;

import java.util.List;

import static com.example.obassop.R.drawable.ic_baseline_check_circle_24;
import static com.example.obassop.R.drawable.ic_baseline_create_24;

public class ServicosAdapter extends RecyclerView.Adapter<ServicosAdapter.ViewHolder> {

    private List<ServicosData> dataList;
    private Activity context;
    private RoomDB database;
    int ObraID, BlocoID, ServicoID;


    public ServicosAdapter(Activity context, List<ServicosData> dataList) {
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_servicos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServicosData data = dataList.get(position);
        database = RoomDB.getInstance(context);
        int contador = database.subservicosDao().countSubsercicoByID(data.getServicoID());

        String qtde = "0";

        if (data.getServicoQtd() == null) {
            qtde = "";
        } else {

            qtde = data.getServicoQtd().toString();
        }

        if (contador == 0) {
            holder.bt_ListarSubservico.setVisibility(View.INVISIBLE);
            holder.tv_servicoQtd.setText(qtde);
            holder.tv_servicoUnidade.setText(data.getServicoUnidade());
            holder.tv_servico_Num.setText(data.getServicoNum());
            holder.tv_servicoTexto.setText(data.getServicoTexto());
            holder.pb_servico.setProgress((int) data.getServicoPercent());
            holder.tv_servicopercent.setText((int) data.getServicoPercent() + " %");
        } else {
            holder.tv_servicoQtd.setVisibility(View.INVISIBLE);
            holder.tv_servicoUnidade.setVisibility(View.INVISIBLE);
            holder.tv_servico_Num.setText(data.getServicoNum());
            holder.tv_servicoTexto.setText(data.getServicoTexto());
            holder.pb_servico.setProgress((int) data.getServicoPercent());
            holder.tv_servicopercent.setText((int) data.getServicoPercent() + " %");
        }

        if (data.getServicoObs().length() > 0) {
            holder.bt_servicoObs.setImageResource(ic_baseline_check_circle_24);
        } else holder.bt_servicoObs.setImageResource(ic_baseline_create_24);

        holder.bt_ListarSubservico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SubservicosActivity.class);
                ServicosData data = dataList.get(position);
                ServicoID = data.getServicoID();
                BlocoID = data.getBlocoID();
                ObraID = data.getObraID();
                intent.putExtra("BlocoID", BlocoID);
                intent.putExtra("ObraID", ObraID);
                intent.putExtra("ServicoID", ServicoID);
                context.startActivity(intent);
            }
        });

        holder.bt_servicoObs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ServicosData data = dataList.get(holder.getAdapterPosition());
                database = RoomDB.getInstance(context);
                int _ServicoID = data.getServicoID();
                int _BlocoID = data.getBlocoID();
                int _ObraID = data.getObraID();

                String sText = data.getServicoObs();
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
                        database.servicosDao().UpdateObsByID(uText, _ServicoID);
                        dataList.clear();
                        dataList.addAll(database.servicosDao().selectObsServicoByBlocoID(_BlocoID));
                        notifyDataSetChanged();
                    }
                });

            }
        });

        holder.bt_Foto_Servico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FotoActivity.class);
                ServicosData data = dataList.get(position);
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

        TextView tv_servicoTexto, tv_servico_Num, tv_servicoQtd, tv_servicoUnidade, tv_servicopercent;
        ImageView bt_ListarSubservico, bt_Foto_Servico, bt_servicoObs;
        ProgressBar pb_servico;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_servicoTexto = itemView.findViewById(R.id.tv_servicoTexto);
            tv_servico_Num = itemView.findViewById(R.id.tv_servicoNum);
            tv_servicoQtd = itemView.findViewById(R.id.tv_servicoQtd);
            tv_servicoUnidade = itemView.findViewById(R.id.tv_servicoUnidade);
            tv_servicopercent = itemView.findViewById(R.id.tv_servicopercent);
            bt_ListarSubservico = itemView.findViewById(R.id.bt_Listar_Subservico);
            bt_Foto_Servico = itemView.findViewById(R.id.bt_foto_servico);
            pb_servico = itemView.findViewById(R.id.pb_servico);
            bt_servicoObs = itemView.findViewById(R.id.bt_Obs_servico);

        }
    }

}
