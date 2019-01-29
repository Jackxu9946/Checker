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
public class GetSpectatorBegin implements Route {
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
    public GetSpectatorBegin(final TemplateEngine templateEngine, PlayerLobby playerlobby) {
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
        BoardView bv = new BoardView();
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Game On!");

        Player temp = request.session().attribute("currentPlayer");
        temp.setGameConnection(true);
        temp.setCurrentPiece(new Piece(0,0));
        Player being_watched = playerlobby.getPlayer(request.queryParams("watch"));
        if(being_watched.getName() == "")
        {
            vm.put("error", "You cannot spectate NOBODY!!!");
            vm.put("numberOfUsers", playerlobby.getPlayerNumber());
            vm.put("users", playerlobby.getPlayerNames());
            vm.put("currentPlayer", temp);
            vm.put("playerLobby", playerlobby);
            return templateEngine.render(new ModelAndView(vm , "home.ftl"));
        }

        temp.setOpponent(being_watched);
        temp.setNonspectator(false);
        vm.put("currentPlayer", temp);
        vm.put("viewMode", "SPECTATOR");
        if(being_watched.getColor())
        {
            vm.put("redPlayer", being_watched);
            vm.put("whitePlayer", being_watched.getOpponent());
            if(being_watched.getMyTurn())
            {
                vm.put("activeColor", "RED");
                temp.setWatcher_turn("RED");
            }
            else
            {
                vm.put("activeColor", "WHITE");
                temp.setWatcher_turn("WHITE");
            }
        }
        else
        {
            vm.put("redPlayer", being_watched.getOpponent());
            vm.put("whitePlayer", being_watched);
            if(being_watched.getMyTurn())
            {
                vm.put("activeColor", "WHITE");
                temp.setWatcher_turn("WHITE");
            }
            else
            {
                vm.put("activeColor", "RED");
                temp.setWatcher_turn("RED");
            }
        }
        temp.setBoard(being_watched.getBoard());
        vm.put("board", being_watched.getBoard().getBV());
        vm.put("Message", new Message("Joined spectator mode!", Message.Type.info));

        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }

}