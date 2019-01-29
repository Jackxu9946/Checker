package com.webcheckers.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
@Tag("model-tier")
public class MoveTest
{
    @Test
    public void moves()
    {
        Move b = new Move(new Position(0,0), new Position(1,1));
        Assertions.assertTrue(b.getStart().equals(new Position(0,0)));
        Assertions.assertTrue(b.getEnd().equals(new Position(1,1)));
    }
}

