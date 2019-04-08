package com.pef.non_cog.interfaces;

public interface JSListner {
    public void playNext();

    /*public  void needHelp();*/
    public void addScore(int totalMarks, int scoredMarks, boolean isHelpTaken, String timeTaken, String startTime, String label, boolean isGroupGame);
}
