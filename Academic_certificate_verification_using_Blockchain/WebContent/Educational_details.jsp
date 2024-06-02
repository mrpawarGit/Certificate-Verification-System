<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="com.user.*"%>
<%@page import="java.sql.Connection"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
	<title>BlockChain</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta charset="UTF-8">
	<link href="https://fonts.googleapis.com/css?family=Encode+Sans+Expanded:400,600,700" rel="stylesheet">
	<link href="plugin-frameworks/bootstrap.css" rel="stylesheet">
	<link href="fonts/ionicons.css" rel="stylesheet">
	<link href="common/styles.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="demo.css" />
</head>
<body>
	<%
	if(request.getParameter("succ")!=null)
	{
	out.println("<script>alert('Congratulations....Login Successfull...!')</script>");	
	}
   %>
	<header>		
		<div class="container">
			<a class="logo" href="index.html"><img src="images/logo-black.png" alt="Logo"></a>
			<a class="menu-nav-icon" data-menu="#main-menu" href="#"><i class="ion-navicon"></i></a>
			<ul class="main-menu" id="main-menu">
				<li><a href="Home.jsp" >Home</a></li>
				<li><a href="ProfileUpdate.jsp" class="current">Profile Update</a></li>
				<li><a href="QRCode_details.jsp">QRCode Upload</a></li>		
				<!-- <li><a href="AddLogin" class="current-demo">Logout</a></li> -->
				<li><a href="index.jsp?logout">Logout</a></li>
			</ul>
			<div class="clearfix"></div>
		</div>
	</header>

	<section class="ptb-0">
	<div class="mb-30 brdr-ash-1 opacty-5"></div>
		<div class="container">
		 <div class="row">
		  <div class="col-sm-12 col-md-8">
			<div class="row">
					<%
			            String emailid=(String)session.getAttribute("userid");
			        %>                
               <form class="post_section" enctype="multipart/form-data" action="updateprofile" method="post">
                   
                   <table align="center" border="5" cellpadding="10" cellspacing="10">  	
					 <tr>
						<td colspan="2" align="center"><b>Home Page (by Best Student)</b></td>
						<td rowspan="2" colspan="3"><img src="img/edu.jpg" width="10px" height="120"></td>
					</tr>
					 <tr align="center">
						<td><b>Email-Id:</b></td>
						<td colspan="1">&nbsp;&nbsp;<b><%=emailid%></b></td>
					</tr>
					 <tr>
					 	<td colspan="3" align="center"><h2 style="font-family: Times New Roman; font-size: 22px; font-weight: bold;">10th</h2></td>
					 </tr>
					 <tr align="center">
					  	<td><b>Percentage(%):</b></td>
        				<td><b>Seat_No:</b></td>
        				<td><b>Passing Year:</b></td>
        			 </tr>
        			 <tr>
        			  	<td><input type="text" name="tenpercentage" required="required" pattern="^[1-9]\d*(\.\d+)?$" placeholder="%"></td>
        			  	<td><input type="text" name="tenMarksheet_No" required="required" pattern="[A-Z].{0,}[0-9].{5,}" placeholder="Enter No" /> </td>
        				<td><input type="text" name="tenPassingYear" required="required" pattern="[0-9].{3,}" maxlength="4" placeholder="eg.2018-2019" /> </td>
                    </tr>
                    <tr>
                    	<td><b>Upload MarkSheet 10th:</b></td>
                    	<td colspan="2"><input type="file" id="file" name="tenfile" required="required" placeholder="Select Document " /></td>
                    </tr>
                    <tr>
					 	<td colspan="3" align="center"><h2 style="font-family: Times New Roman; font-size: 22px; font-weight: bold;">12th</h2></td>
					 </tr>
                    <tr align="center">
					  	<td><b>Percentage(%):</b></td>
        				<td><b>Seat_No:</b></td>
        				<td><b>Passing Year:</b></td>
        			 </tr>
        			 <tr>
        			  	<td><input type="text" name="twelpercentage" required="required" pattern="^[1-9]\d*(\.\d+)?$" placeholder="%"></td>
        			  	<td><input type="text" name="twelMarksheet_No" required="required" pattern="[A-Z].{0,}[0-9].{5,}" placeholder="Enter No" /> </td>
        				<td><input type="text" name="twelPassingYear" required="required" pattern="[0-9].{3,}" maxlength="4" placeholder="eg.2018-2019" /> </td>
                    </tr>
                    <tr>
                    	<td><b>Upload MarkSheet 12th:</b></td>
                    	<td colspan="2"><input type="file" id="file" name="twelfile" required="required" placeholder="Select Document " /></td>
                    </tr>
                    <tr>
					 	<td colspan="3" align="center"><h2 style="font-family: Times New Roman; font-size: 22px; font-weight: bold;">BE</h2></td>
					 </tr>
        			 <tr align="center">
					  	<td><b>Percentage(%):</b></td>
        				<td><b>Seat_No:</b></td>
        				<td><b>Passing Year:</b></td>
        			 </tr>
        			 
        			 <tr>
        			  	<td><input type="text" name="bepercentage" required="required" pattern="^[1-9]\d*(\.\d+)?$" placeholder="%"></td>
        			  	<td><input type="text" name="beMarksheet_No" required="required" pattern="[A-Z].{0,}[0-9].{5,}" placeholder="Enter No" /> </td>
        				<td><input type="text" name="bePassingYear" required="required" pattern="[0-9].{3,}" maxlength="4" placeholder="eg.2018-2019" /> </td>
                    </tr>
        		      <tr>
                    	<td><b>Upload MarkSheet BE:</b></td>
                    	<td colspan="2"><input type="file" id="file" name="befile" required="required" placeholder="Select Document " /></td>
                    </tr> 	
        			<tr>
		         		<td colspan="2">
						   <label data-icon="u"><b>Select Smart Contract Minutes:</b></label>
						   <select id="gender" name="minutes" required="required" >
		   				   <option selected="selected">Select Minutes</option>		
		   				   <%
	   						try{
	   				       		int i=1;
	   				       	    int flag=0;
	   				       		String query="select * from tblsmart_contracts";
	   				       		Connection conn=DBconn.conn();
	   				       		Statement stmt=conn.createStatement();
	   				       		ResultSet rs=stmt.executeQuery(query);
	   				       		while(rs.next())
	   				       		{
	   						%>
							<option value="<%=rs.getString("Duration_Data")%>"><%=rs.getString("MsgData")%></option>
							<%}
   				       		}
   							catch(Exception e)
   							{}
   							%>
						</select>
  						</td>
  						<td colspan="1" align="center"><input type="submit" value="Update" style="background: yellow;color: black;" class="button"/></td>
        			</tr>
        			<tr>
        				
				    </tr>
         		</table>                             					
   			</form>
 			</div>
		</div>
				<div class="col-sm-12 col-md-4"><br><br><br><br>
				<h3 align="center">Educational Details<br> (by Best Student)</h3>
					<img src="img/users.jpg" alt="">
				</div>
			</div>
		</div>
	</section><br><br><br><br><br><br>
	
	<footer class="bg-191 color-ccc">
			<div class="brdr-ash-1 opacty-2"></div>
			<div class="oflow-hidden color-ash font-9 text-sm-center ptb-sm-5"><br>
			<p class="color-ash">
				Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="ion-heart" aria-hidden="true"></i> by <a href="#" target="_blank">Digital Certificate on Blockchain</a>
			</p><br>
			</div>
	</footer>
	<script src="plugin-frameworks/jquery-3.2.1.min.js"></script>	
	<script src="plugin-frameworks/tether.min.js"></script>	
	<script src="plugin-frameworks/bootstrap.js"></script>	
	<script src="common/scripts.js"></script>	
</body>
</html>
     