create table coach
(
	coach_id varchar(20) not null
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
	Register_date date null,
	salary_id varchar(20) null,
	coach_type varchar(20) null,
	constraint e_mail
		unique (e_mail),
	constraint nic
		unique (nic),
	constraint coach_ibfk_1
		foreign key (salary_id) references salary (salary_Id)
			on update cascade on delete cascade
);

create index salary_id
	on coach (salary_id);

