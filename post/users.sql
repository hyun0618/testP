/*
회원가입
*/

create table users (
    id          number(6) generated as identity,
    userid      varchar2(20) not null,
    password    varchar2(256) not null,
    email       varchar2(256) not null,
    points      number(7) default 0,
    constraint users_id_pk primary key (id),
    constraint users_userid_uq unique (userid),
    constraint users_email_uq unique (email),
    constraint users_points_ck check (points >= 0)

);