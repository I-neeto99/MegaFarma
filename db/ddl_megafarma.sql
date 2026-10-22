-- =====================================================================
-- MegaFarma - criacao das tabelas (Oracle)
-- CLIENTES 1:N VENDAS 1:N ITENSVENDIDOS N:1 REMEDIOS
-- =====================================================================

-- ---------------------------------------------------------------------
-- 1) TABELAS
-- ---------------------------------------------------------------------

create table ddd_clientes(
    id_cliente          number,
    nome                varchar2(100),
    cpf                 varchar2(20),
    email               varchar2(50),
    data_de_nascimento  date
);

create table ddd_remedios(
    codigo              number,
    nome                varchar2(100),
    preco               number(6,2),
    data_de_fabricacao  date,
    data_de_validade    date
);

create table ddd_vendas(
    codigo          number,
    data_da_venda   date,
    codcliente      number
);

create table ddd_itensvendidos(
    codvenda    number,
    codremedio  number,
    quantidade  number
);

-- ---------------------------------------------------------------------
-- 2) CHAVES PRIMARIAS
-- ---------------------------------------------------------------------

alter table ddd_clientes
    add constraint ddd_clientes_pk primary key (id_cliente);

alter table ddd_remedios
    add constraint ddd_remedios_pk primary key (codigo);

alter table ddd_vendas
    add constraint ddd_vendas_pk primary key (codigo);

alter table ddd_itensvendidos
    add constraint ddd_itensvendidos_pk primary key (codvenda, codremedio);

-- ---------------------------------------------------------------------
-- 3) CHAVES ESTRANGEIRAS
-- ---------------------------------------------------------------------

alter table ddd_vendas
    add constraint ddd_vendas_cliente_fk foreign key (codcliente)
    references ddd_clientes (id_cliente);

alter table ddd_itensvendidos
    add constraint ddd_itens_venda_fk foreign key (codvenda)
    references ddd_vendas (codigo);

alter table ddd_itensvendidos
    add constraint ddd_itens_remedio_fk foreign key (codremedio)
    references ddd_remedios (codigo);

-- ---------------------------------------------------------------------
-- 4) CAMPOS OBRIGATORIOS
-- ---------------------------------------------------------------------

alter table ddd_clientes  modify nome       not null;
alter table ddd_remedios  modify nome       not null;
alter table ddd_itensvendidos modify quantidade not null;

-- ---------------------------------------------------------------------
-- 5) SEQUENCES + TRIGGERS (gerar os codigos automaticamente)
--
-- Isso existe porque o RemedioDAO faz:
--    insert into ddd_remedios (nome, preco, ...)   <- sem o codigo
-- Sem isso, o insert quebra com "cannot insert NULL".
-- Se o professor pedir para digitar o codigo na mao, apague esta secao.
-- ---------------------------------------------------------------------

create sequence ddd_clientes_seq start with 1 increment by 1;
create sequence ddd_remedios_seq start with 1 increment by 1;
create sequence ddd_vendas_seq   start with 1 increment by 1;

create or replace trigger ddd_clientes_bi
before insert on ddd_clientes
for each row
when (new.id_cliente is null)
begin
    :new.id_cliente := ddd_clientes_seq.nextval;
end;
/

create or replace trigger ddd_remedios_bi
before insert on ddd_remedios
for each row
when (new.codigo is null)
begin
    :new.codigo := ddd_remedios_seq.nextval;
end;
/

create or replace trigger ddd_vendas_bi
before insert on ddd_vendas
for each row
when (new.codigo is null)
begin
    :new.codigo := ddd_vendas_seq.nextval;
end;
/

-- ---------------------------------------------------------------------
-- APAGAR TUDO (so descomente se quiser comecar do zero - perde os dados)
-- ---------------------------------------------------------------------
-- drop table ddd_itensvendidos cascade constraints;
-- drop table ddd_vendas        cascade constraints;
-- drop table ddd_remedios      cascade constraints;
-- drop table ddd_clientes      cascade constraints;
-- drop sequence ddd_clientes_seq;
-- drop sequence ddd_remedios_seq;
-- drop sequence ddd_vendas_seq;
