package com.webcheckers.model;

public class Position {
    int row;
    int cell;

    public Position(int row, int cell){
        this.row = row;
        this.cell = cell;
    }

    public int getRow() {
        return row;
    }

    public int getCell() {
        return cell;
    }

    @Override
    public boolean equals(Object x)
    {
        if(x instanceof Position) {
            Position b = (Position)x;
            if (this.row == b.getRow() && this.cell == b.getCell())
                return true;
            else
                return false;
        }
        else {
            return false;
            //
        }
    }
}
