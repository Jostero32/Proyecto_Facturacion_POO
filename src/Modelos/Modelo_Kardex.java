/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import Clases.Cliente;
import MySql.Mysql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Modelo_Kardex {

    public ResultSet seleccionar(String cod) {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String orden = "select * from facturas where codproducto=?  order by fecha";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, cod);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }

    public ResultSet seleccionarProducto(String busca, String queBusca) {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        busca = busca + "%";
        if (queBusca.matches("Nombre")) {
            queBusca = "nombreP";
        } else {
            queBusca = "codProd";
        }
        try {
            String orden = "select * from productos where " + queBusca + " like ?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, busca);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }

    public ResultSet seleccionarProducto() {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String orden = "select * from productos";
            ps = sql.conectar().prepareStatement(orden);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }

    public ResultSet seleccionarProveedor(Long proveedor) {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String orden = "select * from proveedor where RUC like ?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setLong(1, proveedor);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }
}
