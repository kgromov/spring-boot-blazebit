drop table if exists cat cascade;
drop table if exists cat_kittens cascade;
drop table if exists person cascade;
drop sequence if exists cat_seq;
drop sequence if exists person_seq;
create sequence cat_seq start with 1 increment by 50;
create sequence person_seq start with 1 increment by 50;
create table cat
(
    age       integer,
    father_id bigint,
    id        bigint not null,
    mother_id bigint,
    owner_id  bigint,
    name      varchar(255),
    primary key (id)
);

create table cat_kittens
(
    cat_id     bigint not null,
    kittens_id bigint not null,
    primary key (cat_id, kittens_id)
);
create table person
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
);

alter table if exists cat add constraint FKqkschtmsounckiqtb607r0dk4 foreign key (father_id) references cat;
alter table if exists cat add constraint FKdvto2qy6j6ilpjgc49o0kkavw foreign key (mother_id) references cat;
alter table if exists cat add constraint FKlcaqlt7ghsb3o4n7801e6q2aw foreign key (owner_id) references person;
alter table if exists cat_kittens add constraint FKkdtfj5q5ys6seedwwtiplqsd5 foreign key (kittens_id) references cat;
alter table if exists cat_kittens add constraint FKegctpkoipgfjh4m098ca9o7nt foreign key (cat_id) references cat