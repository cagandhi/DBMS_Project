import java.sql.*;
import java.io.*;
import java.util.*;

public class Reports
{
	private Statement statement = null;
	private ResultSet rs = null;


	// CONSTRUCTOR
	public Reports(Connection connection)
	{
		try
		{
			statement = connection.createStatement();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void op1_get_monthly_reports() throws SQLException{

		Scanner intScanner = new Scanner(System.in);
			
		System.out.println("\nSUB-MENU (select which report you want t generate)");
		System.out.println("1. Number and total price of copies of each publication bought per distributor per month.");
		System.out.println("2. Total revenue of the publishing house.");
		System.out.println("3. Total expenses.");
		System.out.println("0. Exit this menu");
		System.out.println("Enter your choice: ");
		
		int choice = intScanner.nextInt();

		switch(choice)
		{
			case 1:
				op1_get_monthly_reports_pub_bought_per_month();
				break;
			case 2:
				op1_get_monthly_reports_total_revenue();
				break;
			case 3: 
				op1_get_monthly_reports_total_expenses(); 
				break;
			case 0: 
				break;

			default: 
				System.out.println("Invalid choice! Please enter correct choice");
		}
	}

	public void op1_get_monthly_reports_pub_bought_per_month() throws SQLException{
		String query = "select distId,distName,pubId,title,sum(quantity) as quantity,sum(price * quantity) as total_price, extract(year from orderDate) as orderYear,extract(month from orderDate) orderMonth from Publications natural join OrderItems natural join OrderContains natural join Orders natural join Locations natural join Distributors group by distId,pubId,orderYear,orderMonth";
		rs = statement.executeQuery(query);
		
		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 
		headersList.add("Distributor ID");
		headersList.add("Distributor Name");
		headersList.add("Publication ID");
		headersList.add("Title");
		headersList.add("Quantity");
		headersList.add("Total Price");
		headersList.add("Order Year");
		headersList.add("Order Month");

		List<List<String>> rowsList = new ArrayList<>();

		while(rs.next())
		{
			List<String> row = new ArrayList<>(); 
			row.add(String.valueOf(rs.getInt("distId")));
			row.add(rs.getString("distName"));
			row.add(String.valueOf(rs.getInt("pubId")));
			row.add(rs.getString("title"));
			row.add(String.valueOf(rs.getInt("quantity")));
			row.add(String.valueOf(rs.getFloat("total_price")));
			row.add(rs.getString("orderYear"));
			row.add(rs.getString("orderMonth"));

			rowsList.add(row);
		}

		System.out.println(tableGenerator.generateTable(headersList, rowsList));
	}

	public void op1_get_monthly_reports_total_revenue() throws SQLException{	
		String query = "select sum(price*quantity) as 'totalOrder', sum(shippingCost) as 'totalShipping', sum((price*quantity)+shippingCost) as 'totalRevenue' from OrderItems natural join OrderContains natural join Orders";
		rs = statement.executeQuery(query);

		rs.next();

		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 
		headersList.add("Total Order Cost");
		headersList.add("Total Shipping Cost");
		headersList.add("Total Revenue");

		List<List<String>> rowsList = new ArrayList<>();

		List<String> row = new ArrayList<>();
		row.add(String.valueOf(rs.getFloat("totalOrder")));
		row.add(String.valueOf(rs.getFloat("totalShipping")));
		row.add(String.valueOf(rs.getFloat("totalRevenue")));

		rowsList.add(row);

		System.out.println(tableGenerator.generateTable(headersList, rowsList));
	}

	public void op1_get_monthly_reports_total_expenses() throws SQLException{
		String query = "select sum(amount) as total_expenses from Payrolls";
		rs = statement.executeQuery(query);

		rs.next();

		System.out.println("\nTotal Expenses: "+rs.getString("total_expenses"));
		System.out.println("(Shipping cost has not been included because it has been considered as the cost incurred by distributor.)");
	}
		

	public void op2_get_total_distributors() throws SQLException{
		String query = "select count(*) as total_distributors from Distributors";
		rs = statement.executeQuery(query);

		rs.next();

		System.out.println("\nTotal Distributors: "+rs.getString("total_distributors"));
	}
	
	public void op3_calculate_total_revenue() throws SQLException{
		Scanner intScanner = new Scanner(System.in);
			
		System.out.println("\nSUB-MENU");
		System.out.println("1. Calculate total revenue (since inception) per city.");
		System.out.println("2. Calculate total revenue (since inception) per distributor.");
		System.out.println("3. Calculate total revenue (since inception) per location.");
		System.out.println("0. Exit this menu");
		System.out.println("Enter your choice: ");
		
		int choice = intScanner.nextInt();

		switch(choice)
		{
			case 1:
				op3_calculate_total_revenue_per_city();
				break;
			case 2:
				op3_calculate_total_revenue_per_distributor();
				break;
			case 3: 
				op3_calculate_total_revenue_per_location(); 
				break;
			case 0: 
				break;

			default: 
				System.out.println("Invalid choice! Please enter correct choice");
		}
	}

	public void op3_calculate_total_revenue_per_city() throws SQLException{
		String query = "select sum(price*quantity) as total_revenue,city from OrderItems natural join OrderContains natural join Orders natural join Locations group by city";
		rs = statement.executeQuery(query);

		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 

		headersList.add("Total Revenue");
		headersList.add("City");

		List<List<String>> rowsList = new ArrayList<>();

		while(rs.next())
		{
			List<String> row = new ArrayList<>(); 
			row.add(String.valueOf(rs.getFloat("total_revenue")));
			row.add(rs.getString("city"));

			rowsList.add(row);
		}

		System.out.println(tableGenerator.generateTable(headersList, rowsList));
	}

	public void op3_calculate_total_revenue_per_distributor() throws SQLException{
		String query = "select sum(price*quantity) as total_revenue,distId,distName from OrderItems natural join OrderContains natural join Orders natural join Locations natural join Distributors group by distId";
		rs = statement.executeQuery(query);

		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 

		headersList.add("Total Revenue");
		headersList.add("Distributor ID");
		headersList.add("Distributor Name");

		List<List<String>> rowsList = new ArrayList<>();

		while(rs.next())
		{
			List<String> row = new ArrayList<>(); 
			row.add(String.valueOf(rs.getFloat("total_revenue")));
			row.add(String.valueOf(rs.getInt("distId")));
			row.add(rs.getString("distName"));

			rowsList.add(row);
		}

		System.out.println(tableGenerator.generateTable(headersList, rowsList));
	}

	public void op3_calculate_total_revenue_per_location() throws SQLException{
		String query = "select sum(price*quantity) as total_revenue,locId,contactPerson from OrderItems natural join OrderContains natural join Orders natural join Locations group by locId";
		rs = statement.executeQuery(query);

		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 

		headersList.add("Total Revenue");
		headersList.add("Location ID");
		headersList.add("Contact person name");

		List<List<String>> rowsList = new ArrayList<>();

		while(rs.next())
		{
			List<String> row = new ArrayList<>(); 
			row.add(String.valueOf(rs.getFloat("total_revenue")));
			row.add(String.valueOf(rs.getInt("locId")));
			row.add(rs.getString("contactPerson"));

			rowsList.add(row);
		}

		System.out.println(tableGenerator.generateTable(headersList, rowsList));

	}

	public void op4_calculate_total_payments(String beginDate, String endDate) throws SQLException{
		String query = "select sum(amount) as total_payment, 'Editorial Work' as work_type from (	select * from Payrolls 	where paymentDate<='"+endDate+"' 	and paymentDate>'"+beginDate+"') as T1 natural join Editors union select sum(amount) as total_payment, 'Book Authorship' as work_type from (	select * from Payrolls 	where paymentDate<='"+endDate+"' 	and paymentDate>'"+beginDate+"') as T2 natural join Authors union select sum(amount) as total_payment, 'Article Authorship' as work_type from (	select * from Payrolls 	where paymentDate<='"+endDate+"' 	and paymentDate>'"+beginDate+"') as T3 natural join Journalists";
		rs = statement.executeQuery(query);

		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 

		headersList.add("Total Payment");
		headersList.add("Work Type");

		List<List<String>> rowsList = new ArrayList<>();

		while(rs.next())
		{
			List<String> row = new ArrayList<>(); 
			row.add(String.valueOf(rs.getFloat("total_payment")));
			row.add(rs.getString("work_type"));

			rowsList.add(row);
		}

		System.out.println(tableGenerator.generateTable(headersList, rowsList));
	}

}