package severeLobster.backend.spiel;

/**
 * StoppUhr Klasse zur Nachverfolgung der gespielten Zeit
 * @author fwenisch
 * @date	04.11.2012
 */
//TODO das reicht leider nicht aus, ein gespeichertes Spiel muss seine aktuelle laufzeit mitspeichern
public class StoppUhr 
{
	private  long lZeit;
	private  long lZeitStart;
	private boolean isStarted=false;

	public  void start()
	{    
		lZeitStart = System.currentTimeMillis();
		isStarted=true;
	}
	public void stop()
	{
		isStarted=false;
	}

	private   void update()
	{
		lZeit = ((System.currentTimeMillis() - lZeitStart)/1000);
	}

	public   long getZeit()
	{
		if(isStarted)
			update();
		return lZeit;
	}

}
