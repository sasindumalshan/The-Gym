create table employee_salary_details
(
	employee_id varchar(20) null,
	date date not null,
	price decimal not null,
	salary_id varchar(20) null,
	constraint employee_salary_details_ibfk_1
		foreign key (employee_id) references employee (employee_id)
			on update cascade on delete cascade,
	constraint employee_salary_details_ibfk_2
		foreign key (salary_id) references salary (salary_Id)
			on update cascade on delete cascade
);

create index employee_id
	on employee_salary_details (employee_id);

create index salary_id
	on employee_salary_details (salary_id);

