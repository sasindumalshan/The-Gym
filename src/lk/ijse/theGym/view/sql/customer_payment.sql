create table customer_payment
(
	monthly_fees decimal(8,2) not null,
	date date not null,
	month varchar(20) not null,
	customer_id varchar(20) null,
	year varchar(10) null,
	constraint customer_payment_ibfk_1
		foreign key (customer_id) references customer (customer_id)
			on update cascade on delete cascade
);

create index customer_id
	on customer_payment (customer_id);

