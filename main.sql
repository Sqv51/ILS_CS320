use ils_db;

create table Users(userID integer,
      userName varchar(20),
                     password varchar(20),
                     dueFine integer,
                     primary key(userID),
                     isStaff bool);

create table Book(bookID integer,
                  bookName varchar(100),
                  author varchar(30),
                  genre varchar(20),
                  year integer,
                  rating double,
                  description varchar(140),
                  isAvailable bool,
                  primary key(bookID));

create table Reserves(userID integer,
       bookID integer,
                      dueDate date,
                      primary key(userID, bookID),
                      foreign key(userID) references Users(userID),
                      foreign key(bookID) references Book(bookID));

create table BookList(userID integer,
                        bookID integer,
                        bookListName varchar(20),
                        primary key(userID, bookID, bookListName),
                        foreign key(userID) references Users(userID),
                        foreign key(bookID) references Book(bookID));

create table UserBookList(userID integer,
                          bookListName varchar(20),
                          primary key(userID, bookListName));

create table Borrow(userID integer,
                                bookID integer,
                    borrowDate date,
                    returnDate date,
                    primary key(userID, bookID),
                    foreign key(userID) references Users(userID),
                    foreign key(bookID) references Book(bookID));

CREATE TABLE BorrowHistory(
     userID INTEGER,
     bookID INTEGER,
     borrowDate DATE,
     returnDate DATE,
     PRIMARY KEY(userID, bookID, borrowDate),
     FOREIGN KEY(userID) REFERENCES Users(userID),
     FOREIGN KEY(bookID) REFERENCES Book(bookID)
 );
 
CREATE TABLE Ratings(
      ratingID INT PRIMARY KEY AUTO_INCREMENT,
      bookID INT,
      memberID INT,
      score INT,
      FOREIGN KEY (bookID) REFERENCES Book(bookID),
      FOREIGN KEY (memberID) REFERENCES Members(memberID)
);

 CREATE TRIGGER after_book_borrow
 AFTER INSERT ON Borrow
 FOR EACH ROW
 INSERT INTO BorrowHistory (userID, bookID, borrowDate, returnDate)
 VALUES (NEW.userID, NEW.bookID, NEW.borrowDate, NULL);

 CREATE TRIGGER after_book_return
 AFTER UPDATE ON Borrow
 FOR EACH ROW
 WHEN OLD.returnDate IS NULL AND NEW.returnDate IS NOT NULL
 INSERT INTO BorrowHistory (userID, bookID, borrowDate, returnDate)
 VALUES (NEW.userID, NEW.bookID, NEW.borrowDate, NEW.returnDate);
