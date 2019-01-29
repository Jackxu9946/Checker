package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.*;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

/**
 * The UI Controller to GET the Home page.
 *
 */
public class PostValidateMoveRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby lobby;
    private final Gson gson;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET ~} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public PostValidateMoveRoute(TemplateEngine templateEngine, PlayerLobby lobby, Gson gson) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        this.gson = gson;
        this.lobby = lobby;
        //
        LOG.config("PostValidateMoveRoute is initialized.");
    }

    /**
     * Render the WebCheckers Home page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Home page
     */
    @Override
    public Object handle(Request request, Response response) {
        System.out.println("PostValidateMove");
        LOG.finer("PostValidateMoveRoute is invoked.");
        //
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Game On!");

        Player temp = request.session().attribute("currentPlayer");
        if(((Player) request.session().attribute("currentPlayer")).getColor())
        {
            vm.put("redPlayer", request.session().attribute("currentPlayer"));
            vm.put("whitePlayer", ((Player) request.session().attribute("currentPlayer")).getOpponent());
            //vm.put("activeColor", Piece.color.RED);
            vm.put("viewMode", "PLAY");
        }
        else
        {
            vm.put("whitePlayer", request.session().attribute("currentPlayer"));
            vm.put("redPlayer", ((Player) request.session().attribute("currentPlayer")).getOpponent());
            //vm.put("activeColor", Piece.color.RED);
            vm.put("viewMode", "PLAY");
        }
        vm.put("currentPlayer", request.session().attribute("currentPlayer"));
        vm.put("board", temp.getBoard());
        vm.put("users", request.session().attribute("users"));

        String JsonBody = request.body();
        Move m = gson.fromJson(JsonBody, Move.class);

        String messs = temp.getBoard().validateMoves(m.getStart(), m.getEnd(), temp);

        String[] mess2 = messs.split( "_");

        if(mess2[0].equals("T"))
        {
            temp.getBoard().ReAll();
            Piece theOne = null;
            for(Piece c : temp.getMyPiece())
            {
                if(c.getRow() == m.getStart().getRow() && c.getColumn() == m.getStart().getCell())
                {
                    theOne = c;
                    theOne = temp.getBoard().JumpTempMoves(m.getEnd(), theOne);
                    c.overRide(theOne);
                    c.addPastLocation(m.getStart());

                    if(!(Math.abs(m.getStart().getRow()- m.getEnd().getRow()) == 1))
                    {
                        c.addPastLocation(new Position((m.getStart().getRow() + m.getEnd().getRow())/2, (m.getStart().getCell() + m.getEnd().getCell())/2));
                        c.setMadeAJump(true);
                    }
                    else
                    {
                        c.setMadeAMove(true);
                        c.setMadeAJump(true);
                    }

                    temp.setCurrentPiece(c);
                }
            }

            //temp.setCurrentPiece(theOne);
            temp.getBoard().ReAll();
            temp.getBoard().DeleteHighLight();
            //temp.getBoard().setHighlightPiece();
            //temp.getBoard().ReAllSingleJump();
            //temp.getBoard().ReSimpleMove();
            return gson.toJson(new Message("Valid Move!", Message.Type.info));
        }
        else
        {
            String mess3 = mess2[1];
            return gson.toJson(new Message("Invalid Move! " + mess3, Message.Type.error));
     }

    }
}
