<%-- 
    Document   : indy.jsp
    Created on : Nov. 22, 2024, 7:25:42 p.m.
    Author     : strif
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="indy.IndyWinnerServlet" %>
<%@page import="indy.IndyWinner" %>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<%
    Object pageNumberObject = session.getAttribute(IndyWinnerServlet.PAGE_NUMBER);
    int pageNumber = 0;
    if (pageNumberObject != null) {
        pageNumber = ((Integer) pageNumberObject).intValue();
    }
    List<IndyWinner> indyWinnerList = (List<IndyWinner>) session.getAttribute(IndyWinnerServlet.WINNER_LIST);
    Boolean lastPage = (Boolean) session.getAttribute(IndyWinnerServlet.LAST_PAGE);
    if (lastPage == null) {
        lastPage = Boolean.FALSE;
    }
%>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Indy Winners</title>
    </head>
    <body>
        <% if (indyWinnerList != null) { %>
        <table border>
            <thead>
                <tr>
                    <th>Year</th>
                    <th>Driver</th>
                    <th>Average Speed (mph)</th>
                    <th>Country</th>
                </tr>
            </thead>
            <tbody>
                <% for (IndyWinner indyWinner : indyWinnerList) { %>
                <tr>
                    <td><%=indyWinner.getYear() %></td>
                    <td><%=indyWinner.getDriver()%></td>
                    <td><%=indyWinner.getAverageSpeed() %></td>
                    <td><%=indyWinner.getCountry() %></td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <% } %>
        <% if (pageNumber > 1) {%>
        <form action="/IndyListSimple/IndyWinner" method="post">
            <input type="hidden" name="page" value="<%=(pageNumber - 1) %>" />
            <button type="submit">Previous</button>
        </form>
        <% } %>
        <% if (!lastPage.booleanValue()) { %>
        <form action="/IndyListSimple/IndyWinner" method="post">
            <input type="hidden" name="page" value="<%=(pageNumber + 1) %>" />
            <button type="submit">Continue</button>
        </form>
            <% } %>
    </body>
</html>
