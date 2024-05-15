/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Clases.Usuario;
import Interfaz.Interfaz_Programa;
import Interfaz.Interfaz_Provincias_Ciudades;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author User
 */
public class Controlador_Programa {

    private Controlador_Productos productos;
    private Interfaz_Programa interfaz;
    private Controlador_Provincias_Ciudades provincias;
    private Controlador_Clientes clientes;
    private Controlador_Proveedores proveedores;
    private Controlador_Usuarios usuarios;
    private Controlador_Compra compra;
    private Controlador_Venta venta;
    private Controlador_Kardex kardex;
    private Usuario usuario;

    public Controlador_Programa(Controlador_Productos Cproductos, Interfaz_Programa interfaz_p, Controlador_Provincias_Ciudades provincias, Controlador_Clientes clientes, Controlador_Proveedores proveedores, Controlador_Usuarios usuarios, Controlador_Compra compra, Controlador_Venta venta,Controlador_Kardex kardex) {
        this.productos = Cproductos;
        this.interfaz = interfaz_p;
        this.provincias = provincias;
        this.clientes = clientes;
        this.proveedores = proveedores;
        this.compra = compra;
        this.venta = venta;
        this.kardex=kardex;
        this.usuarios = usuarios;
    }

    public void insertarPestañas() {
        //ImageIcon imagen = new ImageIcon(getClass().getResource("/Recursos/PRODUCTOS.png"));
        this.interfaz.jTabbedPane1.remove(productos.getInterfaz());
        this.interfaz.jTabbedPane1.remove(provincias.getInterfaz());
        this.interfaz.jTabbedPane1.remove(clientes.getInterfaz());
        this.interfaz.jTabbedPane1.remove(proveedores.getInterfaz());
        this.interfaz.jTabbedPane1.remove(compra.getInterfaz());
        this.interfaz.jTabbedPane1.remove(venta.getInterfaz());
        this.interfaz.jTabbedPane1.remove(kardex.getInterfaz());
        this.interfaz.jTabbedPane1.remove(usuarios.getInterfaz());
        if (this.usuario.isP_Productos()) {
            this.interfaz.jTabbedPane1.addTab("Productos", null, productos.getInterfaz());
        }
        if (this.usuario.isP_ProvCiu()) {
            this.interfaz.jTabbedPane1.addTab("Provincias", null, provincias.getInterfaz());
        }
        if (this.usuario.isP_Clientes()) {
            this.interfaz.jTabbedPane1.addTab("Clientes", null, clientes.getInterfaz());
        }
        if (this.usuario.isP_Proveedores()) {
            this.interfaz.jTabbedPane1.addTab("Proveedores", null, proveedores.getInterfaz());
        }
        if (this.usuario.isP_Factura()) {
            this.interfaz.jTabbedPane1.addTab("Compra", null, compra.getInterfaz());
        }
        if (this.usuario.isP_Venta()) {
            this.interfaz.jTabbedPane1.addTab("Venta", null, venta.getInterfaz());
        }
        if (this.usuario.isP_Kardex()) {
            this.interfaz.jTabbedPane1.addTab("Kardex", null, kardex.getInterfaz());
        }
        if (this.usuario.isP_Usuarios()) {
            this.interfaz.jTabbedPane1.addTab("Usuarios", null, usuarios.getInterfaz());
        }
    }

    public Interfaz_Programa getInterfaz() {
        return this.interfaz;
    }

    public void setInterfaz(Interfaz_Programa interfaz) {
        this.interfaz = interfaz;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
