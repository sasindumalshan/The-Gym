create table coach_salary_details
(
	coach_id varchar(20) null,
	date date not null,
	price decimal not null,
	salary_id varchar(20) null,
	constraint coach_salary_details_ibfk_1
		foreign key (coach_id) references coach (coach_id)
			on update cascade on delete cascade,
	constraint coach_salary_details_ibfk_2
		foreign key (salary_id) references salary (salary_Id)
			on update cascade on delete cascade
);

create index coach_id
	on coach_salary_details (coach_id);

create index salary_id
	on coach_salary_details (salary_id);

