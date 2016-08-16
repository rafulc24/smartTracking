
package thePackcage.raf;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import thePackage.raf.R;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Load_FileActivity extends ListActivity {
	
	/**
	 * The file path if we get it from extra intent
	 */
	
	public final static String EXTRA_FILE_PATH = "file_path";
		
	/**
	 * Sets whether hidden files should be visible in the list or not
	 */
	public final static String EXTRA_SHOW_HIDDEN_FILES = "show_hidden_files";

	/**
	 * The allowed file extensions in an ArrayList of Strings
	 */
	public final static String EXTRA_ACCEPTED_FILE_EXTENSIONS = "accepted_file_extensions";
	
	/**
	 * The initial directory which will be used if no directory has been sent with the intent 
	 */
	private final static String DEFAULT_INITIAL_DIRECTORY = Environment.getExternalStorageDirectory() + "/.SaveProjects/" ;

	boolean deleteClick =false;
	View tempView=null;
	File newFile=null;
	Intent returnIntent ;
	protected File mDirectory;
	protected ArrayList<File> mFiles;
	protected FilePickerListAdapter mAdapter;
	protected boolean mShowHiddenFiles = false;
	protected String[] acceptedFileExtensions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// the intent that will return to 
		// nextActivity with some data.
		// the sending of the intent will be in the 
		//Continuous , now is the initialize

		returnIntent =  new Intent(this,NextActivity.class);
		
		
		// Set the view to be shown if the list is empty
		/*
		 * LAYOUT_INFLATER_SERVICE:
		 * Use with getSystemService(Class) to retrieve a LayoutInflater 
		 * for inflating layout resources in this context.
		 * inflator Instantiates a layout XML file into its corresponding View objects. 
		 * Instantiates= create mofa (object), 
		 * and after this i can use the method that i need (like inflate)
		 */
		LayoutInflater inflator = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		
		// Generally, the default layout be the empty message , but if this activity
		//will find files, it changes to file_picker_list_item
		
		/*
		 * inflator.inflate= Inflate a new view hierarchy from the specified xml resource.
		 *  Throws InflateException if there is an error.
		 */

		View emptyView = inflator.inflate(R.layout.file_picker_empty_view, null);
		((ViewGroup)getListView().getParent()).addView(emptyView);
		getListView().setEmptyView(emptyView);
		

		// Set initial directory
		mDirectory = new File(DEFAULT_INITIAL_DIRECTORY);
		
		// Initialize the ArrayList
		mFiles = new ArrayList<File>();
		
		// Set the ListAdapter
		mAdapter = new FilePickerListAdapter(this, mFiles);
		
		//Provide the cursor for the list view. 
		setListAdapter(mAdapter);
		
		
		/*
		 * 
		 * 
		 * less important is only if we get 
		 * information from other activity 
		 * 
		 * 
		 * 
		 */
		
		
		// Initialize the extensions array to allow any file extensions
		acceptedFileExtensions = new String[] {};
		
		// Get intent extras
		if(getIntent().hasExtra(EXTRA_FILE_PATH)) {
			mDirectory = new File(getIntent().getStringExtra(EXTRA_FILE_PATH));
		}
		
		// if there are hiden files so dont show theme
		//because it is defined in the top
		if(getIntent().hasExtra(EXTRA_SHOW_HIDDEN_FILES)) {
			mShowHiddenFiles = getIntent().getBooleanExtra(EXTRA_SHOW_HIDDEN_FILES, false);
		}
		
		
		if(getIntent().hasExtra(EXTRA_ACCEPTED_FILE_EXTENSIONS)) {
			/*
			 * Parameters
			 name  The name of the desired item. 
			   Returns
			the value of an item that previously 
			added with putExtra() or null if no ArrayList value was found.
			 */
			ArrayList<String> collection = getIntent().getStringArrayListExtra(EXTRA_ACCEPTED_FILE_EXTENSIONS);
			acceptedFileExtensions = (String[]) collection.toArray(new String[collection.size()]);
			
		}
		

	}


	@Override
	protected void onResume() {
		//   Toast.makeText(this, " onResume() ", Toast.LENGTH_LONG).show(); // For example

		refreshFilesList();
		super.onResume();
	}

	/**
	 * Updates the list view to the current directory
	 */
	protected void refreshFilesList() {
		// Clear the files ArrayList
		mFiles.clear();
		
		// Set the extension file filter which actually set 
		// the files that have end with . and kind of format
		// or get also the directories
		
		// Get the files in the directory
		File[] files = mDirectory.listFiles();
		if(files != null && files.length > 0) {
			for(File f : files) {
				if(f.isHidden() && !mShowHiddenFiles) {
					// Don't add the file
					continue;
				}
							
				// Add the file the ArrayAdapter
				mFiles.add(f);
				
			}
			/*
			 * The root interface in the collection hierarchy. A collection represents a group of objects, 
			 * known as its elements. Some collections allow duplicate
			 *  elements and others do not. Some are ordered and others unordered.
			 *   The JDK does not provide any direct implementations of this interface:
			 *    it provides implementations of more specific subinterfaces like Set and List.
			 *     This interface is typically used to pass collections around and manipulate them 
			 *     where maximum generality is desired.
			 */
			Collections.sort(mFiles, new FileComparator());
		}
		
		/*
		 * untill now we update the list but now
		 * we have to update the layout to display
		 * the update list
		 * 
		 */
		mAdapter.notifyDataSetChanged();
	}

	/*
	 * A comparison function, which imposes a total ordering on some collection of objects.
	 *  Comparators can be passed to a sort method (such as Collections.sort or Arrays.sort)
	 *   to allow precise control over the sort order. 
	 *   Comparators can also be used to control the order of certain data structures 
	 *   (such as sorted sets or sorted maps), or to provide an ordering for 
	 *   collections of objects that don't have a natural ordering.
	 */

	private class FileComparator implements Comparator<File> {
		@Override
		public int compare(File f1, File f2) {
			if(f1 == f2) {
				return 0;
			}

			return f1.getName().compareToIgnoreCase(f2.getName());
			
			/*
			 * example to understand it:
			 * 
			 * public class Test {

			 
			public static void main(String args[]) {
			  String str1 = "Strings are immutable";
			  String str2 = "Strings are immutable";
			  String str3 = "Integers are not immutable";

			  int result = str1.compareToIgnoreCase( str2 );
			  System.out.println(result);
			  
			  result = str2.compareToIgnoreCase( str3 );
			  System.out.println(result);
			 
			  result = str3.compareToIgnoreCase( str1 );
			  System.out.println(result);
			}
			}
					 * 
					 * This produces the following result:

			0
			10
			-10
			 */
			
		}
	}






	/*
	 * create when execute this line in oncreate:
	 * mAdapter = new FilePickerListAdapter(this, mFiles);
	 */
	private class FilePickerListAdapter extends ArrayAdapter<File> {

	
		private List<File> mObjects;

		public FilePickerListAdapter(Context context, List<File> objects) {
			// android.R.id.text1 is default textview from  
			//android.R is:
			//(Android install path)\android-sdk-windows\platforms\android-version\data\res
			
			// file_picker_list_item: the layout of the list with contain 
			// linearlayout and textview within it
			super(context, R.layout.file_picker_list_item, android.R.id.text1, objects);
			mObjects = objects;
		}
		
		
		/*
		 getView() of ArrayAdapter is called multiple times....

		  as an when the new row is added...
		  you scroll up and scroll down the list view....
		  when the list is notfiedchanged, in our case  mAdapter.notifyDataSetChanged();
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			

			// position is the number of the item
			// of the list (stars from 0 )

			//convertView get the view of the item/s
			// (which contain imageView and TextView)
			// that will shown in the list
			
			View row = null;
			
			// be in the first time and convertView is null
			if(convertView == null) { 
				LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.file_picker_list_item, parent, false);
				

				// if is not the first item, so 
				// attach the previous items to the view			
							
			
			}
			
			else {
				row = convertView;
			}

			
			File object = mObjects.get(position);

			ImageView imageView = (ImageView)row.findViewById(R.id.file_picker_image);
			TextView textView = (TextView)row.findViewById(R.id.file_picker_text);
			// Set single line
			textView.setSingleLine(true);
			textView.setText(object.getName());
			
		
			if(object.isFile()) {
				// Show the file icon
				imageView.setImageResource(R.drawable.file);
			} else {
				// Show the folder icon
				imageView.setImageResource(R.drawable.folder);
			}
			
			return row;
		}

	}





	 // if the user lock the screen or exit
	 //from the app, it will be destroyed


	@Override
	public void onBackPressed() {

		
		super.onBackPressed();

		returnIntent.putExtra("data", "back");
		
		// Finish the activity and start NextActivity
		// and send it the string back to recognize
		// that the user does not choose any file in the end


		finish();

		startActivity ( returnIntent );
		
		
	}

	@SuppressLint("NewApi")
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		
		if (tempView==null){
			
			v.setBackgroundColor(Color.parseColor("#C0C0C0"));
			newFile = (File)l.getItemAtPosition(position);

			tempView=v;
		}
		
		else{
			
			if (tempView==v){
				newFile = null;
				v.setBackgroundColor(Color.WHITE);
				tempView=null;
			}
			else
			{
				tempView.setBackgroundColor(Color.WHITE);
				v.setBackgroundColor(Color.parseColor("#C0C0C0"));
				newFile = (File)l.getItemAtPosition(position);
				tempView=v;
			}
			
		}
		

		//newFile.delete();
		//id = (long)l.getSelectedItemId();
		

		//Read text from file
		

		
		/*
		if(newFile.isFile()) {
			// Set result

		
			// Finish the activity
			// send the text of the file
			returnIntent.putExtra("data", text.toString());
			// send the name of the file
			returnIntent.putExtra("name", newFile.getName());
			startActivity ( returnIntent );
		
			finish();	
		} 
		*/
		
		super.onListItemClick(l, v, position, id);
	}




   @Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.load, menu);
	   
		return super.onCreateOptionsMenu(menu); 
	}

   
   @Override
	public boolean onOptionsItemSelected(MenuItem item) {

		super. onOptionsItemSelected(item) ;
	
	
		switch (item.getItemId()) {
			
			case R.id.Delete:

				if (newFile!=null){
				newFile.delete();
				Toast.makeText(this, " File deleted successfully ", Toast.LENGTH_LONG).show(); // For example

				//mFiles.remove(newFile);
				
				newFile=null;
				tempView=null;
				
				Intent intent = getIntent();
				finish();
				startActivity(intent);
				
				}
				
				// this.refreshFilesList();
				//	mAdapter.notifyDataSetChanged();
		
			break;
		
		
			case R.id.DeleteAll:
			
				/*
				File[] files = mDirectory.listFiles();
				if(files != null && files.length > 0) {
					for(File f : files) 
					f.delete();
					
					}
					*/
				Toast.makeText(this, " All files deleted successfully ", Toast.LENGTH_LONG).show(); // For example

				newFile=null;
				tempView=null;
				int i=0;

				while (i<mFiles.size())
					mFiles.get(i++).delete();

				mFiles.clear();


				this.refreshFilesList();


			break;
		
		
			case R.id.ChooseFile:

				if (newFile!=null){

					//Read text from file
					StringBuilder text = new StringBuilder();

					try {
						/*
						 * br its kind of pointer on the file
						 */
						
						
						BufferedReader br = new BufferedReader(new FileReader(newFile));
						String line;

						// while we dont arrive to the end of file
						// we pass every line of the file as a String to line
						// and add it to text

						
						while ((line = br.readLine()) != null) {
					  
							 text.append(line);
							text.append('\n');
						}
						
						br.close();
					}
					catch (IOException e) {
						//You'll need to add proper error handling here
						   Toast.makeText(this, " IOException e ", Toast.LENGTH_LONG).show(); // For example

					}
					
					
					if(newFile.isFile()) {
						// Set result

					
						// Finish the activity
						// send the text of the file
						returnIntent.putExtra("data", text.toString());
						// send the name of the file
						returnIntent.putExtra("name", newFile.getName());
						startActivity ( returnIntent );
					
						finish();	
					} 
				
				}
				
		  
			break;
			
			
			case R.id.help:
			
				Intent myIntent;
				myIntent = new Intent(this, Message_MenuActivity.class);
				myIntent.putExtra("whichActivity", "Load_FileActivity" ); 

				 finish();
				 
				myIntent.putExtra("message", getResources().getString(R.string.help_LoadActivity));
				startActivity(myIntent);
				
			break;
			
		
		}	
	
		return true;
	
	}

}
