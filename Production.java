import java.sql.*;
import java.util.*;

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
	// Insert a new edition of a book
	public void op1_edition(int orderItemId, int pubId, float price, String pubDate, String isbn) throws SQLException
	{
		// Insert the new edition into OrderItems table
		String query = "insert into OrderItems values ("+orderItemId+","+pubId+","+price+",'"+pubDate+"')";
		statement.executeUpdate(query);

		// insert into editions table
		query = "insert into Editions values ("+orderItemId+","+pubId+",'"+isbn+"')";
		statement.executeUpdate(query);
	}

	// Insert a new issue of a periodic publication
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
	// Update the price of an item
	public void op2_update_price(float price, int orderItemId, int pubId) throws SQLException
	{
		String query = "update OrderItems set price="+price+" where orderItemId="+orderItemId+" and pubId="+pubId;
		statement.executeUpdate(query);
	}

	// Update the publication date of an item
	public void op2_update_pubDate(String pubDate, int orderItemId, int pubId) throws SQLException
	{
		String query = "update OrderItems set pubDate='"+pubDate+"' where orderItemId="+orderItemId+" and pubId="+pubId;
		statement.executeUpdate(query);
	}

	// Update the ISBN number of an Edition of a book
	public void op2_update_isbn(String isbn, int orderItemId, int pubId) throws SQLException
	{
		String query = "update Editions set ISBN='"+isbn+"' where orderItemId="+orderItemId+" and pubId="+pubId;
		statement.executeUpdate(query);
	}

	// Delete an Edition/Issue of a Book/Periodic publication
	public void op2_delete_edition_issue(int orderItemId, int pubId) throws SQLException
	{
		String query = "delete from OrderItems where orderItemId="+orderItemId+" and pubId="+pubId;
		statement.executeUpdate(query);
	}

	// Display the Editions/Issues related to a Publication (ID)
	public int op2_display_OrderItems(int pubId) throws SQLException
	{
		String query = "select * from OrderItems where pubId="+pubId;
		rs = statement.executeQuery(query);

		if(!rs.next()) // no rows
		{
			System.out.println("\nNo editions/issues found for this publication ID!");
			return 0;
		}
		else
		{
			System.out.println("\nThese are the editions/issues found for this publication ID: ");
			TableGenerator tableGenerator = new TableGenerator();
			List<String> headersList = new ArrayList<>(); 
			headersList.add("Publication ID");
			headersList.add("Edition/Issue No.");
			headersList.add("Price");
			headersList.add("Publication Date");
			
			List<List<String>> rowsList = new ArrayList<>();

			do
			{
				List<String> row = new ArrayList<>(); 
				row.add(String.valueOf(rs.getInt("pubId")));
				row.add(String.valueOf(rs.getInt("orderItemId")));
				row.add(String.valueOf(rs.getFloat("price")));
				row.add(rs.getString("pubDate"));

				rowsList.add(row);
			} while(rs.next());

			System.out.println(tableGenerator.generateTable(headersList, rowsList));
		}
		return 1;
	}

	// Display the articles in a periodic publication
	public int op2_display_Articles(int pubId, int orderItemId) throws SQLException
	{
		String query = "select * from Articles where pubId="+pubId+" and orderItemId="+orderItemId;
		rs = statement.executeQuery(query);

		if(!rs.next()) // no rows
		{
			System.out.println("\nNo articles found for this publication ID + issue no.!");
			return 0;
		}
		else
		{
			System.out.println("\nThese are the articles found for this publication ID + issue no.: ");
			TableGenerator tableGenerator = new TableGenerator();
			List<String> headersList = new ArrayList<>(); 
			headersList.add("Publication ID");
			headersList.add("Issue No.");
			headersList.add("Article Title");
			headersList.add("Article Text");
			headersList.add("Article Creation Date");
			
			List<List<String>> rowsList = new ArrayList<>();

			do
			{
				List<String> row = new ArrayList<>(); 
				row.add(String.valueOf(rs.getInt("pubId")));
				row.add(String.valueOf(rs.getInt("orderItemId")));
				row.add(rs.getString("title"));
				row.add(rs.getString("articleText"));
				row.add(rs.getString("creationDate"));

				rowsList.add(row);
			} while(rs.next());

			System.out.println(tableGenerator.generateTable(headersList, rowsList));
		}
		return 1;
	}

	// Display the chapters in a Book
	public int op2_display_Chapters(int pubId, int orderItemId) throws SQLException
	{
		String query = "select * from Chapters where pubId="+pubId+" and orderItemId="+orderItemId;
		rs = statement.executeQuery(query);

		if(!rs.next()) // no rows
		{
			System.out.println("\nNo chapters found for this publication ID + edition no.!");
			return 0;
		}
		else
		{
			System.out.println("\nThese are the chapters found for this publication ID + edition no.: ");
			TableGenerator tableGenerator = new TableGenerator();
			List<String> headersList = new ArrayList<>(); 
			headersList.add("Publication ID");
			headersList.add("Edition No.");
			headersList.add("Chapter Title");
			headersList.add("Chapter Text");
			headersList.add("Chapter Creation Date");
			
			List<List<String>> rowsList = new ArrayList<>();

			do
			{
				List<String> row = new ArrayList<>(); 
				row.add(String.valueOf(rs.getInt("pubId")));
				row.add(String.valueOf(rs.getInt("orderItemId")));
				row.add(rs.getString("title"));
				row.add(rs.getString("chapterText"));
				row.add(rs.getString("creationDate"));

				rowsList.add(row);
			} while(rs.next());

			System.out.println(tableGenerator.generateTable(headersList, rowsList));
		}
		return 1;
	}

	// ----------------------------------------------------------------- //
	// OPERATION 3
	// Insert a chapter into a Book
	public void op3_enter_chapter(int orderItemId, int pubId, String title, String chapterText, String chapterDate, String[] topicList, String[] authorList, boolean topicEmpty, boolean authorEmpty) throws SQLException
	{
		// Insert the chapter into the Chapters table
		String query = "insert into Chapters values ('"+title+"',"+orderItemId+","+pubId+",'"+chapterText+"','"+chapterDate+"')";
		statement.executeUpdate(query);

		// Check if the topics are given
		if(!topicEmpty) {
			// Iterate over each topic
			for (String topic : topicList) {
				// Check if the topic already exists in the Topics table
				query = "SELECT * FROM Topics where topicName='" + topic + "'";
				rs = statement.executeQuery(query);

				// If the topic does not exists in the Topics table enter it into the Topics table
				if (!rs.next()) {
					query = "INSERT INTO Topics values('" + topic + "')";
					statement.executeUpdate(query);
				}

				// Enter the chapter and topic mapping in the ChapterMapping table
				query = "insert into ChapterTopicMappings values ('" + title + "'," + orderItemId + "," + pubId + ",'" + topic + "')";
				statement.executeUpdate(query);
			}
		}
		// Check if authors are given
		if(!authorEmpty) {
			// Iterate over each author
			for (String authorId : authorList) {
				// Assign the chapter to the author by entering the values in the ChapterWrittenBy table
				query = "insert into ChapterWrittenBy values ('" + title + "'," + orderItemId + "," + pubId + "," + Integer.parseInt(authorId) + ")";
				statement.executeUpdate(query);
			}
		}
	}

	// Enter an article into a periodic publication
	public void op3_enter_article(int orderItemId, int pubId, String title, String articleText, String articleDate, String[] topicList, String[] authorList, boolean topicEmpty, boolean journalistEmpty) throws SQLException
	{
		// Insert the article into the Articles table
		String query = "insert into Articles values ('"+title+"',"+orderItemId+","+pubId+",'"+articleText+"','"+articleDate+"')";
		statement.executeUpdate(query);

		// Check if topics are given
		if(!topicEmpty) {
			// Iterate over each topic
			for (String topic : topicList) {
				// Check if the topic already exists in the Topics table
				query = "SELECT * FROM Topics where topicName='" + topic + "'";
				rs = statement.executeQuery(query);

				// If the topic does not exists, add it to the Topics table
				if (!rs.next()) {
					query = "INSERT INTO Topics values('" + topic + "')";
					statement.executeUpdate(query);
				}

				// Add the topic and article mapping in the ArticleTopicMappings table
				query = "insert into ArticleTopicMappings values ('" + title + "'," + orderItemId + "," + pubId + ",'" + topic + "')";
				statement.executeUpdate(query);
			}
		}

		// Check if journalists are given
		if(!journalistEmpty) {
			// Iterate over all the journalists
			for (String authorId : authorList) {
				// Assign the journalist the article by inserting their values in the ArticleWrittenBy table
				query = "insert into ArticleWrittenBy values ('" + title + "'," + orderItemId + "," + pubId + "," + Integer.parseInt(authorId) + ")";
				statement.executeUpdate(query);
			}
		}
	}

	// Update the title of the chapter in a particular edition of a book
	public void op3_update_chapter_title(int orderItemId, int pubId, String title, String newTitle) throws SQLException
	{
		String query = "update Chapters set title='"+newTitle+"' where title='"+title+"' and orderItemId="+orderItemId+" and pubId="+pubId;
		statement.executeUpdate(query);
	}

	// Update the title of the article in a particular issue of a periodic publication
	public void op3_update_article_title(int orderItemId, int pubId, String title, String newTitle) throws SQLException
	{
		String query = "update Articles set title='"+newTitle+"' where title='"+title+"' and orderItemId="+orderItemId+" and pubId="+pubId;
		statement.executeUpdate(query);
	}

	// Update the creation date of a chapter in an Edition of a Book
	public void op3_update_chapter_date(int orderItemId, int pubId, String title, String creationDate) throws SQLException
	{
		String query = "update Chapters set creationDate='"+creationDate+"' where title='"+title+"' and orderItemId="+orderItemId+" and pubId="+pubId;
		statement.executeUpdate(query);
	}

	// Update the creation date of an article in a issue of a periodic publication
	public void op3_update_article_date(int orderItemId, int pubId, String title, String creationDate) throws SQLException
	{
		String query = "update Articles set creationDate='"+creationDate+"' where title='"+title+"' and orderItemId="+orderItemId+" and pubId="+pubId;
		statement.executeUpdate(query);
	}

	//  Add an author to a chapter in an Edition of a Book
	public void op3_add_author_chapter(int orderItemId, int pubId, String title, int cmId) throws SQLException
	{
		String query = "insert into ChapterWrittenBy values ('"+title+"',"+orderItemId+","+pubId+","+cmId+")";
		statement.executeUpdate(query);
	}

	// Add a journalist to an article in an Issue of a periodic publication
	public void op3_add_journalist_article(int orderItemId, int pubId, String title, int cmId) throws SQLException
	{
		String query = "insert into ArticleWrittenBy values ('"+title+"',"+orderItemId+","+pubId+","+cmId+")";
		statement.executeUpdate(query);
	}

	// Remove an author from a chapter in an Edition of a Book
	public void op3_remove_author_chapter(int orderItemId, int pubId, String title, int cmId) throws SQLException
	{
		String query = "delete from ChapterWrittenBy where title='"+title+"' and orderItemId="+orderItemId+" and pubId="+pubId+" and cmId="+cmId;
		statement.executeUpdate(query);
	}

	// Remove a journalist from an article in an Issue of a periodic publication
	public void op3_remove_journalist_article(int orderItemId, int pubId, String title, int cmId) throws SQLException
	{
		String query = "delete from ArticleWrittenBy where title='"+title+"' and orderItemId="+orderItemId+" and pubId="+pubId+" and cmId="+cmId;
		statement.executeUpdate(query);
	}

	// Add topic to a chapter in an Edition of a Book
	public void op3_add_topic_chapter(int orderItemId, int pubId, String title, String topicName) throws SQLException
	{
		// Check if the topic already exists in the Topics table
		String query = "SELECT * FROM Topics where topicName='"+topicName+"'";
		rs = statement.executeQuery(query);

		// If the topic does not exists in the table, add it
		if(!rs.next()){
			query = "INSERT INTO Topics values('"+topicName+"')";
			statement.executeUpdate(query);
		}

		// Add a chapter and topic mapping in the ChapterTopicMappings table
		query = "insert into ChapterTopicMappings values ('"+title+"',"+orderItemId+","+pubId+",'"+topicName+"')";
		statement.executeUpdate(query);
	}

	// Add a topic to an article in an Issue of a periodic publication
	public void op3_add_topic_article(int orderItemId, int pubId, String title, String topicName) throws SQLException
	{
		// Check if the topic already exists in the Topics table
		String query = "SELECT * FROM Topics where topicName='"+topicName+"'";
		rs = statement.executeQuery(query);

		// If the topic does not exists in the table, add it
		if(!rs.next()){
			query = "INSERT INTO Topics values('"+topicName+"')";
			statement.executeUpdate(query);
		}

		// Add an article and topic mapping in the ArticleTopicMappings table
		query = "insert into ArticleTopicMappings values ('"+title+"',"+orderItemId+","+pubId+",'"+topicName+"')";
		statement.executeUpdate(query);
	}

	// Remove a topic from a chapter
	public void op3_remove_topic_chapter(int orderItemId, int pubId, String title, String topicName) throws SQLException
	{
		String query = "delete from ChapterTopicMappings where title='"+title+"' and orderItemId="+orderItemId+" and pubId="+pubId+" and topicName='"+topicName+"'";
		statement.executeUpdate(query);
	}

	// Remove a topic from an article
	public void op3_remove_topic_article(int orderItemId, int pubId, String title, String topicName) throws SQLException
	{
		String query = "delete from ArticleTopicMappings where title='"+title+"' and orderItemId="+orderItemId+" and pubId="+pubId+" and topicName='"+topicName+"'";
		statement.executeUpdate(query);
	}

	// Display all the topics associated with that chapter
	public void op3_display_chapter_topics(int orderItemId, int pubId, String title) throws SQLException
	{
		String query = "select * from ChapterTopicMappings where pubId="+pubId+" and orderItemId="+orderItemId+" and title='"+title+"'";
		rs = statement.executeQuery(query);

		// Check if topics are associated with the chapter
		if(!rs.next()) // no rows
		{
			System.out.println("\nNo topics associated with this chapter!");
		}
		else
		{
			System.out.println("\nThese are the topics associated with this chapter: ");
			TableGenerator tableGenerator = new TableGenerator();
			List<String> headersList = new ArrayList<>(); 
			headersList.add("Publication ID");
			headersList.add("Edition No.");
			headersList.add("Chapter Title");
			headersList.add("Topic Name");
			
			List<List<String>> rowsList = new ArrayList<>();

			do
			{
				List<String> row = new ArrayList<>(); 
				row.add(String.valueOf(rs.getInt("pubId")));
				row.add(String.valueOf(rs.getInt("orderItemId")));
				row.add(rs.getString("title"));
				row.add(rs.getString("topicName"));

				rowsList.add(row);
			} while(rs.next());

			System.out.println(tableGenerator.generateTable(headersList, rowsList));
		}
	}

	// Display all the topics associated with an article
	public void op3_display_article_topics(int orderItemId, int pubId, String title) throws SQLException
	{
		String query = "select * from ArticleTopicMappings where pubId="+pubId+" and orderItemId="+orderItemId+" and title='"+title+"'";
		rs = statement.executeQuery(query);

		// Check if there are topics associated with the article
		if(!rs.next()) // no rows
		{
			System.out.println("\nNo topics associated with this article!");
		}
		else
		{
			System.out.println("\nThese are the topics associated with this article: ");
			TableGenerator tableGenerator = new TableGenerator();
			List<String> headersList = new ArrayList<>(); 
			headersList.add("Publication ID");
			headersList.add("Issue No.");
			headersList.add("Article Title");
			headersList.add("Topic Name");
			
			List<List<String>> rowsList = new ArrayList<>();

			do
			{
				List<String> row = new ArrayList<>(); 
				row.add(String.valueOf(rs.getInt("pubId")));
				row.add(String.valueOf(rs.getInt("orderItemId")));
				row.add(rs.getString("title"));
				row.add(rs.getString("topicName"));

				rowsList.add(row);
			} while(rs.next());

			System.out.println(tableGenerator.generateTable(headersList, rowsList));
		}
	}

	// Display all the authors of the chapter
	public void op3_display_chapter_authors(int orderItemId, int pubId, String title) throws SQLException
	{
		String query = "select * from ChapterWrittenBy where pubId="+pubId+" and orderItemId="+orderItemId+" and title='"+title+"'";
		rs = statement.executeQuery(query);

		// Check if authors are associated with the chapter
		if(!rs.next()) // no rows
		{
			System.out.println("\nNo authors associated with this chapter!");
		}
		else
		{
			System.out.println("\nThese are the author IDs associated with this chapter: ");
			TableGenerator tableGenerator = new TableGenerator();
			List<String> headersList = new ArrayList<>(); 
			headersList.add("Publication ID");
			headersList.add("Issue No.");
			headersList.add("Chapter Title");
			headersList.add("Author ID");
			
			List<List<String>> rowsList = new ArrayList<>();

			do
			{
				List<String> row = new ArrayList<>(); 
				row.add(String.valueOf(rs.getInt("pubId")));
				row.add(String.valueOf(rs.getInt("orderItemId")));
				row.add(rs.getString("title"));
				row.add(rs.getString("cmId"));

				rowsList.add(row);
			} while(rs.next());

			System.out.println(tableGenerator.generateTable(headersList, rowsList));
		}
	}

	// Display all the journalists associated with an article
	public void op3_display_article_journalists(int orderItemId, int pubId, String title) throws SQLException
	{
		String query = "select * from ArticleWrittenBy where pubId="+pubId+" and orderItemId="+orderItemId+" and title='"+title+"'";
		rs = statement.executeQuery(query);

		// Check if the article is associated with journalists
		if(!rs.next()) // no rows
		{
			System.out.println("\nNo journalists associated with this article!");
		}
		else
		{
			System.out.println("\nThese are the journalist IDs associated with this article: ");
			TableGenerator tableGenerator = new TableGenerator();
			List<String> headersList = new ArrayList<>(); 
			headersList.add("Publication ID");
			headersList.add("Issue No.");
			headersList.add("Article Title");
			headersList.add("Journalist ID");
			
			List<List<String>> rowsList = new ArrayList<>();

			do
			{
				List<String> row = new ArrayList<>(); 
				row.add(String.valueOf(rs.getInt("pubId")));
				row.add(String.valueOf(rs.getInt("orderItemId")));
				row.add(rs.getString("title"));
				row.add(rs.getString("cmId"));

				rowsList.add(row);
			} while(rs.next());

			System.out.println(tableGenerator.generateTable(headersList, rowsList));
		}
	}


	// ----------------------------------------------------------------- //
	// OPERATION 4
	// Update the text of an article
	public void op4_update_text_article(String title, int orderItemId, int pubId, String articleText) throws SQLException
	{
		String query = "update Articles set articleText='"+articleText+"' where title='"+title+"' and orderItemId="+orderItemId+" and pubId="+pubId;
		statement.executeUpdate(query);
	}

	// ----------------------------------------------------------------- //
	// OPERATION 5
	// Find all the publications associated with a Topic
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

	// Find the publications published on a particular date
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

	// Find the publications which has a particular author
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
	// Add a payment to a person
	public void op6_insert_payment(int cmId, float amount, String payDate) throws SQLException
	{
		String query = "insert into Payrolls(cmId,amount,paymentDate,claimDate) values ("+cmId+","+amount+",'"+payDate+"',NULL)";
		statement.executeUpdate(query);
	}

	// Claim a payment
	public void op6_claim_payment(int payId, String claimDate) throws SQLException
	{
		String query = "update Payrolls set claimDate='"+claimDate+"' where payId="+payId;
		statement.executeUpdate(query);
	}

	// Display all the payments that have not been claimed
	public int op6_display_unclaimed_payments(int cmId) throws SQLException
	{
		String query = "select * from Payrolls where cmId="+cmId+" and claimDate is null";
		rs = statement.executeQuery(query);

		if(!rs.next()) // no rows
		{
			System.out.println("\nNo unclaimed payments present!");
			return 0;
		}
		else
		{
			System.out.println("\nThese are the unclaimed payments for the entered employee ID");
			TableGenerator tableGenerator = new TableGenerator();
			List<String> headersList = new ArrayList<>(); 
			headersList.add("Payment ID");
			headersList.add("Employee ID");
			headersList.add("Amount");
			headersList.add("Payment Date");

			List<List<String>> rowsList = new ArrayList<>();

			do
			{
				List<String> row = new ArrayList<>(); 
				row.add(String.valueOf(rs.getInt("payId")));
				row.add(String.valueOf(rs.getInt("cmId")));
				row.add(String.valueOf(rs.getFloat("amount")));
				row.add(rs.getString("paymentDate"));

				rowsList.add(row);
			} while(rs.next());

			System.out.println(tableGenerator.generateTable(headersList, rowsList));
		}
		return 1;
	}

	// ----------------------------------------------------------------- //
	// ----------------------------------------------------------------- //
}