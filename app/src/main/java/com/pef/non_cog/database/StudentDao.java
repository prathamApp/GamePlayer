package com.pef.non_cog.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import com.pef.non_cog.modalclass.Student;

@Dao
public interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertAllStudent(List<Student> studList);

    @Query("SELECT COUNT(*) FROM Student")
    public int getStudantCount();

   /* @Query("SELECT * FROM Student")
    public List<Student> getAllStudant();*/

    @Query("SELECT * FROM Student WHERE StudentId=:id")
    public Student getStudantByID(String id);

    @Query("SELECT * FROM Student WHERE GroupId=:groupId")
    public List<Student> getStudantByGroupID(String groupId);


    @Query("SELECT * FROM Student WHERE StudentId IN(:idList)")
    public List<Student> getStudantsByIDs(List<String> idList);

    @Query("DELETE FROM Student")
    public int deleteAllStudent();
}
