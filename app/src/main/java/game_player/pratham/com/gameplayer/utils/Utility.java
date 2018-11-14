package game_player.pratham.com.gameplayer.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

public class Utility {
    public static String getProperty(String key, Context context) {
        try {
            Properties properties = new Properties();
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("config.properties");
            properties.load(inputStream);
            return properties.getProperty(key);
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getUniqueID() {
        return UUID.randomUUID().toString();
    }
}
