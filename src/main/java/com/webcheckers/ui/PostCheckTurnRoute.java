package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Message;
import com.webcheckers.model.Piece;
import com.webcheckers.model.Player;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

/**
 * The UI Controller to GET the Home page.
 *
 */
public class PostCheckTurnRoute implements Route {
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
    public PostCheckTurnRoute(final TemplateEngine templateEngine, PlayerLobby lobby, Gson gson) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        this.gson = gson;
        this.lobby = lobby;
        //
        LOG.config("GetLogInRoute is initialized.");
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
        System.out.println("PostCheckTurn");
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

        if(temp.getMyTurn())
        {

            if(temp.getColor())
            {
                vm.put("activeColor", Piece.color.RED);
            }
            else
            {
                vm.put("activeColor", Piece.color.WHITE);
            }

            if(temp.getMyPiece().size() == 0)
            {

            }

            return gson.toJson(new Message("true", Message.Type.info));
        }
        else
        {
            return gson.toJson(new Message("false", Message.Type.info));
        }
    }
}