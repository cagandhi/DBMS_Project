import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

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
		query = "insert into Editions values ("+orderItemId+","+pubId+",'"+isbn+"')";
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
	// OPERATION 3
	public void op3_enter_chapter(int orderItemId, int pubId, String title, String chapterText, String chapterDate, String[] topicList, String[] authorList) throws SQLException
	{
		String query = "insert into Chapters values ('"+title+"',"+orderItemId+","+pubId+",'"+chapterText+"','"+chapterDate+"')";
		statement.executeUpdate(query);

		for(String topic: topicList)
		{
			query = "insert into ChapterTopicMappings values ('"+title+"',"+orderItemId+","+pubId+",'"+topic+"')";
			statement.executeUpdate(query);
		}

		for(String authorId: authorList)
		{
			query = "insert into ChapterWrittenBy values ('"+title+"',"+orderItemId+","+pubId+","+Integer.parseInt(authorId)+")";
			statement.executeUpdate(query);
		}	
	}

	public void op3_enter_article(int orderItemId, int pubId, String title, String articleText, String articleDate, String[] topicList, String[] authorList) throws SQLException
	{
		String query = "insert into Articles values ('"+title+"',"+orderItemId+","+pubId+",'"+articleText+"','"+articleDate+"')";
		statement.executeUpdate(query);

		for(String topic: topicList)
		{
			query = "insert into ArticleTopicMappings values ('"+title+"',"+orderItemId+","+pubId+",'"+topic+"')";
			statement.executeUpdate(query);
		}

		for(String authorId: authorList)
		{
			query = "insert into ArticleWrittenBy values ('"+title+"',"+orderItemId+","+pubId+","+Integer.parseInt(authorId)+")";
			statement.executeUpdate(query);
		}	
	}

	public void op3_update_chapter_title(int orderItemId, int pubId, String title, String newTitle) throws SQLException
	{
		String query = "update Chapters set title='"+newTitle+"' where title='"+title+"' and orderItemId="+orderItemId+" and pubId="+pubId;
		statement.executeUpdate(query);
	}

	public void op3_update_article_title(int orderItemId, int pubId, String title, String newTitle) throws SQLException
	{
		String query = "update Articles set title='"+newTitle+"' where title='"+title+"' and orderItemId="+orderItemId+" and pubId="+pubId;
		statement.executeUpdate(query);
	}

	public void op3_update_chapter_date(int orderItemId, int pubId, String title, String creationDate) throws SQLException
	{
		String query = "update Chapters set creationDate='"+creationDate+"' where title='"+title+"' and orderItemId="+orderItemId+" and pubId="+pubId;
		statement.executeUpdate(query);
	}

	public void op3_update_article_date(int orderItemId, int pubId, String title, String creationDate) throws SQLException
	{
		String query = "update Articles set creationDate='"+creationDate+"' where title='"+title+"' and orderItemId="+orderItemId+" and pubId="+pubId;
		statement.executeUpdate(query);
	}

	public void op3_add_author_chapter(int orderItemId, int pubId, String title, int cmId) throws SQLException
	{
		String query = "insert into ChapterWrittenBy values ('"+title+"',"+orderItemId+","+pubId+","+cmId+")";
		statement.executeUpdate(query);
	}

	public void op3_add_journalist_article(int orderItemId, int pubId, String title, int cmId) throws SQLException
	{
		String query = "insert into ArticleWrittenBy values ('"+title+"',"+orderItemId+","+pubId+","+cmId+")";
		statement.executeUpdate(query);
	}

	public void op3_remove_author_chapter(int orderItemId, int pubId, String title, int cmId) throws SQLException
	{
		String query = "delete from ChapterWrittenBy where title='"+title+"' and orderItemId="+orderItemId+" and pubId="+pubId+" and cmId="+cmId;
		statement.executeUpdate(query);
	}

	public void op3_remove_journalist_article(int orderItemId, int pubId, String title, int cmId) throws SQLException
	{
		String query = "delete from ArticleWrittenBy where title='"+title+"' and orderItemId="+orderItemId+" and pubId="+pubId+" and cmId="+cmId;
		statement.executeUpdate(query);
	}

	public void op3_add_topic_chapter(int orderItemId, int pubId, String title, String topicName) throws SQLException
	{
		String query = "insert into ChapterTopicMappings values ('"+title+"',"+orderItemId+","+pubId+",'"+topicName+"')";
		statement.executeUpdate(query);
	}

	public void op3_add_topic_article(int orderItemId, int pubId, String title, String topicName) throws SQLException
	{
		String query = "insert into ArticleTopicMappings values ('"+title+"',"+orderItemId+","+pubId+",'"+topicName+"')";
		statement.executeUpdate(query);
	}

	public void op3_remove_topic_chapter(int orderItemId, int pubId, String title, String topicName) throws SQLException
	{
		String query = "delete from ChapterTopicMappings where title='"+title+"' and orderItemId="+orderItemId+" and pubId="+pubId+" and topicName='"+topicName+"'";
		statement.executeUpdate(query);
	}

	public void op3_remove_topic_article(int orderItemId, int pubId, String title, String topicName) throws SQLException
	{
		String query = "delete from ArticleTopicMappings where title='"+title+"' and orderItemId="+orderItemId+" and pubId="+pubId+" and topicName='"+topicName+"'";
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
		String query = "select P.title as PublicationTitle, noOfEditions as noOfEditionsOrIssueNo, '-' as ArticleTitle, topicName, 'Book' as BookOrArticle from Books natural join Publications P natural join BookTopicMappings where topicName='"+topicName+"' union select P.title as PublicationTitle, AT.orderItemId as noOfEditionsOrIssueNo, AT.title as ArticleTitle, topicName, 'Article' as 'BookOrArticle' from Publications P inner join ArticleTopicMappings AT on AT.pubId=P.pubId where AT.topicName='"+topicName+"'";
		rs = statement.executeQuery(query);

		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 
		headersList.add("Publication Title");
		headersList.add("NoOfEditionsOrIssueNo");
		headersList.add("Article Title");
		headersList.add("Topic Name");
		headersList.add("Book or Article");

		List<List<String>> rowsList = new ArrayList<>();

		while(rs.next())
		{
			List<String> row = new ArrayList<>(); 
			row.add(rs.getString("PublicationTitle"));
			row.add(String.valueOf(rs.getInt("noOfEditionsOrIssueNo")));
			row.add(rs.getString("ArticleTitle"));
			row.add(rs.getString("topicName"));
			row.add(rs.getString("BookOrArticle"));

			rowsList.add(row);
		}

		System.out.println(tableGenerator.generateTable(headersList, rowsList));
	}

	public void op5_find_pubDate(String pubDate) throws SQLException
	{
		String query = "select title as PublicationTitle, '-' as ArticleTitle, orderItemId as editionOrIssueNo, '-' as articleText, pubDate, 'Book' as 'BookOrArticle' from Publications natural join Books natural join OrderItems where pubDate='"+pubDate+"' union select P.title as PublicationTitle, A.title as ArticleTitle, A.orderItemId as editionOrIssueNo, articleText, creationDate as pubDate, 'Article' as 'BookOrArticle' from Publications P inner join Articles A on P.pubId=A.pubId where creationDate='"+pubDate+"'";
		rs = statement.executeQuery(query);

		/*
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
		*/

		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 
		headersList.add("Publication Title");
		headersList.add("Article title");
		headersList.add("Edition or issue no");
		headersList.add("Article Text");
		headersList.add("Publication Date");
		headersList.add("Book or Article");

		List<List<String>> rowsList = new ArrayList<>();

		while(rs.next())
		{
			List<String> row = new ArrayList<>(); 
			row.add(rs.getString("PublicationTitle"));
			row.add(rs.getString("ArticleTitle"));
			row.add(String.valueOf(rs.getInt("editionOrIssueNo")));
			row.add(rs.getString("articleText"));
			row.add(rs.getString("pubDate"));
			row.add(rs.getString("BookOrArticle"));

			rowsList.add(row);
		}

		System.out.println(tableGenerator.generateTable(headersList, rowsList));
	}

	public void op5_find_authorName(String authorName) throws SQLException
	{
		String query = "select P.title as PublicationTitle, orderItemId as editionOrIssueNo, CW.title as ChapterOrArticleTitle, C.creationDate as pubDate, cmName as 'Authors Name', 'Book' as 'BookOrArticle' from Books B natural join ChapterWrittenBy CW natural join ContentManagers natural join Chapters C inner join Publications P on B.pubId=P.pubId where cmName='"+authorName+"' union select P.title as PublicationTitle, orderItemId as editionOrIssueNo, A.title as ChapterOrArticleTitle, creationDate as pubDate, cmName as 'Authors Name', 'Article' as 'BookOrArticle' from Articles A natural join ArticleWrittenBy natural join ContentManagers inner join Publications P on A.pubId=P.pubId where cmName='"+authorName+"'";
		rs = statement.executeQuery(query);

		/*
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
		*/

		TableGenerator tableGenerator = new TableGenerator();
		List<String> headersList = new ArrayList<>(); 
		headersList.add("Publication Title");
		headersList.add("Edition or issue no");
		headersList.add("Chapter or Article title");
		headersList.add("Publication Date");
		headersList.add("Author's Name");
		headersList.add("Book or Article");

		List<List<String>> rowsList = new ArrayList<>();

		while(rs.next())
		{
			List<String> row = new ArrayList<>(); 
			row.add(rs.getString("PublicationTitle"));
			row.add(String.valueOf(rs.getInt("editionOrIssueNo")));
			row.add(rs.getString("ChapterOrArticleTitle"));
			row.add(rs.getString("pubDate"));
			row.add(rs.getString("Authors Name"));
			row.add(rs.getString("BookOrArticle"));

			rowsList.add(row);
		}

		System.out.println(tableGenerator.generateTable(headersList, rowsList));
	}

	// ----------------------------------------------------------------- //
	// OPERATION 6
	public void op6_insert_payment(int payId, int cmId, float amount, String payDate) throws SQLException
	{
		String query = "insert into Payrolls(payId,cmId,amount,paymentDate,claimDate) values ("+payId+","+cmId+","+amount+",'"+payDate+"',NULL)";
		statement.executeUpdate(query);
	}

	public void op6_claim_payment(int payId, String claimDate) throws SQLException
	{
		String query = "update Payrolls set claimDate='"+claimDate+"' where payId="+payId;
		statement.executeUpdate(query);
	}

	// ----------------------------------------------------------------- //
	// ----------------------------------------------------------------- //
}