package severeLobster.frontend.view;

import javax.swing.JPanel;

/**
 * TODO An neue Architektur anpassen. Darstellung eines Spielfeldes mit den
 * enthaltenen Spielsteinen.
 * 
 * @author LKleiber
 * 
 */
public class SpielfeldView extends JPanel {

    // public SpielfeldView(final Spielfeld spielfeld) {
    // if (null == spielfeld) {
    // throw new NullPointerException("Spielfeld ist null");
    // }
    // setOpaque(false);
    // final int laenge = spielfeld.getHoehe();
    // final int breite = spielfeld.getBreite();
    // /**
    // * Gridlayout bietet die tabellenartige Darstellung der zum Panel
    // * hinzugefuegten Komponenten.
    // */
    // this.setLayout(new GridLayout(laenge, breite));
    // Spielstein spielstein = null;
    // SpielsteinViewImpl view = null;
    //
    // /**
    // * Durchlaufe das Spielfeld zeilenweise und fuege in der Reihenfolge die
    // * Spielsteinansichten zum Panel hinzu.
    // */
    // for (int laengeIndex = 0; laengeIndex < laenge; laengeIndex++) {
    // for (int breiteIndex = 0; breiteIndex < breite; breiteIndex++) {
    // /** Hole naechsten Spielstein */
    // spielstein = spielfeld.getSpielstein(breiteIndex, laengeIndex);
    // /** Erstelle neue Ansichtskomponente fuer diesen Spielstein */
    // view = new SpielsteinViewImpl();
    // /** Verknuepfe Ansicht mit Spielstein: */
    // new SpielsteinController(view, spielstein);
    // /** Fuege Ansicht zum Panel hinzu */
    // this.add(view);
    // }
    // }
    // }
}
