package com.example.herohabit;

import java.util.ArrayList;

public class User {
    private int userID;
    private String username;
    private String password;
    private int level;
    private int experience;
    private ArrayList<Mission> missionList;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public ArrayList<Mission> getMissionList() {
        return missionList;
    }

    public void setMissionList(ArrayList<Mission> missionList) {
        this.missionList = missionList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}