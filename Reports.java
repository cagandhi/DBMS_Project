import java.sql.*;
import java.util.Date;

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

	

}