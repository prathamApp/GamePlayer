package com.pef.non_cog.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.pef.non_cog.modalclass.Groups;
import com.pef.non_cog.modalclass.Student;
import com.pef.non_cog.modalclass.StudentScoreDetail;
import com.pef.non_cog.modalclass.Village;

@Database(entities = {Student.class, Village.class, Groups.class, StudentScoreDetail.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract StudentDao getStudentDao();

    public abstract VillageDao getVillageDao();

    public abstract GroupDao getGroupDao();

    public abstract StudentScoreDetailsDao getStudentScoreDetailsDao();

    private static AppDatabase DATABASEINSTANCE;

    public static AppDatabase getDatabaseInstance(Context context) {
        if (DATABASEINSTANCE == null)
            DATABASEINSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "gamesApp").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        return DATABASEINSTANCE;
    }

    public static void destroyInstance() {
        DATABASEINSTANCE = null;
    }

}
