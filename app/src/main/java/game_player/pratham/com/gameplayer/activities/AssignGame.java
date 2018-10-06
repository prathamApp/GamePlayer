package game_player.pratham.com.gameplayer.activities;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import game_player.pratham.com.gameplayer.R;
import game_player.pratham.com.gameplayer.modalclass.GameName;

public class AssignGame extends AppCompatActivity {
    /*@BindView(R.id.recyclerView)
    RecyclerView recyclerView;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_game);
        ButterKnife.bind(this);
        //getGameNamesFromAssets();
    }
   /* private void getGameNamesFromAssets() {
        try {
           String[] list = getAssets().list("");
            AssetManager assetManager = getResources().getAssets();
            InputStream inputStream=assetManager.open("text");
            int size=inputStream.available();
            byte buffer[]=  new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonGames=new String(buffer,"UTF-8");
           // showRecycler(jsonGames);
        } catch (IOException e) {
            Toast.makeText(this, "Game Names Json not fount in assets ", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }*/

    /*private void showRecycler(String jsonGames) {
        Gson gson=new Gson();
        TypeToken<List<GameName>> token = new TypeToken<List<GameName>>() {};
        List<GameName> animals = gson.fromJson(jsonGames, token.getType());

    }*/
}
