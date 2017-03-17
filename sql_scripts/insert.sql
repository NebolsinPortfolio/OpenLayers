insert into SHAPE_TYPES (NAME) 
values ('Point'), ('LineString'), ('Polygon');

INSERT INTO SHAPES (ID_TYPE, NAME, DESCRIPTION) VALUES 
('1', 'point1', 'desc1'),
('2', 'ls1', 'desc2');
       
INSERT INTO COORDINATES (LONGITUDE, LATITUDE, ID_SHAPE, MARKER_NUMBER) VALUES 
('23.21', '23.22', '1', '1'), 
('11.11', '-11.11','2', '1'),
('22.22', '-22.22', '2','2');