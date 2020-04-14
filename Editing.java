import java.sql.*;
import java.util.*;

public class Editing {
    private Statement statement = null;
    private ResultSet rs = null;


    // CONSTRUCTOR
    public Editing(Connection connection)
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
    // Insert new Book
    public void op1_insert_pub_book(String pubTitle, int pubId, int edition, float price, String ISBN, String pubDate, String[] topicList, boolean topicEmpty) throws SQLException{
        // Insert the book into Publications table
        String query = "INSERT INTO Publications values("+pubId+",'"+pubTitle+"')";
        statement.executeUpdate(query);

        // Insert the book into the Books table
        query = "INSERT INTO Books(pubId) values("+pubId+")";
        statement.executeUpdate(query);
        // If topics is not empty, insert a relevant topic mapping with the book
        if(!topicEmpty) {
            for (String topic : topicList) {
                // Check if the topic already exists
                String check_query = "SELECT * FROM Topics where topicName='" + topic + "'";
                rs = statement.executeQuery(check_query);
                // If topic does not exist, make an entry of that topic in the Topics table
                if (!rs.next()) {
                    query = "INSERT INTO Topics values('" + topic + "')";
                    statement.executeUpdate(query);
                }
                // Insert the Book and topic mapping in the BookTopicMappings table
                query = "INSERT INTO BookTopicMappings values(" + pubId + ",'" + topic + "')";
                statement.executeUpdate(query);
            }
        }

        // Insert the Book into OrderItems table so that it can be used in an order
        query = "INSERT INTO OrderItems values("+edition+","+pubId+","+price+",'"+pubDate+"')";
        statement.executeUpdate(query);

        // Insert an Edition of the book
        query = "INSERT INTO Editions values("+edition+","+pubId+",'"+ISBN+"')";
        statement.executeUpdate(query);
    }

    // Insert new periodic publication
    public void op1_insert_pub_periodic(String pubTitle, int pubId, String periodicityType, String frequency, int issueNo, float price, String pubDate) throws SQLException{

        // Insert the Periodic publication into Publications table
        String query = "INSERT INTO Publications values ("+pubId+",'"+pubTitle+"')";
        statement.executeUpdate(query);

        // Insert it in the PeriodicPublications table
        query = "INSERT INTO PeriodicPublications values ("+pubId+",'"+periodicityType+"','"+frequency+"')";
        statement.executeUpdate(query);

        // Insert it in the OrderItems table
        query = "INSERT INTO OrderItems values("+issueNo+","+pubId+","+price+",'"+pubDate+"')";
        statement.executeUpdate(query);

        // Insert a new Issue of the Periodic publication
        query = "INSERT INTO Issues(orderItemId,pubId) values("+issueNo+","+pubId+")";
        statement.executeUpdate(query);
    }

    // Insert a chapter in a book
    public void op1_insert_chapter(int pubId, int orderItemId, String creationDate) throws SQLException{
        String title, chapterText, topic, ids;
        boolean authorEmpty=false, topicEmpty=false;
        int[] cmId = new int[0];

        Scanner lineScanner = new Scanner(System.in);

        System.out.println("Enter the Title of chapter: ");
        title = lineScanner.nextLine();
        System.out.println("Enter the Chapter Text: ");
        chapterText = lineScanner.nextLine();

        System.out.println("Enter the topics for this chapter (separated by commas only, press ENTER to skip): ");
        topic = lineScanner.nextLine();
        // Check if topics are entered
        if(topic.isEmpty()){
            topicEmpty=true;
        }
        String[] topics = topic.split(",");

        System.out.println("Enter the IDs of authors of this chapter (separated by commas only, press ENTER to skip): ");
        ids = lineScanner.nextLine();
        // Check if authors are entered
        if(ids.isEmpty()){
            authorEmpty=true;
        }
        else{
            String[] idArray = ids.split(",");
            cmId = new int[idArray.length];

            for(int i=0;i<idArray.length;i++){
                cmId[i]=Integer.parseInt(idArray[i]);
            }
        }
        

        op5_insert_chapter(title, orderItemId, pubId, chapterText, creationDate, topics, cmId, authorEmpty, topicEmpty);
        System.out.println("Chapter successfully inserted!");

    }

    public void op1_insert_article(int pubId, int orderItemId, String creationDate) throws SQLException{
        boolean journalistEmpty=false, topicEmpty=false;
        String title, articleText, topic, ids;
        int[] cmId = new int[0];

        Scanner lineScanner = new Scanner(System.in);


        System.out.println("Enter the Title of article: ");
        title = lineScanner.nextLine();
        System.out.println("Enter the Article Text: ");
        articleText = lineScanner.nextLine();

        System.out.println("Enter the of topics for this article (separated by commas only, press ENTER to skip): ");
        topic = lineScanner.nextLine();
        // Check if topics are entered
        if(topic.isEmpty()){
            topicEmpty=true;
        }
        String[] topics = topic.split(",");

        System.out.println("Enter the IDs of journalists of this chapter (separated commas only, press ENTER to skip): ");
        ids = lineScanner.nextLine();
        // Check if journalists are entered
        if(ids.isEmpty()){
            journalistEmpty=true;
        }
        else{
            String[] idArray = ids.split(",");
            cmId = new int[idArray.length];

            for(int i=0;i<idArray.length;i++){
                cmId[i]=Integer.parseInt(idArray[i]);
            }
        }


        op5_insert_article(title, orderItemId, pubId, articleText, creationDate, topics, cmId, journalistEmpty, topicEmpty);
        System.out.println("Article successfully inserted!");

    }

    // ----------------------------------------------------------------- //
    // OPERATION 2
    // Update the title of a publication
    public void op2_update_pub(String pubTitle, int pubId) throws SQLException{
        String query = "UPDATE Publications SET title='"+pubTitle+"' WHERE pubId="+pubId;
        statement.executeUpdate(query);
    }

    // Update the periodicity type of a Periodic publication
    public void op2_update_periodicty(int pubId, String periodicityType) throws SQLException{
        String query = "UPDATE PeriodicPublications SET periodicityType='"+periodicityType+"' WHERE pubId="+pubId;
        statement.executeUpdate(query);
    }

    // Update the Frequency of a Periodic publication
    public void op2_update_frequency(int pubId, String frequency) throws SQLException{
        String query = "UPDATE PeriodicPublications SET frequency='"+frequency+"' WHERE pubId="+pubId;
        statement.executeUpdate(query);
    }

    // ----------------------------------------------------------------- //
    // OPERATION 3
    // Assign an editor to a publication
    public void op3_assign_editor_pub(int[] cmId, int orderItemId, int pubId) throws SQLException{
        for (int cId : cmId) {
            String query = "INSERT INTO ItemEditedBy(cmId,orderItemId,pubId) values(" + cId + "," + orderItemId + "," + pubId + ")";
            statement.executeUpdate(query);
        }
    }

    // ----------------------------------------------------------------- //
    // OPERATION 4
    // List all the publications an editor is responsible for
    public void op4_find_editor_pub(int cmId) throws SQLException{
        String query = "SELECT p.pubId, p.title, o.orderItemId, o.pubDate FROM ItemEditedBy ie NATURAL JOIN Publications p NATURAL JOIN OrderItems o WHERE ie.cmId="+cmId;
        rs = statement.executeQuery(query);

        /*
        int count=1;
        while(rs.next()){
            System.out.println("\nRecord "+count+": ");
            System.out.println("Publication ID: "+rs.getString("pubId"));
            System.out.println("Publication Title: "+rs.getString("title"));
            count++;
        }
        */

        TableGenerator tableGenerator = new TableGenerator();
        List<String> headersList = new ArrayList<>();
        headersList.add("Publication ID");
        headersList.add("Publication Title");
        headersList.add("Edition or Issue No.");
        headersList.add("Publication Date");

        List<List<String>> rowsList = new ArrayList<>();

        while(rs.next())
        {
            List<String> row = new ArrayList<>();

            row.add(rs.getString("pubId"));
            row.add(rs.getString("title"));
            row.add(rs.getString("orderItemId"));
            row.add(rs.getString("pubDate"));

            rowsList.add(row);
        }

        System.out.println(tableGenerator.generateTable(headersList, rowsList));
    }

    // ----------------------------------------------------------------- //
    // OPERATION 5
    // Insert a chapter in a Book
    public void op5_insert_chapter(String title, int orderItemId, int pubId, String chapterText, String creationDate, String[] topicName, int[] cmId, boolean authorEmpty, boolean topicEmpty) throws SQLException{
        String query = "INSERT INTO Chapters values ('"+title+"',"+orderItemId+","+pubId+",'"+chapterText+"','"+creationDate+"')";
        statement.executeUpdate(query);
        // Check if topics are given
        if(!topicEmpty) {
            // Iterate over all the topics
            for (String topic : topicName) {
                // Check if the topic already exists in the Topics table
                String check_query = "SELECT * FROM Topics where topicName='" + topic + "'";
                rs = statement.executeQuery(check_query);
                // If the topic does not exist, insert it into the Topics table
                if (!rs.next()) {
                    query = "INSERT INTO Topics values('" + topic + "')";
                    statement.executeUpdate(query);
                }
                // Add the mapping of chapter and topic to the ChapterTopicMappings table
                query = "INSERT INTO ChapterTopicMappings values ('" + title + "'," + orderItemId + "," + pubId + ",'" + topic + "')";
                statement.executeUpdate(query);
            }
        }
        // Check if author of the chapter is given
        if(!authorEmpty) {
            // Iterate over each author
            for (int cId : cmId) {
                // Insert the chapter and author mapping in the ChapterWrittenBy table
                query = "INSERT INTO ChapterWrittenBy values ('" + title + "'," + orderItemId + "," + pubId + "," + cId + ")";
                statement.executeUpdate(query);
            }
        }
    }

    // Insert article in a PeriodicPublication
    public void op5_insert_article(String title, int orderItemId, int pubId, String articleText, String creationDate, String[] topicName, int[] cmId, boolean journalistEmpty, boolean topicEmpty) throws SQLException{
        // Add the article into the Articles table
        String query = "INSERT INTO Articles(title, orderItemId, pubId, articleText, creationDate) values ('"+title+"',"+orderItemId+","+pubId+",'"+articleText+"','"+creationDate+"')";
        statement.executeUpdate(query);
        // Check if topics are given
        if(!topicEmpty) {
            // Iterate over all the topics
            for (String topic : topicName) {
                // Check if topic exists in the Topics table
                String check_query = "SELECT * FROM Topics where topicName='" + topic + "'";
                rs = statement.executeQuery(check_query);
                // If topic does not exists, insert it into the Topics table
                if (!rs.next()) {
                    query = "INSERT INTO Topics values('" + topic + "')";
                    statement.executeUpdate(query);
                }
                // Add the article and topic mapping in the ArticleTopicMappings table
                query = "INSERT INTO ArticleTopicMappings values ('" + title + "'," + orderItemId + "," + pubId + ",'" + topic + "')";
                statement.executeUpdate(query);
            }
        }
        // Check if journalist are given for the article
        if(!journalistEmpty) {
            // Iterate over all the Journalist IDs
            for (int cId : cmId) {
                // Insert the journalist and the article written by them in the ArticleWrittenBy table
                query = "INSERT INTO ArticleWrittenBy values ('" + title + "'," + orderItemId + "," + pubId + "," + cId + ")";
                statement.executeUpdate(query);
            }
        }
    }

    // Delete a chapter from a book
    public void op5_delete_chapter(String title, int orderItemId, int pubId) throws SQLException{
        String query = "DELETE FROM Chapters WHERE title='"+title+"' AND orderItemId="+orderItemId+" AND pubId="+pubId;
        statement.executeUpdate(query);
    }

    // Delete an article from a Periodic publication
    public void op5_delete_article(String title, int orderItemId, int pubId) throws SQLException{
        String query = "DELETE FROM Articles WHERE title='"+title+"' AND orderItemId="+orderItemId+" AND pubId="+pubId;
        statement.executeUpdate(query);
    }

    // ----------------------------------------------------------------- //
    // ----------------------------------------------------------------- //
    // Show the Editions or Issues of a Publication
    public void show_publications(int pubId) throws SQLException{
        String query = "SELECT * FROM OrderItems NATURAL JOIN Publications WHERE pubId="+pubId;
        rs = statement.executeQuery(query);
        TableGenerator tableGenerator = new TableGenerator();
        List<String> headersList = new ArrayList<>();
        headersList.add("Edition or Issue No.");
        headersList.add("Publication ID");
        headersList.add("Title");
        headersList.add("Price");
        headersList.add("Publication Date");

        List<List<String>> rowsList = new ArrayList<>();

        while(rs.next())
        {
            List<String> row = new ArrayList<>();
            row.add(rs.getString("orderItemId"));
            row.add(rs.getString("pubId"));
            row.add(rs.getString("title"));
            row.add(rs.getString("price"));
            row.add(rs.getString("pubDate"));

            rowsList.add(row);
        }

        System.out.println(tableGenerator.generateTable(headersList, rowsList));
    }

    // Show the chapters in a particular Edition of a particular Book
    public void show_chapter(int orderItemId, int pubId) throws SQLException{
        String query = "SELECT * FROM Chapters WHERE orderItemId="+orderItemId+" AND pubId="+pubId;
        rs = statement.executeQuery(query);
        TableGenerator tableGenerator = new TableGenerator();
        List<String> headersList = new ArrayList<>();
        headersList.add("Title");
        headersList.add("Edition");
        headersList.add("Publication ID");
        headersList.add("Chapter Text");
        headersList.add("Creation Date");

        List<List<String>> rowsList = new ArrayList<>();

        while(rs.next())
        {
            List<String> row = new ArrayList<>();
            row.add(rs.getString("title"));
            row.add(rs.getString("orderItemId"));
            row.add(rs.getString("pubId"));
            row.add(rs.getString("chapterText"));
            row.add(rs.getString("creationDate"));

            rowsList.add(row);
        }

        System.out.println(tableGenerator.generateTable(headersList, rowsList));
    }

    // Show the articles in a particular issue of a particular periodic publication
    public void show_articles(int orderItemId, int pubId) throws SQLException{
        String query = "SELECT * FROM Articles WHERE orderItemId="+orderItemId+" AND pubId="+pubId;
        rs = statement.executeQuery(query);
        TableGenerator tableGenerator = new TableGenerator();
        List<String> headersList = new ArrayList<>();
        headersList.add("Title");
        headersList.add("Issue");
        headersList.add("Publication ID");
        headersList.add("Chapter Text");
        headersList.add("Creation Date");

        List<List<String>> rowsList = new ArrayList<>();

        while(rs.next())
        {
            List<String> row = new ArrayList<>();
            row.add(rs.getString("title"));
            row.add(rs.getString("orderItemId"));
            row.add(rs.getString("pubId"));
            row.add(rs.getString("articleText"));
            row.add(rs.getString("creationDate"));

            rowsList.add(row);
        }

        System.out.println(tableGenerator.generateTable(headersList, rowsList));
    }

}
