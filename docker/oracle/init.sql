ALTER SESSION SET CONTAINER=FREEPDB1;
create user airflow IDENTIFIED by airflow_password_123 QUOTA UNLIMITED ON USERS;
GRANT CREATE SESSION TO airflow;
GRANT CONNECT, RESOURCE TO airflow;

create table airflow.person
(
    Id        int GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
    Name      varchar(100) not null,
    Document  varchar(14)  not null,
    Birthdate date         not null
);

create user application IDENTIFIED by application_password_123;
GRANT CREATE SESSION TO application;

GRANT CONNECT, RESOURCE TO application;

--------------------------------- INSERTS AIRFLOW PERSON -------------------------------------
INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('João Silva', '12345678901234', TO_DATE('1990-05-15', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Maria Oliveira', '98765432109876', TO_DATE('1985-08-22', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Carlos Souza', '45612378901234', TO_DATE('1995-02-10', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Ana Costa', '32165498703625', TO_DATE('2000-11-30', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Lucas Pereira', '78945612304567', TO_DATE('1992-04-18', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Fernanda Lima', '96385274123658', TO_DATE('1988-07-11', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Ricardo Gomes', '14725836908412', TO_DATE('1993-01-04', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Juliana Santos', '25836914705869', TO_DATE('1996-10-26', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Pedro Rocha', '36914725806534', TO_DATE('1987-12-03', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Gabriela Martins', '74185296307412', TO_DATE('2001-09-21', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Ricardo Almeida', '12348765432109', TO_DATE('1994-11-15', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Patrícia Souza', '98765432106543', TO_DATE('1989-02-20', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Renato Pereira', '45678912305678', TO_DATE('1992-07-03', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Sofia Costa', '32165478901234', TO_DATE('1997-03-25', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Bruna Lima', '74125896305673', TO_DATE('1986-12-01', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Eduardo Rocha', '15975348603264', TO_DATE('1993-09-10', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Larissa Martins', '85296374105261', TO_DATE('1999-01-18', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Tiago Santos', '65478932103685', TO_DATE('1994-06-30', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Fernanda Rocha', '36925814703298', TO_DATE('1990-04-14', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Felipe Oliveira', '74196325805674', TO_DATE('1987-09-09', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Lucas Almeida', '85274196305786', TO_DATE('1995-06-17', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Mariana Costa', '96374125803674', TO_DATE('1988-09-03', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Juliano Santos', '75315948602365', TO_DATE('1991-11-21', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Aline Pereira', '96325874102653', TO_DATE('1990-10-11', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Carlos Lima', '14785296304521', TO_DATE('1987-08-07', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Patrícia Rocha', '25874136902468', TO_DATE('1994-01-29', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Eduardo Santos', '36985274103612', TO_DATE('1993-05-15', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Tatiane Martins', '74185296305874', TO_DATE('1996-06-22', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Vinícius Oliveira', '15975345601235', TO_DATE('1989-12-30', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Gustavo Souza', '96314785204867', TO_DATE('1992-04-17', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Beatriz Rocha', '25896314703598', TO_DATE('1990-02-28', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Ricardo Lima', '74196385205641', TO_DATE('1986-11-13', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Juliana Almeida', '36925874104236', TO_DATE('1994-08-04', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Gabriel Santos', '85296374104756', TO_DATE('1993-07-21', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Maria Rocha', '65498732103458', TO_DATE('1991-09-17', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Felipe Souza', '15935725804276', TO_DATE('1989-01-10', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Leonardo Costa', '96374125803546', TO_DATE('1992-02-14', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Carla Martins', '74185296305617', TO_DATE('1988-07-25', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Bruno Oliveira', '25896314705429', TO_DATE('1995-04-09', 'YYYY-MM-DD'));

INSERT INTO airflow.person (Name, Document, Birthdate)
VALUES ('Marcos Pereira', '36925874103165', TO_DATE('1990-05-22', 'YYYY-MM-DD'));
