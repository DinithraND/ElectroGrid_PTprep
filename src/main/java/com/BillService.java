package com;

import model.Bill;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//import com.google.gson.*;

import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Bills")
public class BillService {
	
	Bill billObj = new Bill();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readBills()
	{
		return billObj.readBills();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBill(@FormParam("userID") String userID, @FormParam("billID") String billID, @FormParam("userAccountNo") String userAccountNo, @FormParam("usageAmnt") String usageAmnt)
	{
		String output = billObj.insertBill(userID, billID, userAccountNo, usageAmnt);
		return output;
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String setForm(String billID)
	{
		return billObj.setForm(billID);
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateBill(@FormParam("userID") String userID, @FormParam("billID") String billID, @FormParam("userAccountNo") String userAccountNo, @FormParam("usageAmnt") String usageAmnt)
	{
		String output = billObj.updateBill(userID, billID, userAccountNo, usageAmnt);
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteBill(String billData)
	{
		Document doc = Jsoup.parse(billData, "", Parser.xmlParser());
		
		String billID = doc.select("itemID").text();
		String output = billObj.deleteBill(billID);
		return output;
	}
	
}
