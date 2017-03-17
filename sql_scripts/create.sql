create table SHAPE_TYPES(
ID int AUTO_INCREMENT,
NAME varchar(50),
primary key (ID));

create table SHAPES(
ID bigint AUTO_INCREMENT,
ID_TYPE int,
NAME varchar(256),
DESCRIPTION varchar(256),
primary key (ID),
foreign key (ID_TYPE) references SHAPE_TYPES(ID));

create table COORDINATES(
ID bigint AUTO_INCREMENT,
LONGITUDE double,
LATITUDE double,
ID_SHAPE bigint,
MARKER_NUMBER int,
primary key (ID),
foreign key (ID_SHAPE) references SHAPES(ID));