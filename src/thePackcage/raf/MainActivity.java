package thePackcage.raf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * The JavaMailTM API provides classes that model a mail system. 
 * The javax.mail package defines classes that are common to all mail systems.
 *  The javax.mail.internet package defines classes that are specific 
 *  to mail systems based on internet standards such as MIME, SMTP, POP3, and IMAP.
 *   The JavaMail API includes the javax.mail package and subpackages.
 */
import javax.mail.Authenticator; // because we need to create  general validation 
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication; // we need to validate the user mail and password
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import thePackage.raf.R;


@SuppressLint("SimpleDateFormat")
public class MainActivity extends Activity 
{

	int temp;
	String FILENAME = "hello_16545345345343k1";
    FileOutputStream fos;
    FileInputStream fis;
    private PendingIntent pendingIntent;
	Button btnLogin;
	Button btnForgotDetails;
	EditText name;
	EditText PassWord;
	EditText ConfirmPassWord;
	TextView error;
	TextView Email;
	EditText  EmailEdit;
	My_first_db md1;
	TextView textConfirmPassWord;
	List<String> l1 = new ArrayList <String>();  
	Session session = null;
	//ProgressDialog pdialog = null;
	Context context = null;
	EditText reciep, sub, msg;
	String rec, subject, textMessage , resultString;
	

   @Override
	public boolean onCreateOptionsMenu(Menu menu) {

	   /*
	   File file= new File (FILENAME);
		try {
		if (	file.createNewFile())
		"first time";
		
		else
			"no"
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		
		that is what i have to do instead all the input stream and the ect
	   */
		   
		   
		   
		// Inflate the menu; this adds items to the action bar if it is present.
	    getMenuInflater().inflate(R.menu.main, menu);
		   
	    return super.onCreateOptionsMenu(menu); 
	}
	
	   
   @Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Intent myIntent;
		myIntent = new Intent(MainActivity.this, Message_MenuActivity.class);
		myIntent.putExtra("whichActivity", "mainActivity" ); 

		    
		super. onOptionsItemSelected(item) ;
		
		switch (item.getItemId()) {
		
			case R.id.contact:
				//write your code here

				 finish();
					myIntent.putExtra("message", getResources().getString( R.string.contact_details ));
					 startActivity(myIntent);

				
			break;
				
				
			case R.id.help:
				
				 finish();
					myIntent.putExtra("message", getResources().getString( R.string.help_MainActivity ));
					 startActivity(myIntent);
				
			break;

		}
	 

		return true;
	
	}
	   



  
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		Log.i("tagname","string you want to execute");
		

		/* create new database or connect it if is exist, the name 
		 * of the database is programss and the instance
		 *  of the class My_first_db  extends SQLiteOpenHelper is md1*/
		 md1 = new My_first_db(getApplicationContext(), "Programss", null, 1); 
		 System.out.println("b");
		// connect to the layout of this activity which define the design of this activity
		setContentView(R.layout.layout_mainactivity); 
		
		super.onCreate(savedInstanceState);  // implement from the oncreate class

		context=this; //get the context of this app
		
		
		/* connect the items of the layout to the objects that exist in the code */
		
		btnLogin= (Button) findViewById(R.id.btnLogin);
		name= (EditText) findViewById(R.id.editTextName);
		PassWord= (EditText) findViewById(R.id.editTextPassword);
		ConfirmPassWord= (EditText) findViewById(R.id.editTextConfirmPassword);
		error= (TextView) findViewById(R.id.textError);
		textConfirmPassWord = (TextView) findViewById(R.id.textConfirmPassword);
		EmailEdit = (EditText) findViewById(R.id.editTextEmail);
		Email = (TextView) findViewById(R.id.textEmail);
		btnForgotDetails =(Button) findViewById(R.id.btnForgetDetails) ;
		
		
	 /*
	  * if the file not exist so it will opened in the first time and it will be empty,
		actually we need to do it only in the case of the first time
	  */
		
		try {
			fos = openFileOutput(FILENAME,  MODE_APPEND | MODE_WORLD_READABLE); //open the file
			 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		try {
			fos.close();  //close it
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	   /*  fis now is a cursor on the file  */
		
		try {
			fis = openFileInput(FILENAME);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* now fis read the file and return the number of bytes in the folder and pass it to temp*/
		
		 try {
			
			temp= fis.read();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		   try {
				fis.close();   // close it
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
	   
	 
	 if (temp==-1){ // if is -1 so is the first time that we open the app casue there is not data in the file
 
		 
	    //   Toast.makeText(this, "its -1 its the first time ", Toast.LENGTH_LONG).show();
	
	       
	       /* open the file and write it data in order to know in the next time that is not the first time that the app is opened */
	       
			try {
				fos = openFileOutput(FILENAME,  MODE_APPEND | MODE_WORLD_READABLE);
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	    try {
			fos.write(" next time will not be the first time".toString().getBytes());  //write data

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	    
	    
	    
	    Calendar c1 = GregorianCalendar.getInstance();
	     
		String strdate = null;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		if (c1 != null) 
		strdate = sdf.format(c1.getTime());
	    
		
	        md1.drop_Login();
         	md1.drop_tableCalen();
	  		md1.droptable("onDay");
	  		
	  		md1.create_user(); //table login
	  		md1.create_tableCalen();
	  		md1.create_table("onDay");

	  		
	  		md1.insert_tableCalen( strdate, -1);
	       
	  		for (int i=1 ; i<7 ; i++){
	  			
	  			md1.drop_PastTables(i);
	  			md1.createPastTables(i);
	  		}
	  		
	
	       Intent myIntent2 = new Intent(MainActivity.this, MyReceiver.class);
	       
	/*
	 * A PendingIntent itself is simply a reference to a token
	 *  maintained by the system describing the original
	 *   data used to retrieve it. This means that, even if
	 *    its owning application's process is killed, the
	 *     PendingIntent itself will remain usable from other
	 *      processes that have been given it. If the creating application
	 *       later re-retrieves the same kind of PendingIntent 
	 *       (same operation, same Intent action, data, categories, and components, and same flags),
	 *        it will receive a PendingIntent representing the same token if that is still valid,
	 *         and can thus call cancel() to remove it. 
	 * 
	 */
	       
	        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent2,0);
	        
/*
 * The Alarm Manager holds a CPU wake lock as long as the alarm receiver's onReceive()
 *  method is executing. This guarantees that the phone will not sleep until you have finished 
 *  handling the broadcast. Once onReceive() returns, the Alarm Manager releases this wake lock. 
 * 
 */
	        
	        /*
		        * public static final String ALARM_SERVICE 
	               Use with getSystemService(Class) to retrieve a AlarmManager for
	               receiving intents at a time of your choosing.

		        * 
		        */   
	        
	        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
	        
	        /*
	         * public static final int RTC 
               Alarm time in System.currentTimeMillis() 
(wall clock time in UTC). This alarm does not wake the device up 
if it goes off while the device is asleep, 
it will not be delivered until the next time the device wakes up. 

	         * 
	         */
	        	        
	     alarmManager.set(AlarmManager.RTC, System.currentTimeMillis(), pendingIntent);
	           
	 }
	 
	 
	 /*
	 else{
	      
	      Toast.makeText(this, "its not the first time "+ temp, Toast.LENGTH_LONG).show();

	 }
	*/
	 
	 l1= md1.get_all_Login();

	// if the user already inserted his details

	 if (l1.size()!=0){
		 
		 btnLogin.setText("Login");
		 textConfirmPassWord.setVisibility(View.GONE);
		 ConfirmPassWord.setVisibility(View.GONE);
		 EmailEdit.setVisibility(View.GONE);
		 Email.setVisibility(View.GONE);
		 if (l1.get(3).toString().equals("1"))
		 btnForgotDetails.setVisibility(0);

		
	 }
	 
	 btnForgotDetails.setOnClickListener(new OnClickListener(){

			  @Override
			  public void onClick(View V) {
				  
					l1= md1.get_all_Login();
				  
					rec = l1.get(2); //email of reciper
					subject = "Hello User";// write the body of the message
				//  l1.get(0) is the name of the user
				//  l1.get(1) is the password of the user

					textMessage = "Your User name is " + l1.get(0)+ "\nyour password is " + l1.get(1) ;
					
					//smtp is a communication protocol that can help you to send mail messages
					//Settings of the Post of the message

					Properties props = new Properties();
					props.put("mail.smtp.host", "smtp.gmail.com");
					props.put("mail.smtp.socketFactory.port", "465");
					props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
					props.put("mail.smtp.auth", "true");
					props.put("mail.smtp.port", "465"); // 465 is the port of smtp of gmail
					
					 //session get the properties and the address of the 
			         //mail sender and the password and check if the client 
					// (gmail smtp) response to the request

					session = Session.getDefaultInstance(props, new Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication("smarttracking24@gmail.com", "EliavRefaelPro");
						}
					});
					
					 error.setVisibility(0);
					 resultString="";

					
					//pdialog = ProgressDialog.show(context , "", "Sending Mail...", true);
					
					RetreiveFeedTask task = new RetreiveFeedTask();
					task.execute();
				}
				
			  /*
			   * create new asynchrony task which take care to bulid
                and send the message.asyncTask use a thread and 
                 could run in the backGround

			   */
				class RetreiveFeedTask extends AsyncTask<String, Void, String> {

					@Override
					protected String doInBackground(String... params) {
						
						try{
							 
							 
							Message message = new MimeMessage(session);
							message.setFrom(new InternetAddress("smarttracking24@gmail.com"));
							message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rec));
							message.setSubject(subject);
							message.setContent(textMessage, "text/html; charset=utf-8");
							Transport.send(message);
							resultString="You got a meassge in your Email";

							
							 
						} catch(MessagingException e) {
							e.printStackTrace();
							resultString="Sorry a sending error occured \nplease try again or call us \nfor getting technical support";


						} catch(Exception e) {
							e.printStackTrace();
							
							resultString="Sorry a general error occured \nplease try again or call us \nfor getting technical support";


						}
						return null;
					}
					
					@Override
					protected void onPostExecute(String result) {
						// pdialog.dismiss();
						
						md1.update_forget_Login("0");
						error.setText(resultString);
						if (resultString.equals("You got a meassge in your Email") )
	                   btnForgotDetails.setVisibility(View.GONE);
						
					
					}
				}

			  
	   });
	   
	 

		btnLogin.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View V) {
				  
				  error.setVisibility(0);
				  
				  error.setText(""); // even if there is a mail message it will remove here
			
					  
				  if (  name.getText().toString().equals("") ) 
					  error.setText("Please enter your name \n");
				  
				  if (  PassWord.getText().toString().equals("")   ){
					  error.setText( error.getText().toString() + "Please enter the password \n");
					  
				  }
					  
				if (l1.size()==0){
					  
						  
					if ( ConfirmPassWord.getText().toString().equals("")  )
						error.setText(error.getText().toString()+ "Please enter confirmation password \n");
						
					if ( ! PassWord.getText().toString().equals(ConfirmPassWord.getText().toString()) )
						error.setText(error.getText().toString() + "Password and Confirmition Password \nare not same \n");
				  
					if (  EmailEdit.getText().toString().equals("")  )
						error.setText(error.getText().toString() + "Please enter your email \n");
			
					String emailExpression = "^[A-Z]{3,8}+[\\w]{0,8}@([\\w]+\\.)+[A-Z]{2,4}$";

						
						/* ^  At beginning of line. 
						 * about []: in those Brackets we can determine
						 * which characters the user can type 
						 * and sometimes determine the order of the characters
						 * 
						 * 
						 * \w ->   Any word character (a-z or A-Z or 0-9 )
						 * 
						 * 
						 * backslash character \ have to write like this: \\
						 * because the first \ its a special in string literal 
						 * we have to escape it with another \ 
						 * which gives us \\  (explanation of \\w)
						 * 
						 * @([\\w\\-]+\\.) -> first must appear @
						 * after this 
						 * 
						 * {n,m}  At least n but not more than m. (start from 1)
						 * example: com.org
						 * 
						 * $  At end of line. 
						 */
					

				
					CharSequence inputStr =  EmailEdit.getText().toString();
					boolean IsError=false;
					int index=0;
					
					while (index< inputStr.length() &&  inputStr.charAt(index++)!='@') ;
					

					while (index< inputStr.length() ){

						
						if ( inputStr.charAt(index)=='.')
							
							if (inputStr.length()-1 -index <2 || inputStr.charAt(index+1)=='.' || inputStr.charAt(index+2)=='.'){
								IsError=true;
								break;
						}
						
						index++;

						
					}
						
						
					Pattern pattern = Pattern.compile(emailExpression, Pattern.CASE_INSENSITIVE);
					//CASE insensitive matching

					Matcher matcher = pattern.matcher(inputStr);
					if (!matcher.matches() || IsError)
					{
						error.setText(error.getText().toString() + "The Email is not Correctly typed \n");

					}
					
					
					if (error.getText().toString().equals("")){
						
						md1.insert_Login(name.getText().toString(), PassWord.getText().toString(), EmailEdit.getText().toString(), "1"); // **** IMPORTANT --  MeanWhile i dont take care about the situation that what happend if the user forgot the password

						  finish();
							Intent myIntent = new Intent(MainActivity.this,NextActivity.class);
							 startActivity(myIntent);

					}
		
		

	
				}
				  
				else{
					  
				  if ( error.getText().toString().equals("")){
					  
					  String getit= error.getText().toString();
					  
					  if (  name.getText().toString().equals(l1.get(0)) && PassWord.getText().toString().equals(l1.get(1)) ) 
					  {
						  md1.update_forget_Login("1");
						  finish();
							Intent myIntent = new Intent(MainActivity.this,NextActivity.class);
							 startActivity(myIntent);
						  
					  }
					  
					  else {
						 
						  error.setText(error.getText().toString()+ "The name or the password \nare not correct");
						 
					  }
					
				  }
				  
					  
				}
				  
			}
				  
			  
		});
	 
	   
	   md1.close();
	   
	 
	} 



}