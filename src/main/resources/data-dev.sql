-- Reseta as tabelas antes de inserir, pra poder rodar isso toda vez que reiniciar sem dar erro de duplicidade
TRUNCATE TABLE pessoa_resposta, game_candidatos, game_perguntas_usadas, game, pessoa, pergunta RESTART IDENTITY CASCADE;

INSERT INTO pessoa (nome, foto) VALUES
                                    ('Gustavo', '/images/gustavo.jpg'),
                                    ('João', '/images/joao.jpg'),
                                    ('Maria', '/images/maria.jpg'),
                                    ('Ana', '/images/ana.jpg');

INSERT INTO pergunta (texto) VALUES
                                 ('É homem?'),
                                 ('Usa óculos?'),
                                 ('Gosta de programação?'),
                                 ('Joga futebol?');

INSERT INTO pessoa_resposta (pessoa_id, pergunta_id, resposta) VALUES
   (1, 1, true), (1, 2, false), (1, 3, true), (1, 4, false),
   (2, 1, true), (2, 2, true), (2, 3, false), (2, 4, true),
   (3, 1, false), (3, 2, true), (3, 3, true), (3, 4, false),
   (4, 1, false), (4, 2, false), (4, 3, false), (4, 4, true);