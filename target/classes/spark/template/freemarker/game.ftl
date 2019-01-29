<!DOCTYPE html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <title>${title} | Web Checkers</title>
  <link rel="stylesheet" href="/css/style.css">
  <link rel="stylesheet" href="/css/game.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script>
  window.gameState = {
    "currentPlayer" : "${currentPlayer.name}",
    "viewMode" : "${viewMode}",
    "modeOptions" : ${modeOptionsAsJSON!'{}'},
    "redPlayer" : "${redPlayer.name}",
    "whitePlayer" : "${whitePlayer.name}",
    "activeColor" : "${activeColor}"
  };
  </script>
  <script>
         function openWin() {
             var MyWindow = window.open('','popup','width=600,height=600', 'false')
             MyWindow.document.write("<head>The American Rules of Checkers!</head>");
             MyWindow.document.write("<p>Moves are allowed only on the dark squares, so pieces always move diagonally. Single pieces are always limited to forward moves (toward the opponent).</p>");
             MyWindow.document.write("<p>A piece making a non-capturing move (not involving a jump) may move only one square.</p>");
             MyWindow.document.write("<p>A piece making a capturing move (a jump) leaps over one of the opponent's pieces, landing in a straight diagonal line on the other side. Only one piece may be captured in a single jump; however, multiple jumps are allowed during a single turn.</p>");
             MyWindow.document.write("<p>When a piece is captured, it is removed from the board.</p>");
             MyWindow.document.write("<p>If a player is able to make a capture, there is no option; the jump must be made. If more than one capture is available, the player is free to choose whichever he or she prefers.</p>");
             MyWindow.document.write("<p>When a piece reaches the furthest row from the player who controls that piece, it is crowned and becomes a king. One of the pieces which had been captured is placed on top of the king so that it is twice as high as a single piece.</p>");
             MyWindow.document.write("<p>Kings are limited to moving diagonally but may move both forward and backward. (Remember that single pieces, i.e. non-kings, are always limited to forward moves.)</p>");
             MyWindow.document.write("<p>Kings may combine jumps in several directions, forward and backward, on the same turn. Single pieces may shift direction diagonally during a multiple capture turn, but must always jump forward (toward the opponent).</p>");
             MyWindow.document.write("</br>");
             MyWindow.document.write("</br>");
            MyWindow.document.write("</br>");
            MyWindow.document.write("</br>");
            MyWindow.document.write("</br>");
            MyWindow.document.write("</br>");
            MyWindow.document.write("</br>");
            MyWindow.document.write("</br>");
            MyWindow.document.write("</br>");
            MyWindow.document.write("</br>");


             }
         </script>

     <script>
              function openMoves() {
                  var newWindow = window.open('','popup2','width=600,height=600', 'false')
                  }
              </script>



</head>
<body>
  <div class="page">
    <h1>Web Checkers</h1>
    
    <div class="navigation">
    <#if currentPlayer??>
      <a href="/">my home</a> |
      <a href="/signout">sign out [${currentPlayer.name}]</a>
    <#else>
      <a href="/signin">sign in</a>
    </#if>
    </div>
    
    <div class="body">



       <form>
          <input type="button" value="Rules for American Checkers!" onclick="openWin()">
       </form>

       <#if currentPlayer.getNonspectator()>


      <p id="help_text"></p>

       <p>Options for Toggle Help: </p>

                 <ul>
                     <form action ="./game" method = "GET" id = "bob">
                         <select name ="toggle">
                              <#list users as user>
                              <#if user != currentPlayer.name>
                              <option value = "${user}">${user}</option>
                              </#if>
                              </#list>
                         </select>
                         <button type = "submit" form = "bob" value = "Submit">Toggle!</button>
                     </form>
                 </ul>
         </#if>
      <div>
        <div id="game-controls">
        
          <fieldset id="game-info">
            <legend>Info</legend>
            
            <#if message??>
            <div id="message" class="${message.type}">${message.text}</div>
            <#else>
            <div id="message" class="info" style="display:none">
              <!-- keep here for client-side messages -->
            </div>
            </#if>
            
            <div>
              <table data-color='RED'>
                <tr>
                  <td><img src="../img/single-piece-red.svg" /></td>
                  <td class="name">Red</td>
                </tr>
              </table>
              <table data-color='WHITE'>
                <tr>
                  <td><img src="../img/single-piece-white.svg" /></td>
                  <td class="name">White</td>
                </tr>
              </table>
              <table data-color='YELLOW'>
                              <tr>
                                <td><img src="../img/r-move-piece.svg" /></td>
                                <td class="name">RED HINT</td>
                              </tr>
                            </table>
              <table data-color='GREEN'>
                              <tr>
                                <td><img src="../img/w-move-piece.svg" /></td>
                                <td class="name">WHITE HINT</td>
                              </tr>
                            </table>
            </div>
          </fieldset>
          
          <fieldset id="game-toolbar">
            <legend>Controls</legend>
            <div class="toolbar"></div>
          </fieldset>
          
        </div>
        <#if currentPlayer.getColor()>
        <div class="game-board" style ="transform: rotate(-180deg);">
          <table id="game-board">
            <tbody>
            <#list board.iterator() as row>
              <tr data-row="${row.getIndex()}">
              <#list row.iterator() as space>
                <td data-cell="${space.getCellId()}"
                    <#if space.isValid() >
                    class="Space"
                    </#if>
                    >
                <#if space.piece??>
                  <div class="Piece" style = "transform: rotate(-180deg);"
                       id="piece-${row.index}-${space.getCellId()}"
                       data-type="${space.piece.type}"
                       data-color="${space.piece.color}">

                  </div>
                </#if>
                </td>
              </#list>
              </tr>
            </#list>
            </tbody>
          </table>
          </div>
          <#else>
          <div class="game-board">
                    <table id="game-board">
                      <tbody>
                      <#list board.iterator() as row>
                        <tr data-row="${row.getIndex()}">
                        <#list row.iterator() as space>
                          <td data-cell="${space.getCellId()}"
                              <#if space.isValid() >
                              class="Space"
                              </#if>
                              >
                          <#if space.piece??>
                            <div class="Piece"
                                 id="piece-${row.index}-${space.getCellId()}"
                                 data-type="${space.piece.type}"
                                 data-color="${space.piece.color}">
                            </div>
                          </#if>
                          </td>
                        </#list>
                        </tr>
                      </#list>
                      </tbody>
                    </table>
                    </div>
                    </#if>
      </div>
    </div>
  </div>

  <audio id="audio" src="http://www.soundjay.com/button/beep-07.mp3" autostart="false" ></audio>
  
  <script data-main="/js/game/index" src="/js/require.js"></script>
  
</body>
</html>
