package severeLobster.frontend.view;

import javax.swing.ImageIcon;

/**
 * Kontainer fuer ein ImageIcon in verschiedenen Aufloesungen.
 * 
 * @author Lutz Kleiber
 * 
 */
public class ImageIconInVerschiedenenAufloesungen {

    /**
     * Verfuegbare Aufloesungen des Bildes. Bilder sind nach dem erstellen in
     * aufsteigender Aufloesung sortiert.
     */
    private final ImageIcon[] verschiedeneAufloesungen;

    public ImageIconInVerschiedenenAufloesungen(
            final ImageIcon[] gleichesBildInVerschiedenenAufloesungen) {
        if (null == gleichesBildInVerschiedenenAufloesungen) {
            throw new NullPointerException("Array von ImageIcons ist null");
        }
        this.verschiedeneAufloesungen = gleichesBildInVerschiedenenAufloesungen;
        sortiereVonNiedrigerZuHoherAufloesung(verschiedeneAufloesungen);
    }

    public ImageIcon getNaechstHoehereAufloesung(final int targetWidth) {

        ImageIcon result;
        int index = 0;
        do {
            result = verschiedeneAufloesungen[index];
            index++;
        } while (result.getIconWidth() < targetWidth);
        return result;
    }

    /**
     * Momentan als Bubblesort.
     * 
     * @param array
     */
    private static void sortiereVonNiedrigerZuHoherAufloesung(
            final ImageIcon[] array) {
        for (int n = array.length; n > 1; n = n - 1) {
            for (int i = 0; i < n - 1; i = i + 1) {
                if (array[i].getIconWidth() > array[i + 1].getIconWidth()) {
                    /* Tauschen: */
                    final ImageIcon temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                }
            }
        }
    }
}
