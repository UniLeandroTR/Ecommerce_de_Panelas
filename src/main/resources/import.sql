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

