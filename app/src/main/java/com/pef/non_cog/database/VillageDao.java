package com.pef.non_cog.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import com.pef.non_cog.modalclass.Village;

@Dao
public interface VillageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAllVillages(List<Village> villagesList);

    @Query("SELECT DISTINCT VillageId ,VillageName FROM Village WHERE VillageId In(:villageIds)")
    public List<Village> getUniqVillageNames(List<String> villageIds);

    @Query("DELETE FROM Village ")
    public int deleteAllVillages();
}
