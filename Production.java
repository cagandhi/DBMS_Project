import java.sql.*;
import java.util.Date;

public class Production
{
	private Statement statement = null;
	private ResultSet rs = null;


	// CONSTRUCTOR
	public Production(Connection connection)
	{
		try
		{
			statement = connection.createStatement();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	// ----------------------------------------------------------------- //
	// OPERATION 1
	// for editions
	public void op1_edition(int orderItemId, int pubId, float price, String pubDate, String isbn) throws SQLException
	{
		String query = "insert into OrderItems values ("+orderItemId+","+pubId+","+price+",'"+pubDate+"')";
		statement.executeUpdate(query);

		// insert into editions
		query = "insert into Editions values ("+orderItemId+","+pubId+","+isbn+")";
		statement.executeUpdate(query);
	}

	// for issues
	public void op1_issue(int orderItemId, int pubId, float price, String pubDate, int issueNo) throws SQLException
	{
		String query = "insert into OrderItems values ("+orderItemId+","+pubId+","+price+",'"+pubDate+"')";
		statement.executeUpdate(query);

		// insert into editions
		query = "insert into Issues values ("+orderItemId+","+pubId+","+issueNo+")";
		statement.executeUpdate(query);
	}

	// ----------------------------------------------------------------- //
	// OPERATION 2
	public void op2_update_price(float price, int orderItemId, int pubId) throws SQLException
	{
		String query = "update OrderItems set price="+price+" where orderItemId="+orderItemId+" and pubId="+pubId;
		statement.executeUpdate(query);
	}

	public void op2_update_pubDate(String pubDate, int orderItemId, int pubId) throws SQLException
	{
		String query = "update OrderItems set pubDate='"+pubDate+"' where orderItemId="+orderItemId+" and pubId="+pubId;
		statement.executeUpdate(query);
	}

	public void op2_update_isbn(String isbn, int orderItemId, int pubId) throws SQLException
	{
		String query = "update Editions set ISBN='"+isbn+"' where orderItemId="+orderItemId+" and pubId="+pubId;
		statement.executeUpdate(query);
	}

	public void op2_delete_edition_issue(int orderItemId, int pubId) throws SQLException
	{
		String query = "delete from OrderItems where orderItemId="+orderItemId+" and pubId="+pubId;
		statement.executeUpdate(query);
	}

	// ----------------------------------------------------------------- //
	// ----------------------------------------------------------------- //
	// ----------------------------------------------------------------- //

	public void getName(String name)
	{
		System.out.println("Hello - "+name);
		try
		{
			statement.executeUpdate("drop table Test");
			statement.executeUpdate("create table Test(pid int primary key, nm varchar(45) not null)");
		} catch(SQLException e) { }

		try
		{
			statement.executeUpdate("insert into Test values (1,'chintan')");
			statement.executeUpdate("insert into Test values (2,'modi')");
			statement.executeUpdate("insert into Test values (3,'himol')");
			statement.executeUpdate("delete from Test where pid=3");
			statement.executeUpdate("update Test set nm='Joy Mudi' where pid=2");
			statement.executeUpdate("insert into Test values (4,'st')");
			
			rs = statement.executeQuery("select * from Test");

			while(rs.next())
			{
				System.out.println(rs.getInt("pid") + " - " + rs.getString("nm")); 
			}

			System.out.println("table created successfully"); 
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

}