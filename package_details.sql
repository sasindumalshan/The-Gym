create table package_details
(
	customer_id varchar(20) null,
	package_Id varchar(20) null,
	date date null,
	constraint package_details_ibfk_1
		foreign key (customer_id) references customer (customer_id)
			on update cascade on delete cascade,
	constraint package_details_ibfk_2
		foreign key (package_Id) references package (package_Id)
			on update cascade on delete cascade
);

create index customer_id
	on package_details (customer_id);

create index package_Id
	on package_details (package_Id);

