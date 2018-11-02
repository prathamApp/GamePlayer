package game_player.pratham.com.gameplayer.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import java.util.List;

import game_player.pratham.com.gameplayer.modalclass.Village;

@Dao
public interface VillageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAllVillages(List<Village> villagesList);
}
