package thePackcage.raf;


import java.util.Timer;
import java.util.TimerTask;
import android.app.ActivityManager;
import android.app.Service;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

public class nasserservice extends Service {
    private static long UPDATE_INTERVAL = 5*1000;  //default
 
    private static Timer timer = new Timer(); 
    public static final String PARAM_IN_MSG = "imsg";
    public static final String PARAM_OUT_MSG = "omsg";
    ApplicationInfo applicationInfo;
    String title;
    My_first_db md1;

	
	/*
	 * The TimerTask class represents a task to run at a specified time. 
	 * The task may be run once or repeatedly.
	 */
	TimerTask mTimerTask;

    
    /*
     * You can create your own threads, and communicate back
     *  with the main application thread through a Handler. 
     *  This is done by calling the same post or sendMessage
     *   methods as before, but from your new thread. 
     *   The given Runnable or Message will then be scheduled 
     *   in the Handler's message queue and processed when appropriate. 
     */
    private final Handler handler = new Handler();
    


    @Override 
    public IBinder onBind(Intent intent) {

    	return null;
    }
    
       
    

    @Override 
    public void onCreate(){ 
        super.onCreate(); 
    
     md1 = new My_first_db(getApplicationContext(), "Programss", null, 1); //  יצירת דאטה בייס חדש מסוג מחלקת מיי דיבי בשם אמדי1 ולדאטה בייס עצמו קראים פרוגראמס

     
     doTimerTask();
	
 
    }    
 
    public void doTimerTask(){
    	 
		mTimerTask = new TimerTask() {
		public void run() {
					
			/*
			 * A Handler is a utility class that facilitates interacting with a Looper—mainly by posting messages 
			 * and Runnable objects to the thread's MessageQueue.
			 *  When a Handler is created, it is bound to a specific Looper 
			 *  (and associated thread and message queue)
			 *  
			 *  
		*  public final boolean post (Runnable r) 
		Causes the Runnable r to be added to the message queue. 
		The runnable will be run on the thread to which this handler is attached.

		Parameters
		r  The Runnable that will be executed. 

		Returns
		Returns true if the Runnable was successfully placed in to the message queue.
		 Returns false on failure, usually because the looper
		  processing the message queue is exiting.
							 */
							 
				handler.post(new Runnable() {
					public void run() {

						try { 

				   
							 boolean update;
							
							// getApplicationContext();
							 
							 /*
							  * public static final String ACTIVITY_SERVICE 
								Added in API level 1 
								Use with getSystemService(Class) to retrieve a ActivityManager for
								interacting with the global system state.

							  * 
							  */
							ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);	  
						 
						  
						   @SuppressWarnings("deprecation")
						   
						   /*
							* (1) :
							* maxNum  The maximum number of entries to return in the list. 
							* The actual number returned may be smaller, 
							* depending on how many tasks the user has started.
							* 
							*  get(0) = get the foreground task
							*/
						   
							RunningTaskInfo foregroundTaskInfo = activityManager.getRunningTasks(1).get(0);
						   
						   /*
							* Shorten the whole name of the activity
							* to the short name (the regular name
							* without the path and the source)
							* 
							*/
						   
							String taskName = foregroundTaskInfo.baseActivity.toShortString();	
													   

							int lastIndex = taskName.indexOf("/");
							if(-1 != lastIndex)
							{
								taskName = taskName.substring(1,lastIndex);
							}
							
						   /*
							* Return PackageManager instance to find global package information. 
							*/
							
						   PackageManager packageManager = getPackageManager();
							   
							   
						   try 
						   {
							   
							   /*
								* get the whole name of the app
							   with help of the list of the packages
							   and the name of appropriate activity
							   that adjust to the right package
								* 
								*/
								   
							   applicationInfo = packageManager.getApplicationInfo(taskName, 0);
								   
								// get the label of the app (the name of the app)
							   title = (String)packageManager.getApplicationLabel(applicationInfo) ;
				  
						   
								update= md1.updateprogram("onDay",title);
							   
								if (!update){

									Programs program = new Programs(title, 5*1000); // קבלת הנתונים מהמשתמש והכנסתם לסוג התוכנית הזאת
									md1.add_Program(program, "onDay"); 
						
								}
								  
						   }
						   
							  
						   catch (final NameNotFoundException e) {
							 
							 /*
							  * when the name of the task not appropriate to any
							  * package or application 
							  */
						
						   }
							
						 
								
								
						} 
						
						catch (Exception e) {
							
	
						}
    	                         	                      
    	        
					}
				});
			}
    	        
		};
 
    	 
		// public void schedule (TimerTask task, long delay, long period) 
		timer.schedule(mTimerTask, 0, UPDATE_INTERVAL);  // 

	}
 

  

 
    private void _shutdownService() 
    { 
        if (timer != null) timer.cancel();
      	 Toast.makeText(this, "Timer stopped..." , Toast.LENGTH_LONG).show();

      
    } 
    
    @Override
    public void onDestroy(){
    	 super.onDestroy();
    	 _shutdownService();
    }
    
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }
   
    
    
}


 

