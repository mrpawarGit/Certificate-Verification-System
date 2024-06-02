
package Blockchain;


import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.SQLException;


public class Dbconn {
	public static int blockchain1msg=0,blockchain2msg=0,blockchain3msg=0,blockchain4msg=0,bsize=4;
	public static String PrevHash1=null,PrevHash2=null,PrevHash3=null,PrevHash4=null;

    public Dbconn() throws SQLException {
        //initComponents();
           //Connection con;
        
    }
            
            public static Connection conn1(String database) throws SQLException, ClassNotFoundException {
        		Connection con;
        		Class.forName("com.mysql.jdbc.Driver");
        		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database, "root", "");

        		return (con);

        	}
        	

           
}
