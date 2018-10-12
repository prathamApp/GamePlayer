package game_player.pratham.com.gameplayer.modalclass;

public class StudentForSpinner {
    String studId;
    String studName;
    boolean isSelected=false;

    public StudentForSpinner(String studName) {
        this.studName = studName;
    }

    public StudentForSpinner(String studId, String studName) {
        this.studId = studId;
        this.studName = studName;
    }

    @Override
    public String toString() {
        return  studName ;
    }

    public String getStudId() {
        return studId;
    }

    public void setStudId(String studId) {
        this.studId = studId;
    }

    public String getStudName() {
        return studName;
    }

    public void setStudName(String studName) {
        this.studName = studName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
