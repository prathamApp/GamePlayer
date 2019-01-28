package game_player.pratham.com.gameplayer.modalclass;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class StudentScoreDetail {
    @PrimaryKey(autoGenerate = true)
    int id;
    String studID;
    String studName;
    String gameName;
    String gameID;
    String startTime;
    String timeTaken;
    int totalMarks;
    int scoredMarks;
    String session;
    String lebel;
    boolean isHelpTaken = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudID() {
        return studID;
    }

    public void setStudID(String studID) {
        this.studID = studID;
    }

    public String getStudName() {
        return studName;
    }

    public void setStudName(String studName) {
        this.studName = studName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public int getScoredMarks() {
        return scoredMarks;
    }

    public void setScoredMarks(int scoredMarks) {
        this.scoredMarks = scoredMarks;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public boolean isHelpTaken() {
        return isHelpTaken;
    }

    public void setHelpTaken(boolean helpTaken) {
        isHelpTaken = helpTaken;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getLebel() {
        return lebel;
    }

    public void setLebel(String lebel) {
        this.lebel = lebel;
    }
}
