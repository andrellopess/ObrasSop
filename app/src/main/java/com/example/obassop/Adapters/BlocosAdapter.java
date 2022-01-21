package com.example.obassop.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.obassop.Activities.ServicosActivity;
import com.example.obassop.Dao.RoomDB;
import com.example.obassop.Data.BlocosData;
import com.example.obassop.R;

import java.util.List;

public class BlocosAdapter extends RecyclerView.Adapter<BlocosAdapter.ViewHolder> {

    private List<BlocosData> dataList;
    private Activity context;
    private RoomDB database;
    int BlocoID, ObraID;


    public BlocosAdapter(Activity context, List<BlocosData> dataList) {
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BlocosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_blocos, parent, false);
        return new BlocosAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlocosAdapter.ViewHolder holder, int position) {
        BlocosData data = dataList.get(position);
        BlocoID = data.getBlocoID();
        database = RoomDB.getInstance(context);
        holder.tv_bloco_Num.setText(data.getBlocoNum());
        holder.tv_bloco_Texto.setText(data.getBlocoTexto());
        holder.pb_bloco.setProgress((int) data.getBlocoPercent());
        holder.tv_blocopercent.setText((int) data.getBlocoPercent() + " %");


        holder.bt_ListarServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ServicosActivity.class);
                BlocosData data = dataList.get(position);
                BlocoID = data.getBlocoID();
                ObraID = data.getObraID();
                intent.putExtra("BlocoID", BlocoID);
                intent.putExtra("ObraID", ObraID);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_bloco_Texto, tv_bloco_Num, tv_blocopercent;
        ImageView bt_ListarServico;
        ProgressBar pb_bloco;
        Button bt_voltar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_bloco_Texto = itemView.findViewById(R.id.tv_blocoTexto);
            tv_bloco_Num = itemView.findViewById(R.id.tv_numBloco);
            tv_blocopercent = itemView.findViewById(R.id.tv_blocopercent);
            bt_ListarServico = itemView.findViewById(R.id.bt_abreServico);
            pb_bloco = itemView.findViewById(R.id.determinateBar);
        }
    }
}
