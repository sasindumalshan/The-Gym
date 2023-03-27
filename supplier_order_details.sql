create table supplier_order_details
(
	order_id varchar(20) null,
	item_code varchar(20) null,
	qut int not null,
	price decimal not null,
	constraint supplier_order_details_ibfk_1
		foreign key (order_id) references suppler_order (order_id)
			on update cascade on delete cascade,
	constraint supplier_order_details_ibfk_2
		foreign key (item_code) references item (item_id)
			on update cascade on delete cascade
);

create index item_code
	on supplier_order_details (item_code);

create index order_id
	on supplier_order_details (order_id);

