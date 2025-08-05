# Desafio Alura - Catálogo de Libros y Autores

Esta es una aplicación de consola desarrollada con Spring Boot que se conecta a la API de Gutendex para buscar y almacenar información de libros y autores en una base de datos PostgreSQL.

# Funcionalidades

La aplicación ofrece un menú interactivo con las siguientes opciones:

Buscar libro por título: Realiza una búsqueda en la API de Gutendex y guarda el libro y su autor en la base de datos si es encontrado.

Listar libros buscados: Muestra todos los libros almacenados en la base de datos.

Listar autores buscados: Muestra todos los autores guardados en la base de datos.

Listar autores vivos en un determinado año: Permite filtrar y mostrar los autores que estaban vivos en un año específico.

Listar libros por idioma: Muestra los libros de la base de datos filtrados por su idioma (ej. es, en).

Ver estadísticas de libros por idioma: Muestra la cantidad total de libros disponibles para varios idiomas comunes.

Ver el Top 10 de libros más descargados: Muestra los 10 libros con el mayor número de descargas de la base de datos.

Salir: Termina la ejecución de la aplicación.

# Tecnologías Utilizadas

Java 17: Lenguaje de programación.

Spring Boot 3: Framework para el desarrollo de aplicaciones.

Spring Data JPA: Abstracción para el manejo de la base de datos.

PostgreSQL: Sistema de gestión de bases de datos relacional.

Maven: Herramienta de gestión y construcción de proyectos.

Jackson Databind: Librería para el mapeo de objetos Java a/desde JSON.

# Requisitos de Instalación
Para ejecutar este proyecto, necesitas tener instalado:

Java Development Kit (JDK) 17 o superior.

Maven.

PostgreSQL (con una base de datos creada).

# Configuración de la Base de Datos
Antes de ejecutar la aplicación, debes configurar los datos de conexión a tu base de datos PostgreSQL. Por razones de seguridad, se recomienda utilizar variables de entorno.

Crea una base de datos en PostgreSQL (ej. gutendex_db).

Define las variables de entorno en tu sistema operativo o en la configuración de tu IDE con los siguientes datos:

DB_HOST: Host de la base de datos (ej. localhost).

DB_PORT: Puerto de la base de datos (ej. 5040).

DB_NAME: Nombre de tu base de datos (ej. gutendex_db).

DB_USER: Nombre de usuario de PostgreSQL.

DB_PASSWORD: Contraseña de PostgreSQL.

Asegúrate de que tu archivo application.properties contenga la siguiente configuración:

spring.datasource.url=jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Cómo Ejecutar la Aplicación
Clona el repositorio de GitHub.

Navega al directorio del proyecto en tu terminal.

Ejecuta la aplicación con el siguiente comando de Maven:

mvn spring-boot:run

El programa se iniciará y mostrará el menú principal en la consola, listo para interactuar.
