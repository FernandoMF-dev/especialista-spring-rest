insert into tb_kitchen (id, name)
values (nextval('seq_kitchen'), 'Tailandesa'),
	   (nextval('seq_kitchen'), 'Indiana');

insert into tb_state (id, acronym, name)
values (nextval('seq_state'), 'MG', 'Minas Gerais'),
	   (nextval('seq_state'), 'SP', 'São Paulo'),
	   (nextval('seq_state'), 'CE', 'Ceará');

insert into tb_city (id, acronym, name, state_id)
values (nextval('seq_city'), 'UBE', 'Uberlândia', 1),
	   (nextval('seq_city'), 'BH', 'Belo Horizonte', 1),
	   (nextval('seq_city'), 'SP', 'São Paulo', 2),
	   (nextval('seq_city'), 'CP', 'Campinas', 2),
	   (nextval('seq_city'), 'FTZ', 'Fortaleza', 3);

insert into tb_restaurant (id, name, freight_rate, registration_date, update_date, kitchen_id, address_city_id,
						   address_cep, address_public_space, address_street_number, address_district)
values (nextval('seq_restaurant'), 'Thai Gourmet', 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, '38400-999',
		'Rua João Pinheiro', '1000', 'Centro');

insert into tb_restaurant (id, name, freight_rate, registration_date, update_date, kitchen_id)
values (nextval('seq_restaurant'), 'Thai Delivery', 9.50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
	   (nextval('seq_restaurant'), 'Tuk Tuk Comida Indiana', 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2);

insert into tb_payment_method (id, description)
values (nextval('seq_payment_method'), 'Cartão de crédito'),
	   (nextval('seq_payment_method'), 'Cartão de débito'),
	   (nextval('seq_payment_method'), 'Dinheiro');

insert into tb_permission (id, name, description)
values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas'),
	   (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into rel_restaurant_payment_method (restaurant_id, payment_method_id)
values (1, 1),
	   (1, 2),
	   (1, 3),
	   (2, 3),
	   (3, 2),
	   (3, 3);
