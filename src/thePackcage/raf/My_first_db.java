 package thePackcage.raf;
 
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/*
 * You create a subclass implementing onCreate(SQLiteDatabase), 
 * onUpgrade(SQLiteDatabase, int, int) and optionally onOpen(SQLiteDatabase), 
 * and this class takes care of opening the database if it exists, 
 * creating it if it does not, and upgrading it as necessary. 
 * Transactions are used to make sure the database is always in a sensible state.
 */
public class My_first_db  extends SQLiteOpenHelper{


/*
 * The reason of passing null is you want the standard SQLiteCursor behavior. 
 * If you want to implement a specialized Cursor you can
 *  get it by by extending the Cursor class
 */
	public My_first_db(Context context, String name, CursorFactory factory,
			int version) { 
		super(context, name, factory, version);  // the context of this class , name of the database is Programss factory is null and version is 1
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-geneted method stub
				
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	
	//////////////////////////////////////////////////////////////////////
	
	/*
	 * create new table calls
	 * Login which contain the
	 * columns name ,password , Email 
	 * and the default type of the
	 * column is String
	 */
	public void create_user(){
		
		SQLiteDatabase db1 = getReadableDatabase();
		String quey = "create table if not exists Login (name ,password , Email, forget  )";
		db1.execSQL(quey);
		db1.close();
			
	}
	
	/*
	 * in the first time the user insert his details
	 * and the details will insert to table Login
	 */
	
	public void insert_Login(String name ,String password , String Email, String forget){
		
		SQLiteDatabase db1 = getWritableDatabase();

		try{
			
		    ContentValues cn = new ContentValues();
			cn.put("name", name );
			cn.put("password", password );
			cn.put("Email",  Email);
			cn.put("forget", forget);
		
			db1.insert("Login", null, cn);
			
				
			
		}
			
		catch (Exception ex){

			
		}
			
		
		db1.close();
		
	}


		/*
		 * drop the table Login
		 */
		public void drop_Login(){

			SQLiteDatabase db1 = getWritableDatabase();
			String strSQL = "drop table IF EXISTS Login";
			db1.execSQL(strSQL);
			db1.close();
			
		}
		
		/*
		 * get the information from table login
		 * and pass it to a list of String
		 * and finally return the list
		 */
		public  List <String> get_all_Login (){

			List<String> l1 = new ArrayList <String>();  
			SQLiteDatabase db1 = getReadableDatabase();
			Cursor cr1=null;
			try{
				
				String strSQL = "select * from  Login ";
				
				cr1 = db1.rawQuery(strSQL, null);
				String name;
				String password;
				String Email;
				String forget;
				if(cr1.moveToFirst()){
				
						name = cr1.getString(0);
						l1.add(name);
						password = cr1.getString(1);
						l1.add(password);
						Email= cr1.getString(2);
						l1.add(Email);
						forget= cr1.getString(3);
						l1.add(forget);
						
				}
				
				cr1.close();
				db1.close();
				
				return l1;
				
			}
			
			catch (Exception ex){
				if (cr1!=null)
					cr1.close();
				
				db1.close();
				return l1;
				
			}

			
		}
		
		public void update_forget_Login(String forget){ 		
			
			String query;
			SQLiteDatabase db = getWritableDatabase();
		//	ContentValues cn = new ContentValues();
			
			query = "UPDATE Login SET forget= '"+  forget + "'" ;
			db.execSQL(query);
			db.close();

		}
		
	
	/////////////////////////////////////////////////////////////////
		
		/*
		 * create new table calls
		 * firstCal which contain the
		 * columns firstdate  ,whichday 
		 * and the type of whichday 
		 * is INTEGER
		 */
		
	public void create_tableCalen(){
		
		SQLiteDatabase db1 = getReadableDatabase();
		String quey = "create table if not exists firstCal (firstdate  ,whichday INTEGER )";

		db1.execSQL(quey);
		db1.close();
		
	}
	
/*
 * drop the table tableCalen
 */
	
	public void drop_tableCalen(){

		SQLiteDatabase db1 = getWritableDatabase();
		
		String strSQL = "drop table IF EXISTS firstCal" ;
	
		db1.execSQL(strSQL);
   	
		db1.close();
	}
	
	

	
	/*
	 * if a new day beginning we have to update 
	 * the table with new values of cal and day
	 */

	public void insert_tableCalen(String cal ,int day){ 		
		
		String query;
		SQLiteDatabase db = getWritableDatabase();
		ContentValues cn = new ContentValues();
	
		/*other words if it 
		the user turn on the appliction ,so 
		make the first insert and after 
		that only update
		*/
		if (day==-1){ 
	
			cn.put("firstdate ",cal );
			cn.put("whichday",0 );
			db.insert ("firstCal", null, cn);
			db.close();
			
		}
			
		else{ // it is not the first time so update the table

	      
			query = "UPDATE firstCal SET whichday= "+ day + "  , firstdate ='" + cal +"'"  ;
			db.execSQL(query);
			db.close();
			

		}

	}
	
	
    // the  Calcenservice  get the date of the colum firstday
	//and equale it to the currently date
	
	public String  get_firstDate()
	{
		
		
		String cal="";
		Cursor cr1 = null;
		SQLiteDatabase db = getReadableDatabase();
		
		try{
			
			String query = "select * from firstCal";
			cr1 = db.rawQuery(query, null);
			if(cr1.moveToFirst())
				cal = cr1.getString(0);
			
			cr1.close();
		
		}
		
		catch (Exception e){
			
			if ( cr1!=null)
				cr1.close();
		}
		
		db.close();
	
		return cal;

	}
	
	// get the number of whichday
	public int  get_whichday()
	{
		Cursor cr1 = null;
		SQLiteDatabase db =  getReadableDatabase();
		int whichday = 0;
		try{
			
			String query = "select * from firstCal";
			cr1 = db.rawQuery(query, null);
			if(cr1.moveToFirst())
			whichday = cr1.getInt(1);
			cr1.close();
						
		}
		
		catch (Exception e){
			
			if ( cr1!=null)
				cr1.close();
		}
		
		db.close();
		
		return whichday;

	}
	
	

	//////////////////////////////////////////////////////////////
	

	/*
	 * create new tables calls
	 * programs1dayago untill
	 * programs6dayago (of course
	 * doing it in a loop in
	 * other classes)
	 * which contain the
	 * columns name ,time 
	 * and the type of time
	 * is long
	 */
	
	public void createPastTables(int dayago){
		
		SQLiteDatabase db1 = getReadableDatabase();
		String quey = "create table if not exists programs" +dayago + "dayago (name ,time LONG )";

		db1.execSQL(quey);
		db1.close();

	}

	/*
	 * drop tables calls
	 * programs1dayago untill
	 * programs6dayago (of course
	 * doing it in a loop in
	 * other classes)
	 */
	
	public void drop_PastTables(int dayago){

		
		SQLiteDatabase db1 = getReadableDatabase();
		String strSQL = "drop table IF EXISTS programs" +dayago + "dayago";
		db1.execSQL(strSQL);
		db1.close();
		
	}
	
	/*
	 * could delete from
	 * programs1dayago untill
	 * programs6dayago all
	 * the data
	 * */
	
	public void delete_PastTables(int dayago){

		
		SQLiteDatabase db1 = getWritableDatabase();
		String strSQL = "delete from programs" +dayago + "dayago";
		db1.execSQL(strSQL);
		db1.close();
		
		
	}
	/*
	 * get inormation from the previous table day with
	 * the data of the list of programs
	 * and pass it to the new table
	 * according to the value of whichday
	 */

	public void insert_dayago( List <Programs> l1 , int whichday){ 
		
		/*
		 * delete the data of this table
		 */
		this.delete_PastTables(whichday);

		
		SQLiteDatabase db = getWritableDatabase();
		
		try{

			ContentValues cn = new ContentValues();
				int i=0;
				
			while(i <l1.size()){
					
				/*
				 * insert the name of the 
				 * program to the column
				 * name in the table
				 */			
				cn.put("name", l1.get(i).getName()); 
			
				/*
				 * insert the time of the 
				 * program to the column
				 * time in the table
				*/	
				cn.put("time", l1.get(i).gettime());
			
				db.insert("programs" + whichday + "dayago ", null, cn);  // insert tow of them to new raw
				
				i++; //go to the next program from the list
				
			}
		
			db.close();
		
		}
		
		catch (Exception ex){
			
			db.close();
			
		}
		
	}
	
	
	
	public List<Programs> get_all_dayago( int dayago){
		
		List<Programs> l2 = new ArrayList<Programs>();

		/* if dayago=0 so we need to pass information 
		 * from onDay table to  programs1dayago 
		 * table  (yesterday in other words)
		 */
		
		if (dayago==0) 
		{
			
			l2 = this.get_all("onDay");
		   // new day come so we delete the onday table and creat it to get empty table
			this.deletetable("onDay"); 
			
			
			return l2; //return and of course it is finish
			
		}

		//it will be the else

		SQLiteDatabase db = getReadableDatabase();
		Cursor cr1=null;	

		try{
			/* for exapmle whichday = 1 so it take all 
			 * the information from  programs1dayago  
			 */
			String query = "select * from programs" +dayago + "dayago";
			cr1 = db.rawQuery(query, null);
			Programs pr1;
			String name;
			long time;
			if(cr1.moveToFirst())
				do
				{
					name = cr1.getString(0);
					time = cr1.getLong(1);
					pr1 = new Programs(name, time); 
					l2.add(pr1); // add the program to the list
					
				}while(cr1.moveToNext());
			
			cr1.close();
			db.close();
				
			return l2;
			
			
		}
		
		catch( Exception ex){
			
			if (cr1!=null)
			cr1.close();
			
			db.close();
			return l2;
		}
		
	}
		
	
	
	/////////////////////////////////////////////////////////////////////////
	
	
	/*
	 * create table onDay which it
	 * aim to get the programs that
	 * the user use in the currently day
	 */
	
	public void create_table(String table){ 
		
		SQLiteDatabase db1 = getReadableDatabase();
		String quey = "create table if not exists  "+table+ "(name ,time LONG)";
		db1.execSQL(quey);
		db1.close();
	
	}
	
	

	public void droptable(String table){ //drop specific table

		
		SQLiteDatabase db1 = getReadableDatabase();
		
		String strSQL = "drop table IF EXISTS "+table  ;
				
		db1.execSQL(strSQL);
   	
		db1.close();
	}
	
	
	public void deletetable(String table){ //delete specific table

		
		SQLiteDatabase db1 = getWritableDatabase();
				
		String strSQL = "delete from "+table  ;
		
		db1.execSQL(strSQL);
   	
		db1.close();
	}
	
   //  it insert new program to table onDay 
	
	public void add_Program(Programs pr,String table)  
	{
		
		SQLiteDatabase db1 = getWritableDatabase();

		try{
				
				ContentValues cn = new ContentValues();
				cn.put("name", pr.getName());
				cn.put("time", pr.gettime());
			
				db1.insert(table, null, cn);
				//cn.clear();  nessacery?
				db1.close();
				
		}

		catch (Exception e){
			
			db1.close();
		}
		
				
	}
	

	/* we only do it with onDay table.
     
	 */

	public boolean updateprogram(String table,String name){ 		
		
	    String query;
		int z;
	
		List <Programs>  p1 =new ArrayList <Programs>();
		
	//	 create_table("onDay"); // if CalcenService drop the table and nessaservice want to take or insert information it could cause problems
		
		 p1 = get_all("onDay"); // now we take the information from table onDay 
		 
		 SQLiteDatabase db = getWritableDatabase();
		 
		boolean find= false;
		 
		try{
		
			for ( z=0; z<p1.size();z++)
			{
				
				/* check the name of the program 
				  that the nessaservice 
				  identified if it equals to the 
				  name that exist in the table (the programs
				  that the user already used), if we
				  found that the names are equals then
				  we make update and return true
				  ,else we return false and in nessaservice
				  we make new insert
				  
				  */
				if (p1.get(z).getName().equals(name))
				{
					find=true;
					break;
				}
				
			} // we checked if this program is already exist in the table
			
			/*
			 * if exist so we update the program
			 *  in the way we add 5000 
			 *  millis to the time
			 */
			if (find) { 
			
				
				query = "UPDATE "+table+" SET time =  time + 5000 where name='"+name+"'"  ;

				db.execSQL ( query);

				db.close();
				return find; // here it returns true
			}
		
		}
			
		catch (Exception ex){
				
		}
		
		db.close();
		return find; // here it must returns false
		 
	}
		
		

	
	
	
	
	 //get all information from the onDay table
	public List<Programs> get_all(String table)
	{
	
		List<Programs> l1 = new ArrayList<Programs>(); 
		
		Cursor cr1=null;
		SQLiteDatabase db = getReadableDatabase();  
		
		
		try{
			String query = "select * from "+table;
			cr1 = db.rawQuery(query, null);
			Programs pr1;
			String name;
			long time;
			if(cr1.moveToFirst())
				do
				{
					name = cr1.getString(0);
					time = cr1.getLong(1);
					pr1 = new Programs(name, time); 
					l1.add(pr1);
					
				}while(cr1.moveToNext());
			
			cr1.close();
				db.close();
			return l1;
		
		}
	
	
		catch (Exception ex)
		{
			if (cr1!=null)
				cr1.close();
			
			db.close();
			
			return l1;
		}
	
		
	}
		

}
