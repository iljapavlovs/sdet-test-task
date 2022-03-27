-- todo - NOT NULL could be added as additional safety gate
create table ASSET
(
    id IDENTITY primary key,
    name           VARCHAR2(150),
    currency       VARCHAR2(200),
    year_of_issue YEAR,
    assessed_value DEC(20)
);
