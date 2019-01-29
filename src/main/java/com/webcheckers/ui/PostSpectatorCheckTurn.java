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
public class PostSpectatorCheckTurn implements Route {
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
    public PostSpectatorCheckTurn(TemplateEngine templateEngine, PlayerLobby lobby, Gson gson) {
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
        LOG.finer("PostValidateMoveRoute is invoked.");
        //
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Game On!");

        Player temp = request.session().attribute("currentPlayer");
        Player being_watched = temp.getOpponent();

        vm.put("currentPlayer", temp);
        vm.put("viewMode", "SPECTATOR");
        vm.put("board", being_watched.getBoard().getBV());

        if(being_watched.getColor())
        {
            vm.put("redPlayer", being_watched);
            vm.put("whitePlayer", being_watched.getOpponent());
            if(being_watched.getMyTurn())
            {
                vm.put("activeColor", "RED");

                if(temp.getwatcher_turn() == "RED")
                {
                    return gson.toJson(new Message("false", Message.Type.info));
                }
                else
                {
                    temp.setWatcher_turn("RED");
                    return gson.toJson(new Message("true", Message.Type.info));
                }
            }
            else
            {
                vm.put("activeColor", "WHITE");
                if(temp.getwatcher_turn() == "WHITE")
                {
                    return gson.toJson(new Message("false", Message.Type.info));
                }
                else
                {
                    temp.setWatcher_turn("WHITE");
                    return gson.toJson(new Message("true", Message.Type.info));
                }
            }
        }
        else
        {
            vm.put("redPlayer", being_watched.getOpponent());
            vm.put("whitePlayer", being_watched);
            if(being_watched.getMyTurn())
            {
                vm.put("activeColor", "WHITE");
                if(temp.getwatcher_turn() == "WHITE")
                {
                    return gson.toJson(new Message("false", Message.Type.info));
                }
                else
                {
                    temp.setWatcher_turn("WHITE");
                    return gson.toJson(new Message("true", Message.Type.info));
                }
            }
            else
            {
                vm.put("activeColor", "RED");
                if(temp.getwatcher_turn() == "RED")
                {
                    return gson.toJson(new Message("false", Message.Type.info));
                }
                else
                {
                    temp.setWatcher_turn("RED");
                    return gson.toJson(new Message("true", Message.Type.info));
                }
            }
        }

    }
}