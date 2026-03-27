DROP DATABASE IF EXISTS Aeropuerto;
CREATE DATABASE Aeropuerto;
USE Aeropuerto;

CREATE TABLE rol (
  id_rol INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(50) NOT NULL
);
INSERT INTO rol (nombre) VALUES
    ('admin'),
    ('operaciones'),
    ('atencion');

CREATE TABLE usuario (
  id_usuario INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(20) UNIQUE,
  password VARCHAR(20) NOT NULL,
  id_rol INT,
  
  FOREIGN KEY (id_rol) REFERENCES rol(id_rol)

);

CREATE TABLE cliente (
  dpi VARCHAR(13) PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  fecha_nacimiento DATE,
  telefono VARCHAR(20),
  email VARCHAR(50) UNIQUE,
  nacionalidad VARCHAR(20) NOT NULL
);

CREATE TABLE destino (
  id_destino INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(20) UNIQUE,
  pais VARCHAR(20),
  descripcion TEXT
);

CREATE TABLE paquete (
  id_paquete INT PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(30) UNIQUE,
  id_destino INT,
  duracion INT NOT NULL,
  precio DECIMAL(10,2),
  capacidad INT NOT NULL,
  estado BOOLEAN,
  FOREIGN KEY (id_destino) REFERENCES destino(id_destino)

);

CREATE TABLE reservacion (
  id_reservacion INT PRIMARY KEY AUTO_INCREMENT,
  codigo VARCHAR(10) UNIQUE,
  fecha_creacion DATETIME,
  fecha_viaje DATE,
  id_paquete INT,
  id_usuario INT,
  cantidad_personas INT NOT NULL,
  costo_total DECIMAL(10,2),
  estado VARCHAR(20),
  FOREIGN KEY (id_paquete) REFERENCES paquete(id_paquete),
  FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario) 

);

CREATE TABLE reservacion_cliente (
    id_reservacion INT NOT NULL,
    dpi_cliente VARCHAR(20),
    PRIMARY KEY (id_reservacion, dpi_cliente),
    FOREIGN KEY (dpi_cliente) REFERENCES cliente(dpi),
    FOREIGN KEY (id_reservacion) REFERENCES reservacion(id_reservacion);
);
