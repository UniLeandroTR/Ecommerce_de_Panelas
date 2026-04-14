-- SQL de exemplo para popular o banco com as entidades do pacote leepans.resource
-- Inclui pelo menos 3 registros para cada entidade e exemplos de coleções embutidas

-- Categorias
insert into Categoria (id, tipo) values (1, 'Frigideira');
insert into Categoria (id, tipo) values (2, 'Panela de Pressão');
insert into Categoria (id, tipo) values (3, 'Caçarola');

-- Coleções
insert into Colecao (id, nome) values (1, 'Clássica');
insert into Colecao (id, nome) values (2, 'Gourmet');
insert into Colecao (id, nome) values (3, 'Avançada');

-- Cores
insert into Cor (id, nome) values (1, 'Vermelho');
insert into Cor (id, nome) values (2, 'Preto');
insert into Cor (id, nome) values (3, 'Inox');

-- Fornecedores
insert into Fornecedor (id, nome, telefone, cnpj) values (1, 'Panelas Brasil', '(11) 4002-8922', '12.345.678/0001-01');
insert into Fornecedor (id, nome, telefone, cnpj) values (2, 'Cozinha Pro', '(21) 5555-1234', '98.765.432/0001-09');
insert into Fornecedor (id, nome, telefone, cnpj) values (3, 'Utensílios Prime', '(31) 3333-6789', '45.987.123/0001-55');

-- Materiais
insert into Material (id, nome) values (1, 'Alumínio');
insert into Material (id, nome) values (2, 'Aço Inox');
insert into Material (id, nome) values (3, 'Cerâmica');

-- Qualidades dos materiais
insert into Material_qualidades (Material_id, qualidades) values (1, 'Leve');
insert into Material_qualidades (Material_id, qualidades) values (1, 'Boa condutividade');
insert into Material_qualidades (Material_id, qualidades) values (1, 'Fácil limpeza');
insert into Material_qualidades (Material_id, qualidades) values (2, 'Resistente');
insert into Material_qualidades (Material_id, qualidades) values (2, 'Inoxidável');
insert into Material_qualidades (Material_id, qualidades) values (2, 'Não altera sabor');
insert into Material_qualidades (Material_id, qualidades) values (3, 'Antiaderente');
insert into Material_qualidades (Material_id, qualidades) values (3, 'Acabamento premium');
insert into Material_qualidades (Material_id, qualidades) values (3, 'Resistência ao calor');

-- Fundos
insert into Fundo (id, peso, material_id, cor_id, espessura, isAntiaderente) values (1, 0.75, 1, 3, 0.8, true);
insert into Fundo (id, peso, material_id, cor_id, espessura, isAntiaderente) values (2, 1.10, 2, 2, 1.2, false);
insert into Fundo (id, peso, material_id, cor_id, espessura, isAntiaderente) values (3, 0.95, 3, 1, 0.9, true);

-- Tampas
insert into Tampa (id, peso, material_id, cor_id, isDePressao) values (1, 0.30, 2, 3, false);
insert into Tampa (id, peso, material_id, cor_id, isDePressao) values (2, 0.45, 1, 2, true);
insert into Tampa (id, peso, material_id, cor_id, isDePressao) values (3, 0.35, 3, 1, false);

-- Sustentações
insert into Sustentacao (id, peso, material_id, cor_id, tamanhoEmCm, quantidade, codigo_tipo_sustentacao) values (1, 0.25, 1, 2, 15, 1, 0);
insert into Sustentacao (id, peso, material_id, cor_id, tamanhoEmCm, quantidade, codigo_tipo_sustentacao) values (2, 0.40, 2, 3, 18, 2, 1);
insert into Sustentacao (id, peso, material_id, cor_id, tamanhoEmCm, quantidade, codigo_tipo_sustentacao) values (3, 0.28, 3, 1, 14, 1, 0);

-- -- Panelas
-- insert into Panela (id, modelo, preco, peso, capacidadeLitros, isInducao, id_colecao, id_fundo, id_sustentacao, id_tampa, id_fornecedor, codigo_tamanho, id_categoria) values (1, 'TurboChef 20', 23999, 1.20, 2.5, true, 1, 1, 1, 1, 1, 1, 1);
-- insert into Panela (id, modelo, preco, peso, capacidadeLitros, isInducao, id_colecao, id_fundo, id_sustentacao, id_tampa, id_fornecedor, codigo_tamanho, id_categoria) values (2, 'PressMaster 30', 29999, 1.80, 3.0, false, 2, 2, 2, 2, 2, 2, 2);
-- insert into Panela (id, modelo, preco, peso, capacidadeLitros, isInducao, id_colecao, id_fundo, id_sustentacao, id_tampa, id_fornecedor, codigo_tamanho, id_categoria) values (3, 'CozinhaPro 24', 18999, 1.50, 2.8, true, 3, 3, 3, 3, 3, 1, 3);

-- -- Funcionalidades das panelas
-- insert into Panela_funcionalidade (Panela_id, funcionalidade) values (1, 'Indução');
-- insert into Panela_funcionalidade (Panela_id, funcionalidade) values (1, 'Antiaderente');
-- insert into Panela_funcionalidade (Panela_id, funcionalidade) values (1, 'Fácil de limpar');
-- insert into Panela_funcionalidade (Panela_id, funcionalidade) values (2, 'Cozimento rápido');
-- insert into Panela_funcionalidade (Panela_id, funcionalidade) values (2, 'Válvula de pressão');
-- insert into Panela_funcionalidade (Panela_id, funcionalidade) values (2, 'Segurança');
-- insert into Panela_funcionalidade (Panela_id, funcionalidade) values (3, 'Versátil');
-- insert into Panela_funcionalidade (Panela_id, funcionalidade) values (3, 'Fundo triplo');
-- insert into Panela_funcionalidade (Panela_id, funcionalidade) values (3, 'Cabo ergonômico');
