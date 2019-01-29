package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.BoardView;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import net.bytebuddy.dynamic.scaffold.TypeInitializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import spark.*;

import java.util.ArrayList;
@Tag("UI-tier")

public class GetLogInRouteTest {
    private GetLogInRoute CuT;

    private PlayerLobby playerlobby;
    private Response response;


    private Request request;
    private Session session;
    private TemplateEngine engine;

    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        playerlobby = new PlayerLobby();


        CuT = new GetLogInRoute(engine);
    }

    @Test
    public void ifcurrentplayergetColorTrue(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render((any(ModelAndView.class)))).thenAnswer(testHelper.makeAnswer());


        CuT.handle(request, response);
        //System.out.println(testHelper);
        //testHelper.makeAnswer()
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewModelAttribute("title","Log In!");


    }
}