/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import Clases.Proveedor;
import MySql.Mysql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Modelo_Proveedor {

    public ResultSet seleccionar(String busca, String queBusca) {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        busca = busca + "%";
        if (busca.isEmpty()) {
            busca = "*";
        }
        if ("Nombre".equals(queBusca)) {
            queBusca = queBusca.toLowerCase();
        }
        try {
            String orden = "select * from proveedor where " + queBusca + " like ?";
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
            String orden = "select * from proveedor";
            ps = sql.conectar().prepareStatement(orden);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }

    public boolean eliminar(Proveedor proveedor) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        boolean ejecuto = true;
        try {
            String orden = "delete from proveedor where nombre=? and RUC=? and direccion=? and telefono=? and codP=? and codC=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, proveedor.getNombre());
            ps.setLong(2, proveedor.getRUC());
            ps.setString(3, proveedor.getDireccion());
            ps.setString(4, proveedor.getTelefono());
            ps.setString(5, proveedor.getCodProvincia());
            ps.setString(6, proveedor.getCodCiudad());
            ejecuto = ps.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

    public int modificar(long RUC, Proveedor proveedor) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        int ejecuto = 0;
        try {
            String orden = "update proveedor set  nombre=? , RUC=? , direccion=?, telefono=? , codP=? , codC=? where RUC=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, proveedor.getNombre());
            ps.setLong(2, proveedor.getRUC());
            ps.setString(3, proveedor.getDireccion());
            ps.setString(4, proveedor.getTelefono());
            ps.setString(5, proveedor.getCodProvincia());
            ps.setString(6, proveedor.getCodCiudad());
            ps.setLong(7, RUC);
            ejecuto = ps.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "RUC existente", "ERROR", 0);
            } else {
                JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
            }
        } catch (NullPointerException a) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

    public boolean registrar(Proveedor proveedor) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        boolean ejecuto = true;
        try {
            String orden = "insert into proveedor (nombre,RUC,direccion,telefono,codP,codC) values (?,?,?,?,?,?)";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, proveedor.getNombre());
            ps.setLong(2, proveedor.getRUC());
            ps.setString(3, proveedor.getDireccion());
            ps.setString(4, proveedor.getTelefono());
            ps.setString(5, proveedor.getCodProvincia());
            ps.setString(6, proveedor.getCodCiudad());
            ejecuto = ps.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "RUC existente", "ERROR", 0);
            } else {
                JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
            }
        } catch (NullPointerException a) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

    public ResultSet seleccionarProvincias() {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String orden = "select * from provincias";
            ps = sql.conectar().prepareStatement(orden);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }

    public ResultSet seleccionarCiudades(String busca) {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        busca = busca + "%";
        if (busca.isEmpty()) {
            busca = "*";
        }
        try {
            String orden = "select * from ciudad where codigoProvincia like ?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, busca);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }
}
