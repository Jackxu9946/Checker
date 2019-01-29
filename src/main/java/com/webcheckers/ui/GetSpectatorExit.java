package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

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
public class GetSpectatorExit implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerlobby;
    private Board board;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET ~} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSpectatorExit(final TemplateEngine templateEngine, PlayerLobby playerlobby) {
        // validation
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        this.playerlobby = playerlobby;
        //
        LOG.config("GetGameRoute is initialized.");
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
        LOG.finer("GetGameRoute is invoked.");
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Game On!");

        Player temp = request.session().attribute("currentPlayer");
        temp.setNonspectator(true);
        temp.setOpponent(null);
        temp.setGameConnection(false);
        temp.setCurrentPiece(null);

        vm.put("error", "You have finished spectating a match!\nPlease click 'my home' to return to the home page!!");
        vm.put("numberOfUsers", playerlobby.getPlayerNumber());
        vm.put("users", playerlobby.getPlayerNames());
        vm.put("currentPlayer", temp);
        vm.put("playerLobby", playerlobby);
        return templateEngine.render(new ModelAndView(vm , "FAKEhome.ftl"));
    }

}