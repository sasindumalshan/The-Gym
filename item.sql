create table item
(
	item_id varchar(20) not null
		primary key,
	item_name varchar(45) not null,
	category varchar(45) not null,
	qut int not null,
	price decimal not null,
	brand varchar(45) not null,
	description text null
);

