package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;

public class Player
{
    public String name;
    public boolean isInGame;
    public boolean color;
    public boolean nonSpectator = true;
    //true is red, false is white.
    public Player opponent;
    public boolean connectedToGame;
    public ArrayList<Piece> myPiece = new ArrayList<>();
    public Board board;
    public boolean isMyTurn;
    private Piece currentPiece = null;
    private String watcher_turn;

    public Player(String name)
    {
        this.name = name;
        isInGame = false;
    }

    /**
     * Give the player a set of all his pieces
     * @param myPiece List<Piece></Piece>
     */
    public void setPiece(ArrayList<Piece> myPiece){
        this.myPiece = myPiece;
    }
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    /**public void setStatus(boolean a){
        this.isInGame = a;
    }**/
    public boolean getStatus()
    {
        return isInGame;
    }

    public void switchStatus()
    {
        if(isInGame == false)
        {//
            isInGame = true;
        }
        else
        {
            isInGame = false;
        }
    }

    public void setColor(boolean a)
    {
        color = a;
    }
    public void setStatus(boolean a){
        this.isInGame = a;
    }
    public boolean getColor()
    {
        return color;
    }

    public void setOpponent(Player p)
    {
        opponent = p;
    }

    public Player getOpponent()
    {
        return opponent;
    }

    public boolean getGameConnection()
    {
        return connectedToGame;
    }

    public void setGameConnection(boolean a)
    {
        connectedToGame = a;
    }

    public ArrayList<Piece> getMyPiece(){
        return this.myPiece;
    }

    public void addPiece(Piece p)
    {
        myPiece.add(p);
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public boolean getMyTurn()
    {
        return isMyTurn;
    }

    public void setTurn(boolean a)
    {
        isMyTurn = a;
    }

    public void setCurrentPiece(Piece c)
    {
        currentPiece = c;
    }

    public Piece getCurrentPiece()
    {
        return currentPiece;
    }

    public boolean canMakeMove()
    {
        boolean bob = false;
        for(Piece c : myPiece)
        {
            if(c.getSingleJumpPossibles().size() > 0)
            {
                bob = true;
            }
            if(c.getSimpleMovePossible().size() > 0)
            {
                bob = true;
            }
        }
        return bob;
    }

    public void setNonspectator(boolean t)
    {
        nonSpectator = t;
    }

    public boolean getNonspectator()
    {
        return nonSpectator;
    }

    public String getwatcher_turn()
    {
        return watcher_turn;
    }

    public void setWatcher_turn(String t)
    {
        watcher_turn = t;
    }
}
