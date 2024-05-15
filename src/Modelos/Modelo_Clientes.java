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

public class Modelo_Clientes {

    public ResultSet seleccionar(String busca, String queBusca) {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        busca = busca + "%";
        if (busca.isEmpty()) {
            busca = "*";
        }
        queBusca = queBusca.toLowerCase();
        try {
            String orden = "select * from cliente where " + queBusca + " like ?";
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
            String orden = "select * from cliente";
            ps = sql.conectar().prepareStatement(orden);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }

    public boolean eliminar(Cliente c) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        boolean ejecuto = true;
        try {
            String orden = "delete from cliente where nombre=? and cedula=? and direccion=? and codProvincia=? and codCiudad=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, c.getNombre());
            ps.setInt(2, c.getCedula());
            ps.setString(3, c.getDireccion());
            ps.setString(4, c.getCodProvincia());
            ps.setString(5, c.getCodCiudad());
            ejecuto = ps.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

    public int modificar(int cedula, Cliente c) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        int ejecuto = 0;
        try {
            String orden = "update cliente set  nombre=? , cedula=? , direccion=? , codProvincia=? , codCiudad=? where cedula=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, c.getNombre());
            ps.setInt(2, c.getCedula());
            ps.setString(3, c.getDireccion());
            ps.setString(4, c.getCodProvincia());
            ps.setString(5, c.getCodCiudad());
            ps.setInt(6, cedula);
            ejecuto = ps.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Cedula existente", "ERROR", 0);
            } else {
                JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
            }
        } catch (NullPointerException a) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

    public boolean registrar(Cliente c) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        boolean ejecuto = true;
        try {
            String orden = "insert into cliente (nombre,cedula,direccion,codProvincia,codCiudad) values (?,?,?,?,?)";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, c.getNombre());
            ps.setInt(2, c.getCedula());
            ps.setString(3, c.getDireccion());
            ps.setString(4, c.getCodProvincia());
            ps.setString(5, c.getCodCiudad());
            ejecuto = ps.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Cedula existente", "ERROR", 0);
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
