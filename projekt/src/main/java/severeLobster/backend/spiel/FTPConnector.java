package severeLobster.backend.spiel;
import org.apache.commons.net.ftp.*;
import java.io.*;

public class FTPConnector
{
    private FTPClient ftp;
    private String server, username, password;
    private int port;
    

    public FTPConnector(String server, String username, String password, int port)
    {
        ftp = new FTPClient();
        this.server = server;
        this.username = username;
        this.password = password;
        this.port = port;
        connect();
    }
    
    public void connect()
    {
        try 
        {
            ftp.connect(server, port);
            System.out.println(ftp.getReplyString());
            
            ftp.login(username, password);
            System.out.println(ftp.getReplyString());
            
            ftp.enterLocalPassiveMode();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } 
    }
    
    public void disconnect()
    {
        try 
        {
            boolean logout = ftp.logout();
            if (logout) 
            {
                System.out.println("Logout from FTP server...");
            }
            ftp.disconnect();
            System.out.println("Disconnected from " + server);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    
    public void getFiles()
    {
        if(!ftp.isConnected())
        {
            connect();
        }
        try
        {
            FTPFile[] files = ftp.listFiles();
            for(FTPFile f : files)
            {
                System.out.println(f.getName());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}