package com.webcheckers.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("model-tier")
public class MessageTest
{
    @Test
    public void Messages()
    {
        Message a = new Message("HELLO!", Message.Type.info);
        Assertions.assertTrue(a.getText().equals("HELLO!"));
        Assertions.assertEquals(a.getType(), Message.Type.info);
    }
}