/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Clases.Ciudad;
import Clases.Cliente;
import Clases.Factura;
import Clases.Productos;
import Clases.Proveedor;
import Clases.Provincia;
import Clases.Usuario;
import Interfaz.Interfaz_Clientes;
import Interfaz.Interfaz_Compra;
import Interfaz.Interfaz_Kardex;
import Interfaz.Interfaz_Login;
import Interfaz.Interfaz_Productos;
import Interfaz.Interfaz_Programa;
import Interfaz.Interfaz_Proveedores;
import Interfaz.Interfaz_Provincias_Ciudades;
import Interfaz.Interfaz_Usuarios;
import Interfaz.Interfaz_Venta;
import Modelos.ModeloLogin;
import Modelos.Modelo_CiuProv;
import Modelos.Modelo_Clientes;
import Modelos.Modelo_Compra;
import Modelos.Modelo_Kardex;
import Modelos.Modelo_Productos;
import Modelos.Modelo_Proveedor;
import Modelos.Modelo_Usuarios;
import Modelos.Modelo_Venta;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public class Controlador_Principal {

    public static void main(String[] args) throws SQLException {
        Usuario u = new Usuario();
        Productos p = new Productos();
        ModeloLogin mod_login = new ModeloLogin();
        Interfaz_Login int_Login = new Interfaz_Login();
        Interfaz_Productos intp = new Interfaz_Productos();
        Interfaz_Programa inprog = new Interfaz_Programa();
        Modelo_Productos modelo = new Modelo_Productos();
        Controlador_Productos conp = new Controlador_Productos(modelo, intp, p);
        Interfaz_Provincias_Ciudades interfaz = new Interfaz_Provincias_Ciudades();
        Ciudad c = new Ciudad();
        Provincia prov = new Provincia();
        Modelo_CiuProv modprov = new Modelo_CiuProv();
        Controlador_Provincias_Ciudades provincias = new Controlador_Provincias_Ciudades(interfaz, modprov, prov, c);
        Interfaz_Clientes intClientes = new Interfaz_Clientes();
        Modelo_Clientes modClientes = new Modelo_Clientes();
        Cliente cliente = new Cliente();
        Controlador_Clientes clientes = new Controlador_Clientes(modClientes, intClientes, cliente);
        Interfaz_Proveedores intProveedor = new Interfaz_Proveedores();
        Modelo_Proveedor modProveedor = new Modelo_Proveedor();
        Proveedor proveedor = new Proveedor();
        Controlador_Proveedores conProve = new Controlador_Proveedores(modProveedor, intProveedor, proveedor);
        Interfaz_Usuarios intusuario=new Interfaz_Usuarios();
        Modelo_Usuarios modusuario=new Modelo_Usuarios();
        Controlador_Usuarios conUsuario=new Controlador_Usuarios(modusuario,intusuario,u);
        Interfaz_Compra intcompra=new Interfaz_Compra();
        Modelo_Compra modCom=new Modelo_Compra();
        Factura fac=new Factura();
        Controlador_Compra concompra=new Controlador_Compra(modCom,intcompra,fac);
        Interfaz_Venta intventa=new Interfaz_Venta();
        Modelo_Venta modVenta=new Modelo_Venta();
        Controlador_Venta conVenta=new Controlador_Venta(modVenta,intventa,fac);
        Interfaz_Kardex kar=new Interfaz_Kardex();
        Modelo_Kardex modkar=new Modelo_Kardex();
        Controlador_Kardex kardex=new Controlador_Kardex(modkar,kar,p);
        Controlador_Programa conprog = new Controlador_Programa(conp, inprog, provincias, clientes, conProve,conUsuario,concompra,conVenta,kardex);
        ControladorLogin cont = new ControladorLogin(u, int_Login, mod_login, conprog);
        int_Login.setVisible(true);
    }

}
