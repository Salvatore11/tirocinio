<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="CSS/visualizza_dettagli.css?ts=<?=time()?>&quot" />
<meta charset="UTF-8">
<title>Dettagli</title>

</head>
<body>

<%
	String txid = (String)request.getAttribute("id");
	String hash = (String)request.getAttribute("hash");
	String date = (String)request.getAttribute("date");
	String time = (String)request.getAttribute("time");
	String key = (String)request.getAttribute("key");
	String categoria = (String)request.getAttribute("categoria");
	String idDocumentale = (String)request.getAttribute("idDocumentale");
	
	request.removeAttribute("id");
	request.removeAttribute("hash");
	
	String timestamp = date + " " + time;
	String path = (String)request.getAttribute("path");
	
%>
<form action="downloadFile" method="POST">
	<div class="separatorDiv">
 	
   <table>
   <caption>Dettagli</caption>
   <tr>
  		 <td>Txid: <%=txid %></td>
   </tr>
   	<tr>
   		<td>Hash: <%=hash %> </td>
   	</tr>
   	<tr>
   		<td>Data: <%=timestamp %></td>
   	</tr>
   	<tr>
   		<td>Nome: <%=key %></td>
   	</tr>
   	
   	<tr>
   		<td>Categoria: <%=categoria %></td>
   	</tr>
   	<tr>
   		<td>ID documentale Ateneo: <%=idDocumentale %></td>
   	</tr>
   	
   	<tr>
   		<td><a href=<%=path %>>Download</a></td>
   	</tr>
   	
   </table>
   
   </div>
   
 </form>
 
 
 <div class="divFine">
 </div>


</body>
</html>