import java.sql.*;
import java.io.*;
import java.util.*;

/**
 * 
 * Acknowledgments: This example is a modification of code provided by Dimitri
 * Rakitine. Further modified by Shrikanth N C for MySql(MariaDB) support.
 * Replace all $USER$ with your unity id and $PASSWORD$ with your 9 digit
 * student id or updated password (if changed)
 * 
**/

public class Project {
	static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/cagandhi";
	// Put your oracle ID and password here

	static final String createFile = "create_final.sql";
	static final String insertFile = "insert_final.sql";

	private static Connection connection = null;
	private static Statement statement = null;
	private static ResultSet result = null;

	public static void main(String[] args) {

		initialize();

		while(true)
		{
			System.out.println("\nMAIN MENU");
			System.out.println("1. Editing and Publishing");
			System.out.println("2. Production of a book edition or of an issue of a publication");
			System.out.println("3. Distribution");
			System.out.println("4. Reports");
			System.out.println("0. Exit");
			System.out.println("Enter your choice: ");

			Scanner scanner = new Scanner(System.in);
			int main_choice = scanner.nextInt();

			switch(main_choice)
			{
				case 1: System.out.println("In case 1"); // editing and publishing section
					break;

				case 2: System.out.println("In case 2"); // production section
					
					// pass connection so the class can work on SQL queries
					Production prod = new Production(connection);

					// call instance methods of class
					prod.getName("chintan");
					
					break;

				case 3: System.out.println("In case 3"); // distribution section
					break;

				case 4: System.out.println("In case 4"); // reports section
					break;

				case 0: // exit program
					close();
					System.exit(0);

				default:
					System.out.println("Invalid choice! Please enter correct choice");
			}
		}
		
		/**
		try {
			
			
			boolean canAfford = checkAbilityToStudy("Todd");
			// ************************************************************************

			// --------- MODIFIED CODE START ---------

			// MOD - try catch block to catch any SQLException occurring in modifyALittleBit2().
			try
			{
				// MOD - set auto commit mode to false - this means that the changes made by SQL queries won't be committed to the database unless we call the commit() function manually.
				connection.setAutoCommit(false);

				// modifyALittleBit1();
				modifyALittleBit2();

				// MOD - call commit() manually to permanently commit changes to database as a single transaction.
				// this statement will not be reached if one of the update statements in modifyALittleBit2() is not executed.
				connection.commit();
			}
			// MOD - Catching a SQLException would mean that one of the update queries is not executed properly and hence rollback the database to its previous state.
			catch(SQLException se) 
			{
				connection.rollback();
			}
			// MOD - good practice to again enable the default auto-commit mode to prevent any other issues.
			finally 
			{
				connection.setAutoCommit(true);
			}
			// --------- MODIFIED CODE END ---------

			boolean canAfford1 = checkAbilityToStudy("Angela");

			if (canAfford == canAfford1) {
				System.out.println("Success");
			} else {
				System.out.println("Failure");
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		**/
		// ***********************************************************************
		// close();
	}

	public static void resetDatabase() {
		createTables();
		populateTables();
	}

	private static void initialize() {
		try {
			connectToDatabase();
			resetDatabase();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	private static void createTables() {
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(createFile));

			String line;

			while((line = br.readLine()) != null) {
				// process the line.
				if(!line.isEmpty())
				{
					statement.executeUpdate(line);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private static void populateTables() {
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(insertFile));

			String line;

			while((line = br.readLine()) != null) {
				// process the line.
				if(!line.isEmpty())
				{
					statement.executeUpdate(line);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private static void connectToDatabase() throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");

		String user = "cagandhi";
		String password = "200315238";

		connection = DriverManager.getConnection(jdbcURL, user, password);
		statement = connection.createStatement();

		try {
			// statement.executeUpdate("DROP TABLE Students");
			// statement.executeUpdate("DROP TABLE Schools");
			statement.executeUpdate("drop table OrderContains,OrderBillMappings,Orders,Bills,Locations,Distributors,ArticleWrittenBy,ArticleTopicMappings,Articles,ChapterWrittenBy,ChapterTopicMappings,Chapters,Topics,Issues,Editions,ItemEditedBy,OrderItems,Payrolls,Journalists,Authors,Editors,ContentManagers,PeriodicPublications,Periodicity,Books,Publications");
		} catch (SQLException e) {
		}
	}

	private static void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (result != null) {
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}