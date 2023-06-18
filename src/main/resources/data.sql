insert into tipo_usuario(descripcion,nombre)
values
('descripcion decano','Decano'),
('descripcion decano programa','Director'),
('descripcion profesor','Profesor'),
('descripcion alumno','Alumno')
;

insert into usuario
(nombre, apellido,email,codigo,tipo_usuario_id)
values
('Usuario 1', 'apellido 1', 'usuario@gmail.com', '2333',1),
('Usuario 2', 'apellido 2', 'usuario1@gmail.com', '2334',2),
('Usuario 3', 'apellido 3', 'usuario2@gmail.com', '2335',3),
('Usuario 4', 'apellido 4', 'usuario3@gmail.com', '2336',4)
;