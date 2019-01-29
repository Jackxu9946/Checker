package com.webcheckers.model;


//import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PlayerTest {
    public Player p1;
    @BeforeEach
    public void Setup(){
        this.p1 = new Player("Name");
    }
    @Test
    public void TestName(){
        Assertions.assertEquals(this.p1.getName(),"Name");
    }


    @Test
    public void TestSetPiece(){
        ArrayList<Piece> das = new ArrayList<>();
        das.add(new Piece(3,4));
        p1.setPiece(das);
        Assertions.assertEquals(1,this.p1.getMyPiece().size());
    }

    @Test
    public void TestStatus(){
        this.p1.setStatus(false);
        Assertions.assertEquals(p1.getStatus(),false);
    }

    @Test
    public void TestColor(){
        this.p1.setColor(false);
        Assertions.assertEquals(this.p1.getColor(),false);
    }

    @Test
    public void TestOpponent(){
        Player p2 = new Player("P2");
        p2.setBoard(new Board(p1, p2, new BoardView(), 0));
        p2.setName("P2");
        Board NewB = new Board(p1,new Player("Name2"),new BoardView(), 0);
        p1.setOpponent(p2);
        Assertions.assertEquals(p1.getOpponent().getName(),p2.getName());
    }


    @Test
    public void TestCurrentPiece(){
        Piece newp = new Piece(3,4);
        this.p1.setCurrentPiece(newp);
        boolean b = false;
        if(this.p1.getCurrentPiece().isEquals(newp)){
            b = true;
        }
        Assertions.assertEquals(b,true);
    }

    @Test
    public void test_switch(){
        Player p2 = new Player("David");
        p2.setStatus(true);
        p2.switchStatus();
        p2.setTurn(true);
        Assertions.assertTrue(p2.getMyTurn());
        Assertions.assertFalse(p2.getStatus());
        p2.switchStatus();
        Assertions.assertTrue(p2.getStatus());


        Assertions.assertFalse(p2.canMakeMove());
    }

}

