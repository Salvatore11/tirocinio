package multichain.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.security.MessageDigest;

import java.util.ArrayList;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


import multichain.command.MultiChainCommand;
import multichain.command.MultichainException;

import multichain.object.StreamKey;
import multichain.object.StreamKeyItem;
import multichain.object.StreamKeyTxid;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class Cerca extends HttpServlet{

	private static final long serialVersionUID = 1L;

	static MultiChainCommand multiChainCommand;
	String hashCode;
	String key ;
	String keyInHash;
	String hashFile="";
	List<StreamKey> nameExisting;
	String resultPublish = "";
	List<StreamKeyItem> resultCerca;
	String controllOfString= "";
	String checkKey = "";
	List<StreamKeyTxid> resultTxid;

	String hashDaFile = null;


	//FTP server
	FileOutputStream output = null;
	InputStream input = null;
	String server = "localhost";
	int port = 21;
	String user = "ftpuser";
	String pass = "tirocinio";

	//  variabili cerca hash
	String hashControll = null;
	List<StreamKeyItem> hashChain;
	String hashChainStr = null;
	String nomeFile = null;

	// variabili cerca Categoria
	String categ = null;
	List<StreamKeyItem> categChain= null; // risultati dati dalla chain
	String categChainStr = null;

	// variabili cerca ID Document
	String id = null;
	List<StreamKeyItem> idChain = null;
	String idChainStr = null;


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//connessione alla chain
		multiChainCommand = new MultiChainCommand("localhost", "7440", "multichainrpc","94yixEbsftrYHArfmVhBDocSH1J6TkHTSHCnvB6451aW");

		HttpSession session = request.getSession();


		key = request.getParameter("key").toLowerCase();

		//cerca per Nome
		if(request.getParameter("cercaNome") != null ) {

			if(key.isEmpty()) {
				request.getRequestDispatcher("index.jsp").forward(request, response);
				return;	
			}

			//ritorna le key di multichain presenti all'interno dello stream
			try {
				nameExisting = multiChainCommand.getStreamCommand().listStreamKeys("stream1");
			} catch (MultichainException e) {
				e.printStackTrace();
			}

			checkKey = nameExisting.toString();

			//controllo che la key che si vuole cercare sia presente
			if(!checkKey.toLowerCase().contains(key.toLowerCase())) {
				String log1 = "File non presente ";
				request.setAttribute("logCerca", log1);
				System.out.println("Il file non e' presente!");
				request.getRequestDispatcher("index.jsp").forward(request, response);
				return;

			} else {

				// faccio check sulla chain
				try {
					//MAX_VALUE: restituisce il valore massimo di transazioni rappresentabili sulla macchina
					resultCerca = multiChainCommand.getStreamCommand().listStreamKeyItems("stream1", key, false, Integer.MAX_VALUE);

					ArrayList<String> info = new ArrayList<String> ();
					for(StreamKeyItem ski : resultCerca) {
						if(ski.toString().toLowerCase().contains(key.toLowerCase())) {
							String tmp =  ski.getKey().toString();
							nomeFile = tmp.substring((tmp.lastIndexOf("[") + 1), tmp.lastIndexOf("]"));
							info.add(nomeFile + "," +ski.getHash() + "," +ski.getIDdocumentale()+ "," + ski.getCategoria() + "," + ski.getTimestamp() + "," +ski.getPath());

						}
					}

					session.setAttribute("info", info);

					request.getRequestDispatcher("risultati.jsp").forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}

		// Cerca per Hash
		else if(request.getParameter("cercaHash") != null) {

			InputStream inputStream = null;

			//  ottiene il file part dall'upload nella richiesta multipart
			Part filePart = request.getPart("fileCercaHash");
			String nomeFile = getFileName(filePart);

			if (filePart != null) {
				// ottiene l'inputstream del file caricato
				inputStream = filePart.getInputStream();

				try {
					hashDaFile = getSHA1Checksum(inputStream);
				} catch (Exception e) {
					e.printStackTrace();
				}


			}

			try {
				hashChain = multiChainCommand.getStreamCommand().listStreamItems("stream1", false, Integer.MAX_VALUE);

				hashChainStr = hashChain.toString();

				if(hashChainStr.toLowerCase().contains(hashDaFile.toLowerCase()) || (hashChainStr.toLowerCase().contains(key.toLowerCase()) && !key.isEmpty())) {

					for(StreamKeyItem ski : hashChain) {

						if(ski.getHash().equals(hashDaFile)) {
							String tmp =  ski.getKey().toString();
							nomeFile = tmp.substring((tmp.lastIndexOf("[") + 1), tmp.lastIndexOf("]"));
						}		
					}

					String log2 = "Hash presente nella chain";
					request.setAttribute("logCerca", log2);

					request.getRequestDispatcher("index.jsp").forward(request, response);

				}
				else {
					System.out.println("Hash non presente");
					String log3 = "Hash non presente";
					request.setAttribute("logCerca", log3);
					request.getRequestDispatcher("index.jsp").forward(request, response);
					return;
				}

			} catch (MultichainException e) {
				e.printStackTrace();
			}

		} else if(request.getParameter("cercaId") != null) {

			if(key.isEmpty()) {
				request.getRequestDispatcher("index.jsp").forward(request, response);
				return;

			}
			// faccio check sulla chain
			try {
				idChain = multiChainCommand.getStreamCommand().listStreamItems("stream1", false, Integer.MAX_VALUE);

				idChainStr = idChain.toString();

				int size = idChain.size();
				ArrayList<String> ids = new ArrayList<String>(size);

				for(StreamKeyItem k : idChain) {

					if(k.getIDdocumentale().equals(key.toLowerCase())) {
						String tmp =  k.getKey().toString();
						nomeFile = tmp.substring((tmp.lastIndexOf("[") + 1), tmp.lastIndexOf("]"));
						ids.add(nomeFile + "," +k.getHash() + "," +k.getIDdocumentale()+ "," + k.getCategoria() + "," + k.getTimestamp() + "," +k.getPath());
					}
				}

				if(ids.size() != 0) {
					session.setAttribute("info", ids);
					request.getRequestDispatcher("risultati.jsp").forward(request, response);
				} else {
					System.out.println("non ci sono ID");
					String log4 = "ID non presente";
					request.setAttribute("logCerca", log4);
					request.getRequestDispatcher("index.jsp").forward(request, response);
					return;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		else if(request.getParameter("cercaCateg") != null) {

			String categ = request.getParameter("cercaCateg");

			try {
				categChain = multiChainCommand.getStreamCommand().listStreamItems("stream1", false, Integer.MAX_VALUE);

				categChainStr = categChain.toString();

				if(categ.toLowerCase().equals("titulus") || categ.equals("documento PEC") || categ.toLowerCase().equals("email") || categ.toLowerCase().equals("scansione di documento")) {

					System.out.println("result: " + categChain.toString());

					ArrayList<String> info = new ArrayList<String> ();

					for(StreamKeyItem ski : categChain) {		
						if(ski.toString().toLowerCase().contains(categ.toLowerCase())) {

							String tmp =  ski.getKey().toString();
							nomeFile = tmp.substring((tmp.lastIndexOf("[") + 1), tmp.lastIndexOf("]"));
							info.add(nomeFile + "," +ski.getHash() + "," +ski.getIDdocumentale()+ "," + ski.getCategoria() + "," + ski.getTimestamp() + "," +ski.getPath());

						}
					}
					session.setAttribute("info", info);
					request.getRequestDispatcher("risultati.jsp").forward(request, response);
				} else {

					String log5 = "Categoria non ammessa ";
					request.setAttribute("logCerca", log5);
					System.out.println("categoria non ammessa");
					request.getRequestDispatcher("index.jsp").forward(request, response);
					return;

				}

			} catch (MultichainException e) {
				e.printStackTrace();
			}

		}


	}// fine doPost


	/*
	 * Calcola hashFile
	 * 
	 */
	public static byte[] createChecksum(InputStream filename) throws
	Exception
	{
		InputStream fis =  filename;

		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("SHA1");
		int numRead;
		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);
		fis.close();
		return complete.digest();
	}


	// see this How-to for a faster way to convert
	// a byte array to a HEX string
	public static String getSHA1Checksum(InputStream filename) throws Exception {
		byte[] b = createChecksum(filename);
		String result = "";
		for (int i=0; i < b.length; i++) {
			result +=
					Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		return result;
	}


	private String getFileName(final Part part) {

		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(
						content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;

	}


}


