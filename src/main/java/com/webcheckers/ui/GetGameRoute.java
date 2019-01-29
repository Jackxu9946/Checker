package com.webcheckers.ui;

import java.util.ArrayList;
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
public class GetGameRoute implements Route {
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
    public GetGameRoute(final TemplateEngine templateEngine, PlayerLobby playerlobby) {
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
        System.out.println("GetGameRoute");
        LOG.finer("GetGameRoute is invoked.");
        BoardView bv = new BoardView();
        Map<String, Object> vm = new HashMap<>();
        vm.put("title", "Game On!");
        vm.put("users", request.session().attribute("users"));

        Player temp = request.session().attribute("currentPlayer");
        String change = request.queryParams("toggle");
       //System.out.println(change);
        if(change != null)
        {
            if(change.equals("Help"))
            {
                //System.out.println("HELLO");
                if(temp.getCurrentPiece() !=null) {
                    System.out.println("There is a piece");
                    temp.getCurrentPiece().goBack();
                    temp.getCurrentPiece().setMadeAMove(false);
                    temp.getCurrentPiece().setMadeAJump(false);
                    temp.getCurrentPiece().resetLocations();
                    temp.setCurrentPiece(null);
                }
                temp.getBoard().DeleteHighLight(temp);
                temp.getBoard().AddHighLight(temp);
            }
            else if(change.equals("No Help"))
            {
                //System.out.println("BYEEE");
                if(temp.getCurrentPiece()!= null){
                System.out.println("There is a piece No Help");
                temp.getCurrentPiece().goBack();
                temp.getCurrentPiece().setMadeAMove(false);
                temp.getCurrentPiece().setMadeAJump(false);
                temp.getCurrentPiece().resetLocations();
                temp.setCurrentPiece(null);
                }
                temp.getBoard().DeleteHighLight(temp);
            }
        }

        System.out.println("Does it get here?");
        if(temp.getGameConnection() == true)
        {
            if(((Player) request.session().attribute("currentPlayer")).getColor())
            {
                vm.put("redPlayer", request.session().attribute("currentPlayer"));
                vm.put("whitePlayer", ((Player) request.session().attribute("currentPlayer")).getOpponent());
                vm.put("viewMode", "PLAY");
                this.board = ((Player) request.session().attribute("currentPlayer")).getBoard();

                if(temp.getMyTurn())
                {
                    vm.put("activeColor", Piece.color.RED);
                }
                else
                {
                    vm.put("activeColor", Piece.color.WHITE);
                }
            }
            else
            {
                vm.put("whitePlayer", request.session().attribute("currentPlayer"));
                vm.put("redPlayer", ((Player) request.session().attribute("currentPlayer")).getOpponent());
                vm.put("viewMode", "PLAY");
                this.board = ((Player) request.session().attribute("currentPlayer")).getBoard();

                if(temp.getMyTurn())
                {
                    vm.put("activeColor", Piece.color.WHITE);
                }
                else
                {
                    vm.put("activeColor", Piece.color.RED);
                }
            }
            vm.put("currentPlayer", request.session().attribute("currentPlayer"));
            vm.put("board", this.board);

            if(temp.getOpponent().getBoard() == null)
            {
                vm.put("message", new Message("Your Opponent has surendered! Please press home to return to the home page!", Message.Type.info));
            }
            if(temp.getMyPiece().size() == 0)
            {
                vm.put("message", new Message("You have lost all of your pieces! This means you have lost the game! Please press home to return to the home page!", Message.Type.info));
            }
            else if(!temp.canMakeMove())
            {
                vm.put("message", new Message("You cannot make a move! This means you have lost the game! Please press home to return to the home page!", Message.Type.info));
            }

            ArrayList<String> bob = new ArrayList<>();
            bob.add("Help");
            bob.add("No Help");
            vm.put("users", bob);
            return templateEngine.render(new ModelAndView(vm , "game.ftl"));
        }
        else if(playerlobby.getPlayer(request.queryParams("rival")).getStatus())
        {
            vm.put("error", "That player is already in a game!");
            vm.put("numberOfUsers", playerlobby.getPlayerNumber());
            vm.put("users", playerlobby.getPlayerNames());
            vm.put("currentPlayer", temp);
            vm.put("playerLobby", playerlobby);
            return templateEngine.render(new ModelAndView(vm , "home.ftl"));
        }
        else if(request.queryParams("rival").equals(temp.getName()))
        {
            vm.put("error", "You cannot challenge yourself!");
            vm.put("numberOfUsers", playerlobby.getPlayerNumber());
            vm.put("users", playerlobby.getPlayerNames());
            vm.put("currentPlayer", temp);
            vm.put("playerLobby", playerlobby);
            return templateEngine.render(new ModelAndView(vm , "home.ftl"));
        }
        playerlobby.getPlayer(request.queryParams("rival")).switchStatus();
        temp.switchStatus();
        vm.put("currentPlayer",temp);

        temp.setColor(true);
        temp.setTurn(true);
        playerlobby.getPlayer(request.queryParams("rival")).setTurn(false);
        temp.setOpponent(playerlobby.getPlayer(request.queryParams("rival")));
        vm.put("redPlayer", temp);
        this.board = new Board(request.session().attribute("currentPlayer"),((Player) request.session().attribute("currentPlayer")).getOpponent(), bv, playerlobby.getCurrentGameCount());
        playerlobby.increaseGameCount();
        temp.setBoard(board);

        playerlobby.getPlayer(request.queryParams("rival")).setColor(false);
        playerlobby.getPlayer(request.queryParams("rival")).setOpponent(temp);
        vm.put("whitePlayer", playerlobby.getPlayer(request.queryParams("rival")));
        playerlobby.getPlayer(request.queryParams("rival")).setBoard(board);

        temp.setGameConnection(true);
        playerlobby.getPlayer(request.queryParams("rival")).setGameConnection(true);
        vm.put("board", board);
        vm.put("activeColor", Piece.color.RED);
        vm.put("viewMode", "PLAY");
        ArrayList<String> bob = new ArrayList<>();
        bob.add("Help");
        bob.add("No Help");
        vm.put("users", bob);

        return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }

}