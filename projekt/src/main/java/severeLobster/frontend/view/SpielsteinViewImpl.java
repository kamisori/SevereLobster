package severeLobster.frontend.view;

import javax.swing.JLabel;

/**
 * TODO An neue Architektur anpassen. Darstellung eines einzelnen Spielsteins.
 * 
 * @author LKleiber
 * 
 */
public class SpielsteinViewImpl extends JLabel /**
 * implements
 * IControllableSpielsteinView
 */
{

    // private static final IconFactory ICON_FACTORY = SimpleIconFactory
    // .getInstance();
    // private SpielsteinController spielsteinController;
    //
    // public SpielsteinViewImpl() {
    // final Icon newIcon = ICON_FACTORY.getIconForState(KeinStein
    // .getInstance());
    // this.setIcon(newIcon);
    // this.addMouseListener(new InnerMouseListener());
    // }
    //
    // @Override
    // public void setDisplayedState(final Spielstein newDisplayedState) {
    // final Icon newIcon = ICON_FACTORY.getIconForState(newDisplayedState);
    // SwingUtilities.invokeLater(new Runnable() {
    //
    // public void run() {
    // setIcon(newIcon);
    // }
    // });
    // }
    //
    // @Override
    // public void setSpielsteinController(
    // SpielsteinController spielsteinController) {
    // this.spielsteinController = spielsteinController;
    // /**
    // * Aktualisiere direkt den dargestellten Zustand, da nun der wirkliche
    // * Zustand abgefragt werden kann.
    // */
    // final Spielstein currentState = spielsteinController.getState();
    // setDisplayedState(currentState);
    // }
    //
    // private class InnerMouseListener extends MouseAdapter {
    //
    // @Override
    // public void mouseClicked(MouseEvent arg0) {
    // spielsteinController.clickAction(arg0);
    // }
    // }
}
