package com.example.obassop.Dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.obassop.Data.BlocosData;
import com.example.obassop.Data.ObrasData;
import com.example.obassop.Data.ServicosData;
import com.example.obassop.Data.SubservicosData;

@Database(entities = {ObrasData.class, BlocosData.class, ServicosData.class, SubservicosData.class}, version = 19, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB database;
    private static String DATABASE_NAME = "database";

    public synchronized static RoomDB getInstance(Context context) {
        if (database == null) {

            database = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract ObrasDao obrasDao();

    public abstract BlocosDao blocosDao();

    public abstract ServicosDao servicosDao();

    public abstract SubservicosDao subservicosDao();
}
