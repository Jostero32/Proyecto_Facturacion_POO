/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author User
 */
public class Usuario {

    private String usuario;
    private String clave;
    private boolean bloqueo = false;
    private int int_fallido = 0;
    private boolean P_Clientes = false;
    private boolean P_Proveedores = false;
    private boolean P_ProvCiu = false;
    private boolean P_Productos = false;
    private boolean P_Factura = false;
    private boolean P_Venta = false;
    private boolean P_Kardex = false;
    private boolean P_Usuarios = false;

    public boolean isP_Clientes() {
        return this.P_Clientes;
    }

    public void setP_Clientes(boolean P_Clientes) {
        this.P_Clientes = P_Clientes;
    }

    public boolean isP_Proveedores() {
        return this.P_Proveedores;
    }

    public void setP_Proveedores(boolean P_Proveedores) {
        this.P_Proveedores = P_Proveedores;
    }

    public boolean isP_ProvCiu() {
        return this.P_ProvCiu;
    }

    public void setP_ProvCiu(boolean P_ProvCiu) {
        this.P_ProvCiu = P_ProvCiu;
    }

    public boolean isP_Productos() {
        return this.P_Productos;
    }

    public void setP_Productos(boolean P_Productos) {
        this.P_Productos = P_Productos;
    }

    public boolean isP_Factura() {
        return this.P_Factura;
    }

    public void setP_Factura(boolean P_Factura) {
        this.P_Factura = P_Factura;
    }

    public boolean isP_Venta() {
        return this.P_Venta;
    }

    public void setP_Venta(boolean P_Venta) {
        this.P_Venta = P_Venta;
    }

    public boolean isP_Kardex() {
        return this.P_Kardex;
    }

    public void setP_Kardex(boolean P_Kardex) {
        this.P_Kardex = P_Kardex;
    }

    public boolean isP_Usuarios() {
        return this.P_Usuarios;
    }

    public void setP_Usuarios(boolean P_Usuarios) {
        this.P_Usuarios = P_Usuarios;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return this.clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean isBloqueo() {
        return this.bloqueo;
    }

    public void setBloqueo(boolean bloqueo) {
        this.bloqueo = bloqueo;
    }

    public int getInt_fallido() {
        return this.int_fallido;
    }

    public void setInt_fallido(int int_fallido) {
        this.int_fallido = int_fallido;
    }

}
