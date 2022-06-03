insert into user(user, password) values ("123", "123");
insert into user(user, password) values ("abc", "abc");
insert into user(user, password) values ("vazio", "vazio");

insert into serie(name, number_of_seasons, number_of_episodes, category, total_duration_minutes, release_date, classification) values ("Narcos", 2, 14, "Action", 430, date("1999/09/09"), 7);
insert into serie(name, number_of_seasons, number_of_episodes, category, total_duration_minutes, release_date, classification) values ("Breaking Bad", 4, 70, "Action", 1240, date("2004-07-23"), 9);

insert into season(serie_id, season_number, total_duration_minutes, number_of_episodes, release_date, classification) values (1, 1, 145, 9, date("1999/09/09"), 6);
insert into season(serie_id, season_number, total_duration_minutes, number_of_episodes, release_date, classification) values (2, 1, 400, 15, date("2004-07-23"), 8);
insert into season(serie_id, season_number, total_duration_minutes, number_of_episodes, release_date, classification) values (2, 2, 200, 7, date("2004-12-12"), 9);

insert into episode(season_id, ep_number, name, duration_minutes, release_date, classification) values (2, 1, "Pilot", 55, date("2004-07-23"), 7);
insert into episode(season_id, ep_number, name, duration_minutes, release_date, classification) values (2, 2, "Cancer", 46, date("2004-07-23"), 8);
insert into episode(season_id, ep_number, name, duration_minutes, release_date, classification) values (2, 3, "Fly", 49, date("2004-07-23"), 9);
insert into episode(season_id, ep_number, name, duration_minutes, release_date, classification) values (1, 1, "Pilot", 45, date("1999/09/09"), 7);
insert into episode(season_id, ep_number, name, duration_minutes, release_date, classification) values (1, 2, "New Chapter", 36, date("1999/09/09"), 6);
insert into episode(season_id, ep_number, name, duration_minutes, release_date, classification) values (1, 3, "Novo", 36, date("1999/09/09"), 6);
insert into episode(season_id, ep_number, name, duration_minutes, release_date, classification) values (1, 4, "Velho", 42, date("1999/09/09"), 8);

insert into records(user_id, episode_id, watched_date) values (1, 1, Date("2009-09-23"));
insert into records(user_id, episode_id, watched_date) values (2, 1, Date("2009-09-23"));
insert into records(user_id, episode_id, watched_date) values (1, 2, Date("2009-09-23"));
insert into records(user_id, episode_id, watched_date) values (2, 2, Date("2009-09-23"));