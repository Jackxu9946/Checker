package com.webcheckers.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
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
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetSignOutRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby playerlobby;

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET /} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSignOutRoute(final TemplateEngine templateEngine, PlayerLobby playerlobby) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        //
        this.templateEngine = templateEngine;
        this.playerlobby = playerlobby;
        //
        LOG.config("GetHomeRoute is initialized.");
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
        System.out.println("getSignout");
        LOG.finer("GetSignOutRoute is invoked.");
        //
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Welcome!");

        if(request.session().attribute("currentPlayer") instanceof Player)
        {
            Player temp = request.session().attribute("currentPlayer");
            if(temp.getOpponent() != null)
            {
                temp.getOpponent().setTurn(true);
            }
            temp.setTurn(false);
            temp.setStatus(false);
            temp.setBoard(null);
            temp.setPiece(new ArrayList<>());
            temp.setGameConnection(false);
            temp.setOpponent(null);



            playerlobby.remove(request.session().attribute("currentPlayer"));
            vm.put("currentPlayer", null);
            request.session().attribute("currentPlayer", null);
            vm.put("numberOfUsers", playerlobby.getPlayerNumber());
            return templateEngine.render(new ModelAndView(vm , "home.ftl"));
        }
        vm.put("numberOfUsers", playerlobby.getPlayerNumber());
        return templateEngine.render(new ModelAndView(vm , "home.ftl"));
    }
}