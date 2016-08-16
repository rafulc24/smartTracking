package thePackcage.raf;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import thePackage.raf.R;



public class Message_MenuActivity extends Activity {

	TextView message;
	String value="";
	String whichActivity="";

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{

		setContentView(R.layout.activity_message_menu); 
		
		super.onCreate(savedInstanceState);  // implement from the oncreat class

		message = (TextView) findViewById(R.id.message);   
		

	 // when the user press on "help" or
	 // "contact" button , he will open
	 // this activity and the data
	 // that sent here will shown    
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			 value = extras.getString("message");
			 whichActivity=  extras.getString("whichActivity");
			 
		}
			
		message.setText(value);
    

    
		message.setTextColor(Color.WHITE);

	}
	
	
	// if we get the data from mainactivity so 
	// back to there else back to nextactivity 
		
	 @Override
	 public void onBackPressed() {
		 
		 finish();
		 Intent myIntent;
		 if (whichActivity.equals("mainActivity")){
			 myIntent = new Intent( this,  MainActivity.class);
			 startActivity(myIntent);
		 }
		 
		 else  if (whichActivity.equals("NextActivity")) {
			
			myIntent = new Intent( this,  NextActivity.class);
			startActivity(myIntent);
			 
		 }
		 
		 else
		 {
			 
			myIntent = new Intent( this,  Load_FileActivity.class);
			startActivity(myIntent);
		 }
			 
		 
		 
	}
			
	@Override
	protected void onPause() {
		
		super.onPause();
		System.exit(0);
	}
	
 
 }

