package infrastructure.components;

/**
 * StoppUhr Klasse zur Nachverfolgung der gespielten Zeit
 * 
 * @author fwenisch, Lars Schlegelmilch
 */
public class StoppUhr {
    private long lZeit;
    private long lZeitStart;
    private long lZeitPause;
    private long lZeitGoOn = 0;
    private boolean isStarted = false;

    public void start() {
        lZeitStart = System.currentTimeMillis();
        isStarted = true;
    }

    public void pause() {
        if (isStarted) {
            lZeitPause = System.currentTimeMillis();
            isStarted = false;
        }
    }

    public void goOn() {
        if (!isStarted) {
            lZeitGoOn = System.currentTimeMillis() - lZeitPause;
            isStarted = true;
        }
    }

    public void stop() {
        isStarted = false;
    }

    private void update() {
        lZeit = ((System.currentTimeMillis() - lZeitStart - lZeitGoOn) / 1000);
    }

    public long getZeit() {
        if (isStarted)
            update();
        return lZeit;
    }

}
