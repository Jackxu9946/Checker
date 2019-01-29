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

    public class GetGameRouteTest {
        private GetGameRoute CuT;

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




            CuT = new GetGameRoute(engine, playerlobby);
        }

        @Test
        public void ifcurrentplayergetColorTrue(){
            Player  P1 = new Player("p1");
            Player  P2 = new Player("p2");
            System.out.println(P1);
            P1.setOpponent(P2);
            P1.setGameConnection(true);
            P1.setColor(true);
            playerlobby.addPlayer(P1);
            playerlobby.addPlayer(P2);
            P2.setGameConnection(true);
            when(request.session().attribute("currentPlayer")).thenReturn(P1);

            final TemplateEngineTester testHelper = new TemplateEngineTester();
            when(engine.render((any(ModelAndView.class)))).thenAnswer(testHelper.makeAnswer());

            when(request.session().attribute("currentPlayer")).thenReturn(P1);

            CuT.handle(request, response);
            //System.out.println(testHelper);
            //testHelper.makeAnswer()
            testHelper.assertViewModelExists();
            testHelper.assertViewModelIsaMap();
            testHelper.assertViewModelAttribute("currentPlayer",P1);
            testHelper.assertViewModelAttribute("redPlayer",P1);
            testHelper.assertViewModelAttribute("whitePlayer",P2);
            testHelper.assertViewModelAttribute("viewMode","PLAY");
            testHelper.assertViewModelAttribute("activeColor",Piece.color.WHITE);

        }

        @Test
        public void ifcurrentplayergetColorFalse(){
            Player  P1 = new Player("p1");
            Player  P2 = new Player("p2");
            P1.setOpponent(P2);
            P1.setGameConnection(true);
            P1.setColor(false);
            P2.setGameConnection(true);
            when(request.session().attribute("currentPlayer")).thenReturn(P1);

            when(request.queryParams(ArgumentMatchers.eq("rival"))).thenReturn("P2");

            final TemplateEngineTester testHelper = new TemplateEngineTester();
            when(engine.render((any(ModelAndView.class)))).thenAnswer(testHelper.makeAnswer());


            CuT.handle(request, response);
            testHelper.assertViewModelExists();
            testHelper.assertViewModelIsaMap();
            testHelper.assertViewModelAttribute("whitePlayer",P1);
            testHelper.assertViewModelAttribute("redPlayer",P1.getOpponent());
            testHelper.assertViewModelAttribute("activeColor",Piece.color.RED);
            testHelper.assertViewModelAttribute("viewMode","PLAY");

        }

        @Test
        public void ifplayerlobbygetplayer(){
            Player  P1 = new Player("p1");
            Player  P2 = new Player("p2");
            P1.setOpponent(P2);
            P1.setGameConnection(false);
            P2.setStatus(true);
            playerlobby.addPlayer(P1);
            playerlobby.addPlayer(P2);
            when(request.session().attribute("currentPlayer")).thenReturn(P1);

            when(request.queryParams(ArgumentMatchers.eq("rival"))).thenReturn("p2");
            final TemplateEngineTester testHelper = new TemplateEngineTester();
            when(engine.render((any(ModelAndView.class)))).thenAnswer(testHelper.makeAnswer());


            CuT.handle(request, response);

            Assertions.assertTrue(P1.getGameConnection() == false);

            testHelper.assertViewModelExists();
            testHelper.assertViewModelIsaMap();
            testHelper.assertViewModelAttribute("error", "That player is already in a game!");
            testHelper.assertViewModelAttribute("numberOfUsers", playerlobby.getPlayerNumber());
            testHelper.assertViewModelAttribute("users", playerlobby.getPlayerNames());
            testHelper.assertViewModelAttribute("currentPlayer", P1);



        }


        @Test
        public void challengeself(){
            Player  P1 = new Player("p1");
            Player  P2 = new Player("p2");
            P1.setGameConnection(false);
            playerlobby.addPlayer(P1);
            when(request.session().attribute("currentPlayer")).thenReturn(P1);
            when(request.queryParams(ArgumentMatchers.eq("rival"))).thenReturn("p1");

            final TemplateEngineTester testHelper = new TemplateEngineTester();

            when(engine.render((any(ModelAndView.class)))).thenAnswer(testHelper.makeAnswer());


            CuT.handle(request, response);

            testHelper.assertViewModelExists();
            testHelper.assertViewModelIsaMap();
            testHelper.assertViewModelAttribute("error", "You cannot challenge yourself!");
            testHelper.assertViewModelAttribute("numberofUsers",null);
            testHelper.assertViewModelAttribute("users",playerlobby.getPlayerNames());
            testHelper.assertViewModelAttribute("currentPlayer",P1);


        }

    }

