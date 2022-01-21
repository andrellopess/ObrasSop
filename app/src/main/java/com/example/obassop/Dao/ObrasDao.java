package com.example.obassop.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.obassop.Data.ObrasData;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ObrasDao {

    @Insert(onConflict = REPLACE)
    void insert(ObrasData obrasData);

    @Delete
    void delete(ObrasData obrasData);

    @Delete
    void reset(List<ObrasData> obrasData);

    @Query("DELETE FROM Obras WHERE ObraID = :ID")
    void deleteObrasByID(int ID);

    @Query("SELECT * FROM Obras WHERE ObraContrato = :OBRACONTRATO")
    int ObraID(String OBRACONTRATO);

    @Query("SELECT ObraContrato FROM Obras WHERE ObraID = :OBRAID")
    String SelectContratoByObraID(int OBRAID);

    @Query("SELECT * FROM Obras")
    List<ObrasData> getAll();

    @Query("UPDATE Obras SET ObraNome = :OBRANOME, ObraTelefone = :OBRATELEFONE  WHERE ObraID = :OBRAID")
    void UpdateContatoByObraID(String OBRANOME, String OBRATELEFONE, int OBRAID);

    @Query("SELECT ObraNome FROM Obras WHERE ObraID = :OBRAID")
    String SelectNomeByObraID(int OBRAID);

    @Query("SELECT ObraTelefone FROM Obras WHERE ObraID = :OBRAID")
    String SelectTelefoneByObraID(int OBRAID);
}
