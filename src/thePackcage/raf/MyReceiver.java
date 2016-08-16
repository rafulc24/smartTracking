package thePackcage.raf;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class MyReceiver extends BroadcastReceiver
{
	
	/* method onReceive will called automatically 
    when the general class was called  */

	@Override 
	public void onReceive(Context context, Intent intent) 
	{    
		 
		//   Toast.makeText(context, "Receiver of Smart Tracking ", Toast.LENGTH_LONG).show();

	       
	
		Intent service2 = new Intent(context, Calenservice.class);
		context.startService(service2);
		   
	  
		Intent service1 = new Intent(context, nasserservice.class);
		context.startService(service1);
		  	 
	    		   
	} 
	 

	 
}

