insert into tipo_usuario(descripcion,nombre)
values
('Administrador del Sistema','Decano'),
('Director de Programas Academicos','Director'),
('Profesores de los Cursos','Profesor'),
('Alumnos que integran los cursos','Alumno');

insert into usuario
(nombre, apellido,email,codigo,tipo_usuario_id)
values
('Jose Valerio', 'Perez', 'vimabo2908@gmail.com', '12345678',1),
('Alberto Jesus', 'Martinez', 'mar3214@gmail.com', '87654321',2),
('Ricardo', 'Rangel', 'richard12@gmail.com', '876543214',2),
('Marta', 'Medina', 'mar43@gmail.com', '0983219483',3),
('Lorena Isabel', 'Gonzalez', 'lore23@gmail.com', '09839483',3),
('Julio Cesar', 'Ramirez', 'jucebo@gmail.com', '0983329483',3),
('Carlos Andres', 'Bustamante', 'busta2134@gmail.com', '0987625342',4),
('David Alejandro', 'Rozo', 'rozo09@gmail.com', '0123625342',4),
('Mahisa Fernanda', 'Berrio', 'berrio999@gmail.com', '0988888342',4);