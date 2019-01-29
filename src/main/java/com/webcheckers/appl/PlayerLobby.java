package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.ArrayList;

public class PlayerLobby
{
    private int currentGameCount = 0;

    private ArrayList<Player> players = new ArrayList<>();

    public PlayerLobby()
    {
        players.add(new Player("COMPUTER"));
    }

    public void addPlayer(Player play)
    {
        players.add(play);
    }

    public ArrayList<Player> getPlayers()
    {
        return players;
    }

    public Player getPlayer(String name)
    {
        if(name == "" || name == null) {
        Player temp = new Player("");
        temp.setStatus(true);
        return temp;
        }
        for(Player p : players)
        {
            if(name.equals(p.getName()))
            {//hih
                return p;
            }
        }
        return null;
    }

    public ArrayList<String> getPlayerNames()
    {
        ArrayList<String> names = new ArrayList<>();

        for(Player p : players)
        {
            names.add(p.getName());
        }
        return names;
    }

    public int getPlayerNumber()
    {
        int i = 0;
        for(Player p : players)
        {
           i++;
        }
        return i;
    }

    public boolean isNameAvailable(Player tempPlayer)
    {
        boolean temp = true;
        for(Player player : players)
        {
            if(tempPlayer.getName().equals(player.getName()))
            {
                temp = false;
            }
        }
        if(temp)
        {
            return true;
        }
        else
            return false;
    }

    public boolean isNameValid(Player tempPlayer)
    {
        String temp = tempPlayer.getName();
        if(temp.contains("\""))
        {
            return false;
        }
        return true;
    }

    public void remove(Player tempPlayer)
    {
        int i = -1;
        for(Player p : players)
        {
            if(p.getName().equals(tempPlayer.getName()))
            {
                i = players.indexOf(p);
            }
        }
        if(i != -1)
        {
            players.remove(i);
        }
    }

    public int getCurrentGameCount() {
        return currentGameCount;
    }

    public void increaseGameCount()
    {
        currentGameCount = currentGameCount + 1;
    }
}