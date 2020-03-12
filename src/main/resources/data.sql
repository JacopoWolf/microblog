insert into user (username, email) values 
('xxx_topolino_xxx', 'topolin0@topo.com'),
('Plut0', 'pluto@topo.com'),
('Tortello01', 'torte@tortellino.com');

insert into post (title, content, date, author_id) values 
('tellus in sagittis', 'tellus semper', '2019-11-25',1),
('pizza', 'amet cursus id turpis integer aliquet massa id lobortis convallis tortor', '2019-01-22 10:00:00',2),
('kaffèè', 'buongiornissimoooo', '2019-06-26 02:01:00',3),
('cose', 'est congue elementum in hac habitasse platea dictumst morbi vestibulum velit id pretium iaculis diam erat fermentum justo nec condimentum neque sapien placerat ante nulla justo aliquam quis', '2020-02-04 19:20:00',3),
('interessantemente lungo', 'sit amet cursus id turpis integer aliquet massa id lobortis convallis tortor risus dapibus augue vel accumsan tellus nisi eu orci mauris lacinia sapien quis libero nullam', '2019-04-10 15:15:15',3);

insert into comment (date,author_id,under_id,content) values
('2019-06-26 02:05:00',1,3,'cafffeee del buongiollooo'),
('2019-04-10 15:20:15',2,5,'concordo'),
('2019-04-10 15:22:15',1,5,'anche io');