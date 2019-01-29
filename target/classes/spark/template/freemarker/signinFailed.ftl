<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">

    <h1>Web Checkers</h1>

    <div class="navigation">
      <a href="/">my home</a> <a href="/signin">log in</a>
    </div>


    <#if error??>
      <div class ="body">
        <p>${error}</p>
    <#else>
       <div class="body">
         <p>I'm sorry, but that name is in use! Please use a different name!</p>
       </div>
    </#if>

    <form action ="./" method = "GET" id = "input">
        Username:<br>
        <input type = "text" name = "value1">
    </form>

    <button type = "submit" form = "input" value = "Submit">Submit</button>


  </div>
</body>
</html>