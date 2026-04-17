-- Script de importação para popular todas as entidades do projeto
-- Cada entidade possui pelo menos 3 registros

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

-- Qualidades dos materiais
insert into material_qualidades (material_id, qualidades) values (1, 'Leve');
insert into material_qualidades (material_id, qualidades) values (1, 'Boa condutividade térmica');
insert into material_qualidades (material_id, qualidades) values (2, 'Resistente');
insert into material_qualidades (material_id, qualidades) values (2, 'Durável');
insert into material_qualidades (material_id, qualidades) values (3, 'Antiaderente');
insert into material_qualidades (material_id, qualidades) values (3, 'Fácil limpeza');

-- Componentes (superclasse Componente)
insert into Componente (peso, cor_id) values (0.9, 2);
insert into Componente (peso, cor_id) values (1.2, 3);
insert into Componente (peso, cor_id) values (1.5, 1);
insert into Componente (peso, cor_id) values (0.4, 3);
insert into Componente (peso, cor_id) values (0.5, 1);
insert into Componente (peso, cor_id) values (0.6, 2);
insert into Componente (peso, cor_id) values (0.8, 1);
insert into Componente (peso, cor_id) values (0.7, 2);
insert into Componente (peso, cor_id) values (0.9, 3);

-- Fundos
insert into Fundo (id, espessura, isantiaderente) values (1, 2.8, true);
insert into Fundo (id, espessura, isantiaderente) values (2, 3.2, false);
insert into Fundo (id, espessura, isantiaderente) values (3, 2.5, true);

-- Sustentações
insert into Sustentacao (id, tamanhoemcm, quantidade, codigo_tipo_sustentacao) values (4, 12, 1, 2);
insert into Sustentacao (id, tamanhoemcm, quantidade, codigo_tipo_sustentacao) values (5, 15, 2, 1);
insert into Sustentacao (id, tamanhoemcm, quantidade, codigo_tipo_sustentacao) values (6, 10, 1, 2);

-- Tampas
insert into Tampa (id, isdepressao) values (7, false);
insert into Tampa (id, isdepressao) values (8, true);
insert into Tampa (id, isdepressao) values (9, false);

-- Associação componentes <-> materiais
insert into componente_material (componente_id, material_id) values (1, 1);
insert into componente_material (componente_id, material_id) values (1, 2);
insert into componente_material (componente_id, material_id) values (2, 2);
insert into componente_material (componente_id, material_id) values (2, 3);
insert into componente_material (componente_id, material_id) values (3, 1);
insert into componente_material (componente_id, material_id) values (4, 1);
insert into componente_material (componente_id, material_id) values (5, 2);
insert into componente_material (componente_id, material_id) values (6, 3);
insert into componente_material (componente_id, material_id) values (7, 2);
insert into componente_material (componente_id, material_id) values (8, 3);
insert into componente_material (componente_id, material_id) values (9, 1);

-- Panelas
insert into Panela (modelo, preco, peso, capacidadelitros, descricaco, isinducao, id_colecao, id_fundo, id_sustentacao, id_tampa, id_fornecedor, codigo_tamanho, id_categoria) values
  ('Classic 24', 24990, 1.8, 3.5, 'Panela clássica com fundo triplo.', true, 1, 1, 4, 7, 1, 1, 1),
  ('PressurePro', 49990, 2.4, 5.0, 'Panela de pressão com tampa de segurança.', false, 2, 2, 5, 8, 2, 2, 2),
  ('CookPlus', 32990, 2.0, 4.2, 'Panela multiuso com acabamento inox.', true, 3, 3, 6, 9, 3, 3, 3);
