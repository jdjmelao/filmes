# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table episode (
  id                            integer auto_increment not null,
  season_id                     integer not null,
  ep_number                     integer not null,
  name                          varchar(255) not null,
  duration_minutes              integer not null,
  release_date                  varchar(255),
  classification                integer not null,
  constraint pk_episode primary key (id)
);

create table records (
  id                            integer auto_increment not null,
  user_id                       integer not null,
  episode_id                    integer not null,
  watched_date                  varchar(255),
  constraint pk_records primary key (id)
);

create table season (
  id                            integer auto_increment not null,
  serie_id                      integer not null,
  season_number                 integer not null,
  total_duration_minutes        integer not null,
  number_of_episodes            integer not null,
  release_date                  varchar(255),
  classification                integer not null,
  constraint pk_season primary key (id)
);

create table user (
  id                            integer auto_increment not null,
  user                          varchar(255) not null,
  password                      varchar(255) not null,
  constraint pk_user primary key (id)
);


# --- !Downs

drop table if exists episode;

drop table if exists records;

drop table if exists season;

drop table if exists user;

