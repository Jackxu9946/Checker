package com.webcheckers.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

@Tag("model-tier")
public class BoardTest {
    Player redplayer;
    Player whiteplayer;
    BoardView bv;
    Board board;

    @BeforeEach
    public void Setup(){
        redplayer = new Player("Red");
        whiteplayer = new Player("White");
        bv = new BoardView();
        board = new Board(redplayer, whiteplayer, bv, 0);
    }

    @Test
    public void testBoard()
    {
        Assertions.assertTrue(board.iterator() != null);
    }

    @Test
    public void number_of_pieces()
    {
        Assertions.assertEquals(redplayer.getMyPiece().size(), 12);
    }

    @Test
    public void peices_cant_make_single()
    {
        ArrayList<Piece> bob = redplayer.getMyPiece();
        for(Piece c :bob)
        {
            Assertions.assertEquals(c.getSingleJumpPossibles().size(), 0);
        }
    }

    @Test
    public void peices_can_make_simple()
    {
        ArrayList<Piece> bob = redplayer.getMyPiece();
        boolean temp = false;
        for(Piece c :bob)
        {
            if(c.getSimpleMovePossible().size() > 0)
            {
                temp = true;
            }
        }
        Assertions.assertEquals(temp, true);
    }

    @Test
    public void test_postions()
    {
        Position x = new Position(0,1);
        ArrayList<Piece> bob = redplayer.getMyPiece();
        Assertions.assertTrue(x.equals(new Position(bob.get(0).getRow(), bob.get(0).getColumn())));
        Assertions.assertFalse(x.equals(new Player("Bob")));
        x = new Position(0,0);
        Assertions.assertFalse(x.equals(new Position(bob.get(0).getRow(), bob.get(0).getColumn())));
    }

    @Test
    public void test_king()
    {
        Piece b = redplayer.getMyPiece().get(0);
        b.kingPiece();
        board.SetJumpMoves(b);
    }

    @Test
    public void validate_moves()
    {
        redplayer.setColor(true);
        whiteplayer.setColor(false);
        board.validateMoves(new Position(2,3), new Position(3,4), redplayer);
        board.validateMoves(new Position(2,5), new Position(3,6), whiteplayer);
    }

    @Test
    public void update_board()
    {
        redplayer.setColor(true);
        whiteplayer.setColor(false);
        ArrayList<Position> b = new ArrayList<>();
        b.add(new Position(0,0));
        b.add(new Position(1,1));
        board.BoardUpdate(b, redplayer);
        b.add(new Position(2,2));
        board.BoardUpdate(b, redplayer);
    }
}
