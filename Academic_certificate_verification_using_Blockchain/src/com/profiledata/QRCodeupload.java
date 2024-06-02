package com.profiledata;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.qrcode.Create_QR;
import com.user.DBconn;

/**
 * Servlet implementation class QRCodeupload
 */
@WebServlet("/QRCodeupload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50)
public class QRCodeupload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	File finalpath;
	private static final int BUFFER_SIZE = 4096;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QRCodeupload() {
		super();
		// TODO Auto-generated constructor stub
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

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {

			String Username = "";
			String candidateid = request.getParameter("emailid");
			Connection con = DBconn.conn();
			Statement stRegister12 = (Statement) con.createStatement();
			ResultSet rsLogin12 = stRegister12
					.executeQuery("select * from updateprofile where emailid='"
							+ candidateid + "'");
			if (rsLogin12.next()) {
				Username = rsLogin12.getString("firstname");
			}

			String sql1 = "SELECT * FROM tblimagemaster where Email_Id = ?";
			PreparedStatement stt1 = con.prepareStatement(sql1);
			stt1.setString(1, candidateid);

			ResultSet result1 = stt1.executeQuery();
			if (result1.next()) {
				// gets file name and file blob data
				String fileName = Username+".png";
				Blob blob = result1.getBlob("FileData");
				InputStream inputStream = blob.getBinaryStream();
				int fileLength = inputStream.available();

				System.out.println("fileLength = " + fileLength);

				ServletContext context = getServletContext();

				// sets MIME type for the file download
				String mimeType = context.getMimeType(fileName);
				if (mimeType == null) {
					mimeType = "application/octet-stream";
				}

				// set content properties and header attributes for the response
				response.setContentType(mimeType);
				response.setContentLength(fileLength);
				String headerKey = "Content-Disposition";
				String headerValue = String.format(
						"attachment; filename=\"%s\"", fileName);
				response.setHeader(headerKey, headerValue);

				// writes the file to the client
				OutputStream outStream = response.getOutputStream();

				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}

				inputStream.close();
				outStream.close();
			} else {
				// no file found
				// response.getWriter().print("File not found for the id: " +
				// fileName);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response.getWriter().print("SQL Error: " + ex.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String emailid = (String) session.getAttribute("userid");
		Part tenfile = request.getPart("qrcodefile");
		String tenfilename = getFileName(tenfile);
		String company_name = request.getParameter("company_name");
		InputStream teninputStream = null;
		try {
			Connection con = DBconn.conn();
			Statement st = (Statement) con.createStatement();
			ResultSet rs;
			String Username = (String) session.getAttribute("Uusername");
			File finalpath = new File(DBconn.filepath, Username);
			finalpath.mkdir();
			String filepath = DBconn.newfilepath + "/" + Username + "/"
					+ Username + ".png";
			Create_QR.CreateQR(emailid, filepath);
			teninputStream = tenfile.getInputStream();
			Statement st3 = con.createStatement();
			Statement st2 = con.createStatement();

//			ResultSet rs1;
//			String str1 = "select * from tblqrupdateprofile where emailid='"
//					+ emailid + "'";
//			rs1 = ((java.sql.Statement) st3).executeQuery(str1);
//			if (rs1.next()) {
//
//				System.out.println("IF");
//				// String updateSQL =
//				// "update tblqrupdateprofile set file_path=?,company_name=? where emailid=?";
//				// PreparedStatement pstmt = con.prepareStatement(updateSQL);
//				// pstmt.setString(1, emailid);
//				// pstmt.setString(2, company_name);
//				// pstmt.setString(3, filepath);
//				//
//				//
//				// pstmt.executeUpdate();
//				Statement ss = con.createStatement();
//				ss.executeUpdate("update tblqrupdateprofile set file_path='"
//						+ filepath + "',company_name='" + company_name
//						+ "' where emailid='" + emailid + "'");
//
//			} else
			{
				

				String updateSQL = "insert into tblqrupdateprofile(emailid,file_path,file_data,company_name) values(?,?,?,?)";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);
				pstmt.setString(1, emailid);
				pstmt.setString(2, filepath);
				pstmt.setBlob(3, teninputStream);
				pstmt.setString(4, company_name);

				pstmt.executeUpdate();
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		PrintWriter pw = response.getWriter();
		pw.println("<html><script>alert('QR Code Update Successfully');</script><body>");
		pw.println("");
		pw.println("</body></html>");
		RequestDispatcher rd = request.getRequestDispatcher("/Home.jsp");
		rd.include(request, response);
	}

}
