package com.example.obassop.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.obassop.Adapters.SubservicosAdapter;
import com.example.obassop.Dao.RoomDB;
import com.example.obassop.Data.SubservicosData;
import com.example.obassop.MainActivity;
import com.example.obassop.R;

import java.util.ArrayList;
import java.util.List;

public class SubservicosActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<SubservicosData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    SubservicosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subservicos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);

        ActionBar actionBar = getSupportActionBar();

        int BlocoID = getIntent().getIntExtra("BlocoID", 0);
        int ObraID = getIntent().getIntExtra("ObraID", 0);
        int ServicoID = getIntent().getIntExtra("ServicoID", 0);

        database = RoomDB.getInstance(this);
        dataList = database.subservicosDao().selectSubsercicoByID(ObraID, BlocoID, ServicoID);

        actionBar.setTitle(database.blocosDao().SelectBlocoTextoByID(BlocoID));
        actionBar.setSubtitle(database.servicosDao().SelectServicoTextoByID(ServicoID));

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new SubservicosAdapter(SubservicosActivity.this, dataList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.tirandoFoto = true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}