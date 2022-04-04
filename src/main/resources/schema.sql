-- todo - NOT NULL could be added as additional safety gate
create table ASSET
(
    id IDENTITY primary key,
    name           VARCHAR2(150) NOT NULL,
    currency       VARCHAR2(200) NOT NULL,
    year_of_issue YEAR NOT NULL,
    assessed_value DEC(20) NOT NULL
);
