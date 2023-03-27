create table suppler_order
(
	order_id varchar(20) not null
		primary key,
	total decimal null,
	suppler_id varchar(20) null,
	date date null,
	time time null,
	constraint suppler_order_ibfk_1
		foreign key (suppler_id) references supplier (supplier_id)
			on update cascade on delete cascade
);

create index Suppler_Order
	on suppler_order (suppler_id);

