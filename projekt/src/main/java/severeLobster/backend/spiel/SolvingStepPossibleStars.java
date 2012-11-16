package severeLobster.backend.spiel;

import infrastructure.constants.enums.PfeilrichtungEnumeration;

/**
 * Dies sollte der erste Schritt sein. Es werden alle Positionen als Möglicher Stern
 * markiert, auf die ein Pfeil zeigt.
 *
 * @author Christian Lobach
 */
public class SolvingStepPossibleStars implements SolvingStep {
    @Override
    public Spielfeld execute(Spielfeld input) {

        for (int colums = 0; colums < input.getBreite(); colums++) {
            for (int rows = 0; rows < input.getHoehe(); rows++) {

                if (input.getSpielstein(colums, rows) instanceof Pfeil) {

                    Pfeil currentPfeil = (Pfeil) input.getSpielstein(colums, rows);

                    // Richtung der Pfeile folgen, mögliche Sterne setzen
                    switch (currentPfeil.getPfeilrichtung()) {
                        case NORD:
                            for (int curY = rows; curY >= 0; curY--) {
                                if (!(input.getSpielstein(colums, curY) instanceof Pfeil)) {
                                    input.setSpielstein(colums,curY, MoeglicherStern.getInstance());
                                }
                            }
                            break;

                        case NORDOST:
                            for (int curX = colums, curY = rows; curX < input.getBreite() && curY >= 0; curX++, curY-- ) {
                                if (!(input.getSpielstein(curX, curY) instanceof Pfeil)) {
                                    input.setSpielstein(curX,curY, MoeglicherStern.getInstance());
                                }
                            }

                            break;

                        case OST:
                            for (int curX = colums; curX < input.getBreite(); curX++) {
                                if (!(input.getSpielstein(curX,rows) instanceof Pfeil)) {
                                    input.setSpielstein(curX,rows, MoeglicherStern.getInstance());
                                }
                            }
                            break;

                        case SUEDOST:
                            for (int curX = colums, curY = rows; curX < input.getBreite() && curY < input.getHoehe(); curX++, curY++ ) {
                                if (!(input.getSpielstein(curX, curY) instanceof Pfeil)) {
                                    input.setSpielstein(curX,curY, MoeglicherStern.getInstance());
                                }
                            }
                            break;

                        case SUED:
                            for (int curY = rows; curY < input.getHoehe(); curY++) {
                                if (!(input.getSpielstein(rows, curY) instanceof Pfeil)) {
                                    input.setSpielstein(rows,curY, MoeglicherStern.getInstance());
                                }
                            }
                            break;

                        case SUEDWEST:
                            for (int curX = colums, curY = rows; curX >= 0 && curY < input.getHoehe(); curX--, curY++ ) {
                                if (!(input.getSpielstein(curX, curY) instanceof Pfeil)) {
                                    input.setSpielstein(curX,curY, MoeglicherStern.getInstance());
                                }
                            }
                            break;

                        case WEST:
                            for (int curX = colums; curX >= 0; curX--) {
                                if (!(input.getSpielstein(curX,rows) instanceof Pfeil)) {
                                    input.setSpielstein(curX,rows, MoeglicherStern.getInstance());
                                }
                            }
                            break;

                        case NORDWEST:
                            for (int curX = colums, curY = rows; curX >= 0 && curY >= 0; curX--, curY-- ) {
                                if (!(input.getSpielstein(curX, curY) instanceof Pfeil)) {
                                    input.setSpielstein(curX,curY, MoeglicherStern.getInstance());
                                }
                            }
                            break;
                    }


                }


            }

        }


        return input;
    }
}
