import java.sql.*;
import java.io.*;
import java.util.*;

public class Reports
{
	private Statement statement = null;
	private ResultSet rs = null;


	// Constructor for the class object
	public Reports(Connection connection)
	{
		try
		{
			statement = connection.createStatement();
		} catch(Exception e) {
			System.out.println("Some error occured. Try again!");
			// e.printStackTrace();
		}
	}


	// Operation 1 of task 4: Generate montly reports
	public void op1_get_monthly_reports() throws SQLException{
		// scanner for input
		Scanner intScanner = new Scanner(System.in);
		
		// menu for asking user what report to generate			
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
				// call function to get number and total price of copies of each publication bought per distributor per month
				op1_get_monthly_reports_pub_bought_per_month();
				break;
			case 2:
				// call function to get total revenue
				op1_get_monthly_reports_total_revenue();
				break;
			case 3: 
				// call function to get total expenses
				op1_get_monthly_reports_total_expenses(); 
				break;
			case 0: 
				break;

			default: 
				System.out.println("Invalid choice! Please enter correct choice");
		}
	}

	// this function returns number and total price of copies of each publication bought per distributor per month
	public void op1_get_monthly_reports_pub_bought_per_month() throws SQLException{
		String query = "select distId,distName,pubId,title,sum(quantity) as quantity,sum(price * quantity) as total_price, extract(year from orderDate) as orderYear,extract(month from orderDate) orderMonth from Publications natural join OrderItems natural join OrderContains natural join Orders natural join Locations natural join Distributors group by distId,pubId,orderYear,orderMonth";
		rs = statement.executeQuery(query);
		
		// generate table to display number and total price of copies of each publication bought per distributor per month
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

		// print the report
		System.out.println(tableGenerator.generateTable(headersList, rowsList));
	}

	// this function returns total revenue per month
	public void op1_get_monthly_reports_total_revenue() throws SQLException{	
		String query = "select sum(totalOrder) as 'totalOrder', round(sum(totalShipping),2) as 'totalShipping', round(sum(totalRevenue),2) as 'totalRevenue',  extract(year from orderDate) as orderYear,extract(month from orderDate) orderMonth from (select sum(price*quantity) as 'totalOrder', shippingCost as 'totalShipping', sum(price*quantity)+shippingCost as 'totalRevenue', orderId, orderDate from OrderItems natural join OrderContains natural join Orders group by orderId) as S group by orderYear,orderMonth";
		rs = statement.executeQuery(query);


		// generate table to display total revenue per month
		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 
		headersList.add("Total Order Cost");
		headersList.add("Total Shipping Cost");
		headersList.add("Total Revenue");
		headersList.add("Month");
		headersList.add("Year");

		List<List<String>> rowsList = new ArrayList<>();

		while(rs.next()){

			List<String> row = new ArrayList<>();
			row.add(String.valueOf(rs.getFloat("totalOrder")));
			row.add(String.valueOf(rs.getFloat("totalShipping")));
			row.add(String.valueOf(rs.getFloat("totalRevenue")));
			row.add(rs.getString("orderMonth"));
			row.add(rs.getString("orderYear"));
			
			rowsList.add(row);
		}

		// print the report
		System.out.println(tableGenerator.generateTable(headersList, rowsList));
	}

	// this function returns total expenses per month
	public void op1_get_monthly_reports_total_expenses() throws SQLException{
		String query = "select sum(amount) as total_expenses,extract(year from paymentDate) as paymentYear,extract(month from paymentDate) paymentMonth from Payrolls group by paymentYear,paymentMonth ";
		rs = statement.executeQuery(query);

		// generate table to display total expences per month
		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 
		headersList.add("Total Expenses");
		headersList.add("Month");
		headersList.add("Year");

		List<List<String>> rowsList = new ArrayList<>();

		while(rs.next()){

			List<String> row = new ArrayList<>();
			row.add(String.valueOf(rs.getFloat("total_expenses")));
			row.add(rs.getString("paymentMonth"));
			row.add(rs.getString("paymentYear"));
			
			rowsList.add(row);
		}

		// print the report
		System.out.println(tableGenerator.generateTable(headersList, rowsList));
		System.out.println("(Shipping cost has not been included because it has been considered as the cost incurred by distributor.)");
	}
		
	// Operation 2 of task 4: Calculate the total current number of distributors
	public void op2_get_total_distributors() throws SQLException{
		String query = "select count(*) as total_distributors from Distributors";
		rs = statement.executeQuery(query);

		rs.next();
		// print total number of distributors
		System.out.println("\nTotal Distributors: "+rs.getString("total_distributors"));
	}
	
	// Operation 3 of task 4: calculate total revenue (since inception) per city, per distributor, and per location
	public void op3_calculate_total_revenue() throws SQLException{
		
		// scanner for input
		Scanner intScanner = new Scanner(System.in);
			
		// ask user to select what type of report they want
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
				// call function to calculate total revenue per city
				op3_calculate_total_revenue_per_city();
				break;
			case 2:
				// call function to calculate total revenue per distributor
				op3_calculate_total_revenue_per_distributor();
				break;
			case 3: 
				// call function to calculate total revenue per location
				op3_calculate_total_revenue_per_location(); 
				break;
			case 0: 
				break;

			default: 
				System.out.println("Invalid choice! Please enter correct choice");
		}
	}

	// this function to calculates total revenue per city
	public void op3_calculate_total_revenue_per_city() throws SQLException{
		String query = "select sum(totalOrder) as 'totalOrder', sum(totalShipping) as 'totalShipping', sum(totalRevenue) as 'totalRevenue', city from (select sum(price*quantity) as 'totalOrder', shippingCost as 'totalShipping', sum(price*quantity)+shippingCost as 'totalRevenue', city, orderId from OrderItems natural join OrderContains natural join Orders natural join Locations group by orderId, city) as S group by city";
		rs = statement.executeQuery(query);

		// generate table to show total revenue per city
		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 

		headersList.add("Total Order Cost");
		headersList.add("Total Shipping Cost");
		headersList.add("Total Revenue");
		headersList.add("City");

		List<List<String>> rowsList = new ArrayList<>();

		while(rs.next())
		{
			List<String> row = new ArrayList<>(); 
			row.add(String.valueOf(rs.getFloat("totalOrder")));
			row.add(String.valueOf(rs.getFloat("totalShipping")));
			row.add(String.valueOf(rs.getFloat("totalRevenue")));
			row.add(rs.getString("city"));

			rowsList.add(row);
		}

		// print the report
		System.out.println(tableGenerator.generateTable(headersList, rowsList));
	}

	// this function to calculates total revenue per distributor
	public void op3_calculate_total_revenue_per_distributor() throws SQLException{
		String query = "select sum(totalOrder) as 'totalOrder', sum(totalShipping) as 'totalShipping', sum(totalRevenue) as 'totalRevenue', distId, distName from (select sum(price*quantity) as 'totalOrder', shippingCost as 'totalShipping', sum(price*quantity)+shippingCost as 'totalRevenue', orderId, distId, distName from OrderItems natural join OrderContains natural join Orders natural join Locations natural join Distributors group by orderId, distId) as S group by distId";
		rs = statement.executeQuery(query);

		// generate table to show total revenue per distributor
		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 

		headersList.add("Total Order Cost");
		headersList.add("Total Shipping Cost");
		headersList.add("Total Revenue");
		headersList.add("Distributor ID");
		headersList.add("Distributor Name");

		List<List<String>> rowsList = new ArrayList<>();

		while(rs.next())
		{
			List<String> row = new ArrayList<>(); 
			row.add(String.valueOf(rs.getFloat("totalOrder")));
			row.add(String.valueOf(rs.getFloat("totalShipping")));
			row.add(String.valueOf(rs.getFloat("totalRevenue")));
			row.add(String.valueOf(rs.getInt("distId")));
			row.add(rs.getString("distName"));

			rowsList.add(row);
		}

		// print the report
		System.out.println(tableGenerator.generateTable(headersList, rowsList));
	}

	// this function to calculates total revenue per location
	public void op3_calculate_total_revenue_per_location() throws SQLException{
		String query = "select sum(totalOrder) as 'totalOrder', sum(totalShipping) as 'totalShipping', sum(totalRevenue) as 'totalRevenue', addr, city, locId from (select sum(price*quantity) as 'totalOrder', shippingCost as 'totalShipping', sum(price*quantity)+shippingCost as 'totalRevenue', orderId, locId, addr, city from OrderItems natural join OrderContains natural join Orders natural join Locations group by orderId, locId) as S group by locId";
		rs = statement.executeQuery(query);
		
		// generate table to show total revenue per location
		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 

		headersList.add("Total Order Cost");
		headersList.add("Total Shipping Cost");
		headersList.add("Total Revenue");
		headersList.add("Location address");
		headersList.add("Location city");
		headersList.add("Location ID");

		List<List<String>> rowsList = new ArrayList<>();

		while(rs.next())
		{
			List<String> row = new ArrayList<>(); 
			row.add(String.valueOf(rs.getFloat("totalOrder")));
			row.add(String.valueOf(rs.getFloat("totalShipping")));
			row.add(String.valueOf(rs.getFloat("totalRevenue")));
			row.add(rs.getString("addr"));
			row.add(rs.getString("city"));
			row.add(String.valueOf(rs.getInt("locId")));

			rowsList.add(row);
		}

		// print the report
		System.out.println(tableGenerator.generateTable(headersList, rowsList));

	}

	// Operation 4 of task 4: Calculate total payments to the editors and authors, per time period and per work type (book authorship, article authorship, or editorial work)
	public void op4_calculate_total_payments(String beginDate, String endDate) throws SQLException{
		String query = "select sum(amount) as total_payment, 'Editorial Work' as work_type from (	select * from Payrolls 	where paymentDate<='"+endDate+"' 	and paymentDate>'"+beginDate+"') as T1 natural join Editors union select sum(amount) as total_payment, 'Book Authorship' as work_type from (	select * from Payrolls 	where paymentDate<='"+endDate+"' 	and paymentDate>'"+beginDate+"') as T2 natural join Authors union select sum(amount) as total_payment, 'Article Authorship' as work_type from (	select * from Payrolls 	where paymentDate<='"+endDate+"' 	and paymentDate>'"+beginDate+"') as T3 natural join Journalists";
		rs = statement.executeQuery(query);

		// generate table to show total payments within given date range for all type of workers 
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

		// print the report
		System.out.println(tableGenerator.generateTable(headersList, rowsList));
	}

}