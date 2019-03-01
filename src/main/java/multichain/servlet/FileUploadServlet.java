package multichain.servlet;


import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;

import java.security.MessageDigest;

import java.sql.Timestamp;

import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import org.json.simple.JSONObject;


import multichain.command.MultiChainCommand;
import multichain.command.MultichainException;
import multichain.object.StreamKey;

@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class FileUploadServlet extends HttpServlet {


	private final static Logger LOGGER =  Logger.getLogger(FileUploadServlet.class.getCanonicalName());

	private static final long serialVersionUID = 1L;
	
	// chain
	static MultiChainCommand multiChainCommand;
	String hashFile = "";
	String nomeFile = "";
	List<StreamKey> nameExisting;
	String checkKey = "";
	String resultPublish = "";
	String date = null;
    String time = null;
    String categoria = null;
    String idDocumentale = null;
    InputStream inputStream = null; // input stream of the upload file
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	
		// Create path components to save the file
		
		String server = "localhost";
		int port = 21;
		String user = "ftpuser2";
		String pass = "tirocinio";

		final Part filePart = request.getPart("file");
		String remoteFile = "";

		categoria = request.getParameter("categoria");
		idDocumentale = request.getParameter("id_documentale");
		
		
		
	    FTPClient ftpClient = new FTPClient();
	       try {

	            ftpClient.connect(server, port);
	            ftpClient.login(user, pass);
	            ftpClient.enterLocalActiveMode();
	         
	            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

	            // Uploads first file using an InputStream
	            
	            Timestamp t = new Timestamp(System.currentTimeMillis());
	            date = t.toString().substring(0, 10);
	            time = t.toString().substring(11, 19);
	            nomeFile = getFileName(filePart);
	            
	            if(nomeFile.equals("") || categoria.equals("") || idDocumentale.equals("")) {
	            	String log6 = "Dati inseriti in modo errato!";
	    			request.setAttribute("log", log6);
	            	request.getRequestDispatcher("index.jsp").forward(request, response);
	            	return;
	    		}
	            
	            inputStream = filePart.getInputStream();
	            remoteFile = date + "-" + time + "-" + nomeFile;

	      
	            System.out.println("==== Start uploading file ====");

	            boolean done = ftpClient.storeFile(remoteFile, inputStream);

	            // calcolo hash del file
	            try {

	            	hashFile = getSHA1Checksum(ftpClient.retrieveFileStream("/"+ remoteFile));
	            	System.out.println("L'hash del file è: " + hashFile);

	            	//passo hash del file calcolato
	    			HttpSession session = request.getSession();
	    			session.setAttribute("hashFile", hashFile);

	            }catch (Exception e) {
	            	e.printStackTrace();
	            }

	            inputStream.close();
	            if (done) {
	                System.out.println("==== The file is uploaded successfully ====");
	            }

	        } catch (IOException ex) {
	           
	            ex.printStackTrace();
	        } finally {
	            try {
	                if (ftpClient.isConnected()) {
	                    ftpClient.logout();
	                    ftpClient.disconnect();
	                }
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
	       
	        
	        // INIZIO PUBLISH SU BLOCKCHAIN
			
			//connessione alla chain
			multiChainCommand = new MultiChainCommand("localhost", "7440", "multichainrpc","94yixEbsftrYHArfmVhBDocSH1J6TkHTSHCnvB6451aW");

			System.out.println("key iniziale: " + nomeFile);
			
			//creazione del dato da inserire 
			String timeStampStr = date + "-" + time; // se non converto in string da converte il mese in lettere
			String filePath = "ftp://" + server + "/" + timeStampStr + "-" + nomeFile;
			
			JSONObject preparedData = new JSONObject();
			preparedData.put("timestamp", timeStampStr);  //LocalDateTime.now());
			preparedData.put("hashFile", hashFile);
			preparedData.put("categoria", categoria);
			preparedData.put("ID_documentale" , idDocumentale);
			preparedData.put("path", filePath);
			JSONObject data = new JSONObject();
			data.put("json", preparedData);
			
			try {
				resultPublish = multiChainCommand.getStreamCommand().publishObject("stream1", nomeFile, data);
		
			} catch (MultichainException e) {
				e.printStackTrace();
			}

			String log7 = "Inserimento completato!";
			request.setAttribute("log", log7);

			//passo il nome del file nella text di jsp
			request.setAttribute("fileName", nomeFile);

			request.getRequestDispatcher("index.jsp").forward(request, response);
		
	
	} // fine doPost


	 
	private String getFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(
						content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;

	}

	/*
	 * Calcola hashFile
	 * 
	 */
	public static byte[] createChecksum(String filename) throws
	Exception
	{
		InputStream fis =  new FileInputStream(filename);

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
	public static String getSHA1Checksum(String filename) throws Exception {
		byte[] b = createChecksum(filename);
		String result = "";
		for (int i=0; i < b.length; i++) {
			result +=
					Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		return result;
	}
	
	/*
	 * Calcolo hash tramite InputStream
	 * 
	 */
	
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


}


