package com.example.obassop.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.obassop.Activities.BlocosActivity;
import com.example.obassop.Dao.ObrasDao;
import com.example.obassop.Dao.RoomDB;
import com.example.obassop.Data.BlocosData;
import com.example.obassop.Data.ObrasData;
import com.example.obassop.Data.ServicosData;
import com.example.obassop.Data.SubservicosData;
import com.example.obassop.R;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ObrasAdapter extends RecyclerView.Adapter<ObrasAdapter.ViewHolder> {

    private List<ObrasData> dataList;
    private Context context;
    private RoomDB database;
    int ObraID;

    Toast toast;

    public ObrasAdapter(Context context, List<ObrasData> dataListObras) {
        this.context = context;
        this.dataList = dataListObras;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_obra, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ObrasData data = dataList.get(position);
        database = RoomDB.getInstance(context);
        int Perc = (int) data.getObraPercent();
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        holder.tv_obraContrato.setText(data.getObraContrato());
        holder.tv_obraValor.setText(nf.format(data.getObraValor()));
        holder.tv_obraTexto.setText(data.getObraObjeto());
        holder.tv_obraSaldo.setText(nf.format(data.getObraSaldo()));
        holder.pbObra.setProgress(Perc);
        holder.tv_procentagem.setText(Perc + " %");

        String texto = "Exportação concluída";
        int duracao = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, texto, duracao);

        holder.bt_deleteObra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObrasData data1 = dataList.get(holder.getAdapterPosition());
                ObraID = data1.getObraID();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setTitle("Delete Obra");
                builder.setMessage(data1.getObraContrato());
                builder.setPositiveButton("Confirma",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                database.obrasDao().delete(data1);
                                int position = holder.getAdapterPosition();
                                dataList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, dataList.size());
                                database.obrasDao().deleteObrasByID(ObraID);
                                database.blocosDao().deleteBlocoByID(ObraID);
                                database.servicosDao().deleteServicoByID(ObraID);
                                database.subservicosDao().deleteSubservicoByID(ObraID);
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.bt_abreBloco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BlocosActivity.class);
                ObrasData data1 = dataList.get(holder.getAdapterPosition());
                ObraID = data1.getObraID();
                intent.putExtra("ObraID", ObraID);
                context.startActivity(intent);
            }
        });

        holder.bt_contato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObrasData data = dataList.get(holder.getAdapterPosition());
                database = RoomDB.getInstance(context);
                int _ObraID = data.getObraID();

                String obraNome = database.obrasDao().SelectNomeByObraID(_ObraID);
                String obraTelefone = database.obrasDao().SelectTelefoneByObraID(_ObraID);

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_contato);

                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);
                dialog.show();

                EditText Nome = dialog.findViewById(R.id.tv_textNome);
                EditText Telefone = dialog.findViewById(R.id.tv_textTelefone);

                Button btUpdatecontato = dialog.findViewById(R.id.bt_updateContato);

                Nome.setText(obraNome);
                Telefone.setText(obraTelefone);

                btUpdatecontato.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        String nomeText = Nome.getText().toString().trim();
                        String telefoneText = Telefone.getText().toString().trim();
                        database.obrasDao().UpdateContatoByObraID(nomeText, telefoneText, _ObraID);
                        dataList.clear();
                        dataList.addAll(database.obrasDao().getAll());
                        notifyDataSetChanged();
                    }
                });

            }
        });

        holder.bt_exportaCSV.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                File pasta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
                ObrasData data1 = dataList.get(holder.getAdapterPosition());
                ObraID = data1.getObraID();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String filePath = pasta.getPath() + "/CSV_Obras_SOP/Export" + data1.getObraContrato() + ";" + timeStamp + ".csv";
                File exportCSV = new File(filePath);

                List<ObrasData> dataListObras = new ArrayList<>();
                List<BlocosData> dataListBlocos = new ArrayList<>();
                List<ServicosData> dataListServicos = new ArrayList<>();
                List<SubservicosData> dataListSubservicos = new ArrayList<>();


                database = RoomDB.getInstance(context);
                dataListObras = database.obrasDao().getAll();
                dataListBlocos = database.blocosDao().getAll();
                dataListServicos = database.servicosDao().selectObsServicoByObraID(ObraID);
                dataListSubservicos = database.subservicosDao().selectObsSubservicoByObraID(ObraID);


                CSVWriter writer = null;

                // File exist
                if (exportCSV.exists() && !exportCSV.isDirectory()) {
                    FileWriter mFileWriter = null;
                    try {
                        mFileWriter = new FileWriter(filePath + ".csv", true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    writer = new CSVWriter(mFileWriter);
                } else {
                    try {
                        writer = new CSVWriter(new FileWriter(filePath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                String a[] = new String[dataListServicos.size()];
                int k = 0;

                String obraNome = database.obrasDao().SelectNomeByObraID(ObraID);
                String obraTelefone = database.obrasDao().SelectTelefoneByObraID(ObraID);

                if (obraNome == null){
                    obraNome ="";
                }
                if (obraTelefone == null){
                    obraTelefone = "";
                }

                if (obraNome.length() > 0 && obraTelefone.length() > 0) {
                    a[k] = obraNome + ";" + obraTelefone + "*";
                    k++;
                }
                else{
                    a[k] = " " + ";" + " " + "*";
                    k++;
                }

                for (int i = 0; i < dataListServicos.size(); i++) {
                    String OBS = dataListServicos.get(i).getServicoObs();
                    String Num = dataListServicos.get(i).getServicoNum();
                    if (OBS.length() > 0) {
                        a[k] = Num + ";" + OBS + "*";
                        k++;
                    }
                }
                for (int i = 0; i < dataListSubservicos.size(); i++) {
                    String OBS = dataListSubservicos.get(i).getSubservicoObs();
                    String Num = dataListSubservicos.get(i).getSubservicoNum();
                    if (OBS.length() > 0) {
                        a[k] = Num + ";" + OBS + "*";
                        k++;
                    }
                }

                writer.writeNext(a);
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                toast.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_obraContrato, tv_obraTexto, tv_obraValor, tv_obraSaldo, tv_nome, tv_telefone, tv_procentagem;
        ImageView bt_abreBloco, bt_deleteObra, bt_exportaCSV, bt_contato;
        ProgressBar pbObra;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_obraContrato = itemView.findViewById(R.id.tv_obraContrato);
            tv_obraSaldo = itemView.findViewById(R.id.tv_obraSaldo);
            tv_obraTexto = itemView.findViewById(R.id.tv_obraTexto);
            tv_obraValor = itemView.findViewById(R.id.tv_obraValor);
            tv_nome = itemView.findViewById(R.id.tv_textNome);
            tv_telefone = itemView.findViewById(R.id.tv_textTelefone);
            tv_procentagem = itemView.findViewById(R.id.tv_obraporcentagem);
            bt_contato = itemView.findViewById(R.id.bt_contato);
            bt_abreBloco = itemView.findViewById(R.id.bt_abreBloco);
            bt_exportaCSV = itemView.findViewById(R.id.bt_exportaCSV);
            bt_deleteObra = itemView.findViewById(R.id.bt_deleteObra);
            pbObra = itemView.findViewById(R.id.determinateBar);
        }
    }
}
