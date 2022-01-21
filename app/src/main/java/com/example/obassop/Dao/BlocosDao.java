package com.example.obassop.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.obassop.Data.BlocosData;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface BlocosDao {

    @Insert(onConflict = REPLACE)
    void insert(BlocosData BlocosData);

    @Delete
    void delete(BlocosData BlocosData);

    @Query("DELETE FROM Blocos WHERE ObraID = :ID")
    void deleteBlocoByID(int ID);

    @Query("SELECT * FROM Blocos WHERE ObraID = :ID")
    List<BlocosData> selectBlocoByID(int ID);

    @Query("SELECT * FROM Blocos WHERE blocoTexto = :BLOCOTEXTO and  ObraID = :OBRAID")
    int BlocoID(String BLOCOTEXTO, int OBRAID);

    @Query("SELECT blocoTexto FROM Blocos WHERE BlocoID = :BLOCOID")
    String SelectBlocoTextoByID(int BLOCOID);

    @Delete
    void reset(List<BlocosData> BlocosData);

    @Query("SELECT * FROM Blocos")
    List<BlocosData> getAll();


}
