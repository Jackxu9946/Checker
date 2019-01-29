package com.webcheckers.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
@Tag("model-tier")
public class PieceTest {

    @Test
    public void singleTest(){
        Player Jisook = new Player("Jisook");
        Piece single = new Piece(2,6,false,Jisook);

        Assertions.assertTrue(single.getType() == Piece.type.SINGLE);

    }

    @Test
    public void kingTest(){
        BoardView bv = new BoardView();
        Player Bob = new Player("Bob");
        Piece king = new Piece(2, 4, true, Bob);
        king.kingPiece();

        Assertions.assertTrue(king.getType() == Piece.type.KING );
        king.simpleMove(bv);
    }

    @Test
    public void to_string(){
        Player Bob = new Player("Bob");
        Piece king = new Piece(2, 4, true, Bob);

        Assertions.assertEquals(king.toString(), "2,4");
        Assertions.assertFalse(king.isEquals(new Player("tom")));
    }

    @Test
    public void test_jumps(){
        Player Bob = new Player("Bob");
        Piece king = new Piece(2, 4, true, Bob);
        king.setMadeAJump(true);

        Assertions.assertEquals(king.getMadeAJump(), true);
    }

    @Test
    public void over_ride(){
        Player Bob = new Player("Bob");
        Piece king = new Piece(2, 4, true, Bob);
        king.overRide(new Piece(1,1,false,Bob));

        Assertions.assertEquals(king.getRow(), 1);
        Assertions.assertEquals(king.getColumn(), 1);
    }

    @Test
    public void past_locations(){
        Player Bob = new Player("Bob");
        Piece king = new Piece(2, 4, true, Bob);
        king.addPastLocation(new Position(0,0));

        Assertions.assertEquals(king.getPastLocation().size(), 1);

        king.resetLocations();

        Assertions.assertEquals(king.getPastLocation().size(), 0);
    }


}
