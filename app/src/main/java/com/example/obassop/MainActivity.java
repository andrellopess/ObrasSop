package com.example.obassop;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.obassop.Adapters.BlocosAdapter;
import com.example.obassop.Adapters.ObrasAdapter;
import com.example.obassop.Adapters.ServicosAdapter;
import com.example.obassop.Adapters.SubservicosAdapter;
import com.example.obassop.Dao.RoomDB;
import com.example.obassop.Data.BlocosData;
import com.example.obassop.Data.ObrasData;
import com.example.obassop.Data.ServicosData;
import com.example.obassop.Data.SubservicosData;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button bt_addObra;
    RecyclerView recyclerView;
    List<ObrasData> dataListObras = new ArrayList<>();
    List<BlocosData> dataListBlocos = new ArrayList<>();
    List<ServicosData> dataListServicos = new ArrayList<>();
    List<SubservicosData> dataListSubservicos = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    ObrasAdapter adapterObras;
    BlocosAdapter adapterBlocos;
    ServicosAdapter adapterServicos;
    SubservicosAdapter adapterSubservicos;
    File pasta;
    String[] line;

    public static boolean tirandoFoto = true;

    private final int PERMISSAO_REQUEST = 2;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("OBRAS");

        bt_addObra = findViewById(R.id.bt_addObra);
        recyclerView = findViewById(R.id.recycler_view);

        database = RoomDB.getInstance(this);
        dataListObras = database.obrasDao().getAll();
        dataListBlocos = database.blocosDao().getAll();
        dataListServicos = database.servicosDao().getAll();
        dataListSubservicos = database.subservicosDao().getAll();

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterObras = new ObrasAdapter(MainActivity.this, dataListObras);
        adapterBlocos = new BlocosAdapter(MainActivity.this, dataListBlocos);
        adapterServicos = new ServicosAdapter(MainActivity.this, dataListServicos);
        adapterSubservicos = new SubservicosAdapter(MainActivity.this, dataListSubservicos);

        recyclerView.setAdapter(adapterObras);

        requestPermission();

        pasta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        new File(pasta.getPath() + "/CSV_Obras_SOP").mkdirs();
        new File(pasta.getPath() + "/FOTOS_Obras_SOP").mkdirs();

        bt_addObra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent()
                        .setType("*/*")
                        .setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Selecione o CSV"), 123);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK) {
            Uri selectedfile = data.getData(); //The uri with the location of the file
            File file = new File(selectedfile.getPath());
            String nome = file.getName();
            File csv = new File(pasta.getPath() + "/CSV_Obras_SOP/" + nome);

            int ObraID = 0, BlocoID = 0, ServicoID = 0;

            try {

                CSVReader reader = new CSVReader(new FileReader(csv.getAbsolutePath()));

                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {

                    int tamanho = nextLine.length;
                    String joinLine = "";

                    for (int j = 0; j < tamanho; j++) {
                        if (j == 0) {
                            joinLine = nextLine[j];
                        } else {
                            joinLine = joinLine + "," + nextLine[j];
                        }
                    }

                    line = joinLine.split(";");

                    if (reader.getLinesRead() == 1) {
                        ObrasData ObrasDados = new ObrasData();
                        ObrasDados.setObraPercent(Double.parseDouble(line[0].replace(",", ".")));
                        ObrasDados.setObraContrato(line[1]);
                        ObrasDados.setObraObjeto(line[2]);
                        ObrasDados.setObraValor(Double.parseDouble(line[3].replace(".", "").replace(",", ".")));
                        ObrasDados.setObraSaldo(Double.parseDouble(line[4].replace(".", "").replace(",", ".")));
                        new File(pasta.getPath() + "/FOTOS_Obras_SOP/" + line[1]).mkdirs();
                        database.obrasDao().insert(ObrasDados);
                        ObraID = database.obrasDao().ObraID(line[1]);
                        dataListObras.clear();
                        dataListObras.addAll(database.obrasDao().getAll());
                        adapterObras.notifyDataSetChanged();
                    }

                    if (reader.getLinesRead() > 1) {

                        if (countChar(line[1], '.') == 0) {
                            BlocosData BlocosDados = new BlocosData();
                            BlocosDados.setObraID(ObraID);
                            BlocosDados.setBlocoPercent(Double.parseDouble(line[0].replace(",", ".")));
                            BlocosDados.setBlocoNum(line[1]);
                            BlocosDados.setBlocoTexto(line[2]);
                            database.blocosDao().insert(BlocosDados);
                            BlocoID = database.blocosDao().BlocoID(line[2], ObraID);
                            dataListBlocos.clear();
                            dataListBlocos.addAll(database.blocosDao().getAll());
                            adapterBlocos.notifyDataSetChanged();
                        }

                        if (countChar(line[1], '.') == 1) {
                            ServicosData ServicosDados = new ServicosData();
                            ServicosDados.setObraID(ObraID);
                            ServicosDados.setBlocoID(BlocoID);
                            ServicosDados.setServicoObs("");
                            ServicosDados.setServicoPercent(Double.parseDouble(line[0].replace(",", ".")));
                            ServicosDados.setServicoNum(line[1]);
                            ServicosDados.setServicoTexto(line[2]);
                            String[] lista = line;
                            int a = lista.length;
                            if (a > 3) {
                                ServicosDados.setServicoUnidade(line[3]);
                                ServicosDados.setServicoQtd(Double.valueOf(line[4].replace(".", "").replace(",", ".")));
                            }
                            database.servicosDao().insert(ServicosDados);
                            ServicoID = database.servicosDao().ServicoID(line[2], BlocoID, ObraID);
                            dataListServicos.clear();
                            dataListServicos.addAll(database.servicosDao().getAll());
                            adapterServicos.notifyDataSetChanged();
                        }

                        if (countChar(line[1], '.') == 2) {
                            SubservicosData SubservicosDados = new SubservicosData();
                            SubservicosDados.setObraID(ObraID);
                            SubservicosDados.setBlocoID(BlocoID);
                            SubservicosDados.setSubservicoObs("");
                            SubservicosDados.setServicoID(ServicoID);
                            SubservicosDados.setSubservicoPercent(Double.parseDouble(line[0].replace(",", ".")));
                            SubservicosDados.setSubservicoNum(line[1]);
                            SubservicosDados.setSubservicoTexto(line[2]);
                            String[] lista = line;
                            int a = lista.length;
                            if (a > 3) {
                                SubservicosDados.setSubservicoUnidade(line[3]);
                                SubservicosDados.setSubsubservicoQtd(Double.valueOf(line[4].replace(".", "").replace(",", ".")));
                            }
                            database.subservicosDao().insert(SubservicosDados);
                            dataListSubservicos.clear();
                            dataListSubservicos.addAll(database.subservicosDao().getAll());
                            adapterSubservicos.notifyDataSetChanged();
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Problema na importação", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION}, 3);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSAO_REQUEST);
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                        PERMISSAO_REQUEST);
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSAO_REQUEST);
            }
        }
    }

    public int countChar(String string, Character character) {
        int count = 0;

        //Counts each character except space
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == character)
                count++;
        }
        return count;
    }
}