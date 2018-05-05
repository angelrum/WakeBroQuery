DROP TABLE register             IF EXISTS;
DROP TABLE client_ticket_story  IF EXISTS;
DROP TABLE client_ticket        IF EXISTS;
DROP TABLE ticket               IF EXISTS;
DROP TABLE client               IF EXISTS;
DROP TABLE user_roles           IF EXISTS;
DROP TABLE user                 IF EXISTS;
DROP SEQUENCE GLOBAL_SEQ        IF EXISTS;
DROP SEQUENCE GLOBAL_SEQ_TICKET IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;

CREATE TABLE user
(
  id          INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  date_create TIMESTAMP DEFAULT now() NOT NULL,
  firstname   VARCHAR(30)             NOT NULL,
  lastname    VARCHAR(30)             NOT NULL,
  secondname  VARCHAR(30)             NOT NULL,
  login       VARCHAR(20)             NOT NULL,
  password    VARCHAR(20)             NOT NULL,
  email       VARCHAR(30),
  enabled     BOOLEAN   DEFAULT TRUE  NOT NULL,
  CONSTRAINT user_idx UNIQUE (login)
);

CREATE TABLE user_roles
(
  user_id     INTEGER NOT NULL,
  role        VARCHAR(255),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
);


CREATE TABLE client
(
  id          INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  date_create TIMESTAMP DEFAULT now() NOT NULL,
  firstname   VARCHAR(30)             NOT NULL,
  lastname    VARCHAR(30)             NOT NULL,
  secondname  VARCHAR(30)             NOT NULL,
  telnumber   VARCHAR(20)             NOT NULL,
  city        VARCHAR(30)             NOT NULL,
  user_id     INTEGER                 NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX client_unique_index ON client (firstname, lastname, secondname, telnumber) ;

CREATE TABLE ticket
(
  id          INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  date_create TIMESTAMP DEFAULT now()   NOT NULL,
  pass        VARCHAR(30)               NOT NULL,
  name        VARCHAR(30)               NOT NULL,
  enable      BOOLEAN DEFAULT TRUE      NOT NULL,
  equipment   BOOLEAN                   NOT NULL,
  duration    INTEGER                   NOT NULL,
  start_date  DATE,
  end_date    DATE,
  start_time  TIME,
  end_time    TIME,
  month       INTEGER,
  year        INTEGER                   NOT NULL,
  cost        DOUBLE                    NOT NULL,
  weekendcost DOUBLE                    NOT NULL,
  userId      INTEGER                   NOT NULL,
  FOREIGN KEY (userId) REFERENCES user(id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX ticket_unique_index ON ticket(pass, name, year);

CREATE TABLE client_ticket
(
  id          INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  date_create TIMESTAMP DEFAULT now()         NOT NULL,
  user_id     INTEGER                         NOT NULL,
  client_id   INTEGER                         NOT NULL,
  ticket_id   INTEGER                         NOT NULL,
  date_start  DATE      DEFAULT CURRENT_DATE  NOT NULL,
  date_end    DATE      DEFAULT CURRENT_DATE  NOT NULL,
  active      BOOLEAN   DEFAULT TRUE          NOT NULL,

  FOREIGN KEY (user_id)   REFERENCES user(id)   ON DELETE CASCADE,
  FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE,
  FOREIGN KEY (ticket_id) REFERENCES ticket(id) ON DELETE CASCADE
);

CREATE TABLE client_ticket_story
(
  id                INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  client_ticket_id  INTEGER                   NOT NULL,
  date              DATE DEFAULT CURRENT_DATE NOT NULL,
  time_start        TIME DEFAULT CURRENT_TIME NOT NULL,
  time_end          TIME,
  user_id           INTEGER                   NOT NULL,
  CONSTRAINT story_ticket_idx UNIQUE (client_ticket_id, date, time_start),
  FOREIGN KEY (client_ticket_id) REFERENCES client_ticket(id) ON DELETE CASCADE
);

CREATE TABLE register
(
  user_id     INTEGER   NOT NULL FOREIGN KEY REFERENCES user(id),
  open_shift  TIMESTAMP DEFAULT now()         NOT NULL,
  close_shift TIMESTAMP,
  CONSTRAINT register_idx UNIQUE (user_id, open_shift)
)