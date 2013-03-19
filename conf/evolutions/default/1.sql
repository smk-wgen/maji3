use play2;
drop table if exists story;
drop table if exists facility;
# --- First database schema

# --- !Ups

create table facility (
  id                        int not null auto_increment primary key,
  name                      varchar(255) not null,
  line1             varchar(100),
  line2             varchar(100),
  city                      varchar(100),
  state                     varchar(50),
  country                   varchar(50),
  zip                       varchar(50)

);
# change to bigint later
create table story (
  id                        int not null auto_increment primary key,
  name                      varchar(255) not null,
  description                     varchar(255),
  facility_id               int not null,
  problem                   varchar(100) not null,
  treatment                 varchar(100),
  you                       varchar(100) not null,
  rating                    varchar(10),
  foreign key(facility_id)  references facility(id)
);