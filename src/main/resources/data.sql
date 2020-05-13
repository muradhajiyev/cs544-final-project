-- Roles Data
INSERT INTO ROLE(ID, NAME) VALUES(1, 'ADMIN');
INSERT INTO ROLE(ID, NAME) VALUES(2, 'CHECKER');
INSERT INTO ROLE(ID, NAME) VALUES(3, 'STUDENT');

-- User Data
INSERT INTO USER VALUES(1, 'admin@gmail.com', 'Admin', 'Admin', '123456', 'admin');
INSERT INTO USER VALUES(2, 'albert@gmail.com', 'Albert', 'Einstein', '123456', 'albert');
INSERT INTO USER VALUES(3, 'anna@gmail.com', 'Anna', 'Behrensmeyer', '123456', 'anna');
INSERT INTO USER VALUES(4, 'pascal@gmail.com', 'Blaise', 'Pascal', '123456', 'pascal');
INSERT INTO USER VALUES(5, 'caroline@gmail.com', 'Caroline', 'Herschel', '123456', 'caroline');
INSERT INTO USER VALUES(6, 'edmond@gmail.com', 'Edmond', 'Halley', '123456', 'edmond');
INSERT INTO USER VALUES(7, 'enrico@gmail.com', 'Enrico', 'Fermi', '123456', 'enrico');
INSERT INTO USER VALUES(8, 'stephen@gmail.com', 'Stephen', 'Hawking', '123456', 'stephen');
INSERT INTO USER VALUES(9, 'newton@gmail.com', 'Isaac', 'Newton', '123456', 'newton');

-- *Setting Roles*
/*
admin - ADMIN
albert - CHECKER
anna - STUDENT
pascal - STUDENT
caroline - STUDENT
edmond - STUDENT
enrico - STUDENT
stephen - STUDENT
newton - STUDENT
*/

INSERT INTO USER_ROLE VALUES (1, 1);
INSERT INTO USER_ROLE VALUES (2, 2);
INSERT INTO USER_ROLE VALUES (3, 3);
INSERT INTO USER_ROLE VALUES (4, 3);
INSERT INTO USER_ROLE VALUES (5, 3);
INSERT INTO USER_ROLE VALUES (6, 3);
INSERT INTO USER_ROLE VALUES (7, 3);
INSERT INTO USER_ROLE VALUES (8, 3);
INSERT INTO USER_ROLE VALUES (9, 3);


-- Appointment Data
INSERT INTO APPOINTMENT (ID, DATE_TIME, LOCATION, PROVIDER) VALUES(1, '2020-05-23T10:00:00', 'Verill Hall #35', 2);