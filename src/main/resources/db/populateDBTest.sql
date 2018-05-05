DELETE FROM USER;
DELETE FROM CLIENT;
DELETE FROM TICKET;
DELETE FROM CLIENT_TICKET;

ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO USER (FIRSTNAME, LASTNAME, SECONDNAME, LOGIN, PASSWORD, EMAIL, ENABLED)
VALUES  ('Иван', 'Иванов', 'Иванович', 'admin', 'admin', 'admin@mail.ru', TRUE ), /*id = 100000*/
        ('Антон', 'Сидоров', 'Антонович', 'test', 'test', 'test@mail.ru', TRUE ); /*id = 100001*/


INSERT INTO USER_ROLES (USER_ID, ROLE)
VALUES  (100000, 'ADMIN'),
        (100001, 'USER');

INSERT INTO CLIENT (FIRSTNAME, LASTNAME, SECONDNAME, TELNUMBER, CITY, USER_ID) VALUES
        ('Иван', 'Иванов', 'Иванович', '+7(911)111-11-11', 'Анапа', 100000),    /*id = 100002*/
        ('Петр', 'Антонов', 'Андреевич', '+7(911)111-11-13', 'Анапа', 100000);  /*id = 100003*/

INSERT INTO TICKET (PASS, NAME, EQUIPMENT, DURATION, START_DATE, END_DATE, END_TIME, YEAR, MONTH, COST, WEEKENDCOST, USERID) VALUES
        ('ABN_PASS', 'Абонемент на месяц', TRUE, 5, NULL, NULL, NULL, 2018, 1, 100, 110, 100000),   /*id = 100004*/
        ('SECOND_PASS', 'Сет (дневной)', TRUE, 5, CURRENT_DATE, CURRENT_DATE, NULL, 2018, 0, 100, 110, 100000);     /*id = 100005*/
INSERT INTO TICKET (PASS, NAME, ENABLE, EQUIPMENT, DURATION, START_TIME, END_TIME, YEAR, MONTH, COST, WEEKENDCOST, USERID) VALUES
        ('SECOND_PASS', 'Сет (вечерний)',FALSE ,TRUE, 5, NULL, NULL,2018, 0, 100, 110, 100000); /*id = 100006*/

INSERT INTO CLIENT_TICKET (USER_ID, CLIENT_ID, TICKET_ID, DATE_END) VALUES
        (100000, 100002, 100004, CURRENT_DATE); /*100007*/
