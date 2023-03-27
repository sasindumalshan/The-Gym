create table schedule
(
	customer_id varchar(20) null,
	schedule_id varchar(20) not null
		primary key,
	coach_id varchar(20) null,
	constraint schedule_ibfk_1
		foreign key (customer_id) references customer (customer_id)
			on update cascade on delete cascade,
	constraint schedule_ibfk_2
		foreign key (coach_id) references coach (coach_id)
			on update cascade on delete cascade
);

create index coach_id
	on schedule (coach_id);

create index customer_id
	on schedule (customer_id);

