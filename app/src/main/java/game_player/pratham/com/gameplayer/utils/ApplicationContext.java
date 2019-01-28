package game_player.pratham.com.gameplayer.utils;

import android.app.Application;

/**
 * Created by PEF on 16/11/2018.
 */

public class ApplicationContext extends Application {
    private static ApplicationContext applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
    }

}
