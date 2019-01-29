<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="8">
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
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
</head>
<body>
  <div class="page">

    <h1>Web Checkers</h1>

   <div class="navigation">
   <#if currentPlayer??>
     <a href="/">my home</a>
     <a href="/signout">sign out [${currentPlayer.name}]</a>

     <div class="body">
           <p>Welcome to the world of online Checkers. <form>
                <input type="button" value="Rules for American Checkers!" onclick="openWin()">
                </form></p>
           <#if error??>
                <p>${error}</p>
           </#if>
           <p>Currently signed in players: </p>
              <ul>
                <#list users as user>
                      <P> ${user}</P>
                 </#list>
               </ul>

           <ul>
               <form action ="./game" method = "GET" id = "input">
                   <select name ="rival">
                        <#list users as user>
                        <#if user != currentPlayer.name>
                        <#if !playerLobby.getPlayer(user).getGameConnection()>
                         <option value = "${user}">${user}</option>
                         </#if>
                        </#if>
                        </#list>
                   </select>
                   <button type = "submit" form = "input" value = "Submit">Challenge!</button>
               </form>
           </ul>

           <ul>
              <form action ="./spectator/game" method = "GET" id = "input2">
                  <select name ="watch">
                       <#list users as user>
                       <#if user != currentPlayer.name>
                       <#if playerLobby.getPlayer(user).getNonspectator()>
                       <#if playerLobby.getPlayer(user).getGameConnection()>
                            <option value = "${user}">${user}</option>
                        </#if>
                       </#if>
                       </#if>
                       </#list>
                  </select>
                  <button type = "submit" form = "input2" value = "Submit">Spectate!</button>
              </form>
          </ul>

         </div>

   <#else>
     <a href="/signin">sign in</a>

     <div class="body">
      <p>Welcome to the world of online Checkers.</p>
      <p>Number of players signed on: ${numberOfUsers}</p>
      </div>

   </#if>
   </div>





  </div>
</body>
</html>
