package com.webcheckers.model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.webcheckers.model.Board;
import com.webcheckers.model.Piece;
@Tag("model-tier")

public class SimpleMoveTest {
    private Player P1;
    private Player P2;
    private Board BT;

    @BeforeEach
    public void setup() {
        Player P1 = new Player("First");
        this.P1 = P1;
        Player P2 = new Player("Second");
        this.P2 = P2;
        Board BoardThing = new Board(P1, P2, new BoardView(), 0);
        this.BT = BoardThing;
    }


    @Test
    public void TestSingleMove() {
        //Simple move should be done possible for some but not all
        BT.ReSimpleMove();
        for (Piece P : P1.getMyPiece()) {
            if (P.getSimpleMovePossible().size() > 0) {
                Assertions.assertNotEquals(P.getSimpleMovePossible().size(), 0);
            }
        }
        for (Piece Piece2 : P2.getMyPiece()) {
            ;
            if (Piece2.getSimpleMovePossible().size() > 0) {
                Assertions.assertNotEquals(Piece2.getSimpleMovePossible(), 0);
            }
        }
    }


    @Test
    void TestSingleJumpInit() {
        BT.ReAll();

        Player P1 = new Player("First");
        this.P1 = P1;
        Player P2 = new Player("Second");
        this.P2 = P2;
        Board BoardThing = new Board(P1, P2, new BoardView(), 0);
        this.BT = BoardThing;
        //There should be no single jump possible in the beginning
        for (Piece P : P1.getMyPiece()) {
            Assertions.assertEquals(0,P.getSingleJumpPossibles().size());

        }

        for (Piece Piece2 : P2.getMyPiece()) {
            Assertions.assertEquals(0, Piece2.getSingleJumpPossibles().size());
        }
    }

    @Test
    //When there is one piece blocking one move so there is only one simple move possible
    public void SingleMoveSomething() {
        P2.getMyPiece().get(0).setRow(3);
        P2.getMyPiece().get(0).setColumn(4);
        BT.ReAll();
        for (Piece P : P1.getMyPiece()) {
            if (P.getRow() == 2 && P.getColumn() == 4) {
                Assertions.assertEquals(P.getSimpleMovePossible().size(), 1);
            }
        }
    }



    @Test
    //There should be a simple move
    public void SingleJumpSomething(){
        P2.getMyPiece().get(0).setColumn(4);
        P2.getMyPiece().get(0).setRow(3);
        Piece  TestP = new Piece(3,4,false,P2);
        this.BT.getBV().iterator().get(3).iterator().get(4).addPiece(TestP);
        for(Piece P: P2.getMyPiece()){
            //System.out.println(P);
        }
        BT.ReAll();
        for(Piece P: P1.getMyPiece()){
            //System.out.println(P);
            if(P.getRow()== 2 && P.getColumn()==3){
                Assertions.assertEquals(P.getSingleJumpPossibles().size(),1);
            }
        }
    }




}
