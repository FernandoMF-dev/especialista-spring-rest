-- The following SQL commands only work if they are executed on a newly generated database that has never undergone any modification
-- (except the creation of tables and sequences, of course)

INSERT INTO tb_cuisine (id, name)
VALUES (nextval('seq_cuisine'), 'Tailandesa'),
	   (nextval('seq_cuisine'), 'Indiana'),
	   (nextval('seq_cuisine'), 'Argentina'),
	   (nextval('seq_cuisine'), 'Brasileira');

INSERT INTO tb_state (id, acronym, name)
VALUES (nextval('seq_state'), 'MG', 'Minas Gerais'),
	   (nextval('seq_state'), 'SP', 'São Paulo'),
	   (nextval('seq_state'), 'CE', 'Ceará');

INSERT INTO tb_city (id, acronym, name, state_id)
VALUES (nextval('seq_city'), 'UBE', 'Uberlândia', 1),
	   (nextval('seq_city'), 'BH', 'Belo Horizonte', 1),
	   (nextval('seq_city'), 'SP', 'São Paulo', 2),
	   (nextval('seq_city'), 'CP', 'Campinas', 2),
	   (nextval('seq_city'), 'FTZ', 'Fortaleza', 3);

INSERT INTO tb_restaurant (id, name, open, freight_fee, registration_date, update_date, cuisine_id, address_city_id,
						   address_cep, address_public_space, address_street_number, address_district,
						   address_complement)
VALUES (nextval('seq_restaurant'), 'Thai Gourmet', TRUE, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, '38400-999',
		'Rua João Pinheiro', '1000', 'Centro', ''),
	   (nextval('seq_restaurant'), 'Thai Delivery', FALSE, 9.50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2,
		'40567-387',
		'Av. Carvalho Pedro', '154', 'Centro', '4ª Andar');

INSERT INTO tb_restaurant (id, name, open, freight_fee, registration_date, update_date, cuisine_id)
VALUES (nextval('seq_restaurant'), 'Tuk Tuk Comida Indiana', TRUE, 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2),
	   (nextval('seq_restaurant'), 'Java Steakhouse', TRUE, 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3),
	   (nextval('seq_restaurant'), 'Lanchonete do Tio Sam', TRUE, 11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4),
	   (nextval('seq_restaurant'), 'Bar da Maria', TRUE, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4);

INSERT INTO tb_payment_method (id, description, update_date)
VALUES (nextval('seq_payment_method'), 'Cartão de crédito', CURRENT_TIMESTAMP),
	   (nextval('seq_payment_method'), 'Cartão de débito', CURRENT_TIMESTAMP),
	   (nextval('seq_payment_method'), 'Dinheiro', CURRENT_TIMESTAMP);

INSERT INTO rel_restaurant_payment_method (restaurant_id, payment_method_id)
VALUES (1, 1),
	   (1, 2),
	   (1, 3),
	   (2, 3),
	   (3, 2),
	   (3, 3),
	   (4, 1),
	   (4, 2),
	   (5, 1),
	   (5, 2),
	   (6, 3);

INSERT INTO tb_product (id, name, price, active, restaurant_id, description)
VALUES (nextval('seq_product'), 'Porco com molho agridoce', 78.90, TRUE, 1, 'Deliciosa carne suína ao molho especial'),
	   (nextval('seq_product'), 'Camarão tailandês', 110, TRUE, 1, '16 camarões grandes ao molho picante'),
	   (nextval('seq_product'), 'Salada picante com carne grelhada', 87.20, TRUE, 2,
		'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha'),
	   (nextval('seq_product'), 'Garlic Naan', 21, TRUE, 3, 'Pão tradicional indiano com cobertura de alho'),
	   (nextval('seq_product'), 'Murg Curry', 43, TRUE, 3, 'Cubos de frango preparados com molho curry e especiarias'),
	   (nextval('seq_product'), 'Bife Ancho', 79, TRUE, 4,
		'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé'),
	   (nextval('seq_product'), 'T-Bone', 89, TRUE, 4,
		'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon'),
	   (nextval('seq_product'), 'Sanduíche X-Tudo', 19, TRUE, 5,
		'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese'),
	   (nextval('seq_product'), 'Espetinho de Cupim', 8, TRUE, 6, 'Acompanha farinha, mandioca e vinagrete');

INSERT INTO tb_profile (id, name)
VALUES (nextval('seq_profile'), 'Gerente'),
	   (nextval('seq_profile'), 'Vendedor'),
	   (nextval('seq_profile'), 'Secretária'),
	   (nextval('seq_profile'), 'Cadastrador');

INSERT INTO tb_permission (id, name, description)
VALUES (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas'),
	   (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

INSERT INTO rel_profile_permission (profile_id, permission_id)
VALUES (1, 1),
	   (1, 2),
	   (2, 1),
	   (2, 2),
	   (3, 1);

INSERT INTO tb_user (id, name, email, password, registration_date)
VALUES (nextval('seq_user'), 'João da Silva', 'joao.ger@algafood.com', 'nMNSF%17C69:', CURRENT_TIMESTAMP),
	   (nextval('seq_user'), 'Maria Joaquina', 'maria.vnd@algafood.com', 'p_44:8Yk£kl&', CURRENT_TIMESTAMP),
	   (nextval('seq_user'), 'José Souza', 'jose.aux@algafood.com', '?8j}8JBG!1/<', CURRENT_TIMESTAMP),
	   (nextval('seq_user'), 'Sebastião Martins', 'sebastiao.cad@algafood.com', 'ED/if5=2l#~2', CURRENT_TIMESTAMP),
	   (nextval('seq_user'), 'Manoel Lima', 'manoel.loja@gmail.com', '7*2N}fdIO17h', CURRENT_TIMESTAMP);

INSERT INTO rel_user_profile (user_id, profile_id)
VALUES (1, 1),
	   (1, 2),
	   (2, 2);

INSERT INTO rel_restaurant_responsible (restaurant_id, user_id)
VALUES (1, 5),
	   (3, 5);

INSERT INTO tb_order (id, restaurant_id, customer_id, payment_method_id, address_city_id, address_cep,
					  address_public_space,
					  address_street_number, address_complement, address_district, status, registration_date, subtotal,
					  freight_fee, total_value, uuid_code)
VALUES (nextval('seq_order'), 1, 1, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil', 'CREATED',
		CURRENT_TIMESTAMP, 298.90, 10, 308.90, gen_random_uuid());

INSERT INTO rel_order_product (id, order_id, product_id, quantity, unit_price, total_price, observation)
VALUES (nextval('seq_order_product'), 1, 1, 1, 78.9, 78.9, null);

INSERT INTO rel_order_product (id, order_id, product_id, quantity, unit_price, total_price, observation)
VALUES (nextval('seq_order_product'), 1, 2, 2, 110, 220, 'Menos picante, por favor');

INSERT INTO tb_order (id, restaurant_id, customer_id, payment_method_id, address_city_id, address_cep,
					  address_public_space,
					  address_street_number, address_complement, address_district, status, registration_date, subtotal,
					  freight_fee, total_value, uuid_code)
VALUES (nextval('seq_order'), 4, 1, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro', 'CREATED',
		CURRENT_TIMESTAMP, 79, 0, 79, gen_random_uuid());

INSERT INTO rel_order_product (id, order_id, product_id, quantity, unit_price, total_price, observation)
VALUES (nextval('seq_order_product'), 2, 6, 1, 79, 79, 'Ao ponto');

INSERT INTO tb_order (id, restaurant_id, customer_id, payment_method_id, address_city_id, address_cep,
					  address_public_space,
					  address_street_number, address_complement, address_district, status, registration_date,
					  confirmation_date, delivery_date, subtotal, freight_fee, total_value, uuid_code)
VALUES (nextval('seq_order'), 1, 1, 1, 1, '38400-222', 'Rua Natal', '200', null, 'Brasil', 'DELIVERED',
		'2019-10-30 21:10:00', '2019-10-30 21:10:45', '2019-10-30 21:55:44', 110, 10, 120, gen_random_uuid());

INSERT INTO rel_order_product (id, order_id, product_id, quantity, unit_price, total_price, observation)
VALUES (nextval('seq_order_product'), 3, 2, 1, 110, 110, null);


INSERT INTO tb_order (id, restaurant_id, customer_id, payment_method_id, address_city_id, address_cep,
					  address_public_space,
					  address_street_number, address_complement, address_district, status, registration_date,
					  confirmation_date, delivery_date, subtotal, freight_fee, total_value, uuid_code)
VALUES (nextval('seq_order'), 1, 2, 1, 1, '38400-800', 'Rua Fortaleza', '900', 'Apto 504', 'Centro', 'DELIVERED',
		'2019-11-02 20:34:04', '2019-11-02 20:35:10', '2019-11-02 21:10:32', 174.4, 5, 179.4, gen_random_uuid());

INSERT INTO rel_order_product (id, order_id, product_id, quantity, unit_price, total_price, observation)
VALUES (nextval('seq_order_product'), 4, 3, 2, 87.2, 174.4, null);


INSERT INTO tb_order (id, restaurant_id, customer_id, payment_method_id, address_city_id, address_cep,
					  address_public_space,
					  address_street_number, address_complement, address_district, status, registration_date,
					  confirmation_date, delivery_date, subtotal, freight_fee, total_value, uuid_code)
VALUES (nextval('seq_order'), 1, 3, 2, 1, '38400-200', 'Rua 10', '930', 'Casa 20', 'Martins', 'DELIVERED',
		'2019-11-02 21:00:30', '2019-11-02 21:01:21', '2019-11-02 21:20:10', 87.2, 10, 97.2, gen_random_uuid());

INSERT INTO rel_order_product (id, order_id, product_id, quantity, unit_price, total_price, observation)
VALUES (nextval('seq_order_product'), 5, 3, 1, 87.2, 87.2, null);
