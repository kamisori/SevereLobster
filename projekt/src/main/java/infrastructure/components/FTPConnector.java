package infrastructure.components;

import org.apache.commons.net.ftp.*;

import severeLobster.frontend.application.MainFrame;

import java.io.*;

import javax.swing.JLabel;

/**
 * Util zum Arbeiten mit dem FTP auf dem die Puzzles gespeichert werden
 * @author JFW
 * @date 17.11.2012
 *
 */
public class FTPConnector 
{
	private FTPClient ftp;
	private String server, username, password;
	private int port;
	public FTPFile[] files;

	public FTPConnector(String server, String username, String password,
			int port) {
		ftp = new FTPClient();
		this.server = server;
		this.username = username;
		this.password = password;
		this.port = port;
		connect();
		updateFiles();
		disconnect();
	}

	public void connect() {
		try {
			ftp.connect(server, port);
			System.out.println(ftp.getReplyString());

			ftp.login(username, password);
			System.out.println(ftp.getReplyString());

			ftp.enterLocalPassiveMode();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			boolean logout = ftp.logout();
			if (logout) {
				System.out.println("Logout from FTP server...");
			}
			ftp.disconnect();
			System.out.println("Disconnected from " + server);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Aktualisiert das interne Array in dem alle verfügbaren Puzzles im Online Archiv gespeichert sind
	 * @author JFW
	 * @date 16.11.2012
	 */
	public void updateFiles() 
	{
		if (!ftp.isConnected()) {
			connect();
		}
		try
		{
			files = ftp.listFiles();
			if(files.length>0)
				MainFrame.jlOnlineSpiele.setText("| "+files.length+" Puzzles im Online Archiv verfügbar");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	/**
	 * Methode zum Download eines einzelnen Puzzles
	 * @param strPuzzleName - <String> PuzzleName auf dem FTP
	 * @author JFW
	 * @date 16.11.2012
	 */
	public void getFile(String strPuzzleName)
	{
		FTPFile f;
		try
		{
			File file =null;
			for(int i=0; i < files.length;i++)
			{
				if(files[i].getName().equals(strPuzzleName))
				{
					f=files[i];
					file = new File("C:\\temp\\"+f.getName());
					FileOutputStream fos = new FileOutputStream(file); 
					ftp.retrieveFile( files[ i ].getName(), fos);
					fos.close();
				}

			}
			if (file!=null)
			{
				System.out.println(strPuzzleName+ "wurde heruntergeladen");
			}
			else
				throw new Exception (strPuzzleName+ "wurde nicht gefunden");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Methode zum Upload der Puzzles
	 * @param localSourceFile - <String> Lokale Datei
	 * @param remoteResultFile - <String> Datei auf dem FTP-Server
	 * @author JFW
	 * @date	17.11.2012
	 */
	public  void upload( String localSourceFile, String remoteResultFile ) 
	{
		if (!ftp.isConnected()) 
		{
			connect();
		}  
		FileInputStream fis = null;
		try {


			fis = new FileInputStream( localSourceFile );
			ftp.storeFile( remoteResultFile, fis );
			System.out.println( ftp.getReplyString() );
			ftp.logout();
		}
		catch(Exception e)
		{
			System.out.println("Fehler beim Uploaden von "+localSourceFile);

		}
		finally 
		{
			try 
			{ 
				if( fis != null ) 
				{ 
					fis.close();
				} 
				ftp.disconnect();
			} catch( IOException e )
			{
				System.out.println("Fehler beim Schließen der FTP-Verbindung aufgetreten");
			}
		}


	}



}