package severeLobster.frontend.view;

import infrastructure.ResourceManager;
import infrastructure.components.FTPConnector;
import severeLobster.frontend.application.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Erstellt das Ein Panel auf die Preview zu sehen ist und ein Button zum
 * Starten des Puzzles
 *
 * @author fwenisch
 * @date 10.11.2012
 */
public class OnlinePuzzlePreviewView extends JPanel {
    private final ResourceManager resourceManager = ResourceManager.get();
    private String strPuzzleName = null;

    OnlinePuzzlePreviewView(final Component owner, final String strFileName, final FTPConnector ftpConnector) {
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(680, 50));
        this.setMinimumSize(new Dimension(680, 50));
        this.setMaximumSize(new Dimension(680, 50));
        this.setBackground(Color.BLACK);
        JLabel jlSpielName = new JLabel();
        jlSpielName.setFont(new Font("Arial", java.awt.Font.PLAIN, 22));
        jlSpielName.setForeground(Color.YELLOW);
        jlSpielName.setText(strFileName);
        JButton jbOnlineInfo = new JButton("Download");
        jbOnlineInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    ftpConnector.getFile(owner, strFileName);
                } catch (Exception e) {
                }
            }
        });
        jbOnlineInfo.setPreferredSize(new Dimension(680, 20));
        jlSpielName.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(jlSpielName, BorderLayout.NORTH);
        this.add(jbOnlineInfo, BorderLayout.SOUTH);
    }


}
