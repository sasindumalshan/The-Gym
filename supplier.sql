create table supplier
(
	company_name varchar(45) not null,
	supplier_id varchar(20) not null
		primary key,
	email varchar(45) null,
	mobile_no varchar(15) null,
	location text null,
	constraint email
		unique (email)
);

