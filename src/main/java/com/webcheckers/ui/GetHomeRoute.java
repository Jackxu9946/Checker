package com.webcheckers.ui;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.BoardView;
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
public class GetHomeRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private final TemplateEngine templateEngine;
  private final PlayerLobby playerlobby;
  public Board board;
  /**
   * Create the Spark Route (UI controller) for the
   * {@code GET /} HTTP request.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final TemplateEngine templateEngine, PlayerLobby playerlobby) {
    // validation
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
    LOG.finer("GetHomeRoute is invoked.");

    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Welcome!");

    String name = request.queryParams("value1");
    if(request.session().attribute("currentPlayer") instanceof Player && ((Player) request.session().attribute("currentPlayer")).getOpponent() != null && ((Player) request.session().attribute("currentPlayer")).getOpponent().getBoard() == null)
    {
      Player temp = request.session().attribute("currentPlayer");
      temp.setTurn(false);
      temp.setStatus(false);
      temp.setBoard(null);
      temp.setPiece(new ArrayList<>());
      temp.setGameConnection(false);
      temp.setOpponent(null);
      vm.put("users", playerlobby.getPlayerNames());
      vm.put("currentPlayer", request.session().attribute("currentPlayer"));
      vm.put("playerLobby", playerlobby);
      return templateEngine.render(new ModelAndView(vm , "home.ftl"));
    }
    else if((request.session().attribute("currentPlayer") instanceof Player) && ((Player) request.session().attribute("currentPlayer")).getStatus() == true)
    {
      if(((Player) request.session().attribute("currentPlayer")).getColor())
      {
        vm.put("redPlayer", request.session().attribute("currentPlayer"));
        vm.put("whitePlayer", ((Player) request.session().attribute("currentPlayer")).getOpponent());
        vm.put("activeColor", Piece.color.RED);
        vm.put("viewMode", "PLAY");
        this.board = (((Player) request.session().attribute("currentPlayer")).getOpponent().getBoard());
      }
      else
      {
        vm.put("whitePlayer", request.session().attribute("currentPlayer"));
        vm.put("redPlayer", ((Player) request.session().attribute("currentPlayer")).getOpponent());
        vm.put("activeColor", Piece.color.RED);
        vm.put("viewMode", "PLAY");
        this.board = (((Player) request.session().attribute("currentPlayer")).getOpponent().getBoard());;
      }
      vm.put("currentPlayer", request.session().attribute("currentPlayer"));
      vm.put("board", board);
      ArrayList<String> bob = new ArrayList<>();
      bob.add("Help");
      bob.add("No Help");
      vm.put("users", bob);
      vm.put("playerLobby", playerlobby);
      return templateEngine.render(new ModelAndView(vm , "game.ftl"));
    }
    else if(request.session().attribute("currentPlayer") instanceof Player)
    {
      vm.put("users", playerlobby.getPlayerNames());
      vm.put("currentPlayer", request.session().attribute("currentPlayer"));
      vm.put("playerLobby", playerlobby);
      return templateEngine.render(new ModelAndView(vm , "home.ftl"));
    }
    if(name != null)
    {
      if(name.equals(""))
      {
        vm.put("playerLobby", playerlobby);
        vm.put("error", "You entered a null name! Please enter a name!");
        return templateEngine.render(new ModelAndView(vm , "signinFailed.ftl"));
      }
      Player tempPlayer = new Player(name);
      if(playerlobby.isNameAvailable(tempPlayer))
      {
        if(playerlobby.isNameValid(tempPlayer) == false)
        {
          vm.put("playerLobby", playerlobby);
          vm.put("error", "IOWs, double quote is not a valid character in a Player's name!");
          return templateEngine.render(new ModelAndView(vm , "signinFailed.ftl"));
        }
        else
        {
          playerlobby.addPlayer(tempPlayer);
          request.session().attribute("currentPlayer", tempPlayer);
          vm.put("users", playerlobby.getPlayerNames());
          vm.put("currentPlayer", tempPlayer);
          vm.put("playerLobby", playerlobby);
          return templateEngine.render(new ModelAndView(vm, "home.ftl"));
        }
      }
      else
      {
        vm.put("playerLobby", playerlobby);
        vm.put("error", "That name is taken!");
        return templateEngine.render(new ModelAndView(vm , "signinFailed.ftl"));
      }
    }
    vm.put("playerLobby", playerlobby);
    vm.put("numberOfUsers", playerlobby.getPlayerNumber());
    return templateEngine.render(new ModelAndView(vm , "home.ftl"));
  }
}