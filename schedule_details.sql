create table schedule_details
(
	exercises_id varchar(20) null,
	steps int not null,
	schedule_id varchar(20) null,
	constraint schedule_details_ibfk_1
		foreign key (schedule_id) references schedule (schedule_id)
			on update cascade on delete cascade,
	constraint schedule_details_ibfk_2
		foreign key (exercises_id) references exercises (exercises_id)
			on update cascade on delete cascade
);

create index exercises_id
	on schedule_details (exercises_id);

create index schedule_id
	on schedule_details (schedule_id);

