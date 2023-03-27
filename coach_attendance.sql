create table coach_attendance
(
	date date null,
	time time null,
	coach_id varchar(20) null,
	constraint coach_attendance_ibfk_1
		foreign key (coach_id) references coach (coach_id)
			on update cascade on delete cascade
);

create index coach_id
	on coach_attendance (coach_id);

