import java.sql.*;
import java.util.Date;
import java.util.*;
import java.io.*;

public class Distributor
{
	private Statement statement = null;
	private ResultSet rs = null;
	
	private Scanner intScanner = null;
	private Scanner lineScanner = null;
	private Scanner floatScanner = null;

	// CONSTRUCTOR
	public Distributor(Connection connection)
	{
		try
		{
			statement = connection.createStatement();
			
			intScanner = new Scanner(System.in);
			lineScanner = new Scanner(System.in);
			floatScanner = new Scanner(System.in);

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

	public int op2_update_distributor(int distId) throws SQLException
	{
		
		String query = "select * from Distributors where distId="+distId;
		rs = statement.executeQuery(query);

		if(!rs.next()){
			System.out.println("Sorry, distributor with this ID does not exist.");
			return 0;
		}

		int status=0;

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
		
		System.out.println("Distributor Edit Menu:");
		System.out.println("1. Update Distributor Name");
		System.out.println("2. Update Distributor Type");
		System.out.println("3. Update Balance");
		System.out.println("4. Update Primary Contact Number");
		if(locationCnt>0){
			System.out.println("5. Update warehouse (location) details");
		}
		System.out.println("Enter your choice: ");

		int choice = intScanner.nextInt();

		switch(choice)
		{
			case 1:
				String distName;
				System.out.println("Enter New Name ");
				distName = lineScanner.nextLine();
				status = op2_update_distributor_name(distId, distName);
				break;
			case 2:
				String distType;
				System.out.println("Enter New Type ('wholesale distributor', 'library', 'bookstore') ");
				distType = lineScanner.nextLine();
				status = op2_update_distributor_type(distId, distType);
				break;
			case 3: 
				float balance;
				System.out.println("Enter New Balance ");
				balance = floatScanner.nextFloat();
				status = op2_update_distributor_balance(distId, balance);
				break;
			case 4:
				String contactPhone;
				System.out.println("Enter New Contact Number ");
				contactPhone = lineScanner.nextLine(); 
				status = op2_update_distributor_contact(distId, contactPhone);
				break;
			case 5: 
				if(locationCnt==0){
					System.out.println("Invalid choice! Please enter correct choice");
					break;
				}
				int locId;
				System.out.println("Enter ID of the location that you want to update ");
				locId = intScanner.nextInt();
				status = op2_update_distributor_location(distId, locId);
				break;
			case 0: 
				break;

			default: 
				System.out.println("Invalid choice! Please enter correct choice");
		}

		return status;
	}

	public int op2_update_distributor_name(int distId, String distName) throws SQLException{
		String query = "update Distributors set distName='"+distName+"' where distId="+distId;
		statement.executeUpdate(query);
		return 1;
	}

	public int op2_update_distributor_type(int distId, String distType) throws SQLException{
		String query = "update Distributors set distType='"+distType+"' where distId="+distId;
		statement.executeUpdate(query);
		return 1;
	}

	public int op2_update_distributor_balance(int distId, float balance) throws SQLException{
		String query = "update Distributors set balance="+balance+" where distId="+distId;
		statement.executeUpdate(query);
		return 1;
	}

	public int op2_update_distributor_contact(int distId, String contactPhone) throws SQLException{
		String query = "update Distributors set primaryContact='"+contactPhone+"' where distId="+distId;
		statement.executeUpdate(query);
		return 1;
	}

	public int op2_update_distributor_location(int distId, int locId) throws SQLException{
		String query = "select * from Locations where locId="+locId+" and distId="+distId;
		rs = statement.executeQuery(query);

		if(!rs.next()){
			System.out.println("Sorry, the given location ID does not exist for current distributor.");
			return 0;
		}

		int status=0;

		System.out.println("Distributor Location Edit Menu:");
		System.out.println("1. Update Contact Person");
		System.out.println("2. Update Phone Number");
		System.out.println("3. Update Address");
		System.out.println("4. Update City");
		System.out.println("Enter your choice: ");
		int choice = intScanner.nextInt();

		switch(choice)
		{
			case 1:
				String contactPerson;
				System.out.println("Enter New Name ");
				contactPerson = lineScanner.nextLine();
				status = op2_update_distributor_location_contact_person(distId, locId, contactPerson);
				break;
			case 2:
				String phoneNumber;
				System.out.println("Enter New Phone Number ");
				phoneNumber = lineScanner.nextLine();
				status = op2_update_distributor_location_phone_number(distId, locId, phoneNumber);
				break;
			case 3: 
				String addr;
				System.out.println("Enter New Address ");
				addr = lineScanner.nextLine();
				status = op2_update_distributor_location_address(distId, locId, addr);
				break;
			case 4:
				String city;
				System.out.println("Enter New City ");
				city = lineScanner.nextLine(); 
				status = op2_update_distributor_location_city(distId, locId, city);
				break;
			
			case 0: 
				break;

			default: 
				System.out.println("Invalid choice! Please enter correct choice");
		}
		return status;
		
	}

	public int op2_update_distributor_location_contact_person(int distId, int locId, String contactPerson) throws SQLException{
		String query = "update Locations set contactPerson='"+contactPerson+"' where locId="+locId+" and distId="+distId;
		statement.executeUpdate(query);
		return 1;
	}

	public int op2_update_distributor_location_phone_number(int distId, int locId, String phoneNumber) throws SQLException{
		String query = "update Locations set phoneNumber='"+phoneNumber+"' where locId="+locId+" and distId="+distId;
		statement.executeUpdate(query);
		return 1;
	}

	public int op2_update_distributor_location_address(int distId, int locId, String addr) throws SQLException{
		String query = "update Locations set addr='"+addr+"' where locId="+locId+" and distId="+distId;
		statement.executeUpdate(query);
		return 1;
	}

	public int op2_update_distributor_location_city(int distId, int locId, String city) throws SQLException{
		String query = "update Locations set city='"+city+"' where locId="+locId+" and distId="+distId;
		statement.executeUpdate(query);
		return 1;
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
	public int op5_bill_distributor(int distId, String generationDate) throws SQLException
	{
		String query = "select * from Distributors where distId="+distId;
		rs = statement.executeQuery(query);

		if(!rs.next()){
			System.out.println("Sorry, distributor with this ID does not exist.");
			return 0;
		}

		query = "Select * From Orders JOIN Locations ON Orders.locId=Locations.locId where (Orders.orderId not in (Select orderId from OrderBillMappings) and Locations.distID="+distId+")";
		rs = statement.executeQuery(query);


		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 

		headersList.add("Order ID");
		headersList.add("Order Date");
		headersList.add("Delivery Date: ");

		List<List<String>> rowsList = new ArrayList<>();

		int orderCnt=0;
		while(rs.next())
		{
			orderCnt++;
			List<String> row = new ArrayList<>(); 

			row.add(String.valueOf(rs.getInt("orderId")));
			row.add(rs.getString("orderDate"));
			row.add(rs.getString("deliveryDate"));

			rowsList.add(row);
		}

		if(orderCnt==0){
			System.out.println("Sorry, no unbilled orders exist for current distributor.");
			return 0;
		}

		System.out.println("Bills for following orders have not been generated for current distributor: ");
		System.out.println(tableGenerator.generateTable(headersList, rowsList));

		Scanner Scanner = new Scanner(System.in);
		System.out.println("Input Order IDs for which you want to generate Bill (separated only by commas): ");
		String orderIds = Scanner.nextLine();

		String[] OrderIds = orderIds.split(",");
		float billAmt = 0;
		for (int i = 0; i < OrderIds.length; i++)
		{
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

		op5_bill_distributor_helper(billAmt, generationDate, distId, OrderIds);
		
		return 1;

	}

	public void op5_bill_distributor_helper(float billAmt, String generationDate, int distId, String[] orderId) throws SQLException
	{
		String query = "insert into Bills(billAmt,generationDate,distId) values("+billAmt+",'"+generationDate+"', "+distId+");";
		rs = statement.executeQuery(query);
		
		rs = statement.getGeneratedKeys();
		rs.next();

		int billId = rs.getInt("billId");
		for(int i=0;i<orderId.length;i++)
		{
			query = "insert into OrderBillMappings(orderId, billId) values("+orderId[i]+","+billId+");";
			rs = statement.executeQuery(query);
		}

		//update distributor's balance
		query = "select balance from Distributors where distId="+distId;
		rs = statement.executeQuery(query);
		rs.next();
		float balance = rs.getFloat("balance");
		float updated_balance = balance+billAmt;

		query = "update Distributors set balance="+updated_balance+"where distId="+distId;
		statement.executeUpdate(query);
	}

	// ----------------------------------------------------------------- //
	// OPERATION 6
	// Payment
	public int op6_payment(int distId, String receiptDate) throws SQLException
	{
		String query = "select * from Distributors where distId="+distId;
		rs = statement.executeQuery(query);

		if(!rs.next()){
			System.out.println("Sorry, distributor with this ID does not exist.");
			return 0;
		}

		int status = 0;
		
		query = "Select * from Bills where receiptDate is NULL and distId="+distId;
		rs = statement.executeQuery(query);

		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 

		headersList.add("Bill ID");
		headersList.add("Bill Amount");
		headersList.add("Generation Date");
		headersList.add("Distributor ID");

		List<List<String>> rowsList = new ArrayList<>();

		int billCnt=0;
		while(rs.next())
		{
			billCnt++;
			List<String> row = new ArrayList<>(); 

			row.add(String.valueOf(rs.getInt("billId")));
			row.add(String.valueOf(rs.getFloat("billAmt")));
			row.add(rs.getString("generationDate"));
			row.add(String.valueOf(rs.getInt("distId")));

			rowsList.add(row);
		}

		if(billCnt==0){
			System.out.println("Sorry, no bill payments are pending for current distributor.");
			return 0;
		}

		System.out.println("Following Bills are Pending Payments");
		System.out.println(tableGenerator.generateTable(headersList, rowsList));

	
		System.out.println("Input Bill ID (Comma Seperated) for which you make Payment");
		Scanner Scanner = new Scanner(System.in);
		String billIds = Scanner.nextLine();

		String[] BillIds = billIds.split(",");

		for (int i = 0; i < BillIds.length; i++)
		{
			op6_payment_helper(BillIds[i], receiptDate,distId);
			System.out.println("Bill with id "+BillIds[i]+" Paid successfully");
		}
		return 1;
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
