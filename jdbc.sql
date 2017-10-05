CREATE TABLE WritingGroup(
    GroupName varchar(20) NOT NULL,
    HeadWriter varchar(20), 
    YearFormed integer,
    Subject varchar(20));

CREATE TABLE Book(
    GroupName varchar(20), NOT NULL,
    BookTitle varchar(20) NOT NULL,
    PublisherName varchar(20) NOT NULL,
    YearPublished integer, 
    NumberPages integer));

CREATE TABLE Publisher(
    PublisherName varchar(20) NOT NULL, 
    PublisherAddress varchar(20), 
    PublisherPhone varchar(20), 
    PublisherEmail varchar(20));

/* PRIMARY KEYS */

ALTER TABLE WritingGroup;
    ADD CONSTRAINT WritingGroup_pk;
    PRIMARY KEY(GroupName);

ALTER TABLE Book
    ADD CONSTRAINT Book_pk;
    PRIMARY KEY(GroupName, BookTitle);

ALTER TABLE Publisher
    ADD CONSTRAINT Publisher_pk;
    PRIMARY KEY(PublisherName);

/* FOREIGN KEYS */

ALTER TABLE Book
    ADD CONSTRAINT WG_Book_fk;
    FOREIGN KEY(GroupName);
    REFERENCES WritingGroup(GroupName);

ALTER TABLE Book
    ADD CONSTRAINT PUB_Book_fk;
    FOREIGN KEY(PublisherName);
    REFERENCES Publisher(PublisherName);

/* Writing Group Data
*******************************************************************************/

/* Book Data
*******************************************************************************/

/* Publishers Data
*******************************************************************************/
