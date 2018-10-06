package game_player.pratham.com.gameplayer.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import game_player.pratham.com.gameplayer.modalclass.Student;

@Database(entities = {Student.class}, version = 3, exportSchema = false)
public class AppDatabase extends RoomDatabase{
    private static AppDatabase DATABASEINSTANCE;
    public static AppDatabase getDatabaseInstance(Context context) {
        if (DATABASEINSTANCE == null)
            DATABASEINSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "gamesApp").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        return DATABASEINSTANCE;
    }

    public static void destroyInstance() {
        DATABASEINSTANCE = null;
    }

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
