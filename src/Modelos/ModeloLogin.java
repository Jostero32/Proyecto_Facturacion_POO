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
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class ModeloLogin {

    public ResultSet seleccionar(Usuario u) {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        Statement ps = null;
        try {
            String orden = "select * from usuarios where usuario = '"+u.getUsuario()+"' and clave='"+u.getClave()+"';";
            ps = sql.conectar().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery(orden);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }

    public boolean eliminar(Usuario u) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        boolean ejecuto = false;
        try {
            String orden = "delete from usuarios where usuario=?, contraseña=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, u.getUsuario());
            ps.setString(2, u.getClave());
            ejecuto = ps.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

    public int modificar(String usuario, Usuario uNuevo) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        int ejecuto = 0;
        try {
            String orden = "update usuarios set usuario=?,clave=?, bloqueo=?, int_fallido=? where usuario=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, uNuevo.getUsuario());
            ps.setString(2, uNuevo.getClave());
            ps.setBoolean(3, uNuevo.isBloqueo());
            ps.setInt(4, uNuevo.getInt_fallido());
            ps.setString(5, usuario);
            ejecuto = ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

    public boolean registrar(Usuario uNuevo) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        boolean ejecuto = false;
        try {
            String orden = "insert into usuarios (usuario,clave,bloqueo,int_fallido) values (?,?,?,?)";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, uNuevo.getUsuario());
            ps.setString(2, uNuevo.getClave());
            ps.setBoolean(3, uNuevo.isBloqueo());
            ps.setInt(4, uNuevo.getInt_fallido());
            ejecuto = ps.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return ejecuto;
    }

    public void intentoFallido(String usuario, int fallido, boolean b) {
        Mysql sql = new Mysql();
        PreparedStatement ps = null;
        try {
            String orden = "update usuarios set int_fallido=?,bloqueo=? where usuario=?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setInt(1, fallido);
            ps.setBoolean(2, b);
            ps.setString(3, usuario);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
    }

    public ResultSet seleccionarIntFallido(String u) {
        Mysql sql = new Mysql();
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            String orden = "select int_Fallido,bloqueo from usuarios where usuario = ?";
            ps = sql.conectar().prepareStatement(orden);
            ps.setString(1, u);
            rs = ps.executeQuery();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos", "ERROR", 0);
        }
        return rs;
    }
}
