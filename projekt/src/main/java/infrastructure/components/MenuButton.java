package infrastructure.components;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Lars Schlegelmilch
 */
public class MenuButton extends JButton {

    public MenuButton(final Icon defaultIcon,
                      final Icon rolloverIcon,
                      final Icon pressedIcon) {

        setOpaque(false);
        setContentAreaFilled(false);
        setIcon(defaultIcon);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                setIcon(defaultIcon);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setIcon(rolloverIcon);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setIcon(pressedIcon);
            }
        });
    }


}
