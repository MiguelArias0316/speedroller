INSERT INTO usuario (id, username, password) VALUES
(1, 'juanperez', '{noop}password1'),
(2, 'anagomez', '{noop}password2'),
(3, 'carlosruiz', '{noop}password3');

INSERT INTO estudiante (id, nombre, fecha_nacimiento, genero, correo, telefono, usuario_id) VALUES
(1, 'Juan Perez', '2005-03-15', 'M', 'juan.perez@mail.com', '3001234567', 1),
(2, 'Ana Gomez', '2007-07-22', 'F', 'ana.gomez@mail.com', '3007654321', 2);

INSERT INTO instructor (id, nombre, correo, telefono, usuario_id) VALUES
(1, 'Carlos Ruiz', 'carlos.ruiz@mail.com', '3101234567', 3);

INSERT INTO clase (id, nivel, horario, instructor_id) VALUES
(1, 'Principiante', 'Lunes 4pm-5pm', 1),
(2, 'Intermedio', 'Miércoles 5pm-6pm', 1);

INSERT INTO clase_estudiante (clase_id, estudiante_id) VALUES
(1, 1),
(1, 2),
(2, 1);