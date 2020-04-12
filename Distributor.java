import java.sql.*;
import java.util.Date;
import java.util.*;
import java.io.*;

public class Distributor
{
	private Statement statement = null;
	private ResultSet rs = null;
	private Scanner intScanner = null;


	// CONSTRUCTOR
	public Distributor(Connection connection)
	{
		try
		{
			statement = connection.createStatement();
			intScanner = new Scanner(System.in);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	//Main Function
	public void main_distributor(){}

	// ----------------------------------------------------------------- //
	// OPERATION 1
	// Add new distributor

	public void op1_insert_distributor(int distId, String distName, String distType, float balance, String contactName, String contactPhone, String addr, String city) throws SQLException
	{
		//assumption: distId will always be provided.
		String query = "insert into Distributors(distId, distName,distType,balance,primaryContact) values("+distId+",'"+distName+"','"+distType+"',"+balance+",'"+contactPhone+"')";
		statement.executeUpdate(query);

		query = "insert into Locations(contactPerson,phoneNumber,addr,city,distId) values('"+contactName+"','"+contactPhone+"','"+addr+"','"+city+"',"+distId+")";
		statement.executeUpdate(query);
	}

	// ----------------------------------------------------------------- //
	// OPERATION 2
	//Update Operations

	public void op2_update_distributor(int distId) throws SQLException
	{
		
		String query = "select * from Distributors where distId="+distId;
		rs = statement.executeQuery(query);

		if(!rs.next()){
			System.out.println("Sorry, distributor with this ID does not exist.");
			return;
		}

		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 
		headersList.add("Distributor ID");
		headersList.add("Distributor Name");
		headersList.add("Distributor Type");
		headersList.add("Balance");
		headersList.add("Primary Contact Number");

		List<List<String>> rowsList = new ArrayList<>();

		List<String> row = new ArrayList<>(); 
		row.add(String.valueOf(rs.getInt("distId")));
		row.add(rs.getString("distName"));
		row.add(rs.getString("distType"));
		row.add(String.valueOf(rs.getFloat("balance")));
		row.add(rs.getString("primaryContact"));

		rowsList.add(row);

		System.out.println(tableGenerator.generateTable(headersList, rowsList));
		
		int locationCnt=0;
		query = "select * from Locations where distId="+distId;
		rs = statement.executeQuery(query);


		tableGenerator = new TableGenerator();
		headersList = new ArrayList<>(); 
		headersList.add("Location ID");
		headersList.add("Contact Person");
		headersList.add("Phone Number");
		headersList.add("Address");
		headersList.add("City");

		rowsList = new ArrayList<>();
		while(rs.next()){
			locationCnt++;
			row = new ArrayList<>(); 
			row.add(String.valueOf(rs.getInt("locId")));
			row.add(rs.getString("contactPerson"));
			row.add(rs.getString("phoneNumber"));
			row.add(rs.getString("addr"));
			row.add(rs.getString("city"));

			rowsList.add(row);
		}

		if(locationCnt>0){
			System.out.println("Following is the list of warehouse locations of current distributor");
			System.out.println(tableGenerator.generateTable(headersList, rowsList));
		}
		
		System.out.println("Edit-menu:");
		System.out.println("1. Update Distributor Name");
		System.out.println("2. Update Distributor Type");
		System.out.println("3. Update Balance");
		System.out.println("4. Update Primary Contact Number");
		if(locationCnt>0){
			System.out.println("5. Update warehouse (location) details");
		}



	}

	public void op2_update_distType(String distName, String distType) throws SQLException
	{
		String query = "update Distributors set distType='"+distType+"' where distName='"+distName+"';";
		// what if there are many distributors with same name? Show IDs
		statement.executeUpdate(query);
	}

	public void op2_update_balance(String distName, float balance) throws SQLException
	{
		String query = "update Distributors set balance="+balance+" where distName='"+distName+"';";
		statement.executeUpdate(query);
	}

	public void op2_update_primaryContact(String distName, String primaryContact) throws SQLException
	{
		String query = "update Distributors set primaryContact='"+primaryContact+"' where distName='"+distName+"';";
		statement.executeUpdate(query);
	}

	// ----------------------------------------------------------------- //
	// OPERATION 3
	// Delete a Distributor
	public void op3_delete_distributor(int distId) throws SQLException
	{
		String query = "delete from Distributors where distId = "+distId;
		statement.executeUpdate(query);
	}

	// OPERATION 4
	// Input Order from distributors, for a book edition or an issue of a publication per distributor, for a certain date
	public void op4_input_order_Orders(int orderId, float shippingCost, String orderDate, String deliveryDate, int locId) throws SQLException
	{


		String query = "insert into Orders(orderId, shippingCost,orderDate,deliveryDate,locId) values("+orderId+","+shippingCost+",'"+orderDate+"','"+deliveryDate+"',"+locId+");";
		rs = statement.executeQuery(query);


	}

	public void op4_input_order_OrderContains(int orderId, int orderItemId, int pubId, int quantity) throws SQLException
	{

		String query = "insert into OrderContains(orderItemId,pubId,orderId,quantity) values("+orderItemId+","+pubId+","+orderId+","+quantity+");";
		rs = statement.executeQuery(query);

	}

	// ----------------------------------------------------------------- //
	// OPERATION 5
	// Bill Distributor
	public void op4_bill_distributor(int distId, String generationDate) throws SQLException
	{

			String query = "Select * From Orders JOIN Locations ON Orders.locId=Locations.locId where (Orders.orderId not in (Select orderId from OrderBillMappings) and Locations.distID="+distId+")";
			rs = statement.executeQuery(query);

			/*
			int cnt = 1;
			System.out.println("Bills for following orders have not been generated for current distributor");
			while(rs.next())
			{
				System.out.println("\nRECORD "+cnt+": ");
				System.out.println("Order ID: "+rs.getInt("orderId"));
				System.out.println("Order Date: "+rs.getString("orderDate"));
				System.out.println("Delivery Date: "+rs.getString("deliveryDate"));
				cnt++;
			}
			*/

			// ---------------------------
			// ---------------------------
			System.out.println("Bills for following orders have not been generated for current distributor: ");

			TableGenerator tableGenerator = new TableGenerator();
			List<String> headersList = new ArrayList<>(); 

			headersList.add("Order ID");
			headersList.add("Order Date");
			headersList.add("Delivery Date: ");

			List<List<String>> rowsList = new ArrayList<>();

			while(rs.next())
			{
				List<String> row = new ArrayList<>(); 

				row.add(String.valueOf(rs.getInt("orderId")));
				row.add(rs.getString("orderDate"));
				row.add(rs.getString("deliveryDate"));

				rowsList.add(row);
			}

			System.out.println(tableGenerator.generateTable(headersList, rowsList));

			// ---------------------------
			// ---------------------------

			Scanner Scanner = new Scanner(System.in);
			System.out.println("Input Order IDs for which you want to generate Bill (separated only by commas): ");
			String orderIds = Scanner.nextLine();

			String[] OrderIds = orderIds.split(",");
			float billAmt = 0;
			for (int i = 0; i < OrderIds.length; i++)
			{
				//OrderIds[i] = Integer.parseInt(OrderIds[i]);
				query = "Select OrderContains.quantity, OrderItems.price from OrderContains JOIN OrderItems ON OrderContains.orderItemId = OrderItems.orderItemId where OrderContains.orderId ="+OrderIds[i];
				rs = statement.executeQuery(query);

				while(rs.next())
				{
					int quantity = rs.getInt("quantity");
					Float price = rs.getFloat("price");
					billAmt = billAmt + price*quantity;
				}

				query = "Select shippingCost from Orders where orderId ="+OrderIds[i];
				rs = statement.executeQuery(query);
				rs.next();
				Float shippingCost = rs.getFloat("shippingCost");

			  billAmt += shippingCost;

			}

			try
			{

				op4_bill_distributor_helper(billAmt, generationDate, distId, OrderIds);

			}catch(SQLException e)
			{
				e.printStackTrace();
			}

	}

	public void op4_bill_distributor_helper(float billAmt, String generationDate, int distId, String[] orderId) throws SQLException
	{

		String query = "insert into Bills(billAmt,generationDate,distId) values("+billAmt+",'"+generationDate+"', "+distId+");";
		rs = statement.executeQuery(query);
		query = "SELECT LAST_INSERT_ID()";
		//rs = statement.executeQuery(query);
		rs = statement.getGeneratedKeys();
		rs.next();
		int billId = rs.getInt("billId");
		for(int i=0;i<orderId.length;i++)
		{

			query = "insert into OrderBillMappings(orderId, billId) values("+orderId[i]+","+billId+");";
			rs = statement.executeQuery(query);

		}


	}

	// ----------------------------------------------------------------- //
	// OPERATION 6
	// Payment
	public void op6_payment(int distId, String receiptDate) throws SQLException
	{
		System.out.println("Enter Payment Date");

		System.out.println("Following Bills are Pending Payments");
		String query = "Select * from Bills where receiptDate is NULL and distId="+distId+"";
		rs = statement.executeQuery(query);

		/*
		int cnt = 1;
		while(rs.next())
		{
			System.out.println("\nRECORD "+cnt+": ");
			System.out.println("Bill ID: "+rs.getInt("billId"));
			System.out.println("Bill Amount: "+rs.getInt("billAmt"));
			System.out.println("Generation Date: "+rs.getString("generationDate"));
			System.out.println("Distributor ID: "+rs.getString("distId"));
			cnt++;
		}
		*/

		// ---------------------------
		// ---------------------------
		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 

		headersList.add("Bill ID");
		headersList.add("Bill Amount");
		headersList.add("Generation Date");
		headersList.add("Distributor ID");

		List<List<String>> rowsList = new ArrayList<>();

		while(rs.next())
		{
			List<String> row = new ArrayList<>(); 

			row.add(String.valueOf(rs.getInt("billId")));
			row.add(String.valueOf(rs.getFloat("billAmt")));
			row.add(rs.getString("generationDate"));
			row.add(String.valueOf(rs.getInt("distId")));

			rowsList.add(row);
		}

		System.out.println(tableGenerator.generateTable(headersList, rowsList));

		// ---------------------------
		// ---------------------------

		System.out.println("Input Bill ID (Comma Seperated) for which you make Payment");
		Scanner Scanner = new Scanner(System.in);
		String billIds = Scanner.nextLine();

		String[] BillIds = billIds.split(",");

		for (int i = 0; i < BillIds.length; i++)
		{
			//OrderIds[i] = Integer.parseInt(OrderIds[i]);
			op6_payment_helper(BillIds[i], receiptDate,distId);
			System.out.println("Bill with id "+BillIds[i]+" Paid successfully");

		}


	}
	public void op6_payment_helper(String billId, String receiptDate, float distId) throws SQLException
	{


		String query = "update Bills set receiptDate = '"+receiptDate+"' where billId='"+billId+"';";
		rs = statement.executeQuery(query);

		query = "Select billAmt from Bills where billId = "+billId+";";
		rs = statement.executeQuery(query);
		rs.next();
		float billAmt = rs.getFloat("billAmt");

		query = "Select balance from Distributors where distId = "+distId+";";
		rs = statement.executeQuery(query);
		rs.next();
		float balance = rs.getFloat("balance");
		float updated_balance = balance-billAmt;

		query = "update Distributors set balance = "+updated_balance+" where distId = "+distId+";";
		rs = statement.executeQuery(query);


	}

}
