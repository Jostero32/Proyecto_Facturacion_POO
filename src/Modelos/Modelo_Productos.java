/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import Clases.Productos;
import MySql.Mysql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Modelo_Productos {

    public ResultSet seleccionar(String busca, String queBusca) {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String as = "nombreP";
        busca = busca + "%";
        if (busca.isEmpty()) {
            busca = "*";
        }
        switch (queBusca) {
            case "Nombre":
                as = "nombreP";
                break;

            case "Codigo":
                as = "codProd";
                break;

            case "Proveedor":
                as = "codigoP";
                break;

        }
        try {
            String orden = "select * from productos where " + as + " like ?";
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
            String orden = "select * from productos";
            ps = sql.conectar().prepareStatement(orden);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }

    public boolean eliminar(Productos p) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        boolean ejecuto=true;
        try {
            String orden = "delete from productos where nombreP=? and codProd=? and codigoP=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCodigo());
            ps.setString(3, p.getCodigoProveedor());
            ejecuto = ps.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

    public int modificar(String codigo, Productos p) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        int ejecuto = 0;
        try {
            String orden = "update productos set  nombreP=?, codProd=?, codigoP=? where codProd=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCodigo());
            ps.setString(3, p.getCodigoProveedor());
            ps.setString(4, codigo);
            ejecuto = ps.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Producto existente", "ERROR", 0);
            } else {
                JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
            }
        } catch (NullPointerException a) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

    public boolean registrar(Productos pNuevo) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        boolean ejecuto = true;
        try {
            String orden = "insert into productos (nombreP,codProd,codigoP) values (?,?,?)";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, pNuevo.getNombre());
            ps.setString(2, pNuevo.getCodigo());
            ps.setString(3, pNuevo.getCodigoProveedor());
            ejecuto = ps.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Producto existente", "ERROR", 0);
            } else {
                JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
            }
        } catch (NullPointerException a) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

    public ResultSet seleccionarProveedor() {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String orden = "select * from proveedor";
            ps = sql.conectar().prepareStatement(orden);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }

    public ResultSet seleccionar(String proveedor) {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String orden = "select * from productos where codigoP=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, proveedor);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }

    public ResultSet seleccionar(String busca, String queBusca, String Proveedor) {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String as = "nombreP";
        busca = busca + "%";
        if (busca.isEmpty()) {
            busca = "*";
        }
        switch (queBusca) {
            case "Nombre":
                as = "nombreP";
                break;

            case "Codigo":
                as = "codProd";
                break;

            case "Proveedor":
                as = "codigoP";
                break;

        }
        try {
            String orden = "select * from productos where " + as + " like ? and codigoP=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, busca);
            ps.setString(2, Proveedor);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }
}
