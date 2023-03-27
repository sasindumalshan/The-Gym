create table customer_attendance
(
	date date null,
	time time null,
	customer_id varchar(20) null,
	constraint customer_attendance_ibfk_1
		foreign key (customer_id) references customer (customer_id)
			on update cascade on delete cascade
);

create index customer_id
	on customer_attendance (customer_id);

