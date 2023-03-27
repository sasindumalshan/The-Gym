create table customer
(
	customer_id varchar(20) not null
		primary key,
	fist_name varchar(45) not null,
	last_name varchar(45) null,
	address_street text not null,
	address_city text not null,
	address_lene text null,
	e_mail varchar(45) null,
	birthday date null,
	nic varchar(20) null,
	contact_number varchar(15) null,
	gender enum('MALE', 'FEMALE', 'OTHER') null,
	package_id varchar(20) null,
	package_activate_date date null,
	date date null,
	constraint e_mail
		unique (e_mail),
	constraint nic
		unique (nic),
	constraint customer_ibfk_1
		foreign key (package_id) references package (package_Id)
			on update cascade on delete cascade
);

create index package_id
	on customer (package_id);

