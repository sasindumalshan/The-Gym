create table employee_attendance
(
	date date null,
	time time null,
	employee_id varchar(20) null,
	constraint employee_attendance_ibfk_1
		foreign key (employee_id) references employee (employee_id)
			on update cascade on delete cascade
);

create index employee_id
	on employee_attendance (employee_id);

