package com.example.obassop.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.obassop.Adapters.ServicosAdapter;
import com.example.obassop.Dao.RoomDB;
import com.example.obassop.Data.ServicosData;
import com.example.obassop.MainActivity;
import com.example.obassop.R;

import java.util.ArrayList;
import java.util.List;

public class ServicosActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ServicosData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    ServicosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);

        int BlocoID = getIntent().getIntExtra("BlocoID", 0);
        int ObraID = getIntent().getIntExtra("ObraID", 0);

        database = RoomDB.getInstance(this);
        dataList = database.servicosDao().selectSercicoByID(ObraID, BlocoID);

        setTitle(database.blocosDao().SelectBlocoTextoByID(BlocoID));

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ServicosAdapter(ServicosActivity.this, dataList);
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