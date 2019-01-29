package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Player;
import net.bytebuddy.dynamic.scaffold.TypeInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.ArrayList;

@Tag("UI-tier")
public class GetHomeRouteTest
{
   private GetHomeRoute CuT;

    private PlayerLobby playerlobby;
    private Response response;
    private Request request;
    private Session session;
    private TemplateEngine engine;

    @BeforeEach
    public void setup()
    {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);



        playerlobby = new PlayerLobby();
        CuT = new GetHomeRoute(engine, playerlobby);
    }

    @Test
    public void player_is_in_game()
    {
        Player tim = new Player("tim");
        tim.setGameConnection(true);
        tim.setColor(true);

        Player bob = new Player("bob");
        bob.setGameConnection(true);
        bob.setColor(false);


        when(request.session().attribute("currentPlayer")).thenReturn(tim);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render((any(ModelAndView.class)))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        ArrayList<String> users = new ArrayList<>();
        users.add("COMPUTER");
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute("users", users);
        testHelper.assertViewModelAttribute("currentPlayer", tim);
    }


    @Test
    public void player_is_not_in_game_logged_in()
    {
        Player David = new Player("David");
        playerlobby.addPlayer(David);
        when(request.queryParams(eq("value1"))).thenReturn("David");
        when(request.session().attribute("currentPlayer")).thenReturn(David);

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render((any(ModelAndView.class)))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
        ArrayList<String> users = new ArrayList<>();
        users.add("COMPUTER");
        users.add("David");
        testHelper.assertViewModelAttribute("users", users);
        testHelper.assertViewModelAttribute("currentPlayer", David);
    }


    @Test
    public void player_is_not_in_game_logged_out()
    {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render((any(ModelAndView.class)))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute("numberOfUsers", 1);
        testHelper.assertViewModelAttribute("currentPlayer", null);
    }

    @Test
    public void player_has_no_name()
    {
        when(request.queryParams(eq("value1"))).thenReturn("");

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render((any(ModelAndView.class)))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute("users", null);
        testHelper.assertViewModelAttribute("currentPlayer", null);
        testHelper.assertViewModelAttribute("error", "You entered a null name! Please enter a name!");
    }

    @Test
    public void player_has_same_name()
    {
        when(request.queryParams(eq("value1"))).thenReturn("COMPUTER");

        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render((any(ModelAndView.class)))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute("users", null);
        testHelper.assertViewModelAttribute("currentPlayer", null);
        testHelper.assertViewModelAttribute("error", "That name is taken!");
    }
}
