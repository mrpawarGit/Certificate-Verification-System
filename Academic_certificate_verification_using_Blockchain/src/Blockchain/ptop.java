package Blockchain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import Blockchain.*;
public class ptop {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "";
	   public static void CreateDataNote(int size)
	   {
	   
	       for (int i = 1; i <=size; i++) {
	           Connection conn = null;
	           Connection con = null;
	   Statement stmt = null;
	   Statement stmt1 = null;
	   try{
	      //STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");

	      //STEP 3: Open a connection
	      conn = DriverManager.getConnection(DB_URL, USER, PASS);
	String databasename="DataNode"+i;
	      //STEP 4: Execute a query
	       stmt = conn.createStatement();
	      
	      String sql = "CREATE DATABASE if not exists "+databasename;
	      stmt.executeUpdate(sql);
	      
	      con=DriverManager.getConnection("jdbc:mysql://localhost/"+databasename, USER, PASS);
	      stmt1=con.createStatement();
	      String sql1 = "CREATE TABLE  if not exists transhash " +
	                   "(TransactionID INT(11) NOT NULL AUTO_INCREMENT, " +
	                   " PlainData LONGTEXT NULL, " + 
	                   " BlocKData LONGTEXT NULL, " + 
	                   " PreviousHash LONGTEXT NULL, " + "HashBlock LONGTEXT,"+"Current_Times LONGTEXT NULL, "+" PRIMARY KEY ( TransactionID))"; 
	      stmt1.executeUpdate(sql1);
	     
	   }catch(SQLException se){
	      //Handle errors for JDBC
	      se.printStackTrace();
	   }catch(Exception e){
	      //Handle errors for Class.forName
	      e.printStackTrace();
	   }finally{
	      //finally block used to close resources
	      try{
	         if(stmt!=null)
	            stmt.close();
	      }catch(SQLException se2){
	          se2.printStackTrace();
	      }// nothing we can do
	      try{
	         if(conn!=null)
	            conn.close();
	      }catch(SQLException se){
	         se.printStackTrace();
	      }//end finally try
	   }//end try
	   System.out.println("Data Node created successfully...");
	       }
	       
	   }
	   public static void ptopverify(int noofnode,String data)
	   {
		   
	       try {
	    	   // check pair to pair verification
	           CreateDataNote(noofnode);
	           Connection con = null;
	           Connection conn = null;
	           Connection conndata = null;
	          
	           for (int i = 1; i <= noofnode; i++) {
	               long starttime = System.currentTimeMillis();
	               String db = "DataNode" + i;
	               try {
	                   con = (Connection) Dbconn.conn1(db);
	                   // mining and puzzle
	                   ChainConsensus.Consensus(data, con, i, db);

	               } catch (SQLException ex) {
	                  
	               } catch (ClassNotFoundException ex) {
	                   
	               }
	               long endtime = System.currentTimeMillis();
	               long totaltime = endtime - starttime;
	               System.out.println(i + "\tTotal Time=>" + totaltime);
	           }// for loop end
	           Statement st001;
	           ResultSet rs1;
	           int j = 1;
	           // majority count
	           for (Entry<Integer, String> entry : Block.blocklist.entrySet()) {
	               String db = "DataNode" + j;
	               int key = entry.getKey();
	               String values = entry.getValue();
	               if (values.equals("InValid")) {
	                   Statement stat, st2;
	                   con = (Connection) Dbconn.conn1(db);
	                   stat = (Statement) con.createStatement();
	                   st001 = (Statement) con.createStatement();
	                   int k = 0;
	                   if (noofnode == j) {
	                       k = 1;
	                   } else {
	                       k = j + 1;
	                   }
	                   st001.executeUpdate("TRUNCATE transhash");

	                   // recovery data
	                   db = "DataNode" + k;
	                   conn = Dbconn.conn1(db);
	                   st2 = conn.createStatement();
	                   rs1 = st2.executeQuery("select * from transhash");
	                   while (rs1.next()) {
	                       String PlainData = rs1.getString("PlainData");
	                       String BlocKData = rs1.getString("BlocKData");
	                       String PreviousHash = rs1.getString("PreviousHash");
	                       String HashBlock = rs1.getString("HashBlock");
	                       String Current_Times = rs1.getString("Current_Times");
	                       stat.executeUpdate("INSERT INTO transhash (PlainData, BlocKData, PreviousHash,HashBlock,Current_Times) values('" + PlainData + "','" + BlocKData + "','" + PreviousHash + "','" + HashBlock + "','" + Current_Times + "')");

	                   }
	                   
	               }
	               j++;
	           }// for loop 
	           
	           // new transaction
	            for (int i = 1; i <= noofnode; i++) 
	            {
	                String db = "DataNode" + i;
	                 conndata = (Connection) Dbconn.conn1(db);
	               Statement stat4 = (Statement) conndata.createStatement();
		        stat4.executeUpdate("insert into transhash(PlainData,BlocKData,PreviousHash,HashBlock,Current_Times) values ('" + data +"','" + Block.hash +"','" + Dbconn.PrevHash1 +"','" + Block.timeStamp +"','" + Block.nonce +"')");
		    		
	               
	           }
	           

	       } catch (SQLException | ClassNotFoundException e) {
	           // TODO Auto-generated catch block
	           e.printStackTrace();
	       }
	   }
}
