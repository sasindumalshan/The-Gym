create table orders
(
	order_id varchar(20) not null
		primary key,
	time time not null,
	date date not null,
	final_total decimal not null,
	customer_id varchar(20) null,
	constraint orders_ibfk_1
		foreign key (customer_id) references customer (customer_id)
			on update cascade on delete cascade
);

create index customer_id
	on orders (customer_id);

