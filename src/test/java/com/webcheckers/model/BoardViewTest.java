package com.webcheckers.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardViewTest {
    BoardView bv = new BoardView();

    @Test
    public void testBoardView(){
        for(int i = 0; i< 8; i++){
            Assertions.assertTrue(bv.iterator().get(i) instanceof Row);
            Assertions.assertTrue(bv.iterator().get(i).getIndex() != -1);
            Assertions.assertTrue(bv.iterator().get(i).getIndex() != 8);
            Assertions.assertTrue(bv.iterator().get(i).toString() != "");
        }
    }

    @Test
    public void test_spaces(){
        for(int i = 0; i< 8; i++){
            Assertions.assertTrue(bv.iterator().get(i).iterator().get(i).getCellId() != -1);
            Assertions.assertTrue(bv.iterator().get(i).iterator().get(i).getCellId() != 8);
        }
    }
}
