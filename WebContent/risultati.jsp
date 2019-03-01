<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="multichain.object.StreamKeyItem"%>
<%@page import="java.util.ArrayList"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" type="text/css" href="CSS/risultati.css?ts=<?=time()?>&quot"/>
	<meta charset="UTF-8">
<title>Cerca</title>
</head>
<body>



<div class="separatorDiv">

<table>
	<caption> Risultati </caption>
	
	<tr>
		<th style="padding: 5px; text-align: center;">Nome File</th>
		<th style="padding: 5px; text-align: center;">Hash</th>
	    <th style="padding: 5px; text-align: center;">ID Documentale Ateneo</th>
	    <th style="padding: 5px; text-align: center;">Categoria</th>
	    <th style="padding: 5px; text-align: center;">Date and Time</th>
	    <th style="padding: 5px; text-align: center;">Download</th>
	</tr>
<%

	ArrayList<String> info = (ArrayList<String>)session.getAttribute("info");
	
	
	if(info != null){
		for(String obj : info){
			String tmp1 = obj;
			request.setAttribute("tmp", tmp1);
			System.out.println("risultato: " +obj);



%>
<tr>

<c:set var="msg" value="${tmp}"/>
<c:set var="arrayofmsg" value="${fn:split(msg,',')}"/>
<c:forEach var="i" begin="0" end="5">
	
	<c:choose>
		<c:when test="${i eq 5}">
			<td><a href="${arrayofmsg[i]}">Download</a></td>
		</c:when>
	<c:otherwise>
		<td>${arrayofmsg[i]}</td>
	</c:otherwise>
	
	</c:choose>
	 		
</c:forEach>

	
<%
		}
		%>
		</tr>
<%
		session.removeAttribute("info");
	}
%>

</table>

<div class="divFine">
</div>

</body>
</html>