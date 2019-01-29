package com.webcheckers.model;
import java.util.ArrayList;
import java.util.List;

/**
 * random
 */
public class BoardView {

    public ArrayList<Row> rows = new ArrayList<>();

    public BoardView(){
        int temp = 0;
        int temp2 = 0;
        for(int i=0; i < 4; i++){
            rows.add(new Row(1, temp, 0));
            temp++;
            //temp2++;
            rows.add(new Row(-1, temp, 0));
            temp++;
            //temp2++;
        }
    }

    public ArrayList<Row> iterator(){
        return this.rows;
    }
}
