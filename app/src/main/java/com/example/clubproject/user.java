package com.example.clubproject;

import java.util.ArrayList;

public class user
{
    private ArrayList<ClubSave> usersClubs;
    public user()
    {
        usersClubs = new ArrayList<ClubSave>();
    }
    public ArrayList<ClubSave> getUsersClubs() {
        return usersClubs;
    }
    public void addClub(ClubSave club)
    {
        usersClubs.add(club);
    }
    public void removeClub(ClubSave club)
    {
        usersClubs.remove(club);
    }
}
