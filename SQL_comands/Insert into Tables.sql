use curriculum_vitaes;

insert into T_GENDERS values (1, 'мужчина'), (2, 'женщина');

insert into T_JOB_CANDIDATES values (1, 'Петр', 'Петров', 'Петрович', '1986-12-12', 1), 
(2, 'Иван', 'Иванов', 'Иванович', '1997-4-4', 1), 
(3, 'Морская', 'Мария', 'Васильевна', '1999-11-07', 2);

insert into T_CONTACTS values (1, '+375(29)123-45-67', 1),
(2, 'http://github.com/petya', 1),
(3, 'petrovich@gmail.com', 1),
(4, '+375(29)87-65-43', 2),
(5, 'http://github.com/vanya', 2),
(6, 'skype:ivanko', 2),
(7, '+375(29)999-99-99', 3),
(8, 'https://www.linkedin.com/in/mariya/', 3);

insert into T_TECHNOLOGIES values (1, 'Git'),
(2, 'Spring Boot'),
(3, 'HTML'),
(4, 'JavaEE'),
(5, 'Java Core'),
(6, 'Maven'),
(7, 'REST'),
(8, 'Spring');

insert into T_job_candidates_technologies values (1, 1),(1, 2),(1, 3),
(2, 1),(2, 4),(2, 5),
(3, 6),(3, 7),(3, 8);