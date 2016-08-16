package thePackcage.raf;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import thePackage.raf.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewActivity extends Activity {

	String value;
	My_first_db md1;
	List <Programs>  p1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		   
	    //   Toast.makeText(this, " ListViewActivity ", Toast.LENGTH_LONG).show(); // For example

		 md1 = new My_first_db(getApplicationContext(), "Programss", null, 1); 
		 
		// enter to the GetSearchResults() method and this method return list of ItemDetails calls data

        ArrayList<ItemDetails> data = GetSearchResults(); 
    
        if (data.size()!=0){
        	
        	//if there is data in this list, so get the layout of the list  
        	//and send it to ItemListBaseAdapter 
        	//else take the layout of empty list message 
        	 
        final ListView lv1 = (ListView) findViewById(R.id.listV_main);
        
        lv1.setAdapter(new ItemListBaseAdapter(this, data));
        }
        
        else
    
        setContentView(R.layout.empty_data_programs);
        
        
    }
	
	//when the user press on back button it
	//will return to NextActivity
	
	@Override
	public void onBackPressed() {
	
		super.onBackPressed();
		Intent returnIntent;
		if (getIntent().getStringExtra("fixname")==null){
			
		 returnIntent =  new Intent(this,NextActivity.class);
		returnIntent.putExtra("data", "back");
		}
		
		else
		returnIntent =  new Intent(this,Load_FileActivity.class);

		
		finish();
	
	 startActivity ( returnIntent );
		
		
	}
    
    @SuppressWarnings("null")
	@SuppressLint("NewApi")
	private ArrayList<ItemDetails> GetSearchResults(){
		
    	ArrayList<ItemDetails> results = new ArrayList<ItemDetails>();
		p1 =new ArrayList <Programs>(); // list of programs which contain the Attributes name and time
    	 
    	// take the extra string that sent from NextActivity 
    	// if the user chose file in Load_File_Activity so whichday will get the data
    	// of this date, otherwise ,take the information 
    	//from the database according to the value of whichday  
    	 
		if (getIntent().getStringExtra("whichday") != null )
		{
		   
			value = getIntent().getStringExtra("whichday");

			if (value.length()==1){
    	
				//,take the information 
				//from the database according to the value of whichday  
						
				if (value.equals("0"))
					p1= md1.get_all("onDay");
				
				else{
					int num= (Integer.parseInt(value));
					p1= md1.get_all_dayago(num);
					
				}
				
				for (Programs pr1: p1){
					
					// put the information in the item_datails
					// and add it to the list
					
					ItemDetails item_details = new ItemDetails();

					item_details.setName(pr1.getName());
					Long millis = pr1.gettime();
					String clock=String.format("%02d:%02d:%02d",TimeUnit.MILLISECONDS.toHours(millis),
					TimeUnit.MILLISECONDS.toMinutes(millis)-(TimeUnit.MILLISECONDS.toHours(millis)*60),TimeUnit.MILLISECONDS.toSeconds(millis)-(TimeUnit.MILLISECONDS.toMinutes(millis)*60));
						
					item_details.setTime(clock);

					item_details.setName_title("Name");
					item_details.setTime_title("Time");
					
						item_details.setTitle("");

					results.add(item_details);
					

				}
				
			}
		
			else{
				
				// here we add to the first item of the list , the 
				// date and the time of the chosen file
				// and add it to the title arttibute
				// we get the data from extra string
				// fixname
				
				ItemDetails item_details = new ItemDetails();

				item_details.setName("" );
				item_details.setTime("");
				item_details.setName_title("");
				item_details.setTime_title("");
				// the name of the file
				item_details.setTitle(getIntent().getStringExtra("fixname"));


				results.add(item_details);

				 // now we take the information of the getStringExtra
				 // reminder: value = getIntent().getStringExtra("whichday");   
				 // and put it to name and time and
				 // add it to the list 				
						
					
				Scanner scanner = new Scanner(value);
				while (scanner.hasNextLine()) {

				   item_details = new ItemDetails();
				   
				   
					String line = scanner.nextLine().toString();

					item_details.setName(line);
					
					String time = scanner.nextLine().toString();
					//convert string to long
					Long millis = Long.valueOf(time).longValue();
					
					String clock=String.format("%02d:%02d:%02d",TimeUnit.MILLISECONDS.toHours(millis),
					TimeUnit.MILLISECONDS.toMinutes(millis)-(TimeUnit.MILLISECONDS.toHours(millis)*60),TimeUnit.MILLISECONDS.toSeconds(millis)-(TimeUnit.MILLISECONDS.toMinutes(millis)*60));
					item_details.setTime(clock);

					//set titles
					item_details.setName_title("Name");
					item_details.setTime_title("Time");
					item_details.setTitle("");

					results.add(item_details);

				}
				
				scanner.close();
					
				
			}

    	
   
		}
    	
		return results;
    }
    

	 // if the user lock the screen or exit
	 //from the app, it will be destroyed
		
	@Override
	protected void onPause() {
		
		super.onPause();
		System.exit(0);
	}
}
