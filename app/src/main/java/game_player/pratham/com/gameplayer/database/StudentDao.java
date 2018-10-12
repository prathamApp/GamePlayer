package game_player.pratham.com.gameplayer.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import game_player.pratham.com.gameplayer.modalclass.Student;

@Dao
public interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertAllStudent(List<Student> studList);

    @Query("SELECT COUNT(*) FROM Student")
    public int getStudantCount();

    @Query("SELECT * FROM Student")
    public List<Student> getAllStudant();

    @Query("SELECT * FROM Student WHERE StudentId=:id")
    public Student getStudantByID(String id);

}
