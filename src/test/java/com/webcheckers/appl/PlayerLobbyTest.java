package com.webcheckers.appl;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

@Tag("Application-tier")
public class PlayerLobbyTest
{

    @Test
    public void add_valid_player()
    {
        final PlayerLobby CuT = new PlayerLobby();

        CuT.addPlayer(new Player("David"));
        assertEquals(CuT.getPlayerNumber(), 2);
    }

    @Test void is_name_valid_invalid_name()
    {
        final PlayerLobby CuT = new PlayerLobby();
        assertFalse(CuT.isNameValid(new Player("\"David")));

    }

    @Test void is_name_valid_valid_name()
    {
        final PlayerLobby CuT = new PlayerLobby();
        assertTrue(CuT.isNameValid(new Player("David")));

    }

    @Test void get_player()
    {

        final PlayerLobby CuT = new PlayerLobby();
        assertEquals(CuT.getPlayers().size(), 1);

    }

    @Test
    public void add_invalid_player()
    {
        final PlayerLobby CuT = new PlayerLobby();

        CuT.addPlayer(new Player("COMPUTER"));
        assertEquals(CuT.getPlayerNumber(), 2);
        assertTrue(CuT.getPlayerNames() != null);
    }

    @Test
    public void remove_player_from_list()
    {
        final PlayerLobby CuT = new PlayerLobby();
        CuT.remove(CuT.getPlayer("COMPUTER"));
        assertEquals(CuT.getPlayerNumber(), 0);
    }
/**
    @Test
    public void remove_player_from_NOTIN_list()
    {
        final PlayerLobby CuT = new PlayerLobby();
        CuT.remove(CuT.getPlayer("David"));
        assertEquals(CuT.getPlayerNumber(), 2);
    }
**/
    @Test
    public void is_name_take_valid_player()
    {
        final PlayerLobby CuT = new PlayerLobby();
        assertEquals(CuT.isNameAvailable(new Player("David")), true);
    }

    @Test
    public void is_name_take_invalid_player()
    {
        final PlayerLobby CuT = new PlayerLobby();
        assertEquals(CuT.isNameAvailable(new Player("COMPUTER")), false);
    }
}
