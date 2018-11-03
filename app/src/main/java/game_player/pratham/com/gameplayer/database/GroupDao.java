package game_player.pratham.com.gameplayer.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import game_player.pratham.com.gameplayer.modalclass.Groups;

@Dao
public interface GroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAllGroups(List<Groups> groupsList);

    @Query("SELECT DISTINCT Block FROM Village")
    public List<String> getUniqBlockNames();
}
