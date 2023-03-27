create table order_details
(
	order_id varchar(20) null,
	item_id varchar(20) null,
	price decimal not null,
	qut int not null,
	constraint order_details_ibfk_1
		foreign key (item_id) references item (item_id)
			on update cascade on delete cascade,
	constraint order_details_ibfk_2
		foreign key (order_id) references orders (order_id)
			on update cascade on delete cascade
);

create index item_id
	on order_details (item_id);

create index order_id
	on order_details (order_id);

