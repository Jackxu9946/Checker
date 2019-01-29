package com.webcheckers.model;

public class Space {

    public enum color{
        RED, WHITE
    }

    private color COLOR;
    private int cellId;
    private Piece Pie = null;

    public Space(String col, int cellId){
        this.cellId = cellId;
        if(col.equals("Red"))
        {
            COLOR = color.RED;
        }
        else
        {
            COLOR = color.WHITE;
        }
    }

    public boolean isValid()
    {
        if(COLOR.equals(color.WHITE) && (this.Pie == null))
            return true;
        return false;
    }

    public int getCellId()
    {
        return cellId;
    }

    /**
     * Adds a piece to the space
     * @param Pie
     */
    public void addPiece(Piece Pie){
        this.Pie = Pie;
    }

    public Piece getPiece() {
        return Pie;
    }

}
