package thePackcage.raf;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.IBinder;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class Calenservice extends Service {


    private static long UPDATE_INTERVAL = 20*1000;  
    private static Timer timer = new Timer(); 
    ApplicationInfo applicationInfo;
    List<Programs> l1 = new ArrayList<Programs>();
    My_first_db md1; //My_first_db is a class that extends from SQLiteOpenHelper


	 public static String StringCurrentCalCal;
	 public static String StringfirstDate;
	 

    @Override 
    public IBinder onBind(Intent intent) {
      // TODO Auto-generated method stub 
        return null; 
    } 
 
    @Override 
    public void onCreate(){ 
        super.onCreate(); 
     
        //getApplicationContext() get the current process of the app
        //and its means that the database will also run in the current process
    	 md1 = new My_first_db(getApplicationContext(), "Programss", null, 1); //  יצירת דאטה בייס חדש מסוג מחלקת מיי דיבי בשם אמדי1 ולדאטה בייס עצמו קראים פרוגראמס

        
    	//Then in the new Activity, retrieve those values:
        _startService(); 
 
    }    
 
    
    /*
     * 
     * public void scheduleAtFixedRate (TimerTask task, long delay, long period) 
Added in API level 1
Schedule a task for repeated fixed-rate execution after a specific delay has passed.

Parameters
task  the task to schedule. 
delay  amount of time in milliseconds before first execution. 
period  amount of time in milliseconds between subsequent executions. 

     */
    
    private void _startService() 
    {       
        timer.scheduleAtFixedRate(    
 
                new TimerTask() {
 
                	/*
                	*  if we create new timerTask we (non-Javadoc)
                	*  have to run the srvice every 
                	*  preiod of time so we do it 
*/
                	
                    public void run() { 
 
                      doServiceWork(); 
 
                    } 
                }, 0 ,UPDATE_INTERVAL);
   	
   
    } 
 
    @SuppressLint({ "ServiceCast", "SimpleDateFormat" })
	private void doServiceWork() 
    {  
    	
    	/* now we create tow instances of class
         * Calendar and the 
         * GregorianCalendar.getInstance()
         * means that they get the currently
         * time, Considering with the appropriate
         * region and the appropriate format 
         * of the date (dd/MM/yyyy)
         * 
         *   
         *  */

    	   Calendar CurrentCal = GregorianCalendar.getInstance();
           Calendar FirstCal = GregorianCalendar.getInstance();
 
 
     	int whichday=  md1.get_whichday();
     	
           
     	/*
     	 * the reason that we convert the two date to string
     	 * and convert the string back to the date , is because we
     	 * want the format of the date only with day month and year
     	 * which brings accurate results.
     	 * 
     	 */
           
     	SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
     
     	
    	StringCurrentCalCal = sdf2.format(CurrentCal.getTime());
     	
		try {
			
			CurrentCal.setTime(sdf2.parse(StringCurrentCalCal));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 StringfirstDate = md1.get_firstDate();
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
		try {
			FirstCal.setTime(sdf1.parse(StringfirstDate));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	/*
	 we have to check if the phone was off more then one day and of course not turn off more then a week so
	*/
		
		
	 	boolean ThereIsDilug=false;
	 	int dilug=0;
	 	long day= 1000*60 * 60 *24 ;
	 	
	 	
		if (CurrentCal.getTimeInMillis() -  FirstCal.getTimeInMillis() >  day ){
			
			dilug= (int) (long) ( ( CurrentCal.getTimeInMillis() - FirstCal.getTimeInMillis() ) /  day ); 
			dilug-=1;
			ThereIsDilug=true;
		}
		
		
	 	
	 	if (CurrentCal.getTimeInMillis() != FirstCal.getTimeInMillis()){
		
			switch (whichday + dilug){

				case 0:	case 1:case 2: case 3: case 4: case 5: // until 5 cause 5daysago pass data to 6daysago  
					
					int i;
					
					i = ++whichday;

						
					while ( i> 0 ){
						
						
						if ( ThereIsDilug){
						
							l1=md1.get_all_dayago(--i);
							
							md1.insert_dayago(l1, ++i+dilug);
						
						}
						
						else{
						
						l1=md1.get_all_dayago(--i);
						
						md1.insert_dayago(l1, ++i);
						
						}
						
						i--;
						
					}
					
					ThereIsDilug=false;

					md1.insert_tableCalen(   StringCurrentCalCal , whichday+dilug ); // if dilug is 0 so it will not add any value to whichday (better english: It does not add anything )
					
				break;
				
				
				default:
				//the week is over clear the tables
			
					whichday=0;
					md1.insert_tableCalen(  StringCurrentCalCal , whichday);
			
					md1.deletetable("onDay");
				
					i=1;
				 
					while (i< 7){
						
						md1.delete_PastTables(i);
						i++;
					}
		
				break;
				
				
			}			
		
		}
	 	
	 	md1.close();
		
	}
		

 
    private void _shutdownService() 
    { 
        if (timer != null) timer.cancel();
      	 Toast.makeText(this, "Timer stopped..." , Toast.LENGTH_LONG).show();

      
    } 
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }
   
    
    
}


 

