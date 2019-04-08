package com.pef.non_cog.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.pef.non_cog.modalclass.StudentScoreDetail;

@Dao
public interface StudentScoreDetailsDao {
    @Insert
    public Long insertScore(StudentScoreDetail studentScoreDetails);
}
