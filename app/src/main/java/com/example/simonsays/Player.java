package com.example.simonsays;

public class Player {

    private String mName;
    private int mScore=0;

    public Player(String iName)
    {
        this.mName=iName;
        this.mScore=0;
    }

    public String getName()
    {
        return mName;
    }

    public void setName(String iName)
    {
        mName=iName;
    }

    public int getScore()
    {
        return mScore;
    }

    public void setScore(int iScore)
    {
        mScore=iScore;
    }

}
