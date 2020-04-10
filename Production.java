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
	// OPERATION 4
	public void op4_update_text_article(String title, int orderItemId, int pubId, String articleText) throws SQLException
	{
		String query = "update Articles set articleText='"+articleText+"' where title='"+title+"' and orderItemId="+orderItemId+" and pubId="+pubId;
		statement.executeUpdate(query);
	}

	// ----------------------------------------------------------------- //
	// OPERATION 5
	public void op5_find_topic(String topicName) throws SQLException
	{
		String query = "select distinct P.title as PublicationTitle, noOfEditions as noOfEditionsOrIssueNo, '-' as ArticleTitle, topicName, 'Book' as 'BookOrArticle' from Books B natural join ChapterTopicMappings inner join Publications P on B.pubId = P.pubId where topicName='"+topicName+"' union select P.title as PublicationTitle, AT.orderItemId as noOfEditionsOrIssueNo, AT.title as ArticleTitle, topicName, 'Article' as 'BookOrArticle' from Publications P inner join ArticleTopicMappings AT on AT.pubId=P.pubId where AT.topicName='"+topicName+"'";
		rs = statement.executeQuery(query);

		int cnt = 1;
		while(rs.next())
		{
			System.out.println("\nRECORD "+cnt+": ");
			System.out.println("Publication Title: "+rs.getString("PublicationTitle"));
			System.out.println("No of editions or issue no.: "+rs.getInt("noOfEditionsOrIssueNo"));
			System.out.println("Article title: "+rs.getString("ArticleTitle"));
			System.out.println("Topic Name: "+rs.getString("topicName"));
			System.out.println("Book or Article: "+rs.getString("BookOrArticle"));
			cnt++;
		}
	}

	public void op5_find_pubDate(String pubDate) throws SQLException
	{
		String query = "select title as PublicationTitle, '-' as ArticleTitle, orderItemId as editionOrIssueNo, '-' as articleText, pubDate, 'Book' as 'BookOrArticle' from Publications natural join Books natural join OrderItems where pubDate='"+pubDate+"' union select P.title as PublicationTitle, A.title as ArticleTitle, A.orderItemId as editionOrIssueNo, articleText, creationDate as pubDate, 'Article' as 'BookOrArticle' from Publications P inner join Articles A on P.pubId=A.pubId where creationDate='"+pubDate+"'";
		rs = statement.executeQuery(query);

		int cnt = 1;
		while(rs.next())
		{
			System.out.println("\nRECORD "+cnt+": ");
			System.out.println("Publication Title: "+rs.getString("PublicationTitle"));
			System.out.println("Article title: "+rs.getString("ArticleTitle"));
			System.out.println("Edition or issue no.: "+rs.getInt("editionOrIssueNo"));
			System.out.println("Article Text: "+rs.getString("articleText"));
			System.out.println("Publication Date: "+rs.getString("pubDate"));
			System.out.println("Book or Article: "+rs.getString("BookOrArticle"));
			cnt++;
		}
	}

	public void op5_find_authorName(String authorName) throws SQLException
	{
		String query = "select P.title as PublicationTitle, orderItemId as editionOrIssueNo, CW.title as ChapterOrArticleTitle, C.creationDate as pubDate, cmName as 'Authors Name', 'Book' as 'BookOrArticle' from Books B natural join ChapterWrittenBy CW natural join ContentManagers natural join Chapters C inner join Publications P on B.pubId=P.pubId where cmName='"+authorName+"' union select P.title as PublicationTitle, orderItemId as editionOrIssueNo, A.title as ChapterOrArticleTitle, creationDate as pubDate, cmName as 'Authors Name', 'Article' as 'BookOrArticle' from Articles A natural join ArticleWrittenBy natural join ContentManagers inner join Publications P on A.pubId=P.pubId where cmName='"+authorName+"'";
		rs = statement.executeQuery(query);

		int cnt = 1;
		while(rs.next())
		{
			System.out.println("\nRECORD "+cnt+": ");
			System.out.println("Publication Title: "+rs.getString("PublicationTitle"));
			System.out.println("Edition or issue no.: "+rs.getInt("editionOrIssueNo"));
			System.out.println("Chapter or Article title: "+rs.getString("ChapterOrArticleTitle"));
			System.out.println("Publication Date: "+rs.getString("pubDate"));
			System.out.println("Author's Name: "+rs.getString("Authors Name"));
			System.out.println("Book or Article: "+rs.getString("BookOrArticle"));
			cnt++;
		}
	}

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