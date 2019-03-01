<!DOCTYPE html>
<%@page import="java.util.*"%>
<%@page import="java.util.Timer"%>
<%@page import="multichain.object.StreamKeyTxid"%>
<%@page import="multichain.command.MultiChainCommand" %>
<%@page import="multichain.command.MultichainException" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" type="text/css" href="CSS/index.css?ts=<?=time()?>&quot" />
	<link rel="stylesheet" type="text/css" href="Font-Awesome-4.7/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="CSS/bootstrap.min.css?ts=<?=time()?>&quot">
	

<title>Piattaforma MultiChain</title>

</head>

<body>

 <h1 style="text-align: center; color: white">Blockchain</h1>

<div class="container">
        <section class="col-md-12 content" id="home">
           <div class="col-lg-6 col-md-6 content-item tm-black-translucent-bg tm-logo-box">
              <h2 class="up">Caricamento protocollo</h2>
              
              <i class="tm-logo">
                <h4>Scegli file:</h4>
              </i>
              
              <h4 class="text-uppercase">
				<form method="post" action="upload" enctype="multipart/form-data">
					<input type="file" name="file"/> 
					<br>
					<input style="color:black;" type="text" name="categoria" placeholder="Scegli categoria" list="categorie" id="formCategoria" >
					<datalist id="categorie">
						<option value="documento PEC">
						<option value="email">
						<option value="titulus">
						<option value="scansione di documento">

					</datalist>
					<br>
					<br>
					<input style="color:black;" type="text" name="id_documentale" placeholder="ID documentale ateneo" id="formIDdocumentale" />
					<br> 
					
					<input type="submit" value="Upload & Publish" name="upload" id="upload" />
				</form>
				

<%
	String log = (String)request.getAttribute("log");
	if(log != null) {
		%>
		<br>
		<p style="font-family: sans-serif""><%=log %></p>
	<%
	}
%>

			</h4>
			
		</div>
	<div class="divFine">
 </div>
		
		
		<div class="col-lg-6 col-md-6 content-item content-item-1 background tm-white-translucent-bg">
               <h2 class="dark-blue-text">Cerca</h2>

 				<form action='cerca' method="post" id="form" enctype="multipart/form-data"> 
 					<input type="text" name="key" placeholder="Inserisci nome o hash o ID" id="formCerca" /> 
 					&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;
 				 <div class="cerca">
 					<h4>Scegli il file per cui vuoi controllare l'hash:</h4>
 					<input type="file" name="fileCercaHash" value="ciao"/>

 					<h4>Cerca per: </h4>
 					<input type="submit" value="Nome" name="cercaNome" id = "submitCercaNome"/>
 					<input type="submit" value="Hash" name="cercaHash"  id="submitCercaHash"/>
 					

					
   					<select name="cercaCateg" id="submitCercaCateg" onchange="if(this.value != 0) { this.form.submit(); }">
        				<option value='0'>Categoria</option>
         				<option value='documento PEC'>documento PEC</option>
         				<option value='email'>email</option>
         				<option value='titulus'>titulus</option>
         				<option value='scansione di documento'>scansione di documento</option>
    				</select>
 
 					<input type="submit" value="ID" name="cercaId" id="submitCercaID" />
 	
 				 </div>	
 
 			   </form> 
 			<h4>
 			
 			
 			
 				
 <%


 String logCerca = (String)request.getAttribute("logCerca");
	if(logCerca != null) {
		
	
%>
		<br>
		<p style= "font-family: sans-serif;" id="testoCerca"><%=logCerca %></p>
		
       
<%

	}
%>
			</h4>
 			
 		</div>
 		
 	</section>
 	
 	</div>
 	
 	<div class="separatorDiv">
 		<table style="margin-left:auto; margin-right:auto;">
 		
	 		<caption style="color:#006699;">Ultime transazioni</caption>
	          <tr>
	            <th style="padding: 5px; text-align: center;">Name</th>
	             <th style="padding: 5px; text-align: center;">Categoria</th>
	            <th style="padding: 5px; text-align: center;">Date and Time</th>
	          </tr>
	          
<%
MultiChainCommand multiChainCommand;
List<StreamKeyTxid> resultTxid;
String txid = "";
StreamKeyTxid[] array;
multiChainCommand = new MultiChainCommand("localhost", "7440", "multichainrpc","94yixEbsftrYHArfmVhBDocSH1J6TkHTSHCnvB6451aW");



try {
	resultTxid = multiChainCommand.getStreamCommand().listStreamItemsTxid("stream1", false, 10);
	array = resultTxid.toArray(new StreamKeyTxid[resultTxid.size()]);
	if(array != null) {
		for(int i = 0; i < array.length; i++) {
	
%>
	<tr>
		<td><a href="visualizza_dettagli?id=<%=array[i].toString()%>"><%=array[i].getKey()%> </a></td>
		<td><%=array[i].getCategoria()%> </td>
		<td><%=array[i].getTimestamp()%> </td>
		
	</tr>

<% 		
		}		
	}
} catch (MultichainException e) {
	e.printStackTrace();
}
%>
	          
 		
 		</table>

 	</div>
 	
 	<div class="footer">
    	<br>
  	</div>
 	

</body>
</html>
