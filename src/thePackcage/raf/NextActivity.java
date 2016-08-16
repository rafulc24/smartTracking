	package thePackcage.raf;
	
	import java.io.File;
	import java.io.FileOutputStream;
	import java.io.OutputStreamWriter;
	import java.text.SimpleDateFormat;
	import java.util.ArrayList;
	import java.util.Calendar;
	import java.util.GregorianCalendar;
	import java.util.List;
	
	import thePackage.raf.R;
	
	import android.annotation.SuppressLint;
	import android.app.Activity;
	import android.content.Context;
	import android.content.Intent;
	import android.content.pm.ApplicationInfo;
	import android.os.Bundle;
	import android.os.Environment;
	import android.view.Menu;
	import android.view.MenuItem;
	import android.view.View;
	import android.widget.Button;
	import android.widget.ListView;
	import android.widget.Toast;
	
	public class NextActivity extends Activity 
	{
		
	    /** Called when the activity is first created. */
		//static List  <Student> students = new ArrayList  <Student>();
		public static List <Programs>  p1 =new ArrayList <Programs>(); // list of programs which contain the Attributes name and time
		public static List <Programs>  p2 =new ArrayList <Programs>(); // list of programs which contain the Attributes name and time
		 String name=""; // list of programs which contain the Attributes name and time
		 
	    Intent intent;
	    Button btntoday;
	    Button btnYesterday;
	    Button btnTowDaysAgo;
	    Button btnThreeDaysAgo;
	    Button btnFourDaysAgo;
	    Button btnFiveDaysAgo;
	    Button btnSixDaysAgo;
	    My_first_db md1;
	    ApplicationInfo  applicationInfo;
	    String getDetails;
	    ListView lv1;
	    Context context;
	
	   @Override
	    public boolean onCreateOptionsMenu(Menu menu)
		{
	
		// Inflate the menu; this adds items to the action bar if it is present.
	    getMenuInflater().inflate(R.menu.next, menu);
	    return super.onCreateOptionsMenu(menu); 
		
		}
	
	   
		@Override
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
	
			super. onOptionsItemSelected(item) ;
			
		    Intent myIntent;
		    myIntent = new Intent(this, Message_MenuActivity.class);
		    myIntent.putExtra("whichActivity", "NextActivity" ); 
		    
		    switch (item.getItemId())
		    {
		
		    	case R.id.contact:
			
		    		finish();
				 
				    myIntent.putExtra("message", getResources().getString(R.string.contact_details));
				    startActivity(myIntent);
	
				break;
				
			
		    	case R.id.help:
			
		    		finish();
			 
		    		myIntent.putExtra("message",  getResources().getString(R.string.help_NextActivity));
		    		startActivity(myIntent);
			
	    		break;
			
			
		    	case R.id.save:
			
			
					// write on SD card file data in the text box
		
					Calendar CurrentCal = GregorianCalendar.getInstance();
				  
					SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
					
					String StringCurrentCalCal = sdf2.format(CurrentCal.getTime());
					
		
			
					try
					{
						//Context.getPackageCodePath() 
						///data/data/"your package name"
						// location: sdcard/.SaveProjects"
						
						File folder = new File(Environment.getExternalStorageDirectory() + "/.SaveProjects"); 
						if (!folder.exists())
						{
						     folder.mkdir();
						}
						
						
						File myFile = new File(Environment.getExternalStorageDirectory() + "/.SaveProjects/" +StringCurrentCalCal);
						
				
						myFile.createNewFile();
						FileOutputStream fOut = new FileOutputStream(myFile);
						OutputStreamWriter myOutWriter = 
						new OutputStreamWriter(fOut);
						
						p1= md1.get_all("onDay");
						String information="";
						for (Programs pr1: p1){
							information+=pr1.getName() + "\n" + pr1.gettime() +"\n";
						}
						myOutWriter.append(information);
						//myOutWriter.append("papa");
		
						myOutWriter.close();
						fOut.close();
						
						Toast.makeText(getBaseContext(),
								"save the file "+ StringCurrentCalCal,
								Toast.LENGTH_SHORT).show();
								
					} 
					catch (Exception e)
					{
						Toast.makeText(getBaseContext(), "not work, check if your phone is disconnected from the usb ",
								Toast.LENGTH_LONG).show();
					}
			
				break;
			
			    
			    case R.id.load:
			    	
					intent = new Intent(this, Load_FileActivity.class);
					
					startActivity(intent);
				
		        break;
			
		    }
			    
		    return true;
		    
    	}
	   
	   
		@Override
		protected void onResume() 
		{
		   super.onResume();
		   Bundle extras = getIntent().getExtras();
		   if (extras != null   )
		   {
			   /*
			Toast.makeText(getBaseContext(),
					"RESUMEEE",
					Toast.LENGTH_SHORT).show();
					*/
			this.ActivityResult( extras);
	
		   }
	   
		}
		
		
	
	   @SuppressWarnings("static-access")
	   protected void ActivityResult(Bundle extras)
	   {
		   // 	if(resultCode == RESULT_OK) {
	      //  Toast.makeText(this, " ActivityResult ", Toast.LENGTH_LONG).show(); // For example

	   	  	String value = extras.getString("data");
		
			if (!value.equals("back")  )
			{

				
				name = extras.getString("name");
				String fixname="";
				int index=0;
				while (index<name.length())
				{
					
					if (name.charAt(index)=='-')
					{
						
						if (index< name.length()/2)
							fixname+= '/';
						else
							fixname+= ':';	
						
					}
					
					else
						fixname+= Character.toString(name.charAt(index));
					
					index++;
					
				}
					
				Intent intent = new Intent(NextActivity.this, ListViewActivity.class);
				intent.putExtra("whichday", value); //name of the file
				intent.putExtra("fixname", fixname); // the data of the file
			
				finish();
				startActivity(intent);
	        
			}

	   }
	   
	   

	   
	    @Override
	    public void onCreate(Bundle savedInstanceState)
	    {
			
	        super.onCreate(savedInstanceState);
	        
	    	// connect to the layout of this activity which define the design of this activity
	        setContentView(R.layout.layout_nextactivity);
	        
	        
			/* create new database or connect it if is exist, the name of the database is programss and the instance of the class My_first_db  extends SQLiteOpenHelper is md1*/
			md1 = new My_first_db(getApplicationContext(), "Programss", null, 1);
	
	   
			/* define m1 as a  instance of inner class my_listner that implements View.OnClickListener  */
			my_listner m1 = new my_listner();
	        
	        
			/* connect the items of the layout to the objects that exist in the code */
	
			  btntoday= (Button) findViewById(R.id.btnToday);
			  btnYesterday = (Button) findViewById(R.id.btnYesterday);
			  btnTowDaysAgo = (Button) findViewById(R.id.btnTowDaysAgo);
			  btnThreeDaysAgo= (Button) findViewById(R.id.btnThreeDaysAgo);
			  btnFourDaysAgo= (Button) findViewById(R.id.btnFourDaysAgo);
			  btnFiveDaysAgo= (Button) findViewById(R.id.btnFiveDaysAgo);
			  btnSixDaysAgo= (Button) findViewById(R.id.btnSixDaysAgo);
			  
	      
	      /* now if the user press on specific button
	       * the button would send 
	       * to the method onClick that found 
	       * in the class my_listner because m1 is 
	       * an instance of this class  */
	      
			  btntoday.setOnClickListener(m1);
			  btnYesterday.setOnClickListener(m1);
			  btnTowDaysAgo.setOnClickListener(m1);
			  btnThreeDaysAgo.setOnClickListener(m1);
			  btnFourDaysAgo.setOnClickListener(m1);
			  btnFiveDaysAgo.setOnClickListener(m1);
			  btnSixDaysAgo.setOnClickListener(m1);
		
	      
			
			 //   service1 = new Intent(this, nasserservice.class);
			//	startService(service1); 

	    }
	    

	    @Override
	    public void onStart() 
	    {
	       super.onStart();
	     
	    } 
	
	       
 
	    @Override
	    public void onBackPressed() 
	    { 
		//if the user see the result of some day and want to back to menu
	
			System.exit(0);
	    		
	    }
	    
	    
	
	    
	    /*  OnClickListener :
	     *  If the user clicks on one of the buttons,
	     *  this inner class will be called
	     */
	    
	    public class my_listner implements View.OnClickListener 
	    {
	    
	/*  onClick:
	 *  this method is belong to the class OnClickListener
	 *  so we can implement from it if we type: @Override
	 *  and it helps to the programer because he can use the
	 *  features and the actions of this method
	 *   according to his plan
	 */
	    
	
			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) 
			{ // get the specific button to View arg0
				// because view is a general item
				// TODO Auto-generated method stub
				int id = arg0.getId();  // get the id of the button
				
				// switch case is a CONVENIENCE way to write ( in this case for example)
				// if (id ==  R.id.btnToday )
				// else if ( id ==R.id.btnYesterday)
				
				
				switch (id) 
				{
	
					case R.id.btnToday: //  if (id ==  R.id.btnToday ) or other words 
									   // if the user press on the button btnToday,
									   // commit the lines that are below until the
									  // break will appear
			
						 intent = new Intent(NextActivity.this, ListViewActivity.class);
						 intent.putExtra("whichday", "0");
						 finish();
						
						 startActivity(intent);
				 
					
					break; // if the condition was met, break and get out from the switch case
				
	
					case R.id.btnYesterday:  // if the user press on the button btnYesterday
	
						 finish();
						 intent = new Intent(NextActivity.this, ListViewActivity.class);
									intent.putExtra("whichday", "1");
									startActivity(intent);
								 
					break;
				
				
					case R.id.btnTowDaysAgo: // if the user press on the button btnTowDaysAgo
		
						finish();
						intent = new Intent(NextActivity.this, ListViewActivity.class);
									intent.putExtra("whichday", "2");
									startActivity(intent);
								 
					break;
						
					
					case R.id.btnThreeDaysAgo: // if the user press on the button btnThreeDaysAgo
						
						 finish();
						 intent = new Intent(NextActivity.this, ListViewActivity.class);
								intent.putExtra("whichday", "3");
								 startActivity(intent);
								 
					break;
						
						
					case R.id.btnFourDaysAgo: // if the user press on the button btnFourDaysAgo
						
						 finish();
						 intent = new Intent(NextActivity.this, ListViewActivity.class);
								intent.putExtra("whichday", "4");
								 startActivity(intent);
								 
					break;
						
						
					case R.id.btnFiveDaysAgo: // if the user press on the button btnFiveDaysAgo
						
						 finish();
						 intent = new Intent(NextActivity.this, ListViewActivity.class);
								intent.putExtra("whichday", "5");
								 startActivity(intent);
								 
					break;
						
					case R.id.btnSixDaysAgo: // if the user press on the button btnSixDaysAgo
						
						 finish();
						 intent = new Intent(NextActivity.this, ListViewActivity.class);
								intent.putExtra("whichday", "6");
								 startActivity(intent);
								 
					break;
						
	
				
					
				}
				
				
			}
	    	
	
	    }

	    /*
		@Override
		protected void onPause() 
		{
			super.onPause();
			System.exit(0);
		}
		
	*/
	     
	}
	    
