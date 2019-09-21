#site pessoal para contatos de relacionamento: secjov
drop database if exists secjov;
create database if not exists secjov;

drop table if exists secjov.perfil;
create table if not exists secjov.perfil(
    idper int primary key NOT NULL AUTO_INCREMENT,
    descricaoperfil varchar(256)
);

drop table if exists secjov.email;
create table if not exists secjov.email(
    idemail int primary key NOT NULL AUTO_INCREMENT,
    idusu int,
    email varchar(300) not null unique,
    senha varchar(20) not null,
    autenticacao boolean,
    perfilid int
);

drop table if exists secjov.usuario;
create table if not exists secjov.usuario(
    idusu int primary key NOT NULL AUTO_INCREMENT,
    nome varchar(20),
    sobrenome varchar(200),
    sexo char(1),
    cpf varchar(25),
    rg varchar(25),
    foneres varchar(13),
    fonecel varchar(13),
    idemail int NOT NULL,
    tipologradouro varchar(50),
    rua varchar(150),
    cidade varchar(100),
    uf char(2),
    cep int(8),
    descricao text
);

drop table if exists secjov.empregador;
create table if not exists secjov.empregador(
     idemp int primary key NOT NULL AUTO_INCREMENT,
     razaosocial varchar(20) ,
     nomefantasia varchar(200),
     cnpj varchar(25),
     fonecom varchar(13),
     fonecel varchar(13),
     idemail int NOT NULL,
     tipologradouro varchar(50),
     rua varchar(150),
     cidade varchar(100),
     uf char(2),
     cep int(8),
     descricao text
);

alter table secjov.email   add foreign key(perfilid) references secjov.perfil(idper);
alter table secjov.usuario    add foreign key(idemail) references secjov.email(idemail);
/*alter table secjov.empregador add foreign key(perfilid) references secjov.perfil(idper);*/



insert into secjov.perfil(descricaoperfil)
values ('administrador');

insert into secjov.perfil(descricaoperfil)
values ('usuario');

insert into secjov.perfil(descricaoperfil)
values ('empregador');


insert into secjov.email(email, senha,autenticacao,perfilid)
values ('jjr@com','123',false,2);

insert into secjov.email(email, senha,autenticacao,perfilid)
values ('empresa1@com','1234',false,3);

insert into secjov.usuario (nome,sobrenome,sexo,cpf,rg,foneres,fonecel,idemail,tipologradouro,rua,cidade,uf,cep,descricao)
values('Joao','Júnior','M','013','023','8555','8592',1,'avenida','ruaum','Mara','CE','60','Nada');


insert into secjov.empregador (razaosocial,nomefantasia,cnpj,fonecom,fonecel,idemail,tipologradouro,rua,cidade,uf,cep,descricao)
values('TECNO POG','J3','1111111111','85988887777','853232444',2,'8592','avenida','rua um','CE','6060','Vem pro estagio');

/*UPDATE secjov.usuario
SET	idusu='joao.junior7cc'
WHERE	perfilid=1;*/
