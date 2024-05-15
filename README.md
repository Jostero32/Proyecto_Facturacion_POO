# Proyecto de Facturación con Informe Kardex

## Descripción del Proyecto

Este proyecto consiste en un sistema de facturación que permite la gestión de inventarios y la generación de informes Kardex. Está desarrollado utilizando el lenguaje de programación Java y sigue el patrón de arquitectura de software Modelo Vista Controlador (MVC). La persistencia de los datos se maneja a través de una base de datos MySQL.

## Características Principales

- **Gestión de productos:** Capacidad para agregar, modificar, eliminar y buscar productos.
- **Facturación:** Generación de facturas de venta y manejo de diferentes formas de pago.
- **Informe Kardex:** Registro detallado de las entradas y salidas de inventario, permitiendo un seguimiento del movimiento de productos.
- **Usuarios y permisos:** Sistema de autenticación y autorización para diferentes roles de usuarios.

## Herramientas y Tecnologías Utilizadas

- **Lenguaje de Programación:** Java
- **Base de Datos:** MySQL
- **Framework MVC:** Java Swing para la interfaz de usuario
- **Gestor de Dependencias:** Maven para la gestión de librerías y dependencias

## Estructura del Proyecto

El proyecto está organizado en tres capas principales, siguiendo el patrón MVC:

### Modelo

Encargado de la lógica de negocios y de la interacción con la base de datos MySQL. Incluye clases para la gestión de productos, clientes, facturas y el informe Kardex.

### Vista

Componentes de la interfaz de usuario desarrollados con Java Swing. Incluye ventanas y diálogos para la interacción con el usuario, mostrando y solicitando información.

### Controlador

Intermediario entre la vista y el modelo. Recibe las entradas del usuario a través de la vista, procesa la información con el modelo y actualiza la vista.

## Configuración y Despliegue

### Requisitos

- Java JDK 11 o superior
- MySQL Server 8.0 o superior
- Maven 3.6 o superior

