/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import Clases.Ciudad;
import Clases.Provincia;
import MySql.Mysql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class Modelo_CiuProv {

    public ResultSet seleccionar() {
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

    public ResultSet seleccionarCiudades(String codProvincia) {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String orden = "select * from ciudad where codigoProvincia=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, codProvincia);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }

    public ResultSet seleccionar(String busca, String queBusca) {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        String as = "nombreP";
        busca = busca + "%";
        if (busca.isEmpty()) {
            busca = "*";
        }
        if (queBusca.matches("Nombre")) {
            as = "nombreP";
        } else if (queBusca.matches("Codigo")) {
            as = "codigoP";
        }
        try {
            String orden = "select * from provincias where " + as + " like ?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, busca);
            rs = ps.executeQuery();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }

    public boolean eliminar(Provincia p) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        boolean ejecuto = true;
        try {
            String orden = "delete from provincias where nombreP=? and codigoP=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, p.getNombreP());
            ps.setString(2, p.getCodigoP());
            ejecuto = ps.execute();
            orden = "delete from ciudad where codigoProvincia=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, p.getCodigoP());
            ejecuto = ps.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

    public int modificar(String codigo, Provincia p) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        int ejecuto = 0;
        try {
            String orden = "update provincias set  nombreP=?, codigoP=? where codigoP=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, p.getNombreP());
            ps.setString(2, p.getCodigoP());
            ps.setString(3, codigo);
            ejecuto = ps.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Provincia existente", "ERROR", 0);
            } else {
                JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
            }
        } catch (NullPointerException a) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

    public boolean registrar(Provincia pNuevo) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        boolean ejecuto = true;
        try {
            String orden = "insert into provincias (nombreP,codigoP) values (?,?)";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, pNuevo.getNombreP());
            ps.setString(2, pNuevo.getCodigoP());
            ejecuto = ps.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(null, "Provincia existente", "ERROR", 0);
            } else {
                JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
            }
        } catch (NullPointerException a) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

    public boolean eliminar(Ciudad p) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        boolean ejecuto = true;
        try {
            String orden = "delete from ciudad where nombreC=? and codigoC=? and codigoProvincia=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, p.getNombreC());
            ps.setString(2, p.getCodigoC());
            ps.setString(3, p.getCodigoP());
            ejecuto = ps.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

    public int modificar(String codigo, Ciudad p) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        int ejecuto = 0;
        try {
            String orden = "update ciudad set  nombreC=?, codigoC=?, codigoProvincia=? where codigoC=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, p.getNombreC());
            ps.setString(2, p.getCodigoC());
            ps.setString(3, p.getCodigoP());
            ps.setString(4, codigo);
            ejecuto = ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

    public boolean registrar(Ciudad pNuevo) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        boolean ejecuto = true;
        try {
            String orden = "insert into ciudad (nombreC,codigoC, codigoProvincia) values (?,?,?)";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, pNuevo.getNombreC());
            ps.setString(2, pNuevo.getCodigoC());
            ps.setString(3, pNuevo.getCodigoP());
            ejecuto = ps.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }
}
