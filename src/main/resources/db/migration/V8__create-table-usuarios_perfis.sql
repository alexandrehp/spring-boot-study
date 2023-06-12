CREATE TABLE perfis(

    id bigint not null auto_increment,
    nome varchar(255) not null,

    primary key(id)

);

CREATE TABLE usuarios_perfis(

    usuario_id bigint not null,
    perfil_id bigint not null,

    foreign key(usuario_id) references usuarios(id),
    foreign key(perfil_id) references perfis(id),
    primary key(usuario_id, perfil_id)

);

insert into perfis values(1, 'ROLE_ADMIN');
insert into perfis values(2, 'ROLE_USER');