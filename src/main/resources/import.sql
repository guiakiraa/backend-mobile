-- Usuarios
INSERT INTO USUARIO (NOME_COMPLETO, USERNAME, EMAIL, SENHA) VALUES ('Guilherme Akira', 'gui', 'gui@email.com', 'senha123');
INSERT INTO USUARIO (NOME_COMPLETO, USERNAME, EMAIL, SENHA) VALUES ('Alice Nunes', 'alice', 'alice@email.com', 'abc12345');
INSERT INTO USUARIO (NOME_COMPLETO, USERNAME, EMAIL, SENHA) VALUES ('Anne Rezendes', 'anne', 'anne@email.com', 'anne321');
INSERT INTO USUARIO (NOME_COMPLETO, USERNAME, EMAIL, SENHA) VALUES ('João Menk', 'jmenk', 'jmenk@email.com', 'cloud123');
INSERT INTO USUARIO (NOME_COMPLETO, USERNAME, EMAIL, SENHA) VALUES ('Rennan Santos', 'resan', 'resan@email.com', 'java123');

-- Setores
INSERT INTO SETORES (NOME, DESCRICAO) VALUES ('Centro', 'Operações no centro');
INSERT INTO SETORES (NOME, DESCRICAO) VALUES ('Zona Sul', 'Cobertura zona sul');

-- Motos (referenciam setor por nome)
INSERT INTO MOTOS (MODELO, PLACA, ANO, SETOR_ID) VALUES ('MOTTU_E', 'ABC1D23', 2022, 1);
INSERT INTO MOTOS (MODELO, PLACA, ANO, SETOR_ID) VALUES ('MOTTU_SPORT', 'EFG4H56', 2023, 2);
INSERT INTO MOTOS (MODELO, PLACA, ANO, SETOR_ID) VALUES ('MOTTU_POP', 'IJK7L89', 2021, 1);

-- IOTs (1:1 com moto, referenciam por placa)
INSERT INTO IOTS (MOTO_ID) VALUES (1);
INSERT INTO IOTS (MOTO_ID) VALUES (2);