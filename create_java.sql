create table Publications(pubId int primary key auto_increment,title varchar(200) not null);

create table Books(pubId int primary key,noOfEditions int not null default 1,foreign key (pubId) references Publications(pubId) on delete cascade on update cascade,constraint chk_no_editions check (noOFEditions>0));

create table Topics(topicName varchar(50) primary key);

create table BookTopicMappings(pubId int,topicName varchar(50),primary key (pubId, topicName),foreign key (topicName) references Topics(topicName) on delete cascade on update cascade,foreign key (pubId) references Books(pubId) on delete cascade on update cascade);

create table PeriodicPublications(pubId int primary key,periodicityType varchar(50) not null,frequency varchar(50) not null,foreign key (pubId) references Publications(pubId) on delete cascade on update cascade);

create table ContentManagers(cmId int primary key auto_increment,cmName varchar(200) not null,salary float,age int not null,gender char(1) not null,phoneNo char(12) not null,emailId varchar(100) not null,address varchar(200) not null,constraint chk_age_gender check (age>0 and gender in ('M','F')));

create table Editors(cmId int primary key,foreign key (cmId) references ContentManagers(cmId) on delete cascade on update cascade);

create table Authors(cmId int primary key,foreign key (cmId) references ContentManagers(cmId) on delete cascade on update cascade);

create table Journalists(cmId int primary key,foreign key (cmId) references ContentManagers(cmId) on delete cascade on update cascade);

create table Payrolls(payId int primary key auto_increment,cmId int,amount float not null,paymentDate date not null,claimDate date,foreign key (cmId) references ContentManagers(cmId) on delete set null on update cascade,constraint chk_amt_dates check (amount>=0 and (claimDate is null or claimDate>=paymentDate)));

create table OrderItems(orderItemId int not null,pubId int,price float not null,pubDate date not null,primary key (orderItemId, pubId),foreign key (pubId) references Publications(pubId) on delete cascade on update cascade,constraint chk_price check (price>=0));

create table ItemEditedBy(cmId int,orderItemId int,pubId int,primary key (cmId,orderItemId,pubId),foreign key (cmId) references Editors(cmId) on delete cascade on update cascade,foreign key (orderItemId, pubId) references OrderItems(orderItemId, pubId) on delete cascade on update cascade);

create table Editions(orderItemId int,pubId int, ISBN varchar(20) not null unique,primary key (orderItemId, pubId),foreign key (orderItemId, pubId) references OrderItems(orderItemId, pubId) on delete cascade on update cascade); 

create table Issues(orderItemId int,pubId int,issueNo int not null default 1,primary key (orderItemId, pubId),foreign key (orderItemId, pubId) references OrderItems(orderItemId, pubId) on delete cascade on update cascade,constraint chk_issue_no check (issueNo>0));

create table Chapters(title varchar(200) not null,orderItemId int,pubId int,chapterText varchar(200),creationDate date not null,primary key (title, orderItemId, pubId),foreign key (orderItemId, pubId) references Editions(orderItemId, pubId) on delete cascade on update cascade);

create table ChapterTopicMappings(title varchar(200), orderItemId int,pubId int,topicName varchar(50),primary key (title, orderItemId, pubId, topicName),foreign key (topicName) references Topics(topicName) on delete cascade on update cascade,foreign key (title, orderItemId, pubId) references Chapters(title, orderItemId, pubId) on delete cascade on update cascade);

create table ChapterWrittenBy(title varchar(200),orderItemId int,pubId int,cmId int,primary key (title, orderItemId, pubId, cmId),foreign key (cmId) references Authors(cmId) on delete cascade on update cascade,foreign key (title, orderItemId, pubId) references Chapters(title, orderItemId, pubId) on delete cascade on update cascade);

create table Articles(title varchar(200) not null,orderItemId int,pubId int,articleText varchar(200),creationDate date not null,primary key (title, orderItemId, pubId),foreign key (orderItemId, pubId) references Issues(orderItemId, pubId) on delete cascade on update cascade);

create table ArticleTopicMappings(title varchar(200), orderItemId int,pubId int,topicName varchar(50),primary key (title, orderItemId, pubId, topicName),foreign key (topicName) references Topics(topicName) on delete cascade on update cascade,foreign key (title, orderItemId, pubId) references Articles(title, orderItemId, pubId) on delete cascade on update cascade);

create table ArticleWrittenBy(title varchar(200),orderItemId int,pubId int,cmId int,primary key (title, orderItemId, pubId, cmId),foreign key (cmId) references Journalists(cmId) on delete cascade on update cascade,foreign key (title, orderItemId, pubId) references Articles(title, orderItemId, pubId) on delete cascade on update cascade);

create table Distributors(distId int primary key auto_increment,distName varchar(200) not null,distType varchar(50) not null,balance float not null default 0,primaryContact char(12) not null,constraint chk_bal_disttype check (balance>=0 and distType in ('wholesale distributor', 'library', 'bookstore')));

create table Locations(locId int primary key auto_increment,contactPerson varchar(200) not null,phoneNumber char(12) not null,addr varchar(200) not null,city varchar(50) not null,distId int,foreign key (distId) references Distributors(distId) on delete cascade on update cascade);

create table Bills(billId int primary key auto_increment,billAmt float not null,generationDate date not null,receiptDate date,distId int,foreign key (distId) references Distributors(distId) on delete set null on update cascade,constraint chk_amt_dates_bill check (billAmt>=0 and receiptDate>=generationDate));

create table Orders(orderId int primary key auto_increment,shippingCost float not null,orderDate date not null,deliveryDate date not null,locId int,foreign key (locId) references Locations(locId) on delete cascade on update cascade,constraint chk_cost_dates check (shippingCost>=0 and deliveryDate>=orderDate));

create table OrderBillMappings(orderId int,billId int,foreign key (orderId) references Orders(orderId) on delete cascade on update cascade,foreign key (billId) references Bills(billId) on delete cascade on update cascade);

create table OrderContains(orderItemId int,pubId int,orderId int,quantity int not null,primary key(orderId, orderItemId, pubId),foreign key (orderId) references Orders(orderId) on delete cascade on update cascade,foreign key (orderItemId, pubId) references OrderItems(orderItemId, pubId) on delete cascade on update cascade,constraint chk_qty check (quantity>0));
