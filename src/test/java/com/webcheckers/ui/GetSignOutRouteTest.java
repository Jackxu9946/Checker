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
@Tag("UI-tier")
public class GetSignOutRouteTest{
    private GetSignOutRoute CuT;
    private PlayerLobby playerLobby;
    private Response response;
    private Request request;
    private Session session;
    private TemplateEngine engine;

    @BeforeEach
    public void setup(){
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        playerLobby = new PlayerLobby();
        CuT = new GetSignOutRoute(engine, playerLobby);
    }

    @Test
    public void testTitle(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render((any(ModelAndView.class)))).thenAnswer(testHelper.makeAnswer());
        CuT.handle(request, response);
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
    }

    @Test
    public void testSignout(){
    final TemplateEngineTester testHelper = new TemplateEngineTester();
    when(engine.render((any(ModelAndView.class)))).then(testHelper.makeAnswer());

    CuT.handle(request, response);

    testHelper.assertViewModelExists();
    testHelper.assertViewModelIsaMap();
    testHelper.assertViewModelAttribute("title", "Welcome!");
    testHelper.assertViewModelAttribute("currentPlayer", null);

    }

    @Test void finalNumUsers(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render((any(ModelAndView.class)))).then(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title", "Welcome!");
        testHelper.assertViewModelAttribute("numberOfUsers", 1);
    }
}