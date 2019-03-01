package multichain.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import multichain.command.MultiChainCommand;
import multichain.object.StreamKeyItem;


@WebServlet("/visualizza_dettagli")
public class VisualizzaDettagli extends HttpServlet{
	
	static MultiChainCommand multiChainCommand;
	StreamKeyItem item = null;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		 try {
		 	HttpSession session = request.getSession();
		 	
		 	multiChainCommand = new MultiChainCommand("localhost", "7440", "multichainrpc","94yixEbsftrYHArfmVhBDocSH1J6TkHTSHCnvB6451aW");
	
			String txidTransazione = request.getParameter("id");
			
			item = multiChainCommand.getStreamCommand().getStreamItem("stream1", txidTransazione, true);
			
			
			String itemInStringa = item.toString();
			
			String key = item.getKey().toString();
			String subkey = key.substring((key.lastIndexOf('[') + 1), key.lastIndexOf(']'));
			String hash = item.getHash();
			String date = item.getDate();
			String time = item.getTimeTimestamp();
			String pathFile = item.getPath();
			String categoria = item.getCategoria();
			String idDocumentale = item.getIDdocumentale();
			
			
			request.setAttribute("id", txidTransazione);
			request.setAttribute("hash", hash);
			request.setAttribute("date", date);
			request.setAttribute("time", time);
			request.setAttribute("key", subkey);
			request.setAttribute("path", pathFile);
			request.setAttribute("categoria", categoria);
			request.setAttribute("idDocumentale", idDocumentale);
			
			session.setAttribute("nome", subkey);
			session.setAttribute("data", date);
			session.setAttribute("time", time);
			
			request.getRequestDispatcher("visualizza_dettagli.jsp").forward(request, response);
		
		 } catch (Throwable th) {
			 th.printStackTrace();
		 }
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
		
	}
}
