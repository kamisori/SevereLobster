package severeLobster.frontend.view;

import infrastructure.ResourceManager;
import infrastructure.graphics.GraphicUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import severeLobster.backend.spiel.Spiel;
import severeLobster.backend.spiel.SternenSpielApplicationBackend;
import severeLobster.frontend.application.MainFrame;

/**
 * Erstellt das Ein Panel auf die Preview zu sehen ist und ein Button zum
 * Starten des Puzzles
 * 
 * @author fwenisch
 * @date 10.11.2012
 * 
 */
public class OnlinePuzzlePreviewView extends JPanel 
{
    private final ResourceManager resourceManager = ResourceManager.get();
    private String strPuzzleName = null;

    OnlinePuzzlePreviewView(final String strFileName) 
    {
    	this.setPreferredSize(new Dimension(680, 50));
    	this.setMinimumSize(new Dimension(680, 50));
    	this.setMaximumSize(new Dimension(680, 50));
   this.setBackground(Color.BLACK);
    JLabel jlSpielName=new JLabel();
    jlSpielName.setFont(new Font("Arial", java.awt.Font.PLAIN, 22));
    jlSpielName.setForeground(Color.YELLOW);
    jlSpielName.setText(strFileName);//.substring(0,strFileName.indexOf('-')));
   JButton jbOnlineInfo = new JButton("Ansehen");
   jbOnlineInfo.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            try {
               MainFrame.oFTP.getFile(strFileName);
            } catch (Exception e) {
          
            }
        }
    });
   jbOnlineInfo.setPreferredSize(new Dimension(680, 20));
    this.add(jlSpielName,BorderLayout.NORTH);
    this.add(jbOnlineInfo,BorderLayout.SOUTH);
    }

    
}
