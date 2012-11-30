package infrastructure.components;

import infrastructure.ResourceManager;
import infrastructure.constants.GlobaleKonstanten;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import severeLobster.frontend.application.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Util zum Arbeiten mit dem FTP auf dem die Puzzles gespeichert werden
 *
 * @author JFW
 * @date 17.11.2012
 */
public class FTPConnector {

    private FTPClient ftp;
    private String server, username, password;
    private int port;
    public FTPFile[] files;
    private final ResourceManager resourceManager = ResourceManager.get();

    public FTPConnector(String server, String username, String password, int port) {
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
            System.out.println("Es konnte keine Verbindung zum FTP herstellt werden");
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
     *
     * @author JFW
     * @date 16.11.2012
     */
    public void updateFiles() {
        if (!ftp.isConnected()) {
            connect();
        }
        try {
            files = ftp.listFiles();
            if (files.length > 0)
                MainFrame.jlOnlineSpiele.setText("| " + files.length + " " +
                        resourceManager.getText("online.archive.count.text"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode zum Download eines einzelnen Puzzles
     *
     * @param strPuzzleName - <String> PuzzleName auf dem FTP
     */
    public void getFile(Component owner, String strPuzzleName) {
        FTPFile f;
        try {
            File file;
            boolean success = false;
            for (FTPFile file1 : files) {
                if (file1.getName().equals(strPuzzleName)) {
                    f = file1;
                    file = new File(GlobaleKonstanten.DEFAULT_FREIGEGEBENE_PUZZLE_SAVE_DIR, f.getName());
                    if (file.exists()) {
                        JOptionPane.showMessageDialog(owner,
                                resourceManager.getText("online.archive.already.there.title"),
                                resourceManager.getText("online.archive.already.there.body"),
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        FileOutputStream fos = new FileOutputStream(file);
                        success = ftp.retrieveFile(file1.getName(), fos);
                        fos.close();
                    }
                }

            }
            if (success) {


                JOptionPane.showMessageDialog(owner, resourceManager.getText("online.archive.download.success.body"),
                        resourceManager.getText("online.archive.download.success.title"),
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Methode zum Upload der Puzzles
     *
     * @param localSourceFile  - <String> Lokale Datei
     * @param remoteResultFile - <String> Datei auf dem FTP-Server
     * @author JFW
     * @date 17.11.2012
     */
    public void upload(String localSourceFile, String remoteResultFile) {
        if (!ftp.isConnected()) {
            connect();
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(localSourceFile);
            ftp.storeFile(remoteResultFile, fis);
            System.out.println(ftp.getReplyString());
            ftp.logout();
        } catch (Exception e) {
            System.out.println("Fehler beim Uploaden von " + localSourceFile);

        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                ftp.disconnect();
            } catch (IOException e) {
                System.out.println("Fehler beim Schließen der FTP-Verbindung aufgetreten");
            }
        }
    }

    /**
     * @return Wurde eine Verbindung hergestellt?
     */
    public boolean isOnline() {
        return ftp.isConnected();
    }
}
