package com.example.obassop.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.obassop.Dao.RoomDB;
import com.example.obassop.MainActivity;
import com.example.obassop.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FotoActivity extends AppCompatActivity {

    private final int CAMERA = 4;
    private long UPDATE_INTERVAL = 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 1000; /* 2 sec */

    public String contrato;
    public String ServicoNum = "", SubservicoNum = "";
    private RoomDB database;

    private File arquivoFoto = null;
    private static boolean tirandoFoto = true;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);

        int SubervicoID = getIntent().getIntExtra("SubervicoID", 0);
        int ServicoID = getIntent().getIntExtra("ServicoID", 0);
        int BlocoID = getIntent().getIntExtra("BlocoID", 0);
        int ObraID = getIntent().getIntExtra("ObraID", 0);

        database = RoomDB.getInstance(this);
        ServicoNum = database.servicosDao().selectNumSercicoByID(ServicoID);
        contrato = database.obrasDao().SelectContratoByObraID(ObraID);
        SubservicoNum = database.subservicosDao().selectNumSubsercicoByID(SubervicoID);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        if (MainActivity.tirandoFoto) {
            MainActivity.tirandoFoto = false;
            tirarFoto();
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void tirarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                arquivoFoto = criarArquivo();
            } catch (IOException ex) {
            }

            if (arquivoFoto != null) {
                Uri photoURI = FileProvider.getUriForFile(getBaseContext(),
                        getBaseContext().getApplicationContext().getPackageName() +
                                ".provider", arquivoFoto);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA);
                try {
                    MediaStore.Images.Media.insertImage(getContentResolver(), arquivoFoto.getAbsolutePath(), arquivoFoto.getName(), arquivoFoto.getName());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(arquivoFoto)));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA && resultCode == RESULT_OK) {
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(arquivoFoto))
            );
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private File criarArquivo() throws IOException {
        String name = null;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        if (ServicoNum == "") {
            name = SubservicoNum.replace(".", "-") + "-" + timeStamp;
        } else {
            name = ServicoNum.replace(".", "-") + "-" + timeStamp;
        }
        File pasta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File imagem = new File(pasta.getPath() + "/FOTOS_Obras_SOP/" + contrato + "/" + name + ".jpg");

        return imagem;
    }
}