CREATE TABLE WritingGroups(GroupName varchar(20) NOT NULL, HeadWriter varchar(20), YearFormed integer, Subject varchar(20));
CREATE TABLE Books(BookTitle varchar(20) NOT NULL, PublisherName varchar(20), YearPublished integer, NumberPages integer);
CREATE TABLE Publishers(PublisherName varchar(20) NOT NULL, PublisherAddress varchar(20), PublisherPhone varchar(20), PublisherEmail varchar(20)); 