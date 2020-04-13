--delete all data
delete from Publications;
delete from Books;
delete from Topics;
delete from BookTopicMappings;
delete from PeriodicPublications;
delete from ContentManagers;
delete from Editors;
delete from Authors;
delete from Journalists;
delete from Payrolls;
delete from OrderItems;
delete from ItemEditedBy;
delete from Editions;
delete from Issues;
delete from Chapters;
delete from ChapterTopicMappings;
delete from ChapterWrittenBy;
delete from Articles;
delete from ArticleTopicMappings;
delete from ArticleWrittenBy;
delete from Distributors;
delete from Locations;
delete from Bills;
delete from Orders;
delete from OrderBillMappings;
delete from OrderContains;
delete from Topics;

--insert demo data

insert into Publications values (1001,'introduction to database');
insert into Books values (1001,1);
insert into Topics values ('technology');
insert into BookTopicMappings values (1001, 'technology');
insert into OrderItems values (2, 1001, 20, '2018-10-10');
insert into Editions values (2, 1001, '12345');


insert into Publications values (1002,"Healthy Diet");
insert into PeriodicPublications values (1002,'magazine','monthly');
insert into OrderItems values (1,1002,0,'2020-02-24');
insert into Issues values (1,1002,1);
insert into Articles values ('Healthy diet', 1, 1002, 'ABC', '2020-02-24');
insert into Topics values ('health');
insert into ArticleTopicMappings values ('Healthy diet', 1, 1002, 'health');

insert into Publications values (1003,"Animal Science");
insert into PeriodicPublications values (1003,'journal','monthly');
insert into OrderItems values (1,1003,0,'2020-03-01');
insert into Issues values (1,1003,1);
insert into Articles values ('Animal Science', 1, 1003, 'AAA', '2020-03-01');
insert into Topics values ('science');
insert into ArticleTopicMappings values ('Animal Science', 1, 1003, 'science');

insert into Distributors values(2001,'BookSell','bookstore',215,'9191234567');
insert into Locations values(1,'Jason','9191234567','2200, A Street, NC','charlotte',2001);

insert into Distributors values(2002,'BookDist','wholesaler',0,'9291234567');
insert into Locations values(2,Alex','9291234567','2200, B Street, NC','Raleigh',2002);

insert into ContentManagers values (3001,'John',1000,36,'M','9391234567','3001@gmail.com','21 ABC St, NC 27');
insert into Editors values(3001);
insert into Payrolls values (1,3001,1000,'2020-04-01',NULL);

insert into ContentManagers values (3002,'Ethen',1000,30,'M','9491234567','3002@gmail.com','21 ABC St, NC 27606');
insert into Editors values(3002);
insert into Payrolls values (2,3002,1000,'2020-04-01',NULL);

insert into ContentManagers values (3003,'Cathy',NULL,28,'F','9591234567','3003@gmail.com','3300 AAA St, NC 27606');
insert into Authors values(3003);
insert into Payrolls values (3,3003,1200,'2020-04-01',NULL);

insert into ItemEditedBy(cmId,orderItemId,pubId) values (3001,2,1001);
insert into ItemEditedBy(cmId,orderItemId,pubId) values (3002,1,1002);

insert into Orders values(4001,30,'2020-01-02','2020-01-15',1);
insert into OrderContains(orderItemId,pubId,orderId,quantity) values(2,1001,4001,30);

insert into Orders values(4002,15,'2020-02-05','2020-02-15',1);
insert into OrderContains(orderItemId,pubId,orderId,quantity) values(2,1001,4002,10);

insert into Orders values(4003,15,'2020-02-10','2020-02-25',2);
insert into OrderContains(orderItemId,pubId,orderId,quantity) values(1,1003,4003,10);

insert into Bills values(1,630,'2020-01-02','2020-01-02',2001);
insert into OrderBillMappings(orderId,billId) values (4001,1);

insert into Bills values(2,215,'2020-02-05',NULL,2001);
insert into OrderBillMappings(orderId,billId) values (4002,2);

insert into Bills values(3,115,'2020-02-10','2020-02-10',2002);
insert into OrderBillMappings(orderId,billId) values (4003,3);