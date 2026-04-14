-- SQL de exemplo para popular o banco com as entidades do pacote leepans.resource
-- Inclui pelo menos 3 registros para cada entidade e exemplos de coleções embutidas

-- Categorias
insert into Categoria (tipo) values ('Frigideira');
insert into Categoria (tipo) values ('Panela de Pressão');
insert into Categoria (tipo) values ('Caçarola');

-- Coleções
insert into Colecao (nome) values ('Clássica');
insert into Colecao (nome) values ('Gourmet');
insert into Colecao (nome) values ('Avançada');

-- Cores
insert into Cor (nome) values ('Vermelho');
insert into Cor (nome) values ('Preto');
insert into Cor (nome) values ('Inox');

-- Fornecedores
insert into Fornecedor (nome, telefone, cnpj) values ('Panelas Brasil', '(11) 4002-8922', '12.345.678/0001-01');
insert into Fornecedor (nome, telefone, cnpj) values ('Cozinha Pro', '(21) 5555-1234', '98.765.432/0001-09');
insert into Fornecedor (nome, telefone, cnpj) values ('Utensílios Prime', '(31) 3333-6789', '45.987.123/0001-55');

-- Materiais
insert into Material (nome) values ('Alumínio');
insert into Material (nome) values ('Aço Inox');
insert into Material (nome) values ('Cerâmica');

-- Fundos
insert into Fundo (peso, material_id, cor_id, espessura, isAntiaderente) values (0.75, 1, 3, 0.8, true);
insert into Fundo (peso, material_id, cor_id, espessura, isAntiaderente) values (1.10, 2, 2, 1.2, false);
insert into Fundo (peso, material_id, cor_id, espessura, isAntiaderente) values (0.95, 3, 1, 0.9, true);

-- Tampas
insert into Tampa (peso, material_id, cor_id, isDePressao) values (0.30, 2, 3, false);
insert into Tampa (peso, material_id, cor_id, isDePressao) values (0.45, 1, 2, true);
insert into Tampa (peso, material_id, cor_id, isDePressao) values (0.35, 3, 1, false);

-- Sustentações
insert into Sustentacao (peso, material_id, cor_id, tamanhoEmCm, quantidade, codigo_tipo_sustentacao) values (0.25, 1, 2, 15, 1, 0);
insert into Sustentacao (peso, material_id, cor_id, tamanhoEmCm, quantidade, codigo_tipo_sustentacao) values (0.40, 2, 3, 18, 2, 1);
insert into Sustentacao (peso, material_id, cor_id, tamanhoEmCm, quantidade, codigo_tipo_sustentacao) values (0.28, 3, 1, 14, 1, 0);

-- Panelas
insert into Panela (modelo, preco, peso, capacidadeLitros, isInducao, id_colecao, id_fundo, id_sustentacao, id_tampa, id_fornecedor, codigo_tamanho, id_categoria) values ('TurboChef 20', 23999, 1.20, 2.5, true, 1, 1, 1, 1, 1, 1, 1);
insert into Panela (modelo, preco, peso, capacidadeLitros, isInducao, id_colecao, id_fundo, id_sustentacao, id_tampa, id_fornecedor, codigo_tamanho, id_categoria) values ('PressMaster 30', 29999, 1.80, 3.0, false, 2, 2, 2, 2, 2, 2, 2);
insert into Panela (modelo, preco, peso, capacidadeLitros, isInducao, id_colecao, id_fundo, id_sustentacao, id_tampa, id_fornecedor, codigo_tamanho, id_categoria) values ('CozinhaPro 24', 18999, 1.50, 2.8, true, 3, 3, 3, 3, 3, 1, 3);
