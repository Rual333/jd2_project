insert into T_GENDERS values ('402881e67758e505017758e50a670000', 'мужчина'), ('402881e67758e505017758e50c460001', 'женщина');

            insert into T_JOB_CANDIDATES values ('402881e478020e9b0178020ea1560000', 'Петр', 'Петров', 'Петрович', '1986-12-12', '402881e67758e505017758e50a670000'),
            ('402881e478020e9b0178020ea3e20001', 'Иван', 'Иванов', 'Иванович', '1997-4-4', '402881e67758e505017758e50a670000'),
            ('402881e478020e9b0178020ea3e70002', 'Мария', 'Морская', 'Васильевна', '1999-11-07', '402881e67758e505017758e50c460001');

            insert into T_CONTACTS values ('402881e67758e505017758e50c4b0002', '+375(29)123-45-67', '402881e478020e9b0178020ea1560000'),
            ('402881e67758ed00017758ed04e40000', 'http://github.com/petya', '402881e478020e9b0178020ea1560000'),
            ('402881e67758ed00017758ed06a20001', 'petrovich@gmail.com', '402881e478020e9b0178020ea1560000'),
            ('402881e67758ed00017758ed06a70002', '+375(29)87-65-43', '402881e478020e9b0178020ea3e20001'),
            ('402881e67758ef1b017758ef1f740000', 'http://github.com/vanya', '402881e478020e9b0178020ea3e20001'),
            ('402881e67758ef1b017758ef21540001', 'skype:ivanko', '402881e478020e9b0178020ea3e20001'),
            ('402881e67758ef1b017758ef215e0002', '+375(29)999-99-99', '402881e478020e9b0178020ea3e70002'),
            ('402881e67758f013017758f0186a0000', 'https://www.linkedin.com/in/mariya/', '402881e478020e9b0178020ea3e70002');

            insert into T_TECHNOLOGIES values ('4028e67b7782b229017782b231460002', 'Git'),
            ('402881e67758f013017758f01a000001', 'Spring Boot'),
            ('402881e67758f013017758f01a040002', 'HTML'),
            ('402881e67758f09d017758f0a2590000', 'JavaEE'),
            ('402881e67758f09d017758f0a4730001', 'Java Core'),
            ('402881e67758f09d017758f0a4820002', 'Maven'),
            ('4028e67b7782b229017782b22e740000', 'REST'),
            ('4028e67b7782b229017782b231400001', 'Spring');

            insert into T_job_candidates_technologies values ('402881e478020e9b0178020ea1560000', '4028e67b7782b229017782b231460002'),('402881e478020e9b0178020ea1560000', '402881e67758f013017758f01a000001'),('402881e478020e9b0178020ea1560000', '402881e67758f013017758f01a040002'),
            ('402881e478020e9b0178020ea3e20001', '4028e67b7782b229017782b231460002'),('402881e478020e9b0178020ea3e20001', '402881e67758f09d017758f0a2590000'),('402881e478020e9b0178020ea3e20001', '402881e67758f09d017758f0a4730001'),
            ('402881e478020e9b0178020ea3e70002', '402881e67758f09d017758f0a4820002'),('402881e478020e9b0178020ea3e70002', '4028e67b7782b229017782b22e740000'),('402881e478020e9b0178020ea3e70002', '4028e67b7782b229017782b231400001');