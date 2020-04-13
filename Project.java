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
	static final String username = "sshah28";
	static final String password = "sigma8980172149";
	static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/"+username;
	// Put your oracle ID and password here

	static final String createFile = "create_java.sql";
	static final String insertFile = "insert_java.sql";


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

			Scanner intScanner = new Scanner(System.in);
			Scanner floatScanner = new Scanner(System.in);
			Scanner lineScanner = new Scanner(System.in);

			int main_choice = intScanner.nextInt();

			switch(main_choice)
			{
				case 1: System.out.println("In case 1"); // editing and publishing section
					execute_task1();
					break;

				case 2: System.out.println("In case 2"); // production section

					execute_task2();

					break;

				case 3: System.out.println("In case 3"); // distribution section
					execute_task3();
					break;

				case 4: System.out.println("In case 4"); // reports section

					execute_task4();

					break;

				case 0: // exit program
					close();
					System.exit(0);

				default:
					System.out.println("Invalid choice! Please enter correct choice");
			}
		}
	}

	public static void execute_task1(){
		Editing edit = new Editing(connection);

		Scanner intScanner = new Scanner(System.in);
		Scanner floatScanner = new Scanner(System.in);
		Scanner lineScanner = new Scanner(System.in);

		System.out.println("\nTASK 1: Editing and Publishing");
		System.out.println("1. Enter basic information on a new publication");
		System.out.println("2. Update title of a publication");
		System.out.println("3. Update Periodicity of a Periodic Publication");
		System.out.println("4. Update Frequency of a Periodic Publication");
		System.out.println("5. Assign editor(s) to a publication");
		System.out.println("6. View the information on the publications an editor is responsible for");
		System.out.println("7. Enter a chapter into a book");
		System.out.println("8. Enter an article into a periodic publication");
		System.out.println("9. Delete a chapter from a book");
		System.out.println("10. Delete an article from a periodic publication");
		System.out.println("0. Exit this menu");
		System.out.println("Enter your choice: ");

		int task1_choice = intScanner.nextInt();

		switch (task1_choice){
			case 1:
				String pubTitle, pubDate;
				int pubId;
				float price;
				System.out.println("SUB-MENU");
				System.out.println("0. Insert a new book");
				System.out.println("1. Insert a new periodic publication");
				System.out.println("Enter your choice: ");
				int choice = intScanner.nextInt();
				System.out.println("Enter the Publication ID: ");
				pubId = intScanner.nextInt();
				System.out.println("Enter the title of the Publication: ");
				pubTitle = lineScanner.nextLine();
				System.out.println("Enter the Publication date: ");
				pubDate = lineScanner.nextLine();
				System.out.println("Enter the price: ");
				price = floatScanner.nextFloat();

				if(choice==0){
					int edition;
					String ISBN, topic;
					boolean topicEmpty=false;

					System.out.println("Enter the topics for the book (separated by commas only, press ENTER to skip): ");
					topic = lineScanner.nextLine();
					if(topic.isEmpty()){
						topicEmpty=true;
					}
					String[] topicList = topic.split(",");
					System.out.println("Enter the edition for the book: ");
					edition = intScanner.nextInt();
					System.out.println("Enter the ISBN: ");
					ISBN = lineScanner.nextLine();

					try {
						edit.op1_insert_pub_book(pubTitle, pubId, edition, price, ISBN, pubDate, topicList, topicEmpty);

						System.out.println("Book successfully inserted!");
						System.out.println("Would you like to add chapters for this Book? y/n ");
						String yn = lineScanner.nextLine();
						while(yn.equalsIgnoreCase("y")){
							edit.op1_insert_chapter(pubId, edition, pubDate);
							System.out.println("Would you like to add more chapters? y/n ");
							yn = lineScanner.nextLine();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

				}
				else if(choice==1){
					String periodicityType, frequency;
					int issueNo;

					System.out.println("Enter the Type of the Periodic Publication (magazine, journal, etc.): ");
					periodicityType = lineScanner.nextLine();
					System.out.println("Enter the Frequency of the Periodic Publication (monthly, weekly, etc.): ");
					frequency = lineScanner.nextLine();
					System.out.println("Enter the Issue No.: ");
					issueNo = intScanner.nextInt();

					try {
						edit.op1_insert_pub_periodic(pubTitle, pubId, periodicityType, frequency, issueNo, price, pubDate);
						System.out.println("Periodic publication successfully inserted!");

						System.out.println("Would you like to add articles for this Publication? y/n ");
						String yn = lineScanner.nextLine();

						while(yn.equalsIgnoreCase("y")){
							edit.op1_insert_article(pubId, issueNo, pubDate);
							System.out.println("Would you like to add more articles? y/n ");
							yn = lineScanner.nextLine();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

				}
				else{
					System.out.println("Invalid Input Try again");
				}

				break;

			case 2:
				System.out.println("Enter the Publication ID: ");
				pubId = intScanner.nextInt();
				System.out.println("Enter the new Title for the Publication: ");
				pubTitle = lineScanner.nextLine();
				try {
					edit.op2_update_pub(pubTitle, pubId);
					System.out.println("Title successfully updated!");
				} catch (SQLException e) {
					e.printStackTrace();
				}

				break;

			case 3:
				System.out.println("Enter the Publication ID: ");
				pubId = intScanner.nextInt();
				System.out.println("Enter the new Periodicity type of the Publication: ");
				String periodicityType = lineScanner.nextLine();
				try {
					edit.op2_update_periodicty(pubId, periodicityType);
					System.out.println("Periodicity type successfully updated!");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			case 4:
				String frequency;
				System.out.println("Enter the Publication ID: ");
				pubId = intScanner.nextInt();
				System.out.println("Enter the new frequency of the Publication: ");
				frequency = lineScanner.nextLine();
				try {
					edit.op2_update_frequency(pubId, frequency);
					System.out.println("Frequency successfully updated!");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			case 5:
				int orderItemId;
				System.out.println("Enter the ID of the publication: ");
				pubId = intScanner.nextInt();
				try {
					edit.show_publications(pubId);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("Enter edition/issue no. of the publication: ");
				orderItemId = intScanner.nextInt();
				System.out.println("Enter the IDs of editor(s) (separated by commas only): ");
				String ids = lineScanner.nextLine();

				String[] idArray = ids.split(",");
				int[] cmIds = new int[idArray.length];

				for(int i=0;i<idArray.length;i++){
					cmIds[i]=Integer.parseInt(idArray[i]);
				}

				try {
					edit.op3_assign_editor_pub(cmIds, orderItemId, pubId);
					System.out.println("Editors successfully assigned!");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			case 6:
				int cmId;
				System.out.println("Enter the ID of the editor: ");
				cmId = intScanner.nextInt();
				try {
					edit.op4_find_editor_pub(cmId);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			case 7:
				System.out.println("Enter the Publication ID: ");
				pubId = intScanner.nextInt();
				try {
					edit.show_publications(pubId);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("Enter the edition no. to which chapter is to be added: ");
				orderItemId = intScanner.nextInt();
				System.out.println("Enter the Creation date of chapter (YYYY-MM-DD): ");
				String creationDate = lineScanner.nextLine();
				edit.op1_insert_chapter(pubId, orderItemId, creationDate);
				break;

			case 8:
				System.out.println("Enter the Publication ID: ");
				pubId = intScanner.nextInt();
				try {
					edit.show_publications(pubId);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("Enter the issue no. to which article is to be added: ");
				orderItemId = intScanner.nextInt();
				System.out.println("Enter the Creation date of article (YYYY-MM-DD): ");
				creationDate = lineScanner.nextLine();
				edit.op1_insert_article(pubId, orderItemId, creationDate);
				break;

			case 9:
				String title;

				System.out.println("Enter the Publication ID: ");
				pubId = intScanner.nextInt();
				try {
					edit.show_publications(pubId);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("Enter the edition no. to which chapter is linked: ");
				orderItemId = intScanner.nextInt();
				System.out.println("Enter the title of the Chapter: ");
				title = lineScanner.nextLine();

				try {
					edit.op5_delete_chapter(title, orderItemId, pubId);
					System.out.println("Chapter successfully deleted!");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			case 10:
				System.out.println("Enter the Publication ID: ");
				pubId = intScanner.nextInt();
				try {
					edit.show_publications(pubId);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("Enter the issue no. to which article is linked: ");
				orderItemId = intScanner.nextInt();
				System.out.println("Enter the title of the Article: ");
				title = lineScanner.nextLine();
				try {
					edit.op5_delete_article(title, orderItemId, pubId);
					System.out.println("Article successfully deleted!");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;

			case 0:
				break;

			default:
				System.out.println("Invalid choice! Please enter correct choice");
		}
	}

	public static void execute_task2()
	{
		// pass connection so the class can work on SQL queries
		Production prod = new Production(connection);

		Scanner intScanner = new Scanner(System.in);
		Scanner floatScanner = new Scanner(System.in);
		Scanner lineScanner = new Scanner(System.in);

		System.out.println("\nTASK 2: Production");
		System.out.println("1. Enter a new book edition or new issue of a publication"); //DONE
		System.out.println("2. Update, delete a book edition or publication issue"); //DONE
		System.out.println("3. Enter/update an article or chapter: title, author's name, topic, and date");
		System.out.println("4. Enter/update text of an article"); //DONE
		System.out.println("5. Find books and articles by topic, date, author's name"); //DONE
		System.out.println("6. Enter payment for author or editor, and keep track of when each payment was claimed by its addressee"); //DONE
		System.out.println("0. Exit this menu");
		System.out.println("Enter your choice: ");

		int task2_choice = intScanner.nextInt();

		switch(task2_choice)
		{
			case 1: //operation 1
				int pubId, orderItemId, val;
				String pubDate;
				float price;

				boolean isEdition;
				System.out.println("Enter 0 for edition and 1 for a periodic issue: ");
				val = intScanner.nextInt();

				if(val == 0)
					isEdition = true;
				else if(val == 1)
					isEdition = false;
				else {
					System.out.println("Incorrect value entered!");
					break;
				}

				System.out.println("Enter publication id: ");
				pubId = intScanner.nextInt();

				if(isEdition)
				{
					System.out.println("Enter edition no: ");
					orderItemId = intScanner.nextInt();
				}
				else
				{
					System.out.println("Enter issue no: ");
					orderItemId = intScanner.nextInt();
				}

				System.out.println("Enter price: ");
				price = floatScanner.nextFloat();

				System.out.println("Enter publication date (YYYY-MM-DD): ");
				pubDate = lineScanner.nextLine();

				if(isEdition)
				{
					System.out.println("Enter ISBN: ");
					String isbn = lineScanner.nextLine();

					try
					{
						try
						{
							connection.setAutoCommit(false);

							prod.op1_edition(orderItemId, pubId, price, pubDate, isbn);
							System.out.println("New Book Edition inserted successfully!");
						} catch(SQLException e)
						{
							connection.rollback();
							e.printStackTrace();
							System.out.println("Operation Failed. Changes to database rolled back!");
						}
						finally
						{
							connection.setAutoCommit(true);
						}
					} catch(SQLException e)
					{
						e.printStackTrace();
					}
				}
				else
				{
					try
					{
						try
						{
							connection.setAutoCommit(false);
							prod.op1_issue(orderItemId, pubId, price, pubDate, orderItemId);
							System.out.println("New Periodic Issue inserted successfully!");
						} catch(SQLException e)
						{
							connection.rollback();
							e.printStackTrace();
							System.out.println("Operation Failed. Changes to database rolled back!");
						}
						finally {
							connection.setAutoCommit(true);
						}
					} catch(SQLException e)
					{
						e.printStackTrace();
					}
				}

				break;

			case 2: //operation 2

				System.out.println("Enter 0 for edition and 1 for a periodic issue: ");
				val = intScanner.nextInt();

				if(val == 0)
					isEdition = true;
				else if(val == 1)
					isEdition = false;
				else {
					System.out.println("Incorrect value entered!");
					break;
				}

				System.out.println("Enter publication id for update/delete action: ");
				pubId = intScanner.nextInt();

				try
				{
					if(prod.op2_display_OrderItems(pubId) == 0)
						break;

				} catch(SQLException e) {
					e.printStackTrace();
				}

				System.out.println("Enter edition no./issue no. for update/delete action: ");
				orderItemId = intScanner.nextInt();

				System.out.println("\nUPDATE/DELETE EDITION/ISSUE MENU");
				System.out.println("1. Update price");
				System.out.println("2. Update publication date");

				if(isEdition)
				{
					System.out.println("3. Update ISBN");
					System.out.println("4. Delete Book Edition");
				}
				else
				{
					System.out.println("3. Delete Issue");
				}

				System.out.println("0. Exit this menu");

				System.out.println("Enter your choice:");
				val = intScanner.nextInt();

				switch(val)
				{
					case 1:
						System.out.println("Enter new price: ");
						price = floatScanner.nextFloat();

						try
						{
							prod.op2_update_price(price, orderItemId, pubId);
							System.out.println("Price updated!");
						} catch(SQLException e)
						{
							e.printStackTrace();
							// System.out.println("Operation Failed. Try Again!");
						}

						break;

					case 2:
						System.out.println("Enter new publication date (YYYY-MM-DD): ");
						pubDate = lineScanner.nextLine();

						try
						{
							prod.op2_update_pubDate(pubDate, orderItemId, pubId);
							System.out.println("Publication Date updated!");
						} catch(SQLException e)
						{
							e.printStackTrace();
							// System.out.println("Operation Failed. Try Again!");
						}

						break;

					case 3:
						if(isEdition)
						{
							System.out.println("Enter new ISBN: ");
							String isbn = lineScanner.nextLine();

							try
							{
								prod.op2_update_isbn(isbn, orderItemId, pubId);
								System.out.println("ISBN updated!");
							} catch(SQLException e)
							{
								e.printStackTrace();
								// System.out.println("Operation Failed. Try Again!");
							}
						}
						else
						{
							try
							{
								prod.op2_delete_edition_issue(orderItemId, pubId);
								System.out.println("Issue deleted successfully!");
							} catch(SQLException e)
							{
								e.printStackTrace();
								// System.out.println("Operation Failed. Try Again!");
							}
						}

						break;

					case 4:
						try
						{
							prod.op2_delete_edition_issue(orderItemId, pubId);
							System.out.println("Book Edition deleted successfully!");
						} catch(SQLException e)
						{
							e.printStackTrace();
							// System.out.println("Operation Failed. Try Again!");
						}
						break;

					case 0: break;

					default:
						System.out.println("Invalid choice! Please enter correct choice");
				}

				break;

			case 3: //operation 3
				System.out.println("\nSUB-MENU");
				System.out.println("1. Enter a chapter"); //DONE
				System.out.println("2. Enter an article"); //DONE
				System.out.println("3. Update a chapter/article");
				System.out.println("0. Exit this menu");
				System.out.println("Enter your choice: ");

				val = intScanner.nextInt();

				switch(val)
				{
					case 1:
						boolean topicEmpty=false, authorEmpty=false;
						System.out.println("Enter publication id to which this chapter is linked: ");
						pubId = intScanner.nextInt();

						try {
							int temp = prod.op2_display_OrderItems(pubId);
						} catch (SQLException e) {
							e.printStackTrace();
						}

						System.out.println("Enter edition no. to which this chapter is linked: ");
						orderItemId = intScanner.nextInt();

						System.out.println("Enter chapter title: ");
						String title = lineScanner.nextLine();

						System.out.println("Enter chapter text: ");
						String chapterText = lineScanner.nextLine();

						System.out.println("Enter chapter creation date (YYYY-MM-DD): ");
						String chapterDate = lineScanner.nextLine();

						System.out.println("Enter topic(s) associated with chapter (separated by commas only, press ENTER to skip): ");
						String topics = lineScanner.nextLine();

						System.out.println("Enter author(s) id associated with chapter (separated by commas only, press ENTER to skip): ");
						String authorIds = lineScanner.nextLine();

						String[] topicList = topics.split(",");
						String[] authorList = authorIds.split(",");

						if(topics.isEmpty()){
							topicEmpty=true;
						}
						if(authorIds.isEmpty()){
							authorEmpty=true;
						}

						try
						{
							prod.op3_enter_chapter(orderItemId, pubId, title, chapterText, chapterDate, topicList, authorList, topicEmpty, authorEmpty);
							System.out.println("New Chapter entered successfully!");
						} catch(SQLException e)
						{
							e.printStackTrace();
							// System.out.println("Operation Failed. Try Again!");
						}

						break;

					case 2:
						authorEmpty=false;
						topicEmpty=false;
						System.out.println("Enter publication id to which this article is linked: ");
						pubId = intScanner.nextInt();

						try {
							int temp = prod.op2_display_OrderItems(pubId);
						} catch (SQLException e) {
							e.printStackTrace();
						}

						System.out.println("Enter issue no. to which this article is linked: ");
						orderItemId = intScanner.nextInt();

						System.out.println("Enter article title: ");
						title = lineScanner.nextLine();

						System.out.println("Enter article text: ");
						String articleText = lineScanner.nextLine();

						System.out.println("Enter article creation date (YYYY-MM-DD): ");
						String articleDate = lineScanner.nextLine();

						System.out.println("Enter topic(s) associated with article (separated by commas only, press ENTER to skip): ");
						topics = lineScanner.nextLine();

						System.out.println("Enter journalist(s) id associated with article (separated by commas only, press ENTER to skip): ");
						authorIds = lineScanner.nextLine();

						topicList = topics.split(",");
						authorList = authorIds.split(",");

						if(topics.isEmpty()){
							topicEmpty=true;
						}
						if(authorIds.isEmpty()){
							authorEmpty=true;
						}

						try
						{
							prod.op3_enter_article(orderItemId, pubId, title, articleText, articleDate, topicList, authorList, topicEmpty, authorEmpty);
							System.out.println("New Article entered successfully!");
						} catch(SQLException e)
						{
							e.printStackTrace();
							// System.out.println("Operation Failed. Try Again!");
						}

						break;

					case 3:
						System.out.println("Enter 0 to update chapter and 1 to update article: ");
						int ch = intScanner.nextInt();

						if(ch == 0)
						{
							System.out.println("Enter publication id with which chapter is linked: ");
							pubId = intScanner.nextInt();
						}
						else if(ch == 1)
						{
							System.out.println("Enter publication id with which article is linked: ");
							pubId = intScanner.nextInt();
						}
						else
						{
							System.out.println("Invalid Choice. Try Again!");
							break;
						}
						

						try
						{
							if(prod.op2_display_OrderItems(pubId) == 0)
								break;
						}
						catch(SQLException e)
						{
							e.printStackTrace();
						}

						if(ch == 0) //chapter
						{
							System.out.println("Enter edition no. with which chapter is linked: ");
							orderItemId = intScanner.nextInt();

							try
							{
								if(prod.op2_display_Chapters(pubId, orderItemId) == 0)
									break;
							}
							catch(SQLException e)
							{
								e.printStackTrace();
							}

							System.out.println("Enter title of the chapter which is to be updated: ");
							title = lineScanner.nextLine();
						}
						else if(ch == 1) //article
						{
							System.out.println("Enter issue no. with which article is linked: ");
							orderItemId = intScanner.nextInt();

							try
							{
								if(prod.op2_display_Articles(pubId, orderItemId) == 0)
									break;
							}
							catch(SQLException e)
							{
								e.printStackTrace();
							}

							System.out.println("Enter title of the article which is to be updated: ");
							title = lineScanner.nextLine();
						}
						else
						{
							System.out.println("Invalid Choice. Try Again!");
							break;
						}

						System.out.println("\nUPDATE MENU");
						System.out.println("1. Update title"); //DONE

						if(ch==0)
							System.out.println("2. Update authors"); //DONE
						else if(ch == 1)
							System.out.println("2. Update journalists"); //DONE

						System.out.println("3. Update topics"); //DONE
						System.out.println("4. Update creation date"); //DONE
						System.out.println("0. Exit this menu");
						System.out.println("Enter your choice: ");

						val = intScanner.nextInt();

						switch(val)
						{
							case 1:
								if(ch==0) //chapter
								{
									System.out.println("Enter new title for the chapter: ");
									String newTitle = lineScanner.nextLine();

									try
									{
										prod.op3_update_chapter_title(orderItemId, pubId, title, newTitle);
										System.out.println("Chapter title updated!");
									} catch(SQLException e)
									{
										e.printStackTrace();
									}
								}
								else //article
								{
									System.out.println("Enter new title for the article: ");
									String newTitle = lineScanner.nextLine();

									try
									{
										prod.op3_update_article_title(orderItemId, pubId, title, newTitle);
										System.out.println("Article title updated!");
									} catch(SQLException e)
									{
										e.printStackTrace();
									}
								}

								break;

							case 2:
								try
								{
									if(ch == 0) //chapter
									{
										prod.op3_display_chapter_authors(orderItemId, pubId, title);
									}
									else if(ch == 1) //article
									{
										prod.op3_display_article_journalists(orderItemId, pubId, title);
									}
								}
								catch(SQLException e)
								{
									e.printStackTrace();
								}

								System.out.println("\nSUB-MENU");
								System.out.println("1. Add author/journalist to chapter/article");
								System.out.println("2. Remove author/journalist from chapter/article");
								System.out.println("0. Exit this menu");
								System.out.println("Enter your choice: ");

								int subch = intScanner.nextInt();

								if(subch == 1) // add author
								{
									if(ch == 0) //chapter
									{
										System.out.println("Enter author id to link with chapter: ");
										int cmId = intScanner.nextInt();

										try
										{
											prod.op3_add_author_chapter(orderItemId,pubId,title,cmId);
											System.out.println("Author added to chapter!");
										}
										catch(SQLException e)
										{
											e.printStackTrace();
										}
									}
									else if(ch == 1)
									{
										System.out.println("Enter journalist id to link with article: ");
										int cmId = intScanner.nextInt();

										try
										{
											prod.op3_add_journalist_article(orderItemId,pubId,title,cmId);
											System.out.println("Journalist added to article!");
										}
										catch(SQLException e)
										{
											e.printStackTrace();
										}
									}
								}
								else if(subch == 2) // remove author
								{
									if(ch == 0) //chapter
									{
										System.out.println("Enter author id to remove from chapter: ");
										int cmId = intScanner.nextInt();

										try
										{
											prod.op3_remove_author_chapter(orderItemId,pubId,title,cmId);
											System.out.println("Author removed from chapter!");
										}
										catch(SQLException e)
										{
											e.printStackTrace();
										}
									}
									else if(ch == 1)
									{
										System.out.println("Enter journalist id to remove from article: ");
										int cmId = intScanner.nextInt();

										try
										{
											prod.op3_remove_journalist_article(orderItemId,pubId,title,cmId);
											System.out.println("Journalist removed from article!");
										}
										catch(SQLException e)
										{
											e.printStackTrace();
										}
									}
								}
								else if(subch != 0)
								{
									System.out.println("Invalid choice! Please enter correct choice");
								}
								break;

							case 3: // topic add/remove

								try
								{
									if(ch == 0) //chapter
									{
										prod.op3_display_chapter_topics(orderItemId, pubId, title);
									}
									else if(ch == 1) //article
									{
										prod.op3_display_article_topics(orderItemId, pubId, title);
									}
								}
								catch(SQLException e)
								{
									e.printStackTrace();
								}

								System.out.println("\nSUB-MENU");
								System.out.println("1. Add topic to chapter/article");
								System.out.println("2. Remove topic from chapter/article");
								System.out.println("0. Exit this menu");
								System.out.println("Enter your choice: ");

								subch = intScanner.nextInt();

								if(subch == 1) // add topic
								{
									if(ch == 0) //chapter
									{
										System.out.println("Enter topic to link with chapter: ");
										String topicName = lineScanner.nextLine();

										try
										{
											prod.op3_add_topic_chapter(orderItemId,pubId,title,topicName);
											System.out.println("Topic added to chapter!");
										}
										catch(SQLException e)
										{
											e.printStackTrace();
										}
									}
									else if(ch == 1)
									{
										System.out.println("Enter topic to link with article: ");
										String topicName = lineScanner.nextLine();

										try
										{
											prod.op3_add_topic_article(orderItemId,pubId,title,topicName);
											System.out.println("Topic added to article!");
										}
										catch(SQLException e)
										{
											e.printStackTrace();
										}
									}
								}
								else if(subch == 2) // remove topic
								{
									if(ch == 0) //chapter
									{
										System.out.println("Enter topic to remove from chapter: ");
										String topicName = lineScanner.nextLine();

										try
										{
											prod.op3_remove_topic_chapter(orderItemId,pubId,title,topicName);
											System.out.println("Topic removed from chapter!");
										}
										catch(SQLException e)
										{
											e.printStackTrace();
										}
									}
									else if(ch == 1)
									{
										System.out.println("Enter topic to remove from article: ");
										String topicName = lineScanner.nextLine();

										try
										{
											prod.op3_remove_topic_article(orderItemId,pubId,title,topicName);
											System.out.println("Topic removed from article!");
										}
										catch(SQLException e)
										{
											e.printStackTrace();
										}
									}
								}
								else if(subch != 1 && subch != 2 && subch != 0)
								{
									System.out.println("Invalid choice! Please enter correct choice");
								}
								break;

							case 4:
								if(ch==0) //chapter
								{
									System.out.println("Enter new chapter creation date (YYYY-MM-DD): ");
									String creationDate = lineScanner.nextLine();

									try
									{
										prod.op3_update_chapter_date(orderItemId, pubId, title, creationDate);
										System.out.println("Chapter creation date updated!");
									} catch(SQLException e)
									{
										e.printStackTrace();
									}
								}
								else if(ch == 1)
								{
									System.out.println("Enter new article creation date (YYYY-MM-DD): ");
									String creationDate = lineScanner.nextLine();

									try
									{
										prod.op3_update_article_date(orderItemId, pubId, title, creationDate);
										System.out.println("Article creation date updated!");
									} catch(SQLException e)
									{
										e.printStackTrace();
									}
								}

								break;

							case 0:
								break;

							default:
								System.out.println("Invalid choice! Please enter correct choice");
						}
						break;

					case 0:
						break;

					default:
						System.out.println("Invalid choice! Please enter correct choice");
				}

				break;

			case 4: //operation 4
				System.out.println("Enter publication id for the issue: ");
				pubId = intScanner.nextInt();

				try
				{
					if(prod.op2_display_OrderItems(pubId) == 0)
						break;
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}

				System.out.println("Enter issue no. to which the article is linked: ");
				orderItemId = intScanner.nextInt();

				try
				{
					if(prod.op2_display_Articles(pubId, orderItemId) == 0)
						break;
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}

				System.out.println("Enter title of the article whose text is to be updated: ");
				String title = lineScanner.nextLine();

				System.out.println("Enter new text for this article: ");
				String articleText = lineScanner.nextLine();

				try
				{
					prod.op4_update_text_article(title, orderItemId, pubId, articleText);
					System.out.println("Article's text updated!");
				} catch(SQLException e)
				{
					e.printStackTrace();
				}

				break;

			case 5: //operation 5
				System.out.println("\nFIND MENU ");
				System.out.println("1. Find books and articles by topic");
				System.out.println("2. Find books and articles by date");
				System.out.println("3. Find books and articles by author's name");
				System.out.println("0. Exit this menu");
				System.out.println("Enter your choice: ");

				int op5_choice = intScanner.nextInt();

				switch(op5_choice)
				{
					case 1:
						System.out.println("Enter topic name: ");
						String topicName = lineScanner.nextLine();

						try
						{
							prod.op5_find_topic(topicName);
						} catch(SQLException e)
						{
							e.printStackTrace();
							// System.out.println("Operation Failed. Try Again!");
						}

						break;

					case 2:
						System.out.println("Enter publication date (YYYY-MM-DD): ");
						pubDate = lineScanner.nextLine();

						try
						{
							prod.op5_find_pubDate(pubDate);
						} catch(SQLException e)
						{
							e.printStackTrace();
							// System.out.println("Operation Failed. Try Again!");
						}

						break;

					case 3:
						System.out.println("Enter author's name: ");
						String authorName = lineScanner.nextLine();

						try
						{
							prod.op5_find_authorName(authorName);
						} catch(SQLException e)
						{
							e.printStackTrace();
							// System.out.println("Operation Failed. Try Again!");
						}
						break;

					case 0:
						break;

					default:
						System.out.println("Invalid choice! Please enter correct choice");

				}
				break;

			case 6: //operation 6
				System.out.println("\nPAYROLL MENU"); 
				System.out.println("1. Enter payment record for an employee");
				System.out.println("2. Claim payment received");
				System.out.println("0. Exit this menu");
				System.out.println("Enter your choice: ");

				val = intScanner.nextInt();

				switch(val)
				{
					case 1:
						System.out.println("Enter employee id for which payment record is to be entered: ");
						int cmId = intScanner.nextInt();

						System.out.println("Enter amount: ");
						float amount = floatScanner.nextFloat();

						System.out.println("Enter payment date (YYYY-MM-DD): ");
						String payDate = lineScanner.nextLine();

						try
						{
							prod.op6_insert_payment(cmId, amount, payDate);
							System.out.println("Payment record entered!");
						} catch(SQLException e)
						{
							e.printStackTrace();
							// System.out.println("Operation Failed. Try Again!");
						}

						break;

					case 2:
						System.out.println("Enter employee id whose payment is to be claimed: ");
						cmId = intScanner.nextInt();

						try
						{
							if(prod.op6_display_unclaimed_payments(cmId) == 0)
							{
								break;
							}

							System.out.println("Enter ID of payment record which you want to claim: ");
							int payId = intScanner.nextInt();

							System.out.println("Enter claim date (YYYY-MM-DD): ");
							String claimDate = lineScanner.nextLine();
						
							prod.op6_claim_payment(payId, claimDate);
							System.out.println("Payment record updated!");
						} catch(SQLException e)
						{
							e.printStackTrace();
							// System.out.println("Operation Failed. Try Again!");
						}

						break;

					case 0:
						break;

					default:
						System.out.println("Invalid choice! Please enter correct choice");
				}
				break;

			case 0:
				break;

			default:
				System.out.println("Invalid choice! Please enter correct choice");
		}
	}

	public static void execute_task4() {
		// pass connection so the class can work on SQL queries
		Reports report = new Reports(connection);

		Scanner intScanner = new Scanner(System.in);


		System.out.println("\nTASK 4: Reports");
		System.out.println("1. Generate monthly reports.");
		System.out.println("2. Calculate the total current number of distributors.");
		System.out.println("3. Calculate total revenue (since inception) per city, per distributor, and per location.");
		System.out.println("4. Calculate total payments to the editors and authors, per time period and per work type (book authorship, article authorship, or editorial work).");
		System.out.println("0. Exit this menu");
		System.out.println("Enter your choice: ");

		int task4_choice = intScanner.nextInt();

		switch(task4_choice)
		{
			case 1: // task 4 choice 1
				try
				{
					report.op1_get_monthly_reports();
				} catch(SQLException e)
				{
					e.printStackTrace();
					// System.out.println("Operation Failed. Try Again!");
				}
				break;
			case 2: // task 4 choice 2
				try
				{
					report.op2_get_total_distributors();
				} catch(SQLException e)
				{
					e.printStackTrace();
					// System.out.println("Operation Failed. Try Again!");
				}
				break;
			case 3: // task 4 choice 3
				try
				{
					report.op3_calculate_total_revenue();
				} catch(SQLException e)
				{
					e.printStackTrace();
					// System.out.println("Operation Failed. Try Again!");
				}
				break;
			case 4: // task 4 choice 4
				Scanner lineScanner = new Scanner(System.in);
				String beginDate,endDate;
				System.out.println("Enter begin date: ");
				beginDate = lineScanner.nextLine();
				System.out.println("Enter end date: ");
				endDate = lineScanner.nextLine();

				try
				{
					report.op4_calculate_total_payments(beginDate, endDate);
				} catch(SQLException e)
				{
					e.printStackTrace();
					// System.out.println("Operation Failed. Try Again!");
				}
				break;
			case 0:
				break;

			default:
				System.out.println("Invalid choice! Please enter correct choice");
		}


	}

	public static void execute_task3()
	{
		System.out.println("	1. Add a Distributor");
		System.out.println("	2. Update Distributor ");
		System.out.println("	3. Delete a Distributor");
		System.out.println("	4. Place an Order");
		System.out.println("	5. Bill a Distributor");
		System.out.println("	6. Payment From Distributor");
		System.out.println("    0. Exit this menu");
		System.out.println("Enter your choice: ");

		Distributor distributor = new Distributor(connection);

		Scanner intScanner = new Scanner(System.in);
		Scanner floatScanner = new Scanner(System.in);
		Scanner lineScanner = new Scanner(System.in);

		int distID;
		String distName;
		String distType;
		Float balance;
		String contactName;
		String contactPhone;
		String addr;
		String city;

		int second_choice = intScanner.nextInt();
		switch(second_choice)
		{

			case 1:
				System.out.println("Provide Details of Distributor");
				System.out.println("ID of Distributor ");
				distID = intScanner.nextInt();
				System.out.println("Name of Distributor ");
				distName = lineScanner.nextLine();
				System.out.println("Type of Distributor('wholesale distributor', 'library', 'bookstore')");
				distType = lineScanner.nextLine();
				System.out.println("Balance of Distributor ");
				balance = intScanner.nextFloat();
				System.out.println("Contact Person Name ");
				contactName = lineScanner.nextLine();
				System.out.println("Contact Person Phone ");
				contactPhone = lineScanner.nextLine();
				System.out.println("Address ");
				addr = lineScanner.nextLine();
				System.out.println("City ");
				city = lineScanner.nextLine();

				try
				{
					distributor.op1_insert_distributor(distID, distName, distType, balance, contactName, contactPhone, addr, city);
					System.out.println("Record Added successfully");

				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
				break;

			case 2:
				System.out.println("Provide Details of Distributor");
				System.out.println("ID of Distributor ");
				distID = intScanner.nextInt();

				try
				{
					if(distributor.op2_update_distributor(distID)==1){
						System.out.println("Record Updated successfully");
					}
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
				break;

			case 3:
				System.out.println("Provide Details of Distributor To Delete");
				System.out.println("ID of Distributor ");
				distID = intScanner.nextInt();

				try
				{
					distributor.op3_delete_distributor(distID);
					System.out.println("Record Updated successfully");
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
				break;

			case 4:
				System.out.println("Provide Details of Order");
				System.out.println("ID of Order ");
				int orderId = intScanner.nextInt();
				System.out.println("Shipping Cost ");
				Float shippingCost = intScanner.nextFloat();
				System.out.println("Enter Order Date");
				String orderDate = lineScanner.nextLine();
				System.out.println("Enter Delivery Date");
				String deliveryDate = lineScanner.nextLine();
				System.out.println("Enter Location ID of Distributor's Warehouse");
				int locId = intScanner.nextInt();
				try
				{
					distributor.op4_input_order_Orders(orderId, shippingCost, orderDate, deliveryDate, locId);

				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
				
				int nn;
				do{
					System.out.println("ID of Order Item");
					int orderItemId = intScanner.nextInt();
					System.out.println("ID of Publication");
					int pubId = intScanner.nextInt();
					System.out.println("Enter Quantity");
					int quantity = intScanner.nextInt();

					try
					{
						distributor.op4_input_order_OrderContains(orderId, orderItemId, pubId, quantity);
					}
					catch (SQLException e)
					{
						e.printStackTrace();
					}
					System.out.println("Press 1 to add new Item and 0 to complete order");
					nn = intScanner.nextInt();
				}while(nn==1);

				System.out.println("Order Placed successfully");

				break;

			case 5:
				System.out.println("Enter Distributor ID for which you want to generate bills");
				int distId = intScanner.nextInt();
				System.out.println("Enter Bill Generation Date");
				String generationDate = lineScanner.nextLine();

				try
				{
					if(distributor.op5_bill_distributor(distId, generationDate)==1){
						System.out.println("Bill Generated successfully");
					}
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}

				break;

			case 6:
				System.out.println("Enter Distributor ID for which you want to Pay bills");
				distId = intScanner.nextInt();
				System.out.println("Enter Bill Payment Date");
				String receiptDate = lineScanner.nextLine();
				try
				{
					if(distributor.op6_payment(distId, receiptDate)==1){
						System.out.println("Bills Paid successfully");
					}

				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}


				break;

			case 0:
				break;

			default:
				System.out.println("Invalid choice! Please enter correct choice");

		}
	}

	public static void resetDatabase() {
		try {
			statement.executeUpdate("drop table OrderContains,OrderBillMappings,Orders,Bills,Locations,Distributors,ArticleWrittenBy,ArticleTopicMappings,Articles,ChapterWrittenBy,ChapterTopicMappings,Chapters,Issues,Editions,ItemEditedBy,OrderItems,Payrolls,Journalists,Authors,Editors,ContentManagers,PeriodicPublications,BookTopicMappings,Topics,Books,Publications");
		}
		catch (SQLException e) { }

		createTables();
		populateTables();
	}

	private static void initialize() {
		try {
			connectToDatabase();
			// resetDatabase();
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

		connection = DriverManager.getConnection(jdbcURL, username, password);
		statement = connection.createStatement();
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
