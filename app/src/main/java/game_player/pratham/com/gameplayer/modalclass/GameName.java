package game_player.pratham.com.gameplayer.modalclass;

import com.google.gson.annotations.SerializedName;

public class GameName {
    @SerializedName("gameId")
    String id;
    @SerializedName("gameName")
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
