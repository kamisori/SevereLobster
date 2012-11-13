package infrastructure.components;

/**
 * Koordinatenspeicher
 *
 * @author Lars Schlegelmilch
 */
public class Koordinaten {

    private int x;
    private int y;

    public Koordinaten(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
