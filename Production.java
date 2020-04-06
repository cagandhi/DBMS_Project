import java.sql.*;

public class Production
{
	private Statement statement = null;
	private ResultSet result = null;

	public Production(Connection connection)
	{
		try
		{
			statement = connection.createStatement();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void getName(String name)
	{
		System.out.println("Hello - "+name);
		try
		{
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

			System.out.println("table created successfully"); 
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}