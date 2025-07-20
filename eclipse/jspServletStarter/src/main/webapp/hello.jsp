<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hello JSP</title>
</head>
<body>
    <h1>Hello JSP Page 👋</h1>
    <p>Message from Servlet:</p>
    <form action="/jspServletStarter/hello" method="get">
        <button type="submit">Submit</button>
    </form>
    <h3><%= request.getAttribute("message") %></h3>
</body>
</html>
