<%@ page isErrorPage="true" %>
<html>
<head><title>Error</title></head>
<body>
<h2 style="color:red;">Oops! Something went wrong.</h2>
<pre><%= exception.getMessage() %></pre>
<a href="Home.jsp">Go back home</a>
</body>
</html>
