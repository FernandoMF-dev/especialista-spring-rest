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

INSERT INTO tb_restaurant (id, name, freight_fee, registration_date, update_date, cuisine_id, address_city_id,
						   address_cep, address_public_space, address_street_number, address_district,
						   address_complement)
VALUES (nextval('seq_restaurant'), 'Thai Gourmet', 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 1, '38400-999',
		'Rua João Pinheiro', '1000', 'Centro', ''),
	   (nextval('seq_restaurant'), 'Thai Delivery', 9.50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1, 2, '40567-387',
		'Av. Carvalho Pedro', '154', 'Centro', '4ª Andar');

INSERT INTO tb_restaurant (id, name, freight_fee, registration_date, update_date, cuisine_id)
VALUES (nextval('seq_restaurant'), 'Tuk Tuk Comida Indiana', 15, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2),
	   (nextval('seq_restaurant'), 'Java Steakhouse', 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3),
	   (nextval('seq_restaurant'), 'Lanchonete do Tio Sam', 11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4),
	   (nextval('seq_restaurant'), 'Bar da Maria', 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4);

INSERT INTO tb_payment_method (id, description)
VALUES (nextval('seq_payment_method'), 'Cartão de crédito'),
	   (nextval('seq_payment_method'), 'Cartão de débito'),
	   (nextval('seq_payment_method'), 'Dinheiro');

INSERT INTO tb_permission (id, name, description)
VALUES (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas'),
	   (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

INSERT INTO rel_restaurant_payment_method (restaurant_id, payment_method_id)
values (1, 1),
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
