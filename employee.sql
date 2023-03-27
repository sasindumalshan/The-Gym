create table employee
(
	employee_id varchar(20) not null
		primary key,
	fist_name varchar(45) not null,
	last_name varchar(45) null,
	usr_name varchar(45) null,
	password varchar(20) null,
	roll varchar(20) not null,
	address_street text null,
	address_city text not null,
	address_lene text null,
	e_mail varchar(45) null,
	birthday date null,
	nic varchar(20) null,
	contact_number varchar(15) null,
	date date null,
	salary_Id varchar(20) null,
	constraint e_mail
		unique (e_mail),
	constraint nic
		unique (nic),
	constraint employee_ibfk_1
		foreign key (salary_Id) references salary (salary_Id)
			on update cascade on delete cascade
);

create index salary_Id
	on employee (salary_Id);

