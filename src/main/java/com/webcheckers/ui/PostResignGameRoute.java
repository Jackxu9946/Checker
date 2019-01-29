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
public class PostResignGameRoute implements Route {
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
    public PostResignGameRoute(final TemplateEngine templateEngine, PlayerLobby lobby, Gson gson) {
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
        LOG.finer("GetLogInRoute is invoked.");
        //
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Welcome!");
        Player temp = request.session().attribute("currentPlayer");

        if(temp.getColor())
        {
            vm.put("activeColor", Piece.color.WHITE);
        }
        else
        {
            vm.put("activeColor", Piece.color.RED);
        }

        temp.getOpponent().setTurn(true);
        temp.setTurn(false);
        temp.setStatus(false);
        temp.setBoard(null);
        temp.setPiece(new ArrayList<>());
        temp.setGameConnection(false);
        //temp.getOpponent().setStatus(false);
        //temp.getOpponent().setBoard(null);
        //temp.getOpponent().setPiece(new ArrayList<>());
        //temp.getOpponent().setGameConnection(false);
        //temp.getOpponent().setOpponent(null);
        temp.setOpponent(null);

        vm.put("currentPlayer", temp);
        vm.put("users", lobby.getPlayerNames());

        return gson.toJson(new Message("Good Job!", Message.Type.info));
    }
}