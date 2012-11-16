package infrastructure.components;

import org.apache.commons.net.ftp.*;

import severeLobster.frontend.application.MainFrame;

import java.io.*;

import javax.swing.JLabel;

public class FTPConnector {
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

}