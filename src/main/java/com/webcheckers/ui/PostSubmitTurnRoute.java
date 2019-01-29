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
public class PostSubmitTurnRoute implements Route {
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
    public PostSubmitTurnRoute(final TemplateEngine templateEngine, PlayerLobby lobby, Gson gson) {
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
        System.out.println("PostSubmitTurn");
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
        vm.put("users", request.session().attribute("users"));


        if(temp.getCurrentPiece().getSingleJumpPossibles().size() > 0 && temp.getCurrentPiece().getMadeAJump())
        {
            if(!temp.getCurrentPiece().getMadeAMove())
                return gson.toJson(new Message("You MUST complete all part of the multiple jump move!", Message.Type.error));
        }

        if(temp.getColor())
        {
            if(temp.getCurrentPiece().getRow() == 7)
            {
                temp.getCurrentPiece().kingPiece();
            }
        }
        else
        {
            if(temp.getCurrentPiece().getRow() == 0)
            {
                temp.getCurrentPiece().kingPiece();
            }
        }
        temp.getCurrentPiece().addPastLocation(new Position(temp.getCurrentPiece().getRow(), temp.getCurrentPiece().getColumn()));
        temp.getBoard().BoardUpdate(temp.getCurrentPiece().getPastLocation(), temp);
        temp.getCurrentPiece().resetLocations();
        temp.getCurrentPiece().setMadeAJump(false);
        temp.getCurrentPiece().setMadeAMove(false);
        temp.setCurrentPiece(null);

        vm.put("board", temp.getBoard());
        // push the submission of the new board
        if(temp.getColor())
        {
            vm.put("activeColor", Piece.color.WHITE);
        }
        else
        {
            vm.put("activeColor", Piece.color.RED);
        }
        temp.setTurn(false);
        temp.getOpponent().setTurn(true);
        return gson.toJson(new Message("You Submitted your turn!", Message.Type.info));

    }
}
