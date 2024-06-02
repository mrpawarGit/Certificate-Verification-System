package com.user;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBconn {

	public static String filepath="E:\\tsetsub\\mark4\\QRCode";
	public static String newfilepath="E:/tsetsub/mark4/QRCode";
	public static Connection conn() throws ClassNotFoundException, SQLException
	{
		Connection con;
		 Class.forName("com.mysql.jdbc.Driver");
		  con=DriverManager.getConnection("jdbc:mysql://localhost:3306/22ci1533_ecertificateblockchain","root","");
		   
	return con;
	}

}
