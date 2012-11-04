package severeLobster.backend.spiel;

import severeLobster.frontend.view.MainView;

public class StoppUhr {


    private  long lZeit;
    private  long lZeitStart;
        
    
    public  void start()
    {
        
        lZeitStart = System.currentTimeMillis();
    }
    
    private   void update()
    {
    	lZeit = ((System.currentTimeMillis() - lZeitStart)/1000);
    }
    
    public   long getZeit()
    {
    	update();
    	return lZeit;
    }

}
