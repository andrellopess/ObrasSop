package com.example.obassop.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.obassop.Adapters.BlocosAdapter;
import com.example.obassop.Dao.RoomDB;
import com.example.obassop.Data.BlocosData;
import com.example.obassop.R;

import java.util.ArrayList;
import java.util.List;

public class BlocosActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<BlocosData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    BlocosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);

        int ObraID = getIntent().getIntExtra("ObraID", 0);

        database = RoomDB.getInstance(this);
        dataList = database.blocosDao().selectBlocoByID(ObraID);

        //setTitle(database.obrasDao().SelectContratoByObraID(ObraID));

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new BlocosAdapter(BlocosActivity.this, dataList);
        recyclerView.setAdapter(adapter);

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