package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.sql.Statement;

public class Bill {
	public Connection connect()
	{
	 Connection con = null;

	 try
	 {
	 Class.forName("com.mysql.jdbc.Driver");
	 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/dinithrapaf",
	 "root", "");
	 //For testing
	 System.out.println("Successfully connected");
	 }
	 catch(Exception e)
	 {
	 e.printStackTrace();
	 }

	 return con;
	}
	
	public String getMonth() {
		String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};

        Calendar cal = Calendar.getInstance();
        String month = monthName[cal.get(Calendar.MONTH)];
	    return month;
	}
	
	/*
	public String generateBill(int userID, int userAccountNo, int usageAmnt) {
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = " insert into bills(`userID`,`billID`,`userAccountNo`,`usageAmnt`,`totalCost`,`month`)" + " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, userID);
			preparedStmt.setInt(2, ((int)Math.random()*10001));
			preparedStmt.setInt(3, userAccountNo);
			preparedStmt.setInt(4, usageAmnt);
			preparedStmt.setDouble(5, ((double)usageAmnt*18.5));
			preparedStmt.setString(6, getMonth());
		 
			//execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		}
		catch (Exception e)
		{
			output = "Error while inserting";
			System.err.println(e.getMessage());
		}
		return output;
	}
	*/

	public String insertBill(String userID, String billID, String userAccountNo, String usageAmnt) {
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database";
			}
			
			String query = "insert into bills(`userID`,`billID`,`userAccountNo`,`usageAmnt`,`totalCost`,`month`)" + " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			preparedStmt.setInt(1, Integer.parseInt(userID));
			preparedStmt.setInt(2, Integer.parseInt(billID));
			preparedStmt.setInt(3, Integer.parseInt(userAccountNo));
			preparedStmt.setInt(4, Integer.parseInt(usageAmnt));
			preparedStmt.setDouble(5, Double.parseDouble(usageAmnt)*18.5);
			preparedStmt.setString(6, getMonth());
		 
			preparedStmt.execute();
			con.close();
			
			String newBills = readBills();
			output = "{\"status\":\"success\", \"data\": \"" +
					 newBills + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the Bill.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String readBills(){
	 String output = "";
	 
	 try
	 {
		 Connection con = connect();
		 if (con == null)
		 {
			 return "Error while connecting to the database for reading.";
		 }
		 
		 output = "<table border='1'><tr><th>User ID</th>"+"<th>Bill ID</th><th>User Account Num</th>"+"<th>Usage Amount</th>"+"<th>Total Cost</th><th>Month</th><th>Remove</th></tr>";
		 String query = "select * from bills";
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(query);
		 
		 while (rs.next()){
			 String userID = Integer.toString(rs.getInt("userID"));
			 String billID = Integer.toString(rs.getInt("billID"));
			 String userAccountNo = Integer.toString(rs.getInt("userAccountNo"));
			 String usageAmnt = Integer.toString(rs.getInt("usageAmnt"));
			 String totalCost = Integer.toString(rs.getInt("totalCost"));
			 String month = rs.getString("month");
			 
			 output += "<tr><td><input id='hidBillIDUpdate' name='hidBillIDUpdate'\r\n"
			 		+ " type='hidden' value='\" " + userID + "</td>";
			 output += "<td>" + billID + "</td>";
			 output += "<td>" + userAccountNo + "</td>";
			 output += "<td>" + usageAmnt + "</td>";
			 output += "<tr><td>" + totalCost + "</td>";
			 output += "<tr><td>" + month + "</td>";
			 
			 output += "<td><input name='btnUpdate' "
			 + " type='button' value='Update' class='btnUpdate btn btn-secondary' data-billid='" + billID + "'></td>"
			 + "<td>"
			 + "<input name='btnRemove' "
			 + " type='submit' value='Remove' class=btnRemove btn btn-danger' data-billid='" + billID + "'>";
			 }
			 con.close();
			 
			 output += "</table>";
			 
		 
	 }
	 catch (Exception e)
	 {
		 output = "Error while reading the bills.";
		 System.err.println(e.getMessage());
	 }
	 
	 
	return output; 
	}
	
	public String deleteBill(String billID)
	{
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database for deleting.";
			}
			
			String query = "delete from bills where billID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			preparedStmt.setInt(1, Integer.parseInt(billID));

			
			preparedStmt.execute();
	 	con.close();
	 	String newBills = readBills();
		output = "{\"status\":\"success\", \"data\": \"" +
				 newBills + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"success\", \"data\": \"Error while deleting bill.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String setForm (String bill_ID)
	{
		String output = "";
		
		try
		{
			 Connection con = connect();
			 if (con == null)
			 {
				 return "Error while connecting to the database.";
			 }
			 
			 String query = "select userID, billID, userAccountNo, usageAmnt from bills where billID="+bill_ID;
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			 
			 
			 String userID = Integer.toString(rs.getInt("userID"));
			 String billID = Integer.toString(rs.getInt("billID"));
			 String userAccountNo = Integer.toString(rs.getInt("userAccountNo"));
			 String usageAmnt = Integer.toString(rs.getInt("usageAmnt"));
			 
			 
			 output += "<form method='post' action='Bills.jsp'>";
			 output += "User ID: <input name='userID' type='text' value'"+ userID +"'><br>";
			 output += "Bill ID: <input name='billID' type='text' value'"+ billID +"' readonly><br>";
			 output += "User Account Number: <input name='userAccountNo' type='text' value'"+ userAccountNo +"'><br>";
			 output += "Usage Amount: <input name='usageAmnt' type='text' value'"+ usageAmnt +"'><br>";
			 output += "<input name='btnSubmit' type='submit' value='Update02'></form>";
			 			 
			 con.close();
			 		 
			 
		 }
		 catch (Exception e)
		 {
			 output = "Error reading data";
			 System.err.println(e.getMessage());
		 }
		
		return output;
	}
	
	public String updateBill (String userID, String billID, String userAccountNo, String usageAmnt)
	{
		String output = "";
		
		try
		{
			Connection con = connect();
			if (con == null)
			{
				return "Error while connecting to the database";
			}
			
			String query = "update bills set userID=?, userAccountNo=?, usageAmnt=?, totalCost=? where billID="+billID;
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			preparedStmt.setInt(1, Integer.parseInt(userID));
			preparedStmt.setInt(2, Integer.parseInt(userAccountNo));
			preparedStmt.setInt(3, Integer.parseInt(usageAmnt));
			preparedStmt.setDouble(4, Double.parseDouble(usageAmnt)*18.5);
		 
			preparedStmt.execute();
			con.close();
			String newBills = readBills();
			output = "{\"status\":\"success\", \"data\": \"" +
					 newBills + "\"}";
		}
		catch (Exception e)
		{
			output = "{\"status\":\"error\", \"data\":\"Error while updating the item.\"}";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
	
}
