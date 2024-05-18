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
                  primary key(bookID));

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