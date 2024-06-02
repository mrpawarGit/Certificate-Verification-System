package com.profiledata;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.user.DBconn;

@WebServlet("/updateprofile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50)
public class updateprofile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public updateprofile() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	String getFileName(Part filePart) {

		for (String cd : filePart.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String fileName = cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
				return fileName.substring(fileName.lastIndexOf('/') + 1)
						.substring(fileName.lastIndexOf('\\') + 1);
				// return cd.substring(cd.indexOf('=') + 1).trim().replace("\"",
				// "") ;
			}

		}

		return null;

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		HttpSession session = request.getSession(false);
		String emailid = (String) session.getAttribute("userid");
		// 10 info
		String tenpercentage = request.getParameter("tenpercentage");
		String tenMarksheet_No = request.getParameter("tenMarksheet_No");
		String tenPassingYear = request.getParameter("tenPassingYear");
		System.out.println(tenPassingYear);
		Part tenfile = request.getPart("tenfile");
		// String tenfilename=getFileName(tenfile);
		InputStream teninputStream = null;
		// 12 info

		String twelpercentage = request.getParameter("twelpercentage");
		String twelMarksheet_No = request.getParameter("twelMarksheet_No");
		String twelPassingYear = request.getParameter("twelPassingYear");
		System.out.println(twelPassingYear);
		Part twelfile = request.getPart("twelfile");
		// String twelfilename=getFileName(twelfile);
		InputStream twelinputStream = null;
		// Be info
		String bepercentage = request.getParameter("bepercentage");
		String beMarksheet_No = request.getParameter("beMarksheet_No");
		String bePassingYear = request.getParameter("bePassingYear");
		System.out.println(bePassingYear);
		Part befile = request.getPart("befile");
		// String befilename=getFileName(befile);
		InputStream beinputStream = null;
		String minutes = request.getParameter("minutes");

		long fsize=tenfile.getSize();
		long ssize=twelfile.getSize();
		long tsize=befile.getSize();
		int imagetotal=0;
		long fsum=fsize/1024;
		long ssum=ssize/1024;
		long tsum=tsize/1024;
		if(fsum>0&&fsum<1024)
		{
			imagetotal++;
		}
		else if(ssum>0&&ssum<1024)
		{
			imagetotal++;
		}
		else if(tsum>0&&tsum<1024)
		{
			imagetotal++;
		}
		//if(imagetotal==3)
		{
		try {
			Connection con = DBconn.conn();

			teninputStream = tenfile.getInputStream();
			twelinputStream = twelfile.getInputStream();
			beinputStream = befile.getInputStream();
			
			System.out.println("First Size=>"+tenfile.getSize());
			System.out.println("Second Size=>"+twelfile.getSize());
			System.out.println("Thirt Size=>"+befile.getSize());
			
			
			String updateSQL = "update updateprofile set  tenpercentage=?,tenMarksheet_No=?,tenPassingYear=?,tenfile=?,twelpercentage=?,twelMarksheet_No=?,twelPassingYear=?,twelfile=?,bepercentage=?,bePassingYear=?,beMarksheet_No=?,befile=?  where emailid=?";
			PreparedStatement pstmt = con.prepareStatement(updateSQL);
			pstmt.setString(1, tenpercentage);
			pstmt.setString(2, tenMarksheet_No);
			pstmt.setString(3, tenPassingYear);
			pstmt.setBlob(4, teninputStream);
			pstmt.setString(5, twelpercentage);
			pstmt.setString(6, twelMarksheet_No);
			pstmt.setString(7, twelPassingYear);
			pstmt.setBlob(8, twelinputStream);
			pstmt.setString(9, bepercentage);
			pstmt.setString(10, bePassingYear);
			pstmt.setString(11, beMarksheet_No);
			pstmt.setBlob(12, beinputStream);
			pstmt.setString(13, emailid);
			pstmt.executeUpdate();
			// smart contract
			String[] minutesdata = minutes.split("#");
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			SimpleDateFormat sdfnew = new SimpleDateFormat("yyyy-MM-dd");
			Date day = new Date();
			String starttime = sdf.format(day);
			String currentdate = sdfnew.format(day);
			Date dNow;

			dNow = sdf.parse(starttime);
			int minu = Integer.valueOf(minutesdata[0].toString());
			Calendar cal = Calendar.getInstance();
			cal.setTime(dNow);
			cal.add(Calendar.MINUTE, minu);
			dNow = cal.getTime();
			String endtime = sdf.format(dNow);
			String Status_U="0";
			String str = "insert into tblmastersmartcontracts (EmailId,Contract_Data,StartTime_Info,EndTime_Info,Current_Date_Info,Status_U) values(?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(str);
			ps.setString(1, emailid);
			ps.setString(2, minutes);
			ps.setString(3, starttime);
			ps.setString(4, endtime);
			ps.setString(5, currentdate);
			ps.setString(6, Status_U);
			ps.executeUpdate();
			
			Statement st = (Statement) con.createStatement();
		    ResultSet rs;
		    Statement st1 = (Statement) con.createStatement();
		    ResultSet rs1;
		    int amount=0,smartamount=0,Owner_Cost=0;
		    String strdemo="select * from tblsmartrs where emailId='" + emailid + "'";
		    rs = ((java.sql.Statement) st).executeQuery(strdemo);
		    if(rs.next())
		    {
		    	amount=rs.getInt("amount");
		    }
		    String strdemo1="select * from tblsmart_contracts where Duration_Data='" + minutes + "'";
		    rs1 = ((java.sql.Statement) st1).executeQuery(strdemo1);
		    if(rs1.next())
		    {
		    	smartamount=rs1.getInt("Cost_Data");
		    	Owner_Cost=rs1.getInt("Owner_Cost");
		    }
		    int sum=amount+Owner_Cost;
		    double total=amount-sum;
		    Statement sat=con.createStatement();
		    sat.executeUpdate("update tblsmartrs set amount='"+total+"' where emailId='" + emailid + "'");

		    pw.println("<script> alert(' Profile Update Done');</script>");
		} catch (Exception e) {
			System.out.println(e);
		}
		}
//		else
//		{
//			pw.println("<script> alert(' Image Size Not Valid');</script>");
//		}
		RequestDispatcher rd = request.getRequestDispatcher("/Home.jsp");
		rd.include(request, response);
	}

}
