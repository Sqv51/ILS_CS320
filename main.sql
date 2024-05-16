use database_name;

create table Student(studentID integer,
					 userName varchar(20),
                     password varchar(20),
                     dueFine integer,
                     primary key(studentID));
                     
create table Book(bookID integer,
				  bookName varchar(100),
                  genre varchar(20),
                  year integer,
                  rating double,
                  description varchar(140),
                  primary key(bookID));
                  
create table Reserves(studentID integer,
					  bookID integer,
                      dueDate date,
                      primary key(studentID, bookID),
                      foreign key(studentID) references Student(studentID),
                      foreign key(bookID) references Book(bookID));

create table Favourites(studentID integer,
                        bookID integer,
                        primary key(studentID, bookID),
                        foreign key(studentID) references Student(studentID),
                        foreign key(bookID) references Book(bookID));
                      
create table Staff(staffID integer,
				   userName varchar(20),
                   password varchar(20),
                   primary key(staffID));
