use ils_db;

create table Users(userID integer,
      userName varchar(20),
                     password varchar(20),
                     dueFine integer,
                     primary key(userID),
                     isStaff bool);

create table Book(bookID integer,
      bookName varchar(100),
                  genre varchar(20),
                  year integer,
                  rating double,
                  description varchar(140),
                  primary key(bookID),
                  isAvailable bool);

create table Reserves(userID integer,
       bookID integer,
                      dueDate date,
                      primary key(userID, bookID),
                      foreign key(userID) references Users(userID),
                      foreign key(bookID) references Book(bookID));

create table Favourites(userID integer,
                        bookID integer,
                        primary key(userID, bookID),
                        foreign key(userID) references Users(userID),
                        foreign key(bookID) references Book(bookID));

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