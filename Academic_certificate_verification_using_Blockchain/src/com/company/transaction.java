package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Blockchain.ptop;

import com.user.DBconn;

/**
 * Servlet implementation class transaction
 */
@WebServlet("/transaction")
public class transaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public transaction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		try {
			HttpSession session = request.getSession(true);
			
			String id=(String)session.getAttribute("Cuserid");
			String cmobile=(String)session.getAttribute("Cmobile");
			String cusername=(String)session.getAttribute("Cusername");
			String firstname = null,middlename=null,lastname=null;
			String candidateid = request.getParameter("emailid");
			Connection con = (Connection) DBconn.conn();
			String sql1 = "SELECT * FROM updateprofile where emailid = ?";
			PreparedStatement stt1 = con.prepareStatement(sql1);
			stt1.setString(1, candidateid);

			ResultSet result1 = stt1.executeQuery();
			if (result1.next()) {
				firstname=result1.getString("firstname");
				middlename=result1.getString("middlename");
				lastname=result1.getString("lastname");
			}
			String finaldata=firstname+middlename+lastname+candidateid+id+cmobile+cusername;
			System.out.println("Data=>"+finaldata);
			ptop.ptopverify(4,finaldata);
		}
		catch(Exception e)
		{
			
		}
		pw.println("<html><script>alert('Transaction Successfully');</script><body>");
		pw.println("");
		pw.println("</body></html>");
		response.sendRedirect("CHome.jsp?msg=1");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
