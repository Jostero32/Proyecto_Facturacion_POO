/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import Clases.Usuario;
import MySql.Mysql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Modelo_Usuarios {

    public ResultSet seleccionar(String busca) {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        busca = busca + "%";
        try {
            String orden = "select * from usuarios where usuario like ?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, busca);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }

    public ResultSet seleccionar() {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String orden = "select * from usuarios";
            ps = sql.conectar().prepareStatement(orden);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }

    public boolean eliminar(Usuario U) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        boolean ejecuto = false;
        try {
            String orden = "delete from usuarios where usuario=? and clave=? and P_Clientes=? and P_Proveedores=? and P_ProvCiu=? and P_Productos=? and P_Factura=? and P_Venta=? and P_Kardex=? and P_Usuarios=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, U.getUsuario());
            ps.setString(2, U.getClave());
            ps.setBoolean(3, U.isP_Clientes());
            ps.setBoolean(4, U.isP_Proveedores());
            ps.setBoolean(5, U.isP_ProvCiu());
            ps.setBoolean(6, U.isP_Productos());
            ps.setBoolean(7, U.isP_Factura());
            ps.setBoolean(8, U.isP_Venta());
            ps.setBoolean(9, U.isP_Kardex());
            ps.setBoolean(10, U.isP_Usuarios());
            ejecuto = ps.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

    public int modificar(String usuario, String clave, Usuario U) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        int ejecuto = 0;
        try {
            String orden = "update usuarios set  usuario=?, clave=?, P_Clientes=?, P_Proveedores=?, P_ProvCiu=?, P_Productos=?, P_Factura=?, P_Venta=?, P_Kardex=?, P_Usuarios=? where usuario=? and clave=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, U.getUsuario());
            ps.setString(2, U.getClave());
            ps.setBoolean(3, U.isP_Clientes());
            ps.setBoolean(4, U.isP_Proveedores());
            ps.setBoolean(5, U.isP_ProvCiu());
            ps.setBoolean(6, U.isP_Productos());
            ps.setBoolean(7, U.isP_Factura());
            ps.setBoolean(8, U.isP_Venta());
            ps.setBoolean(9, U.isP_Kardex());
            ps.setBoolean(10, U.isP_Usuarios());
            ps.setString(11, usuario);
            ps.setString(12, clave);
            ejecuto = ps.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Usuario existente", "ERROR", 0);
            } else {
                JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
            }
        } catch (NullPointerException a) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

    public boolean registrar(Usuario U) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        boolean ejecuto = true;
        try {
            String orden = "insert into usuarios (usuario, clave, P_Clientes, P_Proveedores, P_ProvCiu, P_Productos, P_Factura, P_Venta, P_Kardex, P_Usuarios) values (?,?,?,?,?,?,?,?,?,?)";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, U.getUsuario());
            ps.setString(2, U.getClave());
            ps.setBoolean(3, U.isP_Clientes());
            ps.setBoolean(4, U.isP_Proveedores());
            ps.setBoolean(5, U.isP_ProvCiu());
            ps.setBoolean(6, U.isP_Productos());
            ps.setBoolean(7, U.isP_Factura());
            ps.setBoolean(8, U.isP_Venta());
            ps.setBoolean(9, U.isP_Kardex());
            ps.setBoolean(10, U.isP_Usuarios());
            ejecuto = ps.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Usuario existente", "ERROR", 0);
            } else {
                JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
            }
        } catch (NullPointerException a) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

}
