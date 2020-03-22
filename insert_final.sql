use jmodi3;



delete from Publications;
insert into Publications(title) values ('Book 1');
insert into Publications(title) values ('Book 2');
insert into Publications(title) values ('Book 3');
insert into Publications(title) values ('Book 4');
insert into Publications(title) values ('Book 5');
insert into Publications(title) values ('Book 6');
insert into Publications(title) values ('Book 7');
insert into Publications(title) values ('PerPub 1');
insert into Publications(title) values ('PerPub 2');
insert into Publications(title) values ('PerPub 3');
insert into Publications(title) values ('PerPub 4');
insert into Publications(title) values ('PerPub 5');
insert into Publications(title) values ('PerPub 6');

delete from Books;
insert into Books values (1, 1);
insert into Books values (2, 2);
insert into Books(pubId) values (3);
insert into Books values (4, 1);
insert into Books(pubId) values (5);
insert into Books values (6, 1);
insert into Books values (7, 3);

delete from Periodicity;
insert into Periodicity values ('magazine','weekly');
insert into Periodicity values ('journal','monthly');

delete from PeriodicPublications;
insert into PeriodicPublications values (8,'magazine');
insert into PeriodicPublications values (9,'journal');
insert into PeriodicPublications values (10,'magazine');
insert into PeriodicPublications values (11,'journal');
insert into PeriodicPublications values (12,'magazine');
insert into PeriodicPublications values (13,'journal');

delete from ContentManagers;
insert into ContentManagers(cmName,salary) values ('Chintan',NULL);
insert into ContentManagers(cmName,salary) values ('Jay', 2000);
insert into ContentManagers(cmName,salary) values ('Nirav',1000);
insert into ContentManagers(cmName,salary) values ('Sheel',4000);
insert into ContentManagers(cmName,salary) values ('Brandon',500);
insert into ContentManagers(cmName,salary) values ('Payton',NULL);
insert into ContentManagers(cmName,salary) values ('Brady',NULL);
insert into ContentManagers(cmName,salary) values ('Gary',1500);
insert into ContentManagers(cmName,salary) values ('Hank',NULL);
insert into ContentManagers(cmName,salary) values ('Hannah',2000);
insert into ContentManagers(cmName,salary) values ('Steph',1200);
insert into ContentManagers(cmName,salary) values ('Don',NULL);
insert into ContentManagers(cmName,salary) values ('Sheldon',1500);
insert into ContentManagers(cmName,salary) values ('Sally',3000);
insert into ContentManagers(cmName,salary) values ('Robert',NULL);

delete from Editors;
insert into Editors values (1);
insert into Editors values (4);
insert into Editors values (6);
insert into Editors values (8);
insert into Editors values (11);

delete from Authors;
insert into Authors values (2);
insert into Authors values (3);
insert into Authors values (5);
insert into Authors values (7);
insert into Authors values (9);
insert into Authors values (10);

delete from Journalists;
insert into Journalists values (12);
insert into Journalists values (13);
insert into Journalists values (14);
insert into Journalists values (15);

delete from Payrolls;
insert into Payrolls(cmId,amount,paymentDate,claimDate) values (2,2000,'2020-03-01',NULL);
insert into Payrolls(cmId,amount,paymentDate,claimDate) values (2,2000,'2020-02-01',NULL);
insert into Payrolls(cmId,amount,paymentDate,claimDate) values (2,2000,'2020-01-01','2020-02-07');
insert into Payrolls(cmId,amount,paymentDate,claimDate) values (2,2000,'2019-12-01','2019-12-10');
insert into Payrolls(cmId,amount,paymentDate,claimDate) values (4,4000,'2020-01-01','2020-01-05');
insert into Payrolls(cmId,amount,paymentDate,claimDate) values (4,4000,'2019-11-01','2019-11-06');
insert into Payrolls(cmId,amount,paymentDate,claimDate) values (4,4000,'2019-08-01','2019-08-05');
insert into Payrolls(cmId,amount,paymentDate,claimDate) values (1,1000,'2020-02-19',NULL);
insert into Payrolls(cmId,amount,paymentDate,claimDate) values (1,1500,'2019-11-10','2019-11-10');
insert into Payrolls(cmId,amount,paymentDate,claimDate) values (7,500,'2020-03-15',NULL);
insert into Payrolls(cmId,amount,paymentDate,claimDate) values (7,250,'2019-08-15','2019-08-25');
insert into Payrolls(cmId,amount,paymentDate,claimDate) values (11,1200,'2019-06-01','2019-06-01');
insert into Payrolls(cmId,amount,paymentDate,claimDate) values (9,1000,'2020-07-15','2020-07-16');

delete from OrderItems;
insert into OrderItems values (1,1,10,'2019-11-10');
insert into OrderItems values (1,2,50,'2019-07-01');
insert into OrderItems values (2,2,75,'2019-09-01');
insert into OrderItems values (1,3,20,'2019-09-15');
insert into OrderItems values (1,4,35,'2019-06-01');
insert into OrderItems values (1,5,25,'2019-07-10');
insert into OrderItems values (1,6,150,'2019-10-20');
insert into OrderItems values (1,7,30,'2019-08-10');
insert into OrderItems values (2,7,65,'2019-11-11');
insert into OrderItems values (3,7,70,'2020-02-05');
insert into OrderItems values (1,8,3,'2020-02-03');
insert into OrderItems values (2,8,3,'2020-02-10');
insert into OrderItems values (3,8,3,'2020-02-17');
insert into OrderItems values (1,9,10,'2019-12-01');
insert into OrderItems values (2,9,10,'2020-01-01');
insert into OrderItems values (3,9,10,'2020-02-01');
insert into OrderItems values (4,9,10,'2020-03-01');
insert into OrderItems values (1,10,5,'2020-03-01');
insert into OrderItems values (2,10,5,'2020-03-08');
insert into OrderItems values (3,10,5,'2020-03-15');
insert into OrderItems values (1,11,15,'2020-01-15');
insert into OrderItems values (2,11,15,'2020-02-15');
insert into OrderItems values (3,11,15,'2020-03-15');
insert into OrderItems values (1,12,1,'2020-03-07');
insert into OrderItems values (2,12,1,'2020-03-14');
insert into OrderItems values (1,13,7,'2020-03-05');

delete from ItemEditedBy;
insert into ItemEditedBy(cmId,orderItemId,pubId) values (1,1,1);
insert into ItemEditedBy(cmId,orderItemId,pubId) values (1,3,7);
insert into ItemEditedBy(cmId,orderItemId,pubId) values (1,1,5);
insert into ItemEditedBy(cmId,orderItemId,pubId) values (4,2,10);
insert into ItemEditedBy(cmId,orderItemId,pubId) values (4,2,11);
insert into ItemEditedBy(cmId,orderItemId,pubId) values (6,1,13);
insert into ItemEditedBy(cmId,orderItemId,pubId) values (6,3,7);
insert into ItemEditedBy(cmId,orderItemId,pubId) values (6,1,4);
insert into ItemEditedBy(cmId,orderItemId,pubId) values (8,2,9);
insert into ItemEditedBy(cmId,orderItemId,pubId) values (11,3,11);
insert into ItemEditedBy(cmId,orderItemId,pubId) values (11,2,7);
insert into ItemEditedBy(cmId,orderItemId,pubId) values (11,1,1);
insert into ItemEditedBy(cmId,orderItemId,pubId) values (11,1,5);

delete from Editions;
insert into Editions(orderItemId,pubId,ISBN) values (1,1,'978-3-9589-6835-6');
insert into Editions(orderItemId,pubId,ISBN) values (1,2,'978-8-2877-8421-9');
insert into Editions(orderItemId,pubId,ISBN) values (2,2,'978-5-4266-2066-7');
insert into Editions(orderItemId,pubId,ISBN) values (1,3,'978-6-5436-1412-6');
insert into Editions(orderItemId,pubId,ISBN) values (1,4,'978-1-0630-5322-6');
insert into Editions(orderItemId,pubId,ISBN) values (1,5,'978-3-1846-3361-5');
insert into Editions(orderItemId,pubId,ISBN) values (1,6,'978-6-9269-9519-4');
insert into Editions(orderItemId,pubId,ISBN) values (1,7,'978-9-7069-6234-8');
insert into Editions(orderItemId,pubId,ISBN) values (2,7,'978-2-9146-1048-3');
insert into Editions(orderItemId,pubId,ISBN) values (3,7,'978-0-5391-6629-3');

delete from Issues;
insert into Issues(orderItemId,pubId,issueNo) values (1,8,1);
insert into Issues(orderItemId,pubId,issueNo) values (2,8,2);
insert into Issues(orderItemId,pubId,issueNo) values (3,8,3);
insert into Issues(orderItemId,pubId,issueNo) values (1,9,1);
insert into Issues(orderItemId,pubId,issueNo) values (2,9,2);
insert into Issues(orderItemId,pubId,issueNo) values (3,9,3);
insert into Issues(orderItemId,pubId,issueNo) values (4,9,4);
insert into Issues(orderItemId,pubId,issueNo) values (1,10,1);
insert into Issues(orderItemId,pubId,issueNo) values (2,10,2);
insert into Issues(orderItemId,pubId,issueNo) values (3,10,3);
insert into Issues(orderItemId,pubId,issueNo) values (1,11,1);
insert into Issues(orderItemId,pubId,issueNo) values (2,11,2);
insert into Issues(orderItemId,pubId,issueNo) values (3,11,3);
insert into Issues(orderItemId,pubId,issueNo) values (1,12,1);
insert into Issues(orderItemId,pubId,issueNo) values (2,12,2);
insert into Issues(orderItemId,pubId,issueNo) values (1,13,1);

delete from Topics;
insert into Topics(topicName) values ('Adventure');
insert into Topics(topicName) values ('Autobiography');
insert into Topics(topicName) values ('Biography');
insert into Topics(topicName) values ('Crime');
insert into Topics(topicName) values ('Encyclopedia');
insert into Topics(topicName) values ('Drama');
insert into Topics(topicName) values ('Health');
insert into Topics(topicName) values ('Mystery');
insert into Topics(topicName) values ('Romance');
insert into Topics(topicName) values ('Science fiction');
insert into Topics(topicName) values ('Thriller');
insert into Topics(topicName) values ('Art');
insert into Topics(topicName) values ('Travel');
insert into Topics(topicName) values ('True crime');
insert into Topics(topicName) values ('Science');
insert into Topics(topicName) values ('Self help');
insert into Topics(topicName) values ('Political');
insert into Topics(topicName) values ('Math');

delete from Chapters;
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 1',1,1,'This is Chapter 1 of OrderItem 1, Publication 1.','2019-10-05');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 2',1,1,'This is Chapter 2 of OrderItem 1, Publication 1.','2019-10-04');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 3',1,1,'This is Chapter 3 of OrderItem 1, Publication 1.','2019-10-23');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 1',1,2,'This is Chapter 1 of OrderItem 1, Publication 2.','2019-05-01');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 2',1,2,'This is Chapter 2 of OrderItem 1, Publication 2.','2019-05-25');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 1',2,2,'This is Chapter 1 of OrderItem 2, Publication 2.','2019-08-01');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 2',2,2,'This is Chapter 2 of OrderItem 2, Publication 2.','2019-08-02');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 1',1,3,'This is Chapter 1 of OrderItem 1, Publication 3.','2019-07-05');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 2',1,3,'This is Chapter 2 of OrderItem 1, Publication 3.','2019-07-19');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 3',1,3,'This is Chapter 3 of OrderItem 1, Publication 3.','2019-07-26');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 1',1,4,'This is Chapter 1 of OrderItem 1, Publication 4.','2019-05-20');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 2',1,4,'This is Chapter 2 of OrderItem 1, Publication 4.','2019-05-21');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 1',1,5,'This is Chapter 1 of OrderItem 1, Publication 5.','2019-03-26');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 2',1,5,'This is Chapter 2 of OrderItem 1, Publication 5.','2019-04-16');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 3',1,5,'This is Chapter 3 of OrderItem 1, Publication 5.','2019-05-23');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 4',1,5,'This is Chapter 4 of OrderItem 1, Publication 5.','2019-05-14');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 1',1,6,'This is Chapter 1 of OrderItem 1, Publication 6.','2019-09-24');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 1',1,7,'This is Chapter 1 of OrderItem 1, Publication 7.','2019-06-28');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 2',1,7,'This is Chapter 2 of OrderItem 1, Publication 7.','2019-07-02');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 3',1,7,'This is Chapter 3 of OrderItem 1, Publication 7.','2019-07-05');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 1',2,7,'This is Chapter 1 of OrderItem 2, Publication 7.','2019-10-11');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 2',2,7,'This is Chapter 2 of OrderItem 2, Publication 7.','2019-10-15');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 3',2,7,'This is Chapter 3 of OrderItem 2, Publication 7.','2019-10-19');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 1',3,7,'This is Chapter 1 of OrderItem 3, Publication 7.','2020-01-01');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 2',3,7,'This is Chapter 2 of OrderItem 3, Publication 7.','2020-01-10');
insert into Chapters(title,orderItemId,pubId,chapterText,creationDate) values ('Chapter 3',3,7,'This is Chapter 3 of OrderItem 3, Publication 7.','2020-01-25');

delete from ChapterTopicMappings;
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 1',1,1,'Adventure');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 1',1,1,'Crime');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 2',1,1,'Adventure');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 2',1,1,'Mystery');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 3',1,1,'Mystery');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 3',1,1,'Thriller');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 3',1,1,'Crime');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 1',1,2,'Autobiography');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 2',1,2,'Mystery');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 2',1,2,'Science fiction');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 1',2,2,'Autobiography');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 2',2,2,'Mystery');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 2',2,2,'Science fiction');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 1',1,3,'Romance');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 1',1,3,'Crime');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 2',1,3,'Romance');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 2',1,3,'Drama');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 2',1,3,'Thriller');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 3',1,3,'Drama');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 1',1,4,'Biography');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 1',1,4,'Self help');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 2',1,4,'Biography');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 2',1,4,'Self help');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 1',1,5,'Encyclopedia');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 2',1,5,'Encyclopedia');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 3',1,5,'Encyclopedia');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 4',1,5,'Encyclopedia');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 1',1,6,'Crime');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 1',1,6,'Drama');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 1',1,6,'Mystery');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 1',1,6,'Thriller');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 1',1,7,'Autobiography');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 1',1,7,'Health');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 2',1,7,'Autobiography');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 3',1,7,'Mystery');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 3',1,7,'Adventure');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 1',2,7,'Autobiography');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 1',2,7,'Health');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 2',2,7,'Autobiography');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 3',2,7,'Mystery');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 3',2,7,'Adventure');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 1',3,7,'Autobiography');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 1',3,7,'Health');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 2',3,7,'Autobiography');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 3',3,7,'Mystery');
insert into ChapterTopicMappings(title,orderItemId,pubId,topicName) values ('Chapter 3',3,7,'Adventure');

delete from ChapterWrittenBy;
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 1',1,1,2);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 2',1,1,2);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 3',1,1,5);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 1',1,2,3);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 2',1,2,7);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 1',2,2,10);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 2',2,2,7);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 1',1,3,5);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 2',1,3,3);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 3',1,3,3);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 1',1,4,9);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 2',1,4,9);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 1',1,5,10);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 2',1,5,10);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 3',1,5,10);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 4',1,5,2);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 1',1,6,5);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 1',1,7,3);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 2',1,7,3);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 3',1,7,5);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 1',2,7,3);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 2',2,7,3);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 3',2,7,5);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 1',3,7,3);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 2',3,7,3);
insert into ChapterWrittenBy(title,orderItemId,pubId,cmId) values ('Chapter 3',3,7,7);

delete from Articles;
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 1',1,8,'This is article 1 of OrderItem 1, Publication 8','2020-01-01');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 2',1,8,'This is article 2 of OrderItem 1, Publication 8','2020-01-02');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 1',2,8,'This is article 1 of OrderItem 2, Publication 8','2020-01-12');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 2',2,8,'This is article 2 of OrderItem 2, Publication 8','2020-01-13');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 1',3,8,'This is article 1 of OrderItem 3, Publication 8','2020-01-18');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 1',1,9,'This is article 1 of OrderItem 1, Publication 9','2019-11-01');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 2',1,9,'This is article 2 of OrderItem 1, Publication 9','2019-11-02');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 1',2,9,'This is article 1 of OrderItem 2, Publication 9','2019-12-01');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 2',2,9,'This is article 2 of OrderItem 2, Publication 9','2019-12-23');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 1',3,9,'This is article 1 of OrderItem 3, Publication 9','2020-01-01');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 2',3,9,'This is article 2 of OrderItem 3, Publication 9','2020-01-15');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 1',4,9,'This is article 1 of OrderItem 4, Publication 9','2020-02-09');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 2',4,9,'This is article 2 of OrderItem 4, Publication 9','2020-02-21');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 3',4,9,'This is article 3 of OrderItem 4, Publication 9','2020-01-28');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 1',1,10,'This is article 1 of OrderItem 1, Publication 10','2020-02-01');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 1',2,10,'This is article 1 of OrderItem 2, Publication 10','2020-02-14');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 2',2,10,'This is article 2 of OrderItem 2, Publication 10','2020-02-22');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 1',3,10,'This is article 1 of OrderItem 3, Publication 10','2020-02-23');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 2',3,10,'This is article 2 of OrderItem 3, Publication 10','2020-02-24');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 1',1,11,'This is article 1 of OrderItem 1, Publication 11','2020-01-10');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 1',2,11,'This is article 1 of OrderItem 2, Publication 11','2020-01-25');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 1',3,11,'This is article 1 of OrderItem 3, Publication 11','2020-03-10');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 1',1,12,'This is article 1 of OrderItem 1, Publication 12','2020-02-01');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 1',2,12,'This is article 1 of OrderItem 2, Publication 12','2020-03-02');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 1',1,13,'This is article 1 of OrderItem 1, Publication 13','2020-02-15');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 2',1,13,'This is article 2 of OrderItem 1, Publication 13','2020-02-19');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 3',1,13,'This is article 3 of OrderItem 1, Publication 13','2020-02-23');
insert into Articles(title,orderItemId,pubId,articleText,creationDate) values ('Article 4',1,13,'This is article 4 of OrderItem 1, Publication 13','2020-02-23');

delete from ArticleTopicMappings;
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 1',1,8,'Art');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 1',1,8,'Travel');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 2',1,8,'Travel');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 2',1,8,'True crime');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 2',1,8,'Self help');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 1',2,8,'Art');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 2',2,8,'Travel');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 2',2,8,'Self help');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 1',3,8,'Art');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 1',1,9,'Political');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 1',1,9,'True crime');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 2',1,9,'Science');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 2',1,9,'Math');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 1',2,9,'Political');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 2',2,9,'Science');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 2',2,9,'Math');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 1',3,9,'Political');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 1',3,9,'True crime');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 2',3,9,'Science');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 1',4,9,'Political');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 2',4,9,'Science');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 3',4,9,'Travel');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 3',4,9,'True crime');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 3',4,9,'Self help');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 3',4,9,'Political');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 1',1,10,'Art');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 1',2,10,'Self help');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 2',2,10,'Political');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 1',3,10,'Self help');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 2',3,10,'Political');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 1',1,11,'Art');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 1',2,11,'Art');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 1',3,11,'Art');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 1',1,12,'Science');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 1',2,12,'Math');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 1',1,13,'Travel');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 2',1,13,'Travel');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 3',1,13,'True Crime');
insert into ArticleTopicMappings(title,orderItemId,pubId,topicName) values ('Article 4',1,13,'Political');

delete from ArticleWrittenBy;
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 1',1,8,14);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 2',1,8,15);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 1',2,8,14);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 2',2,8,15);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 1',3,8,14);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 1',1,9,14);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 2',1,9,13);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 1',2,9,13);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 2',2,9,13);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 1',3,9,13);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 2',3,9,13);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 1',4,9,13);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 2',4,9,12);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 3',4,9,12);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 1',1,10,14);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 1',2,10,14);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 2',2,10,15);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 1',3,10,14);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 2',3,10,15);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 1',1,11,14);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 1',2,11,14);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 1',3,11,14);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 1',1,12,12);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 1',2,12,12);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 1',1,13,12);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 2',1,13,15);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 3',1,13,14);
insert into ArticleWrittenBy(title,orderItemId,pubId,cmId) values ('Article 4',1,13,13);

delete from Distributors;
insert into Distributors(distName,distType,balance,primaryContact) values('Distirbutor 1','wholesale distributor',0,'+19191234567');
insert into Distributors(distName,distType,balance,primaryContact) values('Distirbutor 2','library',0,'+19123456789');
insert into Distributors(distName,distType,balance,primaryContact) values('Distirbutor 3','bookstore',0,'+19151234567');
insert into Distributors(distName,distType,balance,primaryContact) values('Distirbutor 4','bookstore',0,'+19161234567');

delete from Locations;
insert into Locations(contactPerson,phoneNumber,addr,city,distId) values('Steve','+19191234567','Address of Location 1 of Distributor 1','Garner',1);
2 insert into Locations(contactPerson,phoneNumber,addr,city,distId) values('Scott','+19191234568','Address of Location 2 of Distributor 1','Raleigh',1);
1 insert into Locations(contactPerson,phoneNumber,addr,city,distId) values('Matthews','+19191234569','Address of Location 3 of Distributor 1','Greensboro',1);
2 insert into Locations(contactPerson,phoneNumber,addr,city,distId) values('Bill','+19123456789','Address of Location 1 of Distributor 2','Garner',2);
1 insert into Locations(contactPerson,phoneNumber,addr,city,distId) values('Chaman','+19151234567','Address of Location 1 of Distributor 3','Durham',3);
1 insert into Locations(contactPerson,phoneNumber,addr,city,distId) values('Chagan','+19161234567','Address of Location 1 of Distributor 4','Garner',4);
insert into Locations(contactPerson,phoneNumber,addr,city,distId) values('Magan','+19161234568','Address of Location 2 of Distributor 4','Raleigh',4);

delete from Bills;

delete from Orders;
insert into Orders(shippingCost,orderDate,deliveryDate) values(9.99,'2019-07-19','2019-07-21');
insert into Orders(shippingCost,orderDate,deliveryDate) values(9.99,'2019-09-15','2019-09-17');
insert into Orders(shippingCost,orderDate,deliveryDate) values(14.99,'2020-01-04','2020-01-09');
insert into Orders(shippingCost,orderDate,deliveryDate) values(12.99,'2020-02-25','2020-02-28');
insert into Orders(shippingCost,orderDate,deliveryDate) values(12.99,'2020-03-10','2020-03-13');
insert into Orders(shippingCost,orderDate,deliveryDate) values(12.99,'2020-03-16','2020-03-19');
insert into Orders(shippingCost,orderDate,deliveryDate) values(9.99,'2020-03-20','2020-03-22');

delete from OrderBillMappings;

delete from OrderContains;
insert into OrderContains(orderItemId,pubId,orderId,quantity) values(1,5,1)
insert into OrderContains(orderItemId,pubId,orderId,quantity) values(1,4,1)
insert into OrderContains(orderItemId,pubId,orderId,quantity) values(2,2,2)
insert into OrderContains(orderItemId,pubId,orderId,quantity) values(1,2,3)
insert into OrderContains(orderItemId,pubId,orderId,quantity) values(2,9,3)
insert into OrderContains(orderItemId,pubId,orderId,quantity) values(1,5,4)
insert into OrderContains(orderItemId,pubId,orderId,quantity) values(1,6,4)
insert into OrderContains(orderItemId,pubId,orderId,quantity) values(3,8,4)
insert into OrderContains(orderItemId,pubId,orderId,quantity) values(1,9,4)
insert into OrderContains(orderItemId,pubId,orderId,quantity) values(1,5,5)
insert into OrderContains(orderItemId,pubId,orderId,quantity) values(4,9,5)
insert into OrderContains(orderItemId,pubId,orderId,quantity) values(1,10,6)
insert into OrderContains(orderItemId,pubId,orderId,quantity) values(1,9,7)
insert into OrderContains(orderItemId,pubId,orderId,quantity) values(3,11,7)

insert into OrderItems values (1,1,10,'2019-11-10');
insert into OrderItems values (1,2,50,'2019-07-01');
insert into OrderItems values (2,2,75,'2019-09-01');
insert into OrderItems values (1,3,20,'2019-09-15');
insert into OrderItems values (1,4,35,'2019-06-01');
insert into OrderItems values (1,5,25,'2019-07-10');
insert into OrderItems values (1,6,150,'2019-10-20');
insert into OrderItems values (1,7,30,'2019-08-10');
insert into OrderItems values (2,7,65,'2019-11-11');
insert into OrderItems values (3,7,70,'2020-02-05');
insert into OrderItems values (1,8,3,'2020-02-03');
insert into OrderItems values (2,8,3,'2020-02-10');
insert into OrderItems values (3,8,3,'2020-02-17');
insert into OrderItems values (1,9,10,'2019-12-01');
insert into OrderItems values (2,9,10,'2020-01-01');
insert into OrderItems values (3,9,10,'2020-02-01');
insert into OrderItems values (4,9,10,'2020-03-01');
insert into OrderItems values (1,10,5,'2020-03-01');
insert into OrderItems values (2,10,5,'2020-03-08');
insert into OrderItems values (3,10,5,'2020-03-15');
insert into OrderItems values (1,11,15,'2020-01-15');
insert into OrderItems values (2,11,15,'2020-02-15');
insert into OrderItems values (3,11,15,'2020-03-15');
insert into OrderItems values (1,12,1,'2020-03-07');
insert into OrderItems values (2,12,1,'2020-03-14');
insert into OrderItems values (1,13,7,'2020-03-05');


delete from OrderPlacedBy;


























































