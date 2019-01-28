package game_player.pratham.com.gameplayer.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import game_player.pratham.com.gameplayer.modalclass.StudentScoreDetail;

@Dao
public interface StudentScoreDetailsDao {
    @Insert
    public Long insertScore(StudentScoreDetail studentScoreDetails);
}
