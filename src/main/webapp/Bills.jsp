<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="model.Bill"%>
<%@page import="com.BillService"%>

<% /*
		
	if (request.getParameter("hidBillIDSave") == "Save")
	{
		BillService billObj = new BillService();
		String stsMsg = billObj.insertBill(request.getParameter("userID"),
										request.getParameter("billID"),
										request.getParameter("userAccountNo"),
										request.getParameter("usageAmnt"));
	
		session.setAttribute("statusMsg", stsMsg);
	}
	
	
	if (request.getParameter("hidBillIDDelete") == "Remove")
	{
		BillService billObj = new BillService();
		String stsMsg = billObj.deleteBill(request.getParameter("billID"));
		session.setAttribute("statusMsg", stsMsg);
	}
	
	if (request.getParameter("value") == "Update")
	{
		BillService billObj = new BillService();
		
		String stsMsg = billObj.setForm(request.getParameter("billID"));
		session.setAttribute("statusMsg", stsMsg);
	}
	
	if (request.getParameter("hidBillIDSave") == "Update02")
	{
		BillService billObj = new BillService();
		
		String stsMsg = billObj.updateBill(request.getParameter("userID"),
											request.getParameter("billID"),
											request.getParameter("userAccountNo"),
											request.getParameter("usageAmnt"));
		session.setAttribute("statusMsg", stsMsg);
	}
*/	
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Bill Management</title>
	<link rel="stylesheet" href="Views/bootstrap.min.css">
	<script src="Components/jquery-3.6.0.min.js"></script>
	<script src="Components/bills.js"></script>
	
</head>
<body>
	
	<h2>Insert Bill</h2>
	<form method="post" action="Bills.jsp">
		User ID: <input id="userID" name="userID" type="text"><br>
 		Bill ID: <input id="billID" name="billID" type="text"><br>
 		User Account Number: <input id="userAccountNo" name="userAccountNo" type="text"><br>
 		Usage Amount: <input id="usageAmnt" name="usageAmnt" type="text"><br>
 		<input id="btnSubmit" name="btnSubmit" type="button" value="Save">
		<input type="hidden" id="hidBillIDSave" name="hidBillIDSave" value="">
	</form>
	<br>
	<%
 	BillService billObj = new BillService();
 	out.print(billObj.readBills());
	%>
</body>
</html>