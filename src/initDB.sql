-- Adding 50 users
DELIMITER //
CREATE PROCEDURE AddUsers()
BEGIN
  DECLARE i INT DEFAULT 1;
  WHILE i <= 50 DO
    INSERT INTO Users (userID, userName, password, dueFine, isStaff)
    VALUES (i, CONCAT('User', i), 'password', 0, false);
    SET i = i + 1;
  END WHILE;
END //
DELIMITER ;

CALL AddUsers();

-- Adding 50 books
DELIMITER //
CREATE PROCEDURE AddBooks()
BEGIN
  DECLARE i INT DEFAULT 1;
  WHILE i <= 50 DO
    INSERT INTO Book (bookID, bookName, author, genre, year, rating, description, isAvailable)
    VALUES (i, CONCAT('Book', i), 'Author', 'Genre', 2000, 5, 'Description', true);
    SET i = i + 1;
  END WHILE;
END //
DELIMITER ;

CALL AddBooks();

-- Adding 3 staff users
DELIMITER //
CREATE PROCEDURE AddStaffUsers()
BEGIN
  DECLARE i INT DEFAULT 51; -- Start from 51 to avoid conflict with existing user IDs
  WHILE i <= 53 DO
    INSERT INTO Users (userID, userName, password, dueFine, isStaff)
    VALUES (i, CONCAT('Staff', i), 'password', 0, true); -- Set isStaff to true
    SET i = i + 1;
  END WHILE;
END //
DELIMITER ;

CALL AddStaffUsers();