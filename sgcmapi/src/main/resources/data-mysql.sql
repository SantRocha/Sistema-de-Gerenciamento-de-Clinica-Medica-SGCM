SET SESSION FOREIGN_KEY_CHECKS=0;

INSERT INTO `atendimento` (`id`, `data`, `hora`, `status`, `convenio_id`, `paciente_id`, `profissional_id`) VALUES
    (1,'2024-08-15','14:00:00','AGENDADO',1,1,1),
    (2,'2024-08-15','14:30:00','CONFIRMADO',2,2,2),
    (3,'2024-08-15','14:00:00','AGENDADO',3,3,3),
    (4,'2024-08-15','15:00:00','CHEGADA',1,4,4),
    (5,'2024-08-16','15:00:00','AGENDADO',2,1,1),
    (6,'2024-08-16','16:00:00','CONFIRMADO',3,2,1),
    (7,'2024-08-19','17:30:00','AGENDADO',1,3,2),
    (8,'2024-08-16','16:30:00','CHEGADA',2,4,2),
    (9,'2024-08-16','15:00:00','AGENDADO',3,5,5),
    (10,'2024-08-19','17:30:00','CONFIRMADO',1,2,3),
    (11,'2024-08-19','18:00:00','CHEGADA',3,3,1),
    (12,'2024-08-20','17:00:00','AGENDADO',1,3,5),
    (13,'2024-08-20','17:30:00','AGENDADO',1,4,5),
    (14,'2024-08-20','18:00:00','CONFIRMADO',1,5,5),
    (15,'2024-08-20','18:30:00','CHEGADA',1,1,5),
    (16,'2024-08-21','15:00:00','AGENDADO',1,4,4),
    (17,'2024-08-21','15:00:00','AGENDADO',2,1,1),
    (18,'2024-08-21','16:00:00','CONFIRMADO',3,2,1),
    (19,'2024-08-21','15:30:00','CHEGADA',1,3,2),
    (20,'2024-08-21','16:30:00','AGENDADO',2,4,2),
    (21,'2024-08-15','15:30:00','AGENDADO',2,5,3),
    (22,'2024-08-15','16:00:00','CONFIRMADO',3,3,4),
    (23,'2024-08-16','14:30:00','CHEGADA',1,4,1),
    (24,'2024-08-16','15:30:00','AGENDADO',2,2,4),
    (25,'2024-08-19','14:00:00','CONFIRMADO',3,1,2),
    (26,'2024-08-19','14:30:00','AGENDADO',1,2,3),
    (27,'2024-08-20','15:00:00','CHEGADA',2,4,1),
    (28,'2024-08-20','15:30:00','CONFIRMADO',3,1,5),
    (29,'2024-08-20','16:00:00','AGENDADO',1,2,4),
    (30,'2024-08-21','14:00:00','CONFIRMADO',2,3,5),
    (31,'2024-08-21','14:30:00','AGENDADO',3,5,3),
    (32,'2024-08-21','17:00:00','CHEGADA',1,4,5),
    (33,'2024-08-21','17:30:00','CONFIRMADO',2,2,3),
    (34,'2024-08-21','18:00:00','AGENDADO',3,1,4),
    (35,'2024-08-21','18:30:00','CHEGADA',1,5,2),
    (36,'2024-08-15','16:30:00','AGENDADO',1,1,4),
    (37,'2024-08-15','17:00:00','CONFIRMADO',2,3,1),
    (38,'2024-08-15','17:30:00','CHEGADA',3,4,3),
    (39,'2024-08-16','17:00:00','AGENDADO',1,5,2),
    (40,'2024-08-16','17:30:00','CONFIRMADO',2,1,3),
    (41,'2024-08-19','15:00:00','CHEGADA',3,2,4),
    (42,'2024-08-19','15:30:00','AGENDADO',1,3,5),
    (43,'2024-08-19','16:00:00','CONFIRMADO',2,4,1),
    (44,'2024-08-19','16:30:00','CHEGADA',3,5,4),
    (45,'2024-08-20','14:00:00','AGENDADO',1,1,2),
    (46,'2024-08-20','14:30:00','CONFIRMADO',2,3,4),
    (47,'2024-08-20','16:30:00','CHEGADA',3,2,3),
    (48,'2024-08-21','14:00:00','AGENDADO',1,5,1),
    (49,'2024-08-21','14:30:00','CONFIRMADO',2,4,5),
    (50,'2024-08-21','17:00:00','CHEGADA',3,3,2);

INSERT INTO `convenio` (`id`, `ativo`, `cnpj`, `email`, `nome`, `razao_social`, `representante`, `telefone`) VALUES
    (1,b'1','84-313.741/0001-12','contato@unimedriobranco.com.br','Unimed','Unimed Rio Branco Cooperativa de Trabalho Médico Ltda.','Thayline Figueredo Vaz','(68) 3668-1546'),
    (2,b'1','29.309.127/0001-79','contato@amil.com.br','Amil','AMIL ASSISTENCIA MEDICA INTERNACIONAL S.A.','Davi Faria da Conceição','(11) 3279-3035'),
    (3,b'1','92.693.118/0001-60','contato@bradescosaude.com.br','Bradesco','BRADESCO SAUDE S.A.','Leandra Paes dos Anjos','(21) 2503-0787');

INSERT INTO `especialidade` (`id`, `nome`) VALUES
    (1,'Cardiologia'),
    (2,'Dermatologia'),
    (3,'Geriatria'),
    (4,'Infectologia'),
    (5,'Pediatria'),
    (6,'Psiquiatria'),
    (7,'Urologia');

INSERT INTO `paciente` (`id`, `cep`, `cidade`, `data_nascimento`, `email`, `endereco`, `estado`, `grupo_sanguineo`, `nome`, `sexo`, `telefone`) VALUES
    (1,'69903-134','Rio Branco','1945-09-26','giulia.bencatel@gmail.com','Rua Cedro, 100 - Parque dos Sabiás','AC','AB_POSITIVO','Giulia Farias Bencatel','F','(68) 98105-2583'),
    (2,'69901-884','Rio Branco','1941-04-22','wallace.soriano@yahoo.com','Travessa Mossoró, 90 - Vitória','AC','B_POSITIVO','Wallace Macedo Inácio Soriano','M','(68) 97431-7722'),
    (3,'69909-060','Rio Branco','1997-06-05','helen.vilar@gmail.com','Rua Adelaide, 50 - Rosa Linda','AC','AB_NEGATIVO','Helen Dutra Vilar','F','(68) 99752-4954'),
    (4,'69911-262','Rio Branco','2009-03-10','mario.branco@uol.com.br','Rua Luiz Galvez, 200 - Conjunto Castelo Branco','AC','O_POSITIVO','Jean Schuenck Amorin','M','(68) 97120-8079'),
    (5,'69980-970','Cruzeiro do Sul','1974-11-14','lucilene.lucas@gmail.com','Rua Rego Barros, 427 - Centro','AC','A_POSITIVO','Lucilene Santos Lucas','F','(68) 99384-3354'),
    (6,'69914-290','Rio Branco','1977-08-09','silvio.passos@gmail.com','Rua Teresina, 213 - Waldemar Maciel','AC','O_POSITIVO','Silvio Abreu Passos','M','(68) 98041-8649'),
    (7,'69907-567','Rio Branco','1959-01-12','laysa.filho@uol.com.br','Rua Lázaro, 114 - Recanto dos Buritis','AC','B_POSITIVO','Laysa Guimarães Filho','F','(68) 97344-6728'),
    (8,'69908-116','Rio Branco','1975-04-17','luiza.cosme@gmail.com','Travessa da Casa de Farinha, 443 - Vila Santa Cecília','AC','AB_POSITIVO','Luiza da Silva Cosme','F','(68) 98241-5978'),
    (9,'69917-540','Rio Branco','1976-07-19','elio.pedroso@uol.com.br','Alameda Garças, 11 - Chácara Ipê','AC','AB_POSITIVO','Elio da Sousa Pedroso','M','(68) 98726-9181'),
    (10,'69906-021','Rio Branco','1987-05-03','ruan.souza@gmail.com','Rua Jasmim, 213 - Areal','AC','B_NEGATIVO','Ruan Alvarenga da Souza','M','(68) 97179-3565'),
    (11,'69900-019','Rio Branco','2012-02-04','maria.carino@gmail.com','Rua Xapuri, 325 - Base','AC','B_POSITIVO','Maria Rosa Reis Carino','F','(68) 98631-1584'),
    (12,'69912-500','Cruzeiro do Sul','1946-09-09','fernanda.chaves@uol.com.br','Rua Tabosa, 129 - Floresta Sul','AC','B_POSITIVO','Fernanda Beatriz Reis Chaves','F','(68) 96734-6577'),
    (13,'69918-026','Rio Branco','1935-04-16','hugo.macedo@uol.com.br','Rua Tijuca, 323 - Conjunto Mascarenhas de Moraes','AC','AB_POSITIVO','Hugo Frossard Macedo','M','(68) 98249-3158'),
    (14,'69912-006','Rio Branco','1969-06-24','sonia.franca@gmail.com','Rua 11 de Dezembro, 340 - Sobral','AC','AB_NEGATIVO','Sonia da Sousa França','F','(68) 98289-2278'),
    (15,'69920-224','Rio Branco','1937-05-08','jhonne.heizelmann@uol.com.br','Rua Josefa Feitosa da Silva, 386 - Distrito Industrial','AC','A_NEGATIVO','Jhonne Marcello Heizelmann','M','(68) 96883-1175'),
    (16,'69902-840','Rio Branco','2006-03-28','iara.valansuela@hotmail.com','Rua Anibal Viana, 362 - Wanderley Dantas','AC','A_NEGATIVO','Iára Spilman Valansuela','F','(68) 99285-5212'),
    (17,'69902-744','Rio Branco','1950-09-01','lilian.portela@yahoo.com','Rua 13 de Maio, 360 - Chico Mendes','AC','O_NEGATIVO','Lilian Consendey Portela','F','(68) 99312-9295'),
    (18,'69901-438','Rio Branco','1968-09-13','isabela.carino@outlook.com','Rua Wilde Viana das Neves Filho, 104 - Conjunto Guiomard Santos','AC','B_POSITIVO','Isabela Bilé Carino','F','(68) 99475-7255'),
    (19,'69921-362','Rio Branco','1948-08-27','gilmara.conceicao@hotmail.com','Rua Maçaranduba, 323 - Alto Alegre','AC','B_POSITIVO','Gilmara Ximenes da Conceição','F','(68) 99165-8169'),
    (20,'69921-001','Rio Branco','2018-07-07','gabrielle.gabrig@uol.com.br','Rua 11, 264 - Raimundo Melo','AC','B_NEGATIVO','Gabrielle de Carvalho Gabrig','F','(68) 99239-2457'),
    (21,'69901-833','Rio Branco','1932-08-17','zacarias.marotti@outlook.com','Travessa Santa Isabel, 350 - Vitória','AC','B_POSITIVO','Zacarias Antonio Marotti','M','(68) 98789-6185'),
    (22,'69911-686','Rio Branco','1939-06-01','jose.sarmento@hotmail.com','Rua Lagoinha, 376 - Bahia Nova','AC','A_NEGATIVO','José Antônio Santana Sarmento','M','(68) 98442-2343'),
    (23,'69920-078','Rio Branco','1964-03-03','dayvid.auzier@yahoo.com','Travessa Cupuaçu, 13 - Mocinha Magalhães','AC','O_NEGATIVO','Dayvid Fernandes Auzier','M','(68) 98701-4305'),
    (24,'69917-740','Rio Branco','1979-02-15','silvia.gouveia@hotmail.com','Rua Neto Almeida, 40 - Conjunto Universitário','AC','A_NEGATIVO','Silvia Ascar Gouveia','F','(68) 98295-7333'),
    (25,'69900-060','Rio Branco','1967-08-03','adolfo.figueiredo@outlook.com','Avenida Getúlio Vargas, 199 - Centro','AC','B_NEGATIVO','Adolfo Miranda Figueiredo','M','(68) 98145-2024'),
    (26,'69902-431','Rio Branco','1977-04-09','cassio.fernandes@hotmail.com','Rua Bom Jesus, 329 - Eldorado','AC','O_NEGATIVO','Cassio Mesquita Fernandes','F','(68) 99947-3858'),
    (27,'69901-369','Rio Branco','1995-02-09','juzelina.zava@uol.com.br','Rua Lauro Fontes, 342 - Conjunto Guiomard Santos','AC','O_NEGATIVO','Juzelina Fernandes Zava','M','(68) 98490-1833'),
    (28,'69921-114','Rio Branco','2017-06-29','douglas.pimenta@gmail.com','Rua Guariúba, 221 - Loteamento Novo Horizonte','AC','O_NEGATIVO','Douglas Junior Pimenta','M','(68) 96764-7777'),
    (29,'69915-002','Rio Branco','1979-08-21','geanny.meyer@uol.com.br','Rua Dourado, 90 - Tangará','AC','A_NEGATIVO','Geanny Valansuela Meyer','F','(68) 98183-1843'),
    (30,'69918-244','Rio Branco','1986-05-26','joaquim.livramento@gmail.com','Rua Jaguari, 298 - Isaura Parente','AC','B_NEGATIVO','Joaquim Vilar Livramento','M','(68) 98421-3063'),
    (31,'69908-382','Rio Branco','1985-03-13','jennifer.montilla@uol.com.br','Rua do Abacate, 51 - Vila Liberdade','AC','O_POSITIVO','Jennifer Eger Montilla','F','(68) 96948-1218'),
    (32,'69917-726','Rio Branco','1937-03-21','elisangela.diniz@outlook.com','Rua Romã, 149 - Conjunto Universitário','AC','A_POSITIVO','Elisângela Latin Diniz','F','(68) 98868-1345'),
    (33,'69921-416','Rio Branco','1978-09-03','aledio.lucio@outlook.com','Travessa Jasmim, 346 - Montanhês','AC','AB_NEGATIVO','Aledio Quintela Lucio','M','(68) 96742-2425'),
    (34,'69903-040','Rio Branco','2001-02-20','benedito.vasconcellos@gmail.com','Rua dos Lirios, 159 - Xavier Maia','AC','O_NEGATIVO','Benedito Braga Vasconcellos','M','(68) 97978-7595'),
    (35,'69921-025','Rio Branco','1969-08-14','emmanuel.cruz@outlook.com','Beco Nova Linda, 75 - Raimundo Melo','AC','O_POSITIVO','Emmanuel Louzano Cruz','M','(68) 96985-2215'),
    (36,'69906-222','Rio Branco','1999-08-09','valdeli.rodrigues@outlook.com','Travessa Flávio Batista, 121 - Triângulo Velho','AC','O_POSITIVO','Valdeli Trindade Rodrigues','F','(68) 98025-5411'),
    (37,'69911-048','Rio Branco','1955-04-04','erica.sodre@gmail.com','Rua Rosas, 238 - Aeroporto Velho','AC','O_NEGATIVO','Erica Correia Sodre','F','(68) 98502-9573'),
    (38,'69917-736','Rio Branco','2014-07-16','nathan.monteiro@hotmail.com','Rua Álvaro Thomaz, 249 - Conjunto Universitário','AC','B_POSITIVO','Nathan Dias Monteiro','M','(68) 97994-8572'),
    (39,'69920-064','Rio Branco','1952-11-14','wellington.teodoro@hotmail.com','Travessa Cajarana, 170 - Mocinha Magalhães','AC','AB_POSITIVO','Wellington Gouveia Teodoro','M','(68) 98343-3775'),
    (40,'69918-394','Rio Branco','1933-07-30','zilanda.giacomini@yahoo.com','Rua Passarela, 48 - Nova Estação','AC','O_POSITIVO','Zilanda Silvino Giacomini','F','(68) 98924-6682'),
    (41,'69911-111','Rio Branco','1938-07-03','erika.portugal@hotmail.com','Travessa Pista, 403 - Aeroporto Velho','AC','AB_POSITIVO','Erika Almeida Portugal','F','(68) 96855-1348'),
    (42,'69909-222','Rio Branco','1976-05-17','munique.moura@hotmail.com','Rua Rita Costa, 347 - Cidade do Povo','AC','O_POSITIVO','Munique Queiroz Moura','F','(68) 96706-7473'),
    (43,'69911-774','Rio Branco','1978-06-02','caue.paula@yahoo.com','Rua 10 de Novembro, 436 - Boa União','AC','O_NEGATIVO','Cauê Santos Paula','M','(68) 98727-6784'),
    (44,'69909-406','Rio Branco','2006-09-04','david.matos@yahoo.com','Rua Yocanaan de Campos Pereira, 371 - Cidade do Povo','AC','B_NEGATIVO','David Lopes Matos','M','(68) 98102-1496'),
    (45,'69921-710','Cruzeiro do Sul','1975-09-22','debora.freitas@gmail.com','Travessa da Abelha, 116 - Tancredo Neves','AC','A_POSITIVO','Débora França Freitas','M','(68) 98264-2267'),
    (46,'69918-386','Cruzeiro do Sul','1966-08-09','enrico.dores@yahoo.com','Rua Libertação, 371 - Nova Estação','AC','B_NEGATIVO','Enrico Saldanha Dores','F','(68) 97262-6811'),
    (47,'69915-702','Cruzeiro do Sul','2012-11-15','nilton.claudino@yahoo.com','Travessa Araponga, 43 - Portal da Amazônia','AC','B_POSITIVO','Nilton Fernandes Claudino','M','(68) 97252-0581'),
    (48,'69909-143','Cruzeiro do Sul','2011-05-24','jose.soares@uol.com.br','Rua Jessé Santiago, 269 - Cidade do Povo','AC','AB_NEGATIVO','José Luis Miranda Soares','F','(68) 96861-2313'),
    (49,'69906-412','Cruzeiro do Sul','1976-02-27','juzelina.carvalho@hotmail.com','Travessa Taquari, 422 - Taquarí','AC','A_NEGATIVO','Juzelina Feitosa Carvalho','M','(68) 96728-7324'),
    (50,'69917-556','Cruzeiro do Sul','1939-11-10','grecy.diniz@uol.com.br','Alameda Flamboyant, 35 - Chácara Ipê','AC','B_POSITIVO','Grecy Felizardo Diniz','F','(68) 98484-6444');

INSERT INTO `profissional` (`id`, `email`, `nome`, `registro_conselho`, `telefone`, `especialidade_id`, `unidade_id`) VALUES
    (1,'monique.nespoli@uol.com.br','Maria Adelia Serravalle Bezerra','CRM/AC 377','(68) 98205-4704',1,1),
    (2,'elielson.andrade@gmail.com','Elielson Silveira Andrade','CRM/AC 455','(68) 98085-4910',1,1),
    (3,'davi.mendes@yahoo.com','Davi Jesus Mendes','CRM/AC 123','(68) 98408-5352',4,3),
    (4,'carla.valle@gmail.com','Carla da Paixão Valle','CRM/AC 234','(68) 98395-5604',3,1),
    (5,'neuza.nobrega@uol.com.br','Neuza Biango Nobrega','CRM/AC 232','(68) 98561-6622',2,2),
    (6,'eva.campelo@gmail.com','Eva Vitorino Campelo','CRM/AC 345','(68) 99368-3962',2,1),
    (7,'carmen.oliveira@gmail.com','Carmen Marins de Oliveira','CRM/AC 782','(68) 98842-4368',2,3),
    (8,'denise.barthon@uol.com.br','Denise Pacheco Barthon','CRM/AC 543','(68) 98860-5446',3,3),
    (9,'vanderlei.mota@gmail.com','Vanderlei Nunes Mota','CRM/AC 323','(68) 98034-7858',4,2),
    (10,'maria.rabelo@uol.com.br','Maria Alice Lopes Rabelo','CRM/AC 189','(68) 99710-6657',5,1),
    (11,'luiz.camelo@yahoo.com','Luiz Feitosa Camelo','CRM/AC 256','(68) 98034-0867',5,3),
    (12,'diogo.vieira@gmail.com','Diogo da Silva Vieira','CRM/AC 654','(68) 98563-9765',6,2),
    (13,'jonas.siqueira@gmail.com','Jonas Biango Siqueira','CRM/AC 621','(68) 96835-5271',7,2),
    (14,'gilson.saraiva@uol.com.br','Gilson Ramos Saraiva','CRM/AC 545','(68) 98664-0836',7,3);

INSERT INTO `unidade` (`id`, `endereco`, `nome`) VALUES
    (1,'Rua Independência, 56 - Conjunto Bela Vista','Bela Vista'),
    (2,'Rua Paulo VI, 100 - Bosque','Bosque'),
    (3,'Rua Rui Barbosa, 252 - Centro','Cruzeiro do Sul');

INSERT INTO `usuario` (`id`, `ativo`, `nome_completo`, `nome_usuario`, `papel`, `senha`) VALUES
    (1,b'1','Administrador','admin','ROLE_ADMIN','$2a$10$h.s3BmgI2/N0MuDYEiawsOYh1P8l7gUcKRY6nhlDqnfcgHTv9Eb7S'),
    (2,b'1','Daniel','daniel','ROLE_ADMIN','$2a$10$8b4pxEEFWXleegyJRhopLO748ONErS6gNpw8MkzldN7KU0jM6hjK.'),
    (3,b'1','Paulo','paulo','ROLE_ATENDENTE','$2a$10$eDxP.Kb7uOQ9.oX1Vjq25.JoInkqmgA2oP6J0oMTWa5pCzLRZOTw2'),
    (4,b'1','Limeira','limeira','ROLE_ADMIN','$2a$10$x3e7yFAQ5cN8bD2bZkZ3t.X6IMjHA9o34JmIfuiVVBQfUiGmWnXtG');

SET SESSION FOREIGN_KEY_CHECKS=1;