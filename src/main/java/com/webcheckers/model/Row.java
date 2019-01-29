package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;

public class Row {

    public ArrayList<Space> spaces = new ArrayList<>();
    private int index;
    private int spaceIndex;

    public Row(int j, int index, int spaceIndex)
    {
        this.index = index;
        this.spaceIndex = spaceIndex;
        if(j >0)
        {
            for(int i=0; i < 4; i++) {
                spaces.add(new Space("Red", spaceIndex));
                spaceIndex++;
                spaces.add(new Space("White", spaceIndex));
                spaceIndex++;
            }
        }
        else
        {
            for(int i=0; i < 4; i++) {
                spaces.add(new Space("White", spaceIndex));
                spaceIndex++;
                spaces.add(new Space("Red", spaceIndex));
                spaceIndex++;
            }
        }
    }
    /**
     * Public List<Spoace>
     *     return a list of spaces
     */

    public ArrayList<Space> iterator(){
        return this.spaces;
    }

    public String toString()
    {
        return Integer.toString(index);
    }

    public int getIndex()
    {
        return index;
    }
}


